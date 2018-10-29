package com.anvisa.model.persistence.mongodb.cosmetic.register.presentation;

import com.anvisa.model.persistence.rest.IStringListGeneric;
import com.fasterxml.jackson.annotation.JsonAlias;


public class PresentationDestination implements IStringListGeneric {


	@JsonAlias(value = "valor")
	public String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public PresentationDestination(String valor) {
		super();
		this.valor = valor;
	}
	
	public PresentationDestination() {
		// TODO Auto-generated constructor stub
	}
}
