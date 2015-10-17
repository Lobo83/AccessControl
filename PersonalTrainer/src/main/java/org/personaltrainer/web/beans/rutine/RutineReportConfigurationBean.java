package org.personaltrainer.web.beans.rutine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RutineReportConfigurationBean {
	 private Map<Integer, String> rutine;
	 private List<RecordRutine> rutineExercises;
	 
	 
	 
	public Map<Integer, String> getRutine() {
		return rutine;
	}
	public void setRutine(Map<Integer, String> rutine) {
		this.rutine = rutine;
	}
	public List<RecordRutine> getRutineExercises() {
		return rutineExercises;
	}
	public void setRutineExercises(
			List<RecordRutine> rutineExercises) {
		this.rutineExercises = rutineExercises;
	}
}
