package com.anvisa.model.persistence.mongodb.cosmetic.register.presentation;

import com.fasterxml.jackson.annotation.JsonAlias;


public class PresentationRestriction {

	@JsonAlias(value = "valor")
	public String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public PresentationRestriction(String valor) {
		super();
		this.valor = valor;
	}
	
	public PresentationRestriction() {
		// TODO Auto-generated constructor stub
	}

}
