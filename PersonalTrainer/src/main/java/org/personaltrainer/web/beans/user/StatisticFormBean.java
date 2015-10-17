package org.personaltrainer.web.beans.user;

public class StatisticFormBean {
	private String measure;
	private String dateFrom;
	private String dateTo;
	private long idRutine;
	private long idExercise;
	
	public long getIdRutine() {
		return idRutine;
	}

	public void setIdRutine(long idRutine) {
		this.idRutine = idRutine;
	}

	public long getIdExercise() {
		return idExercise;
	}

	public void setIdExercise(long idExercise) {
		this.idExercise = idExercise;
	}

	

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
}
