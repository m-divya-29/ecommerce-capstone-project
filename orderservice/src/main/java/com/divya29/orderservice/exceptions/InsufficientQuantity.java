package com.divya29.orderservice.exceptions;

public class InsufficientQuantity extends Exception {
	private static final long serialVersionUID = 1L;

	public InsufficientQuantity(String message) {
		super(message);
	}

}
