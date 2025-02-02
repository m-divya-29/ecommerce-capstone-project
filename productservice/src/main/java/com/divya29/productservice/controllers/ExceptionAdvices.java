package com.divya29.productservice.controllers;

import org.example.productservice.exceptions.ExceptionResponseDto;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvices {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponseDto> exception(Exception exception) {
		ExceptionResponseDto errorResponseDto = new ExceptionResponseDto();
		errorResponseDto.setMessage(exception.getMessage());

		return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler({ RuntimeException.class, Exception.class })
	public ResponseEntity<ExceptionResponseDto> handleGenericException(Exception exception) {
		ExceptionResponseDto errorResponseDto = new ExceptionResponseDto();
		errorResponseDto.setMessage("An unexpected error occurred: " + exception.getMessage());
		return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
