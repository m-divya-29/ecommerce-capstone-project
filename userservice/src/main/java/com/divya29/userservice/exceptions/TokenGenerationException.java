package com.divya29.userservice.exceptions;

public class TokenGenerationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public TokenGenerationException(String message) {
        super(message);
    }
}
