package com.divya29.paymentservice.exceptions;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 7239440567769093717L;

	public NotFoundException(String message) {
		super(message);
	}
}
