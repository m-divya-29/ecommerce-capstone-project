package com.divya29.cartservice.exceptions;

public class JwtAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 1213119517062527022L;

	public JwtAuthenticationException(String message) {
		super(message);
	}
}
