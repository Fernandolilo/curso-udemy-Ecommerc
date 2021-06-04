package com.systempro.services.exceptions;

public class AuthorizationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	// sobre carga com uma outra excessão para uma causa do possivel erro.
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
