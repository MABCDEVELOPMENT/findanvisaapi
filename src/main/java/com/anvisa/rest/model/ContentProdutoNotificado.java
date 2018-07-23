package com.anvisa.rest.model;

import java.time.LocalDate;

public class ContentProdutoNotificado {

	String assunto; 
    String expedientePeticao;
    String transacao;
    String processo;
    String expedienteProcesso;
    String produto;
    String cnpj;
    String razaoSocial;
    String situacao;
    LocalDate vencimento;
    
    
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getExpedientePeticao() {
		return expedientePeticao;
	}
	public void setExpedientePeticao(String expedientePeticao) {
		this.expedientePeticao = expedientePeticao;
	}
	public String getTransacao() {
		return transacao;
	}
	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getExpedienteProcesso() {
		return expedienteProcesso;
	}
	public void setExpedienteProcesso(String expedienteProcesso) {
		this.expedienteProcesso = expedienteProcesso;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public LocalDate getVencimento() {
		return vencimento;
	}
	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

    
	
}

