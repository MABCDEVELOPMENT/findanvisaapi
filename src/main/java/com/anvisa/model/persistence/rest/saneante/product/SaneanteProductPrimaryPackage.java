package com.anvisa.model.persistence.rest.saneante.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
@Table(name = "saneante_product_primary_package")
public class SaneanteProductPrimaryPackage extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "type")
	@JsonAlias(value = "tipo")
	private String tipo;
	
	@Column(name = "note")
	@JsonAlias(value = "observacao")
	private String observacao;
	
	public SaneanteProductPrimaryPackage() {
		// TODO Auto-generated constructor stub
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public void load(JsonNode node,String attribute) {
		this.setTipo(JsonToObject.getValue(node, attribute, "tipo"));
		this.setObservacao(JsonToObject.getValue(node, attribute, "observacao"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof SaneanteProductPrimaryPackage)) {
			return false;
		}
		SaneanteProductPrimaryPackage other = (SaneanteProductPrimaryPackage) obj;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	
}
