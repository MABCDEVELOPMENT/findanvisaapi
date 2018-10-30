package com.anvisa.model.persistence.mongodb.saneante.product;

import com.fasterxml.jackson.annotation.JsonAlias;


public class SaneanteProductConservation {

	@JsonAlias(value = "valor")
	public String valor;


	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public SaneanteProductConservation(String valor) {
		super();
		this.valor = valor;
	}
	
	public SaneanteProductConservation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SaneanteProductConservation)) {
			return false;
		}
		SaneanteProductConservation other = (SaneanteProductConservation) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	
}

