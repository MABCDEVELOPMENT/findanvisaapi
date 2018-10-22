package com.anvisa.model.persistence.rest.process;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "process_situation")
public class ProcessArea extends BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "initials", length = 20)
	@JsonAlias(value = "sigla")
	String sigla;
	
	@Column(name = "name")
	@JsonAlias(value = "nome")
    String nome;

	@Column(name = "reciption")
	@JsonAlias(value = "recebimento")
    String recebimento;
	
	@Column(name = "shipping")
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
