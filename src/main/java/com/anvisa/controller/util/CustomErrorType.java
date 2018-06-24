package com.anvisa.controller.util;

public class CustomErrorType extends Exception {

	private String errorMessage;
	private int id;

	public CustomErrorType(int id, String errorMessage) {
		this.id = id;
		this.errorMessage = errorMessage;
	}

	public CustomErrorType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getId() {
		return id;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}