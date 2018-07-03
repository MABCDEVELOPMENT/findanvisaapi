package com.anvisa.rest.model;

public class Peticao {
	public Peticao(String expediente, String protocolo, String remetente, Assunto assunto) {
		super();
		this.expediente = expediente;
		this.protocolo = protocolo;
		this.remetente = remetente;
		this.assunto = assunto;
	}

	private String expediente;

	public String getExpediente() {
		return this.expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	private String protocolo;

	public String getProtocolo() {
		return this.protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	private String remetente;

	public String getRemetente() {
		return this.remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	private Assunto assunto;

	public Assunto getAssunto() {
		return this.assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
}