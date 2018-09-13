package com.anvisa.rest.detalhe.saneante.product;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ContentDetalheSaneanteProduct {

	String razaoSocial;
	String cnpj;
	String numeroAutorizacao;
	String nomeComercial;
	String classesTerapeuticas;
	String registro;
	String processo;
	String mesAnoVencimento;

	ArrayList<String> rotulos;

	ArrayList<SaneanteProductApresentacao> apresentacoes;

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNumeroAutorizacao() {
		return numeroAutorizacao;
	}

	public void setNumeroAutorizacao(String numeroAutorizacao) {
		this.numeroAutorizacao = numeroAutorizacao;
	}

	public String getNomeComercial() {
		return nomeComercial;
	}

	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}

	public String getClassesTerapeuticas() {
		return classesTerapeuticas;
	}

	public void setClassesTerapeuticas(String classesTerapeuticas) {
		this.classesTerapeuticas = classesTerapeuticas;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getMesAnoVencimento() {
		return mesAnoVencimento;
	}

	public void setMesAnoVencimento(String mesAnoVencimento) {
		this.mesAnoVencimento = mesAnoVencimento;
	}

	public ArrayList<String> getRotulos() {
		return rotulos;
	}

	public void setRotulos(ArrayList<String> rotulos) {
		this.rotulos = rotulos;
	}

	public ArrayList<SaneanteProductApresentacao> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(ArrayList<SaneanteProductApresentacao> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}

	public void loadApresentaçoes(JsonNode node, String attribute) {

		this.apresentacoes = new ArrayList<SaneanteProductApresentacao>();

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		if (element != null) {

			for (JsonNode jsonNode : element) {

				SaneanteProductApresentacao apresentacao = new SaneanteProductApresentacao();
				apresentacao.loadApresentacao(jsonNode);
				this.apresentacoes.add(apresentacao);

			}
		}

	}

}
