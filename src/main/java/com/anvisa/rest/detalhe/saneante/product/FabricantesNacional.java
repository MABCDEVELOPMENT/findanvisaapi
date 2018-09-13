package com.anvisa.rest.detalhe.saneante.product;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;

public class FabricantesNacional {
	  
	private String fabricante;
	private String pais;
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
