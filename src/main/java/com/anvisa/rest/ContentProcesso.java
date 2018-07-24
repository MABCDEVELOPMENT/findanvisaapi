package com.anvisa.rest;

public class ContentProcesso {
	
	int ordem;
	
	String cnpj;
	
	String razaoSocial;
	
	String processo;
	
	String assunto;
	
	public ContentProcesso() {
		
	}
	public ContentProcesso(Content content) {
		this.setOrdem(content.getOrdem());
		this.setCnpj(content.getEmpresa().getCnpjFormatado());
		this.setRazaoSocial(content.getEmpresa().getRazaoSocial());
		this.setProcesso(this.getProcesso());
		this.setAssunto(content.getPeticao().getAssunto().toString());
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
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

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	


}
