package com.anvisa.rest;

public class ContentDetalheAlimento {
	
	String razaoSocial;
	String cnpj;
	String nomeComercial;
	String[] classesTerapeuticas;
	String registro;
	String processo;
	String marca;
	
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
	public String getNomeComercial() {
		return nomeComercial;
	}
	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}
	public String[] getClassesTerapeuticas() {
		return classesTerapeuticas;
	}
	public void setClassesTerapeuticas(String[] classesTerapeuticas) {
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
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

}
