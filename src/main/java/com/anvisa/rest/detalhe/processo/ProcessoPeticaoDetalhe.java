package com.anvisa.rest.detalhe.processo;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;

public class ProcessoPeticaoDetalhe {


    String expediente;
    String dataEntrada;
    String protocolo;

    ProcessoAssuntoDetalhe assunto;

    ProcessoSituacaoDetalhe situacao;
    ProcessoAreaDetalhe area;

    String dataPublicacao;
    String resolucao;
    
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public ProcessoAssuntoDetalhe getAssunto() {
		return assunto;
	}
	public void setAssunto(ProcessoAssuntoDetalhe assunto) {
		this.assunto = assunto;
	}
	public ProcessoSituacaoDetalhe getSituacao() {
		return situacao;
	}
	public void setSituacao(ProcessoSituacaoDetalhe situacao) {
		this.situacao = situacao;
	}
	public ProcessoAreaDetalhe getArea() {
		return area;
	}
	public void setArea(ProcessoAreaDetalhe area) {
		this.area = area;
	}
	public String getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public String getResolucao() {
		return resolucao;
	}
	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}
	
	
	public static ProcessoPeticaoDetalhe getPeticao(JsonNode node,String attribute) {
    	
		JsonNode element = (JsonNode) node.findValue(attribute);
		ProcessoPeticaoDetalhe peticao = new ProcessoPeticaoDetalhe();

		if (element != null) {

			peticao.setExpediente(JsonToObject.getValue(node, "expediente"));
			peticao.setDataEntrada(JsonToObject.getValueDateToString(node, "dataEntrada"));
			peticao.setProtocolo(JsonToObject.getValue(node, "protocolo"));
			
			ProcessoAssuntoDetalhe assunto = new ProcessoAssuntoDetalhe();
			assunto.setCodigo(JsonToObject.getValue(node, "assunto","codigo"));
			assunto.setDescricao(JsonToObject.getValue(node, "assunto","descricao"));
			peticao.setAssunto(assunto);
			
			ProcessoSituacaoDetalhe situacao = new ProcessoSituacaoDetalhe();
			situacao.setDescricao(JsonToObject.getValue(node, "situacao","descricao"));
			situacao.setData(JsonToObject.getValueDateToString(node, "situacao","data"));
			situacao.setRotulo(JsonToObject.getValue(node, "situacao","descricao"));
			peticao.setSituacao(situacao);
			
			ProcessoAreaDetalhe area = new ProcessoAreaDetalhe();
			area.setSigla(JsonToObject.getValue(node, "area","sigla"));
			area.setNome(JsonToObject.getValue(node, "area","nome"));
			area.setRecebimento(JsonToObject.getValue(node, "area","recebimento"));
			area.setRemessa(JsonToObject.getValue(node, "area","remessa"));
			peticao.setArea(area);
			
			peticao.setDataPublicacao(JsonToObject.getValueDateToString(node, "dataPublicacao"));
			peticao.setResolucao(JsonToObject.getValue(node, "resolucao"));
			
		}

		return peticao;
    	
   }
	
}
