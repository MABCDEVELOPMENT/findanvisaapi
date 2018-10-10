package com.anvisa.model.persistence.rest.cosmetic.register.presentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_presentation_conservation")
public class PresentationConservation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "value", length = 200)
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
