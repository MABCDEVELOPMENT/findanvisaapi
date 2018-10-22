package com.anvisa.model.persistence.rest.process;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "process_subject")
public class ProcessoSubject extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "code", length = 20)
	@JsonAlias(value = "codigo")
	String codigo;
	
	@Column(name = "description")
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
