package com.divya29.paymentservice.exceptions;

public class ResponseStatusException extends Exception {
	private static final long serialVersionUID = -3034461969152585662L;

	public ResponseStatusException(String message) {
		super(message);
	}
}
