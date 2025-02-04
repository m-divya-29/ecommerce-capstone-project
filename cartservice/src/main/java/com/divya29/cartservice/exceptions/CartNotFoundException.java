package com.divya29.cartservice.exceptions;

public class CartNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6577060347713891168L;

	public CartNotFoundException(String message) {
		super(message);
	}
}