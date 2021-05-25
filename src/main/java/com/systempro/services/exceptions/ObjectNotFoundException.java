package com.systempro.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	// sobre carga com uma outra excessão para uma causa do possivel erro.
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
