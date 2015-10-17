package org.personaltrainer.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.personaltrainer.business.UserHelper;
import org.personaltrainer.web.beans.user.UserDataBean;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({ "profile", "user","idUser" })
public class AddUserData {
	Log log = LogFactory.getLog(AddUserData.class);
	@RequestMapping(value="/UserPage" , method = RequestMethod.GET)
	public ModelAndView UserPage(ModelMap model, HttpSession session) {//probar a utilizar model en vez de modelView
		ModelAndView modelView = new ModelAndView("UserPage");
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		if ("".equals(user) || "".equals(profile) || null==user || null==profile) {
			
			ModelAndView loginPage = new ModelAndView("index", "errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return loginPage;
		}
		//initializing form
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		UserDataBean userData = ((UserHelper)ctx.getBean("userHelper")).getUserDataByLogin(profile, user);

		if(userData==null){
			return modelView;
		}
		
		return modelView.addAllObjects(getUserDataMap(userData));
	}

	@RequestMapping(value = "/addUserData", method = RequestMethod.POST)
	public ModelAndView addUserData(HttpServletRequest request,
			HttpSession session, UserDataBean usuario, BindingResult result) {
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		ModelAndView modelView = new ModelAndView("UserPage");
		if ("".equals(user) || "".equals(profile) || null==user || null==profile) {
			ModelAndView loginPage = new ModelAndView("index", "errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return loginPage;
		}

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());

		try{
			
			if(validateFormUserData(usuario)){
				((UserHelper)ctx.getBean("userHelper")).saveUserData(profile, usuario);
			}else{
				modelView.addObject("errorUserData", "Error, algunos campos introducidos tienen valor incorrecto");
			}
		}catch(DataAccessException e){
				log.error("Error accediendo al sistema",e);
				ModelAndView userPage = new ModelAndView("UserPage", "errorUserData",
						"Error: No se encuentran los datos del usuario "+user);
				userPage.addObject("errorUserData", "Error insertando datos del usuario "+user);
			}
		
			modelView.addAllObjects(getUserDataMap(usuario));

		return modelView;

	}
	private boolean validateFormUserData(UserDataBean usuario) {
		// TODO Auto-generated method stub
		boolean validated=true;
		try{
			Long.valueOf(usuario.getAge());
			//Si el usuario introduce un valor no numerico en un campo que se mapea a BigDecimal, el campo queda seteado a null
			if(null==usuario.getBelly()){
				validated=false;
			}
			if(null==usuario.getChest()){
				validated=false;
			}
			if(null==usuario.getHeight()){
				validated=false;
			}
			if(null==usuario.getHip()){
				validated=false;
			}
			if(null==usuario.getWeight()){
				validated=false;
			}
		}catch(NumberFormatException e){
			log.error("Error, campo no numérico ",e);
			validated=false;
		}
		return validated;
	}

	private Map<String,Object> getUserDataMap(UserDataBean usuario){
		Map<String,Object> map=new HashMap<String,Object>();

		map.put("user", usuario.getLogin());
		map.put("profile", usuario.getProfile());
		map.put("idUserData", usuario.getIdUserData());
		map.put("name", usuario.getName());
		map.put("email", usuario.getEmail());
		map.put("belly", usuario.getBelly());
		map.put("imc", usuario.getImc());
		map.put("mbasal", usuario.getMbasal());
		map.put("heartrate", usuario.getHeartrate());
		map.put("maxheartrate", usuario.getMaxheartrate());
		map.put("heartrateA", usuario.getHeartrateA());
		map.put("heartrateB", usuario.getHeartrateB());
		map.put("heartrateC", usuario.getHeartrateC());
		map.put("heartrateD", usuario.getHeartrateD());
		map.put("heartrateE", usuario.getHeartrateE());
		map.put("age", usuario.getAge());
		map.put("weight", usuario.getWeight());
		map.put("hip", usuario.getHip());
		map.put("IGC", usuario.getIGC());
		map.put("sex", usuario.getSex());
		map.put("height", usuario.getHeight());
		map.put("chest", usuario.getChest());
		map.put("ICA", usuario.getICA());
		map.put("mcm", usuario.getMcm());
		return map;
		
	}

}

/*
 * @SuppressWarnings("deprecation") public class DoLogin extends
 * SimpleFormController {
 * 
 * public DoLogin(){ setCommandClass(UserLoginBean.class);
 * setCommandName("userLoginBean"); }
 * 
 * @Override protected ModelAndView onSubmit(HttpServletRequest request,
 * HttpServletResponse response, Object command, BindException errors) throws
 * Exception { // TODO Auto-generated method stub
 * 
 * UserLoginBean usuario=(UserLoginBean)command; String user =
 * usuario.getLogin();
 * 
 * //wellcomePage.addObject(); String
 * profile=((AccessControlLogic)this.getApplicationContext
 * ().getBean("logicaAcceso")).doLoginAndGetProfile(usuario.getLogin(),
 * usuario.getPassword()); if(!"".equals(profile)){ ModelAndView wellcomePage =
 * new ModelAndView("WelcomeUser","user", user); HttpSession session =
 * request.getSession(true); session.setAttribute("profile", profile);
 * 
 * return wellcomePage; }else{//hay que devolver a página de inicio indicando
 * que las credenciales son erroneas ModelAndView loginPage = new
 * ModelAndView("index","errorLogin","Error: Usuario incorrecto"); return
 * loginPage; } }
 * 
 * 
 * @Override protected Object formBackingObject(HttpServletRequest request)
 * throws Exception { UserLoginBean user = new UserLoginBean();
 * user.setLogin("hola"); return user; }
 * 
 * 
 * }
 */

