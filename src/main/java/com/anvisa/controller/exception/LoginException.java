package com.anvisa.controller.exception;

public class LoginException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {

		return errorMessage;

	}

	public LoginException(String errorMessage) {

		super(errorMessage);

		this.errorMessage = errorMessage;

	}

	public LoginException() {

		super();

	}

}
