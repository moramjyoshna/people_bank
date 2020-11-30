package com.bank.people.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception {

	private static final long serialVersionUID = 1L;

	@ExceptionHandler(InfoException.class)
	public ResponseEntity<ErrorResponse> infoExistException(InfoException e, WebRequest request) {

		ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(error, HttpStatus.OK);
	}
	
	@ExceptionHandler(BeneficiaryException.class)
	public ResponseEntity<ErrorResponse> BeneficiaryException(BeneficiaryException e, WebRequest request) {

		ErrorResponse error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(error, HttpStatus.OK);
	}
}
