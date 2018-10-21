package com.anvisa.model.persistence.rest.saneante.product;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;

public class SaneanteProductPrimaryPackage {
	private String tipo;
	private String observacao;

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
}
