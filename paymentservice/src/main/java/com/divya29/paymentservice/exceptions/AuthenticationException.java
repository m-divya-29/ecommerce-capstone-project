package com.divya29.paymentservice.exceptions;

public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 4627261210630685556L;

	public AuthenticationException(String message) {
		super(message);
	}
}
