package org.personaltrainer.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.personaltrainer.business.StatisticHelper;
import org.personaltrainer.web.beans.rutine.RutineReportConfigurationBean;
import org.personaltrainer.web.beans.user.StatisticFormBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Controller
@SessionAttributes({ "profile", "user", "idUser" })
public class StatisticController {
	private static final Log log = LogFactory.getLog(StatisticController.class);
	private static final int X_AXXIS=0;
	private static final int Y_AXXIS=1;
	private static final int TOP_REPORT=2;
	private Map<String, String> bioMeasures=null;
	private Map<String, Object[]> reportConfiguration=null;
	private Map<String, String>exerciseMeasures=null;
	@RequestMapping(value="/UserDataStatisticInput", method=RequestMethod.GET)
	public String initUserStatisticForm(StatisticFormBean statisticForm,BindingResult result,
			Model model, HttpSession session){
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
		
		generateBioMeasureList();
		model.addAttribute("userMeasuresList",bioMeasures);
		statisticForm=new StatisticFormBean();
		statisticForm.setMeasure("NONE");
		model.addAttribute("statisticForm",statisticForm);
							
		return "UserDataStatisticInput";
	}
	private void generateBioMeasureList(){
		if(bioMeasures!=null){
			return ;
		}
		bioMeasures = new LinkedHashMap<String,String> ();
		bioMeasures.put("weight", "Peso");
		bioMeasures.put("height", "Altura");
		bioMeasures.put("belly", "Cintura");
		bioMeasures.put("hip", "Cadera");
		bioMeasures.put("chest", "Peso");
		bioMeasures.put("imc", "IMC");
		bioMeasures.put("igc", "IGC");
		bioMeasures.put("mcm", "MCM");
		bioMeasures.put("mb", "MB");
		bioMeasures.put("baseHeartRate", "Frecuencia Cardiaca en reposo");
		bioMeasures.put("bellyHeightRate", "Cintura/Altura");
		bioMeasures.put("maxHeartRate", "Frecuencia Cardiaca máxima");
		bioMeasures.put("heartRatea", "Freq. Card. Grupo A");
		bioMeasures.put("heartRateb", "Freq. Card. Grupo B");
		bioMeasures.put("heartRatec", "Freq. Card. Grupo C");
		bioMeasures.put("heartRated", "Freq. Card. Grupo D");
		bioMeasures.put("heartRatee", "Freq. Card. Grupo E");
		
	}
	private void generateReportConfiguration(){
		
		if(reportConfiguration!=null){
			return ;
		}
		reportConfiguration= new HashMap<String,Object[]> ();
		
		reportConfiguration.put("weight", new Object[]{"Fecha de cambio","Kg", "Peso"});
		reportConfiguration.put("height", new Object[]{"Fecha de cambio","Cm", "Altura"});
		reportConfiguration.put("belly", new Object[]{"Fecha de cambio","Cm", "Cintura"});
		reportConfiguration.put("hip", new Object[]{"Fecha de cambio","Cm", "Cadera"});
		reportConfiguration.put("chest", new Object[]{"Fecha de cambio","Cm", "Pecho"});
		reportConfiguration.put("imc", new Object[]{"Fecha de cambio","Kg/Altura^2", "Indice Masa Corporal"});
		reportConfiguration.put("igc", new Object[]{"Fecha de cambio","%Grasa", "Indice de Grasa Corporal"});
		reportConfiguration.put("mcm", new Object[]{"Fecha de cambio","KG", "Materia Magra"});
		reportConfiguration.put("mb", new Object[]{"Fecha de cambio","Calorías", "Metabolismo Basal"});
		reportConfiguration.put("baseHeartRate", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Frecuencia Cardiaca en Reposo"});
		reportConfiguration.put("bellyHeightRate", new Object[]{"Fecha de cambio","Cintura/Altura", "Cociente Cintura/Altura"});
		reportConfiguration.put("maxHeartRate", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Frecuencia Cardiaca Máxima"});
		reportConfiguration.put("heartRatea", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Esfuerzo tipo A"});
		reportConfiguration.put("heartRateb", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Esfuerzo tipo B"});
		reportConfiguration.put("heartRatec", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Esfuerzo tipo C"});
		reportConfiguration.put("heartRated", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Esfuerzo tipo D"});
		reportConfiguration.put("heartRatee", new Object[]{"Fecha de cambio","Pulsaciones/Minuto", "Esfuerzo tipo E"});
		
	}
	@RequestMapping(value="/UserDataStatistic", method=RequestMethod.POST)
	public String createUserReport(StatisticFormBean statisticForm,BindingResult result,
			Model model, HttpSession session){
		
		
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		Integer idUser = (Integer) session.getAttribute("idUser");
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
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		generateReportConfiguration();
		StatisticHelper statisticHelper=(StatisticHelper) ctx.getBean("statisticHelper");
		String measure = statisticForm.getMeasure();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<Object> reportData=statisticHelper.getUserMeasureStastic(measure, idUser.intValue());
		for(Object record:reportData){
			Object[] data=(Object[]) record;
			BigDecimal value = (BigDecimal)data[0];
			dataset.setValue(value, "Informe Usuario", data[1].toString());
		}
		model.addAttribute("dataSet", dataset);
		Object[] configuration=reportConfiguration.get(measure);
		model.addAttribute("xAxxis",configuration[X_AXXIS]);
		model.addAttribute("yAxxis",configuration[Y_AXXIS]);
		model.addAttribute("topReport",configuration[TOP_REPORT]);
		return "UserDataStatistics";
	}
	@RequestMapping(value="/UserRutinesStatisticInput", method=RequestMethod.GET)
	public String initRutinesStatisticForm(StatisticFormBean statisticForm,BindingResult result,
			Model model, HttpSession session){
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		Integer idUser=(Integer) session.getAttribute("idUser");
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
		
		//obtener el listado de rutinas de usuario
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		
		StatisticHelper statisticHelper=(StatisticHelper) ctx.getBean("statisticHelper");
		RutineReportConfigurationBean reportConfiguration = statisticHelper.getUserRutinesReportConfiguration(idUser);
		statisticForm=new StatisticFormBean();
		exerciseMeasures=statisticHelper.getExersicesMeasure();
		//cargarlo todo como atributo de modelo
		model.addAttribute("userRutinesList", reportConfiguration.getRutine());
		model.addAttribute("userExercisesList", reportConfiguration.getRutineExercises());
		model.addAttribute("statisticForm",statisticForm);
		model.addAttribute("measureList",exerciseMeasures);
		return "UserRutinesStatisticInput";
	}
	
	@RequestMapping(value="/RutineDataStatitic", method=RequestMethod.POST)
	public String createRutineReport(StatisticFormBean statisticForm,BindingResult result,
			Model model, HttpSession session){
		
		
		String user = (String) session.getAttribute("user");
		String profile = (String) session.getAttribute("profile");
		Integer idUser = (Integer) session.getAttribute("idUser");
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
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());
		StatisticHelper statisticHelper=(StatisticHelper) ctx.getBean("statisticHelper");
		Integer idExerciseRutine=new Integer(statisticForm.getIdExercise()+"");
		String measure=statisticForm.getMeasure();
		List<Object> reportData=statisticHelper.getUserRutinesStastic(measure, idUser.intValue(),idExerciseRutine.intValue());
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object record:reportData){
			Object[] data=(Object[]) record;
			if(measure.equals("weight")){
				BigDecimal value = (BigDecimal)data[0];
				dataset.setValue(value, "Informe Usuario", data[1].toString());
			}else{
				Integer value = (Integer)data[0];
				dataset.setValue(value, "Informe Usuario", data[1].toString());
			}
			
		}
		model.addAttribute("dataSet", dataset);

		model.addAttribute("xAxxis","Fecha");
		model.addAttribute("yAxxis",this.exerciseMeasures.get(measure));
		model.addAttribute("topReport","Estadisticas de Rutinas");
		return "UserDataStatistics";
		
	}
}
