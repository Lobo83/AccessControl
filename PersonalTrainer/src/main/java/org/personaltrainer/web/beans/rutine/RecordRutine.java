package org.personaltrainer.web.beans.rutine;

import java.math.BigDecimal;

public class RecordRutine {
	private int idRecordRutine;
	private String duration;
	private String exerciseMeasure;
	private String seriesNumber;
	private String order;
	private String weight;
	private int idExercise;
	private int lineNumber;
	
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public int getIdRutine() {
		return idRutine;
	}
	public void setIdRutine(int idRutine) {
		this.idRutine = idRutine;
	}
	public String getExercisesName() {
		return exercisesName;
	}
	public void setExercisesName(String exercisesName) {
		this.exercisesName = exercisesName;
	}
	private int idRutine;
	private String exercisesName;
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSeriesNumber() {
		return seriesNumber;
	}
	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}
	public int getIdRecordRutine() {
		return idRecordRutine;
	}
	public void setIdRecordRutine(int idRecordRutine) {
		this.idRecordRutine = idRecordRutine;
	}
	
	
	public int getIdExercise() {
		return idExercise;
	}
	public void setIdExercise(int idExercise) {
		this.idExercise = idExercise;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getExerciseMeasure() {
		return exerciseMeasure;
	}
	public void setExerciseMeasure(String exerciseMeasure) {
		this.exerciseMeasure = exerciseMeasure;
	}
	
}
