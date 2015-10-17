package org.accessControl.persistence.exception;

public class CannotAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CannotAccessException(Throwable ex) {
		// TODO Auto-generated constructor stub
	}
	
	public CannotAccessException(Exception ex) {
		// TODO Auto-generated constructor stub
	}
	public CannotAccessException(String msg){
		super(msg);
	}


}
