package com.anvisa.model.persistence.rest.process;

public class ProcessArea {
	String sigla;
    String nome;
    String recebimento;
    String remessa;
    
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRecebimento() {
		return recebimento;
	}
	public void setRecebimento(String recebimento) {
		this.recebimento = recebimento;
	}
	public String getRemessa() {
		return remessa;
	}
	public void setRemessa(String remessa) {
		this.remessa = remessa;
	}
}
