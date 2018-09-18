package com.anvisa.rest.detalhe.comestico.registrado.detalhe.apresentacao;

import java.util.ArrayList;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;

public class DetalheCosmeticoRegistradoApresentacao {


     private String nomeProduto;  
     private String processo;
     private String apresentacao;
     private String categoria;
     private ArrayList<RegistradoApresentacaoFabricantesNacionais> fabricantesNacionais;
     private String formaFisica;
     private String tonalidade;
     private String prazoValidade;
     private ArrayList<String> conservacao;
     private ArrayList<String> destinacao;
     private ArrayList<String> restricao;
     
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getApresentacao() {
		return apresentacao;
	}
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public ArrayList<RegistradoApresentacaoFabricantesNacionais> getFabricantesNacionais() {
		return fabricantesNacionais;
	}
	public void setFabricantesNacionais(
			ArrayList<RegistradoApresentacaoFabricantesNacionais> fabricantesNacionais) {
		this.fabricantesNacionais = fabricantesNacionais;
	}
	public String getFormaFisica() {
		return formaFisica;
	}
	public void setFormaFisica(String formaFisica) {
		this.formaFisica = formaFisica;
	}
	public String getTonalidade() {
		return tonalidade;
	}
	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}
	public String getPrazoValidade() {
		return prazoValidade;
	}
	public void setPrazoValidade(String prazoValidade) {
		this.prazoValidade = prazoValidade;
	}
	public ArrayList<String> getConservacao() {
		return conservacao;
	}
	public void setConservacao(ArrayList<String> conservacao) {
		this.conservacao = conservacao;
	}
	public ArrayList<String> getRestricao() {
		return restricao;
	}
	public void setRestricao(ArrayList<String> restricao) {
		this.restricao = restricao;
	}

	public ArrayList<String> getDestinacao() {
		return destinacao;
	}
	public void setDestinacao(ArrayList<String> destinacao) {
		this.destinacao = destinacao;
	}
	public void load(JsonNode node,String attribute) {
		
		JsonNode element = node.findValue(attribute);
		
		if (element != null) {
			
			this.setNomeProduto(JsonToObject.getValue(element, "nomeProduto"));
			this.setProcesso(JsonToObject.getValue(element, "processo"));
			this.setApresentacao(JsonToObject.getValue(element, "apresentacao","embalagemPrimaria"));
			this.setCategoria(JsonToObject.getValue(element, "categoria"));
			
			JsonNode elementLocalFabricacao = node.findValue(attribute);
			
			if (elementLocalFabricacao != null) {
				
				RegistradoApresentacaoFabricantesNacionais fabricante = new RegistradoApresentacaoFabricantesNacionais();
				fabricante.setCnpj(JsonToObject.getValue(elementLocalFabricacao, "cnpj"));
				fabricante.setRazaoSocial(JsonToObject.getValue(elementLocalFabricacao, "razaoSocial"));
				fabricante.setCidade(JsonToObject.getValue(elementLocalFabricacao, "cidade"));
				fabricante.setUf(JsonToObject.getValue(elementLocalFabricacao, "uf"));
				fabricante.setPais(JsonToObject.getValue(elementLocalFabricacao, "pais"));
				fabricante.setTipo(JsonToObject.getValue(elementLocalFabricacao, "tipo"));
				
			}
			
			this.setFormaFisica(JsonToObject.getValue(element, "formaFisica"));
			this.setTonalidade(JsonToObject.getValue(element, "formaFisica"));
			
			this.setApresentacao(JsonToObject.getValue(element, "apresentacao","tonalidade"));
			
			this.setPrazoValidade(JsonToObject.getValue(element,"prazoValidade"));
			
			this.setConservacao(JsonToObject.getArrayStringValue(element,"conservacao"));
			this.setDestinacao(JsonToObject.getArrayStringValue(element,"destinacao"));
			this.setRestricao(JsonToObject.getArrayStringValue(element,"restricao"));
			
		}
		
		
		
	}
     
}
