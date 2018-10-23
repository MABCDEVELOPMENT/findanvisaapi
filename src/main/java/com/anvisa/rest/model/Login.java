package com.anvisa.rest.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Login {

	@JsonAlias(value = "userName")
	private String userName;

	@JsonAlias(value = "password")
	private String password;

	@JsonAlias(value = "email")
	private String email;
	
	@JsonAlias(value = "token")	
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
