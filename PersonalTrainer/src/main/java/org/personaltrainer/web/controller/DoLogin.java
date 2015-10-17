package org.personaltrainer.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.accessControl.persistence.exception.CannotAccessException;
import org.accessControl.persistence.logic.AccessControlLogic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.personaltrainer.business.UserHelper;
import org.personaltrainer.web.beans.user.UserDataBean;
import org.personaltrainer.web.beans.user.UserLoginBean;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({ "profile", "user", "idUser" })
public class DoLogin {
	private static final Log log = LogFactory.getLog(DoLogin.class);

	private static final String USER_PROFILE="ACPROFILE2";
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			UserLoginBean usuario, BindingResult result) {
		String user = usuario.getLogin();
		String password=usuario.getPassword();
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		String action = request.getParameter("buttonLogin");
		
		boolean login;
		if(action.equals("Entrar")){
			login=true;
		}else{
			login=false;
		}
		
		ModelAndView loginPage=null;
		if (login) {
			String profile = ((AccessControlLogic) ctx.getBean("logicaAcceso"))
					.doLoginAndGetProfile(user,
							password);
			UserDataBean userData = ((UserHelper) ctx.getBean("userHelper"))
					.getUserDataByLogin(profile, user);
			if (!"".equals(profile)) {
				ModelAndView wellcomePage = new ModelAndView("WelcomeUser",
						"user", user);
				 session = request.getSession(true);
				 session.setAttribute("profile", profile);
				 session.setAttribute("user", user);
				 session.setAttribute("idUser",userData.getIdUserData());
				//wellcomePage.addObject("profile", profile);
				//wellcomePage.addObject("idUser", userData.getIdUserData());
				
				return wellcomePage;
			} else {// hay que devolver a página de inicio indicando que las
					// credenciales son erroneas
				 loginPage = new ModelAndView("index",
						"errorLogin", "Error: Usuario incorrecto");
				return loginPage;
			}
		} else {
			 try {
				((AccessControlLogic) ctx.getBean("logicaAcceso")).createUserAccount(user, password);
				((UserHelper) ctx.getBean("userHelper")).createDefaultUserData(USER_PROFILE, user);
				 loginPage = new ModelAndView("index",
						"errorLogin", "Usuario Creado correctamente");
				 
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				log.error("Error obteniendo bean logicaAcceso",e);
				loginPage = new ModelAndView("index",
						"errorLogin", e.getMessage());
				
			} catch (CannotAccessException e) {
				// TODO Auto-generated catch block
				log.error("Error intentando acceder al sistema",e);
				 loginPage = new ModelAndView("index",
						"errorLogin", e.getMessage());
				
			}
			 return loginPage;
		}

	}

	

}
