package com.anvisa.model.persistence.mongodb.process;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;

public class ProcessPetition {


	@JsonAlias(value = "expediente")
    String expediente;
	
	@JsonAlias(value = "dataEntrada")
	@JsonFormat(pattern="dd/MM/yyyy")
    LocalDate dataEntrada;
	
	@JsonAlias(value = "protocolo")
    String protocolo;

	@JsonAlias(value = "processoSubject")
    ProcessoSubject assunto;
	
	@JsonAlias(value = "processSituation")
    ProcessSituation situacao;
    
	@JsonAlias(value = "processArea")
    ProcessArea area;

	@JsonAlias(value = "dataPublicacao")
	@JsonFormat(pattern="dd/MM/yyyy")
    LocalDate dataPublicacao;
    
	@JsonAlias(value = "resolucao")
    String resolucao;
    
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public LocalDate getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public ProcessoSubject getAssunto() {
		return assunto;
	}
	public void setAssunto(ProcessoSubject assunto) {
		this.assunto = assunto;
	}
	public ProcessSituation getSituacao() {
		return situacao;
	}
	public void setSituacao(ProcessSituation situacao) {
		this.situacao = situacao;
	}
	public ProcessArea getArea() {
		return area;
	}
	public void setArea(ProcessArea area) {
		this.area = area;
	}
	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public String getResolucao() {
		return resolucao;
	}
	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}
	
	
	public static ProcessPetition getPeticao(JsonNode node,String attribute) {
    	
		JsonNode element = (JsonNode) node.findValue(attribute);
		ProcessPetition peticao = new ProcessPetition();
		if (element == null) {
			element = node;
		}
		if (node != null) {

			peticao.setExpediente(JsonToObject.getValue(node, "expediente"));
			peticao.setDataEntrada(JsonToObject.getValueDate(node, "dataEntrada"));
			peticao.setProtocolo(JsonToObject.getValue(node, "protocolo"));
			
			ProcessoSubject assunto = new ProcessoSubject();
			assunto.setCodigo(JsonToObject.getValue(node, "assunto","codigo"));
			assunto.setDescricao(JsonToObject.getValue(node, "assunto","descricao"));
			peticao.setAssunto(assunto);
			
			ProcessSituation situacao = new ProcessSituation();
			situacao.setDescricao(JsonToObject.getValue(node, "situacao","descricao"));
			situacao.setData(JsonToObject.getValueDate(node, "situacao","data"));
			situacao.setRotulo(JsonToObject.getValue(node, "situacao","rotulo"));
			peticao.setSituacao(situacao);
			
			ProcessArea area = new ProcessArea();
			area.setSigla(JsonToObject.getValue(node, "area","sigla"));
			area.setNome(JsonToObject.getValue(node, "area","nome"));
			area.setRecebimento(JsonToObject.getValue(node, "area","recebimento"));
			area.setRemessa(JsonToObject.getValue(node, "area","remessa"));
			peticao.setArea(area);
			
			peticao.setDataPublicacao(JsonToObject.getValueDate(node, "dataPublicacao"));
			peticao.setResolucao(JsonToObject.getValue(node, "resolucao"));
			
		}

		return peticao;
    	
   }
	
}
