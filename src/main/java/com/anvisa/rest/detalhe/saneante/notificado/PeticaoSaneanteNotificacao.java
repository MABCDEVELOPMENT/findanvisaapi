package com.anvisa.rest.detalhe.saneante.notificado;

public class PeticaoSaneanteNotificacao {
	
	String expediente;
    String publicacao;
    String transacao;
    String assunto;
    String situacao;
    
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(String publicacao) {
		this.publicacao = publicacao;
	}
	public String getTransacao() {
		return transacao;
	}
	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}