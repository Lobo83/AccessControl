package org.personaltrainer.web.beans.rutine;

import java.util.List;

public class TableRutine {
	private List<RecordRutine> table;
	private int idUser;
	private String rutineName;
	private String rutineDescription;
	private int idRutine;

	public int getIdRutine() {
		return idRutine;
	}

	public void setIdRutine(int idRutine) {
		this.idRutine = idRutine;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getRutineName() {
		return rutineName;
	}

	public void setRutineName(String rutineName) {
		this.rutineName = rutineName;
	}

	public String getRutineDescription() {
		return rutineDescription;
	}

	public void setRutineDescription(String rutineDescription) {
		this.rutineDescription = rutineDescription;
	}

	public List<RecordRutine> getTable() {
		return table;
	}

	public void setTable(List<RecordRutine> table) {
		this.table = table;
	}
}
