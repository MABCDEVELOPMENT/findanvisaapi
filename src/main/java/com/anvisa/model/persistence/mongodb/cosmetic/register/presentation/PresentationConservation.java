package com.anvisa.model.persistence.mongodb.cosmetic.register.presentation;

import com.fasterxml.jackson.annotation.JsonAlias;


public class PresentationConservation {


	@JsonAlias(value = "valor")
	public String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public PresentationConservation(String valor) {
		super();
		this.valor = valor;
	}
	
	public PresentationConservation() {
		// TODO Auto-generated constructor stub
	}
	

}
