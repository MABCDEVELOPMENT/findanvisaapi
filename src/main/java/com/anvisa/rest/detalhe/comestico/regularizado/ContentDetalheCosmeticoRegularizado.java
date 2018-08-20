package com.anvisa.rest.detalhe.comestico.regularizado;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.rest.detalhe.comestico.registrado.ApresentacaoCosmeticoRegistrado;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ContentDetalheCosmeticoRegularizado {
	
	 String processo;
	 String produto;
	 String tipo;
	 String situacao;
	 String data;
	 CaracterizacaoVigente caracterizacaoVigente;
	 EmpresaDetentora empresaDetentora;
	 String destinacoes;
	 LocalNacional locaisNacionais;
	 ArrayList<ApresentacaoCosmeticoRegularizado> apresentacoes;
	 
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public CaracterizacaoVigente getCaracterizacaoVigente() {
		return caracterizacaoVigente;
	}
	public void setCaracterizacaoVigente(CaracterizacaoVigente caracterizacaoVigente) {
		this.caracterizacaoVigente = caracterizacaoVigente;
	}
	public EmpresaDetentora getEmpresaDetentora() {
		return empresaDetentora;
	}
	public void setEmpresaDetentora(EmpresaDetentora empresaDetentora) {
		this.empresaDetentora = empresaDetentora;
	}
	public String getDestinacoes() {
		return destinacoes;
	}
	public void setDestinacoes(String destinacoes) {
		this.destinacoes = destinacoes;
	}
	public LocalNacional getLocaisNacionais() {
		return locaisNacionais;
	}
	public void setLocaisNacionais(LocalNacional locaisNacionais) {
		this.locaisNacionais = locaisNacionais;
	}
	public ArrayList<ApresentacaoCosmeticoRegularizado> getApresentacoes() {
		return apresentacoes;
	}
	public void setApresentacoes(ArrayList<ApresentacaoCosmeticoRegularizado> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}
	
	public void setApresentacoes(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		ArrayList<ApresentacaoCosmeticoRegularizado> apresentacoes = new ArrayList<ApresentacaoCosmeticoRegularizado>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					ApresentacaoCosmeticoRegularizado apresentacaoCosmeticoRegularizado = new ApresentacaoCosmeticoRegularizado();
					
					apresentacaoCosmeticoRegularizado.setPeriodoValidade(JsonToObject.getValue(nodeIt,"periodoValidade")+" "+JsonToObject.getValue(nodeIt,"tipoValidade"));
					apresentacaoCosmeticoRegularizado.setRestricaoUso(JsonToObject.getValue(nodeIt,"restricaoUso"));
					apresentacaoCosmeticoRegularizado.setCuidadoConservacao(JsonToObject.getValue(nodeIt,"cuidadoConservacao"));
					apresentacaoCosmeticoRegularizado.setEmbalagemPrimaria(JsonToObject.getValue(nodeIt,"embalagemPrimaria"));
					apresentacaoCosmeticoRegularizado.setEmbalagemSecundaria(JsonToObject.getValue(nodeIt,"embalagemSecundaria"));

					

					apresentacoes.add(apresentacaoCosmeticoRegularizado);
					
				}
			
			
			
		}
		
		this.setApresentacoes(apresentacoes);
		
	}
	
	public void setLocalNacional(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					LocalNacional localNacional = new LocalNacional();
					localNacional.setCnpj(JsonToObject.getValue(nodeIt,"cnpj"));
					localNacional.setRazaoSocial(JsonToObject.getValue(nodeIt,"razaoSocial"));
					localNacional.setUf( JsonToObject.getValue(nodeIt,"uf"));
					localNacional.setCidade( JsonToObject.getValue(nodeIt,"cidade"));
					localNacional.setCodigoMunicipio( JsonToObject.getValue(nodeIt,"codigoMunicipio"));
					localNacional.setAutorizacao(JsonToObject.getValue(nodeIt,"autorizacao"));
					this.setLocaisNacionais(localNacional);
					break;
					
				}
			
			
			
		}
		
		
		
	}
	 

}
