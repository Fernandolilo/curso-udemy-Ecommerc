package com.systempro.services.exceptions;

public class FileException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg);
	}
	
	// sobre carga com uma outra excessão para uma causa do possivel erro.
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
