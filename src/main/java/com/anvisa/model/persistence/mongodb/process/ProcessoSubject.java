package com.anvisa.model.persistence.mongodb.process;

import com.fasterxml.jackson.annotation.JsonAlias;


public class ProcessoSubject {
	

	@JsonAlias(value = "codigo")
	String codigo;
	
	@JsonAlias(value = "descricao")
    String descricao;
     
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
     
	
}
