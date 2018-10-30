package com.anvisa.model.persistence.mongodb.saneante.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;


public class SaneanteProductManufactureNational {
	
	
	@JsonAlias(value = "fabricante")
	private String fabricante;
	
	@JsonAlias(value = "pais")
	private String pais;
	
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
