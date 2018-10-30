package com.anvisa.model.persistence.mongodb.process;

import com.fasterxml.jackson.annotation.JsonAlias;


public class ProcessArea {

	@JsonAlias(value = "sigla")
	String sigla;
	
	@JsonAlias(value = "nome")
    String nome;

	@JsonAlias(value = "recebimento")
    String recebimento;
	
	@JsonAlias(value = "remessa")
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
