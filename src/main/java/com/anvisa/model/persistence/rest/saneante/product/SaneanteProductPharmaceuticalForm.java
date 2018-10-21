package com.anvisa.model.persistence.rest.saneante.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.IStringListGeneric;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "saneant_product_pharmaceutical_Form")
public class SaneanteProductPharmaceuticalForm extends BaseEntity implements IStringListGeneric {

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

	public SaneanteProductPharmaceuticalForm(String valor) {
		super();
		this.valor = valor;
	}
	
	public SaneanteProductPharmaceuticalForm() {
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
		if (!(obj instanceof SaneanteProductPharmaceuticalForm)) {
			return false;
		}
		SaneanteProductPharmaceuticalForm other = (SaneanteProductPharmaceuticalForm) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	
}
