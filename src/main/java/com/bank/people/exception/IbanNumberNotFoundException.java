package com.bank.people.exception;

public class IbanNumberNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public IbanNumberNotFoundException(String message) {
		super(message);
	}

}
