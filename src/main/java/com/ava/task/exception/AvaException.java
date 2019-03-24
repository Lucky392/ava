package com.ava.task.exception;

public class AvaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8119334778882665329L;
	
	public AvaException() {
	}
	
	public AvaException(String message) {
		super(message);
	}
	
	public AvaException(String message, Throwable exc) {
		super(message, exc);
	}
	
}
