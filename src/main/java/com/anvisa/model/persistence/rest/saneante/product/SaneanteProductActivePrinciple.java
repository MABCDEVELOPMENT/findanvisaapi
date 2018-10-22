package com.anvisa.model.persistence.rest.saneante.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "saneant_product_active_psrinciple")
public class SaneanteProductActivePrinciple extends SaneanteStringListGeneric {

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

	public SaneanteProductActivePrinciple(String valor) {
		super();
		this.valor = valor;
	}
	
	public SaneanteProductActivePrinciple() {
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
		if (!(obj instanceof SaneanteProductActivePrinciple)) {
			return false;
		}
		SaneanteProductActivePrinciple other = (SaneanteProductActivePrinciple) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	
}
