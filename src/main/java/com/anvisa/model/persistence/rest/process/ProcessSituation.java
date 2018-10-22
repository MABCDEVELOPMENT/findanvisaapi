package com.anvisa.model.persistence.rest.process;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "process_situation")
public class ProcessSituation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "description")
	@JsonAlias(value = "descricao")
	String descricao;

	@Column(name = "data")
	@JsonAlias(value = "data")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate data;
	
	@Column(name = "label")
	@JsonAlias(value = "rotulo")
	String rotulo;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
}
