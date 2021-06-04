package com.systempro.services.exceptions;

public class AutorizationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AutorizationException(String msg) {
		super(msg);
	}
	
	// sobre carga com uma outra excessão para uma causa do possivel erro.
	public AutorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
