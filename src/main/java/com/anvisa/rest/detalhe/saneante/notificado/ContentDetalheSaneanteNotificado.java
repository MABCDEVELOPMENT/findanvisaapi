package com.anvisa.rest.detalhe.saneante.notificado;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.rest.detalhe.comestico.registrado.PeticaoCosmeticoRegistrado;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ContentDetalheSaneanteNotificado {

	String assunto;
	String produto;
	String empresa;
	String processo;
	String area;
	String situacao;
	String dataNotificacao;
	ArrayList<ApresentacaoSaneanteNotificado> apresentacoes;
	ArrayList<PeticaoCosmeticoRegistrado> peticoes;

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

	public ArrayList<ApresentacaoSaneanteNotificado> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(ArrayList<ApresentacaoSaneanteNotificado> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}

	public ArrayList<PeticaoCosmeticoRegistrado> getPeticoes() {
		return peticoes;
	}

	public void setPeticoes(ArrayList<PeticaoCosmeticoRegistrado> peticoes) {
		this.peticoes = peticoes;
	}

	public void setApresentacoes(JsonNode node, String attribute) {

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		ArrayList<ApresentacaoSaneanteNotificado> apresentacoes = new ArrayList<ApresentacaoSaneanteNotificado>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				ApresentacaoSaneanteNotificado apresentacaoSaneanteNotificado = new ApresentacaoSaneanteNotificado();

				apresentacaoSaneanteNotificado.setApresentacao(JsonToObject.getValue(nodeIt, "apresentacao"));
				apresentacaoSaneanteNotificado.setTonalidade(JsonToObject.getValue(nodeIt, "versao"));
				apresentacaoSaneanteNotificado.setEans(JsonToObject.getValue(nodeIt, "tonalidade"));
				apresentacaoSaneanteNotificado.setEans("");
				apresentacoes.add(apresentacaoSaneanteNotificado);

			}

		}

		this.setApresentacoes(apresentacoes);

	}

	public void setPeticoes(JsonNode node, String attribute) {

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		ArrayList<PeticaoCosmeticoRegistrado> peticoes = new ArrayList<PeticaoCosmeticoRegistrado>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				PeticaoCosmeticoRegistrado peticaoCosmeticoRegistrado = new PeticaoCosmeticoRegistrado();

				peticaoCosmeticoRegistrado.setExpediente(JsonToObject.getValue(nodeIt, "expediente"));
				peticaoCosmeticoRegistrado.setPublicacao(JsonToObject.getValueDateToString(nodeIt, "publicacao"));
				peticaoCosmeticoRegistrado.setTransacao(JsonToObject.getValue(nodeIt, "transacao"));

				String assunto = JsonToObject.getValue(nodeIt, "assunto", "codigo") + " "
						+ JsonToObject.getValue(nodeIt, "assunto", "descricao");
				peticaoCosmeticoRegistrado.setAssunto(assunto);

				String situacao = JsonToObject.getValue(nodeIt, "situacao", "situacao") + " "
						+ JsonToObject.getValueDateToString(nodeIt, "situacao", "data");
				peticaoCosmeticoRegistrado.setSituacao(situacao);

				peticoes.add(peticaoCosmeticoRegistrado);

			}

		}

		this.setPeticoes(peticoes);

	}

}
