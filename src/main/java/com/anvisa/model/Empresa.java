package com.anvisa.model;

public class Empresa {

	private String cnpj;

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	private String razaoSocial;

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	private String numeroAutorizacao;

	public String getNumeroAutorizacao() {
		return this.numeroAutorizacao;
	}

	public void setNumeroAutorizacao(String numeroAutorizacao) {
		this.numeroAutorizacao = numeroAutorizacao;
	}

	public Empresa(String cnpj, String razaoSocial, String numeroAutorizacao, String cnpjFormatado) {
		super();
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.numeroAutorizacao = numeroAutorizacao;
		this.cnpjFormatado = cnpjFormatado;
	}

	private String cnpjFormatado;

	public String getCnpjFormatado() {
		return this.cnpjFormatado;
	}

	public void setCnpjFormatado(String cnpjFormatado) {
		this.cnpjFormatado = cnpjFormatado;
	}
}
