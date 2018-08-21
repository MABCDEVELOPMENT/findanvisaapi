package com.anvisa.rest.model;

public class Processo {
	
	private String numeroProcessoFormatado;
	private String situacao;
	private String numero;
	
	public Processo(String numero, boolean ativo) {
		super();
		this.numero = numero;
		this.ativo = ativo;
	}

	public Processo(String numero, String situacao, String numeroProcessoFormatado) {
		super();
		this.numero = numero;
		this.situacao = situacao;
		this.numeroProcessoFormatado = numeroProcessoFormatado;
	}



	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}



	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	private boolean ativo;

	public boolean getAtivo() {
		return this.ativo;
	}

	public void setSituacao(boolean ativo) {
		this.ativo = ativo;
	}

	public String getNumeroProcessoFormatado() {
		return this.numeroProcessoFormatado;
	}

	public void setNumeroProcessoFormatado(String numeroProcessoFormatado) {
		this.numeroProcessoFormatado = numeroProcessoFormatado;
	}
}
