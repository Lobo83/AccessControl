package org.personaltrainer.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.personaltrainer.business.UserRutinesHelper;
import org.personaltrainer.web.beans.rutine.RecordRutine;
import org.personaltrainer.web.beans.rutine.RutineList;
import org.personaltrainer.web.beans.rutine.TableRutine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

@Controller
@SessionAttributes({ "profile", "user", "idUser" })
public class RutineManager {

	private static final Log log = LogFactory.getLog(RutineManager.class);
	private Map<String, String> exercise;
	private Map<String, String> measure;

	@RequestMapping(value = "/UserRutinesPage", method = RequestMethod.GET)
	public String initForm(TableRutine rutineTable, BindingResult result,
			Model model, // ojo, el orden de los parámetros tiene que ser este.
							// Si no Spring da fallos
			HttpSession session) {
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");

		if ("".equals(user) || "".equals(profile) || null == user
				|| null == profile) {

			model.addAttribute("errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return "index";
		}
		if (result.hasErrors()) {
			model.addAttribute("errorLogin",
					"Error: No se que carajo ha pasado");
			return "index";
		}
		// hay que verificar si el usuario tiene rutinas. Si las tiene
		// cargarlas, si no, se genera una tabla vacía.
		// Hay que arreglar este código para que lo haga
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		UserRutinesHelper rutineHelper = (UserRutinesHelper) ctx
				.getBean("userRutinesHelper");
		exercise = rutineHelper.getExercises();
		measure = rutineHelper.getMeasures();
		List<RecordRutine> lTable = new ArrayList<RecordRutine>();

		RecordRutine record = new RecordRutine();
		record.setSeriesNumber("0");
		record.setDuration("0");
		record.setOrder("1");
		record.setLineNumber(0);
		lTable.add(record);
		rutineTable = new TableRutine();
		rutineTable.setTable(lTable);

		model.addAttribute("exerciseList", exercise);
		model.addAttribute("measureList", measure);

		model.addAttribute("rutineTable", rutineTable);
		model.addAttribute("disable",false);
		// return modelView;

		return "UserRutinesPage";
	}

	@RequestMapping(value = "/manageRutine", method = RequestMethod.POST)
	// El valor debería ser addRow. Hay que ver como manejar varios submits
	public String loadRutinesUserPage(
			@ModelAttribute("rutineTable") TableRutine rutineTable,
			BindingResult result, Model model, ServletRequest request,
			HttpSession session) {

		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		if ("".equals(user) || "".equals(profile) || null == user
				|| null == profile) {

			model.addAttribute("errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return "index";
		}
		if (result.hasErrors()) {
			model.addAttribute("errorValidation", "Error en los datos introducidos. La rutina no se ha salvado");
			return "UserRutinesPage";
		}

		boolean save = WebUtils.hasSubmitParameter(request, "save");
		if (save) {
			WebApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(session
							.getServletContext());
			UserRutinesHelper rutineHelper = (UserRutinesHelper) ctx
					.getBean("userRutinesHelper");
			if(validateFormTable(rutineTable)){
				rutineHelper.saveRutine(rutineTable);
				rutineTable=rutineHelper.getRutineById(rutineTable.getIdRutine());
			}else{
				model.addAttribute("errorValidation", "Error en los datos introducidos. La rutina no se ha salvado");
			}
			
			model.addAttribute("rutineTable", rutineTable);
		} else {
			RecordRutine blanck = new RecordRutine();
			blanck.setDuration("0");
			blanck.setLineNumber(0);
			blanck.setSeriesNumber("0");
			blanck.setWeight("0");
			
			
			if(null==rutineTable.getTable()){//Esto ocurrirá cuando se hayan eliminado todas los ejercicios de la rutina
				rutineTable.setTable(new ArrayList<RecordRutine>());
				
			}
			blanck.setOrder(String.valueOf(rutineTable.getTable().size()+1));
			rutineTable.getTable().add(blanck);
			
			//Se resetean los atributos en la tabla de rutinas porque si no se pierden
			rutineTable.setRutineName(rutineTable.getRutineName());
			rutineTable.setRutineDescription(rutineTable.getRutineDescription());

			
		
			// model.addAttribute("exerciseTable",lTable);
			// modelView.addAllObjects(rutineMap);

		}
		model.addAttribute("exerciseList", exercise);
		model.addAttribute("measureList", measure);
		model.addAttribute("disable",false);
		// hacer model.asMap para recuperar atributos de modelo por alguna razon
		// me devuelve nulos. Esto se puede solverntar metiendolos en la firma y
		// añadiendo la anotación @ModelAttribute
		return "UserRutinesPage";
	}

	private boolean validateFormTable(TableRutine rutineTable) {
		// TODO Auto-generated method stub
		boolean validated=true;
		List<RecordRutine> listExercises=rutineTable.getTable();
		for(RecordRutine exercise:listExercises){
			if(null==exercise.getDuration()||exercise.getIdExercise()==-1||null==exercise.getSeriesNumber()||null==exercise.getWeight()||"NONE".equals(exercise.getExerciseMeasure())){
				validated=false;
				break;
			}
			try{
				Long.valueOf(exercise.getDuration());
				Long.valueOf(exercise.getSeriesNumber());
				Double.valueOf(exercise.getWeight());
			}catch(NumberFormatException e){
				log.error("Error, introducido valor no válido en los campos",e);
				validated=false;
				break;
			}
			
		}
		return validated;
	}

	@RequestMapping(value = "/UserRutinesList", method = RequestMethod.GET)
	public String getRutines(  Model model,
			HttpSession session) {

		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		Integer idUser = (Integer)session.getAttribute("idUser");
		if ("".equals(user) || "".equals(profile) || null == user
				|| null == profile) {

			model.addAttribute("errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return "index";
		}
//		if (result.hasErrors()) {
//			model.addAttribute("errorLogin",
//					"Error: No se que carajo ha pasado");
//			return "index";
//		}
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session
						.getServletContext());
		UserRutinesHelper rutineHelper = (UserRutinesHelper) ctx
				.getBean("userRutinesHelper");
		 RutineList list=rutineHelper.getRutineList(idUser);
		model.addAttribute("rutineList",list);

		
		return "UserRutinesList";
	}
	
	@RequestMapping(value = {"/editRutine"}, method=RequestMethod.GET)
	public String getUserRutine(  @RequestParam(value="idRutine")Integer idRutine,Model model, HttpSession session) {
	
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		
		
		if ("".equals(user) || "".equals(profile) || null == user
				|| null == profile) {

			model.addAttribute("errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return "index";
		}

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		UserRutinesHelper rutineHelper = (UserRutinesHelper) ctx
				.getBean("userRutinesHelper");
		exercise = rutineHelper.getExercises();
		measure = rutineHelper.getMeasures();
		
				
		TableRutine rutineTable = rutineHelper.getRutineById(idRutine);
		
		//
		model.addAttribute("exerciseList", exercise);
		model.addAttribute("measureList", measure);
		model.addAttribute("disable",true);

		model.addAttribute("rutineTable", rutineTable);
		// return modelView;

		return "UserRutinesPage";

	}
	@RequestMapping(value="/removeRutineExercise", method=RequestMethod.GET)
	public String removeRutineExercise(@RequestParam(value="idRecordRutine")Integer idRecordRutine,Model model, HttpSession session){
	//public String removeRutineExercise(@RequestParam(value="idRecordRutine")Integer idRecordRutine,Model model, HttpSession session){
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		
		
		if ("".equals(user) || "".equals(profile) || null == user
				|| null == profile) {

			model.addAttribute("errorLogin",
					"Error: No se ha identificado correctamente. Por Favor, autentifíquese");
			return "index";
		}

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		UserRutinesHelper rutineHelper = (UserRutinesHelper) ctx
				.getBean("userRutinesHelper");
		exercise = rutineHelper.getExercises();
		measure = rutineHelper.getMeasures();
		
		model.addAttribute("disable",true);
		TableRutine rutineTable=rutineTable=rutineHelper.removeRutineExercise(idRecordRutine);

		
		//
		model.addAttribute("exerciseList", exercise);
		model.addAttribute("measureList", measure);
		model.addAttribute("rutineTable", rutineTable);

		//model.addAttribute("rutineTable", rutineTable);
		// return modelView;

		return "redirect:editRutine?idRutine="+rutineTable.getIdRutine();
	}
}
