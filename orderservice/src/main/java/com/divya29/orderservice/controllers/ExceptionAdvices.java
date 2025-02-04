package com.divya29.orderservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.divya29.orderservice.exceptions.AuthenticationException;
import com.divya29.orderservice.exceptions.ErrorResponseDto;
import com.divya29.orderservice.exceptions.InsufficientQuantity;
import com.divya29.orderservice.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionAdvices {

	@ExceptionHandler({ NotFoundException.class, InsufficientQuantity.class })
	public ResponseEntity<ErrorResponseDto> exception(Exception exception) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setMessage(exception.getMessage());
		return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler({ RuntimeException.class, Exception.class })
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception exception) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setMessage("An unexpected error occurred: " + exception.getMessage());
		return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<ErrorResponseDto> handleAuthenticationException(Exception exception) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setMessage(exception.getMessage());
		return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
	}
}
