package com.anvisa.model.persistence.rest.saneante.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
@Table(name = "saneante_product_manufacture_national")
public class SaneanteProductManufactureNational extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "manufacturer")
	@JsonAlias(value = "fabricante")
	private String fabricante;
	
	@Column(name = "county", length = 20)
	@JsonAlias(value = "pais")
	private String pais;
	
	@Column(name = "city", length = 60)
	@JsonAlias(value = "cidade")
	private String cidade;

	public String getFabricante() {
		return this.fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public void load(JsonNode node) {
		this.setFabricante(JsonToObject.getValue(node,  "fabricante"));
		this.setPais(JsonToObject.getValue(node,  "pais"));
		this.setCidade(JsonToObject.getValue(node,  "cidade"));
	}

}
