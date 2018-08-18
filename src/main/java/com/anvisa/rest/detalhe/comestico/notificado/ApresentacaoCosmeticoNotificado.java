package com.anvisa.rest.detalhe.comestico.notificado;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.rest.detalhe.comestico.registrado.ApresentacaoCosmeticoRegistrado;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ApresentacaoCosmeticoNotificado {
	
	String apresentacao;
    String tonalidade;
    String eans;
    boolean apenasExportacao;
    boolean rotuloAprovado;
    
	public String getApresentacao() {
		return apresentacao;
	}
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	public String getTonalidade() {
		return tonalidade;
	}
	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}
	public String getEans() {
		return eans;
	}
	public void setEans(String eans) {
		this.eans = eans;
	}
	public boolean isApenasExportacao() {
		return apenasExportacao;
	}
	public void setApenasExportacao(boolean apenasExportacao) {
		this.apenasExportacao = apenasExportacao;
	}
	public boolean isRotuloAprovado() {
		return rotuloAprovado;
	}
	public void setRotuloAprovado(boolean rotuloAprovado) {
		this.rotuloAprovado = rotuloAprovado;
	}
	
	

}
