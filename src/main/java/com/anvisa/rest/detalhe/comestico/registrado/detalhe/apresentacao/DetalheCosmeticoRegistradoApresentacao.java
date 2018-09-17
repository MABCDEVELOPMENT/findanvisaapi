package com.anvisa.rest.detalhe.comestico.registrado.detalhe.apresentacao;

import java.util.ArrayList;

public class DetalheCosmeticoRegistradoApresentacao {

     private String produto;
     private String processo;
     private String apresentacao;
     private String categoria;
     private ArrayList<CosmeticoRegistradoApresentacaoFabricantesNacionais> fabricantesNacionais;
     private String formaFisica;
     private String tonalidade;
     private String prazoValidade;
     private ArrayList<String> conservaçao;
     private ArrayList<String> restricao;
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
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
	public ArrayList<CosmeticoRegistradoApresentacaoFabricantesNacionais> getFabricantesNacionais() {
		return fabricantesNacionais;
	}
	public void setFabricantesNacionais(
			ArrayList<CosmeticoRegistradoApresentacaoFabricantesNacionais> fabricantesNacionais) {
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
	public ArrayList<String> getConservaçao() {
		return conservaçao;
	}
	public void setConservaçao(ArrayList<String> conservaçao) {
		this.conservaçao = conservaçao;
	}
	public ArrayList<String> getRestricao() {
		return restricao;
	}
	public void setRestricao(ArrayList<String> restricao) {
		this.restricao = restricao;
	}

     
}
