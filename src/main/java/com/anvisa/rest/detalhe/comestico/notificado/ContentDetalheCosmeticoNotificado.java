package com.anvisa.rest.detalhe.comestico.notificado;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ContentDetalheCosmeticoNotificado {
	
	String assunto;
	String produto;
	String empresa;
	String processo;
	String area;
	String situacao;
	String dataNotificacao;
	ArrayList<ApresentacaoCosmeticoNotificado> apresentacoes;
	
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getDataNotificacao() {
		return dataNotificacao;
	}
	public void setDataNotificacao(String dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}
	public ArrayList<ApresentacaoCosmeticoNotificado> getApresentacoes() {
		return apresentacoes;
	}
	public void setApresentacoes(ArrayList<ApresentacaoCosmeticoNotificado> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}
	
	public void setApresentacoes(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		ArrayList<ApresentacaoCosmeticoNotificado> apresentacoes = new ArrayList<ApresentacaoCosmeticoNotificado>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					ApresentacaoCosmeticoNotificado apresentacaoCosmeticoNotificado = new ApresentacaoCosmeticoNotificado();
					
					apresentacaoCosmeticoNotificado.setApresentacao(JsonToObject.getValue(nodeIt,"apresentacao"));
					apresentacaoCosmeticoNotificado.setTonalidade(JsonToObject.getValue(nodeIt,"tonalidade"));
					apresentacaoCosmeticoNotificado.setEans("");
					apresentacoes.add(apresentacaoCosmeticoNotificado);
					
				}
			
			
			
		}
		
		this.setApresentacoes(apresentacoes);
		
	}

}
