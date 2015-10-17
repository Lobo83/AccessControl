package org.personaltrainer.business;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.personaltrainer.persistence.bean.ExerciseMeasure;
import org.personaltrainer.persistence.bean.ExerciseRutine;
import org.personaltrainer.persistence.bean.Rutine;
import org.personaltrainer.web.beans.rutine.RecordRutine;
import org.personaltrainer.web.beans.rutine.RutineReportConfigurationBean;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class StatisticHelper {
	private HibernateTemplate hibernateTemplate;
	private static final Log log=LogFactory.getLog(StatisticHelper.class);
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public List<Object> getUserMeasureStastic(String measure, int idUserData){
		List<Object>result=hibernateTemplate.find("select "+measure+", DATE_FORMAT(historic_date,'%d/%m/%y') from UserDataHistoric where userData.idUserData=?",idUserData);
		return result;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public List<Object> getUserRutinesStastic(String data,  int idUserData,int idUserRutineExercise){
		List<Object>result;
		if(data.equals("weight")){
			result=hibernateTemplate.find("select "+data+", DATE_FORMAT(change_date,'%d/%m/%y') from ExerciseRutineHistoric where rutine.userData.idUserData=? and exerciseRutine.idExerciseRutine=?",new Object[]{idUserData,idUserRutineExercise});
		}else{
			result=hibernateTemplate.find("select duration, DATE_FORMAT(change_date,'%d/%m/%y') from ExerciseRutineHistoric where rutine.userData.idUserData=? and exerciseRutine.idExerciseRutine=? and exerciseMeasure.codExerciseMeasureType=?",new Object[]{idUserData,idUserRutineExercise,data});
		}
		return result;
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public RutineReportConfigurationBean getUserRutinesReportConfiguration( int idUserData){
		RutineReportConfigurationBean result= new RutineReportConfigurationBean() ;
		 Map<Integer,String>rutine = new LinkedHashMap<Integer,String>();
		 List<RecordRutine>rutineExercises= new ArrayList<RecordRutine>();
		List<Rutine> list = hibernateTemplate.find(
				"from Rutine where userData.idUserData=?", idUserData);
		
		for(Rutine record:list){
			Integer idRutine=record.getIdRutine();
			String rutineName=record.getName();
			
			List<ExerciseRutine> exercises = hibernateTemplate.find("from ExerciseRutine where rutine.idRutine=? order by exerciseOrder asc",idRutine);
			
			for(ExerciseRutine exercise:exercises){
				RecordRutine configuration = new RecordRutine();
				configuration.setExercisesName(exercise.getExercise().getName());
				configuration.setIdRutine(idRutine);
				configuration.setIdRecordRutine(exercise.getIdExerciseRutine());
				rutineExercises.add(configuration);
			}
			rutine.put(idRutine, rutineName);
			
		}
		result.setRutine(rutine);
		result.setRutineExercises(rutineExercises);
		return result;
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, propagation = Propagation.REQUIRED)
	public Map<String,String> getExersicesMeasure(){
		Map<String,String> result=new LinkedHashMap<String,String>();
		List<ExerciseMeasure> data =hibernateTemplate.find("from ExerciseMeasure order by nameType asc");
		for(ExerciseMeasure record:data){
			result.put(record.getCodExerciseMeasureType(), record.getNameType());
		}
		//al resultado se le añade el peso para poder generar informes también sobre el peso utilizado en el ejercicio
		result.put("weight","Peso");
		return result;
	}
	
}
