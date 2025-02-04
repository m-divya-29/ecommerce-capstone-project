package com.divya29.orderservice.exceptions;

public class ResponseStatusException extends Exception {
	private static final long serialVersionUID = 1L;

	public ResponseStatusException(String message) {
		super(message);
	}
}
