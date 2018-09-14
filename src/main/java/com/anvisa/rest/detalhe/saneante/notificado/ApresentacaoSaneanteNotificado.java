package com.anvisa.rest.detalhe.saneante.notificado;

import java.util.ArrayList;

public class ApresentacaoSaneanteNotificado {
	
	String apresentacao;
    String tonalidade;
    ArrayList<Ean> eans;
    String versao;
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
	public ArrayList<Ean> getEans() {
		return eans;
	}
	public void setEans(ArrayList<Ean> eans) {
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
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	

}

