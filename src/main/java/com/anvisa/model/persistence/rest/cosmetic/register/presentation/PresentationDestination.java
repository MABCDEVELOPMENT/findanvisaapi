package com.anvisa.model.persistence.rest.cosmetic.register.presentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.IStringListGeneric;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_presentation_destination")
public class PresentationDestination extends BaseEntity implements IStringListGeneric {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "value", length = 600)
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
