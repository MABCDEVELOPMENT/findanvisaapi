package com.anvisa.model.persistence.mongodb.process;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ProcessSituation {

	@JsonAlias(value = "descricao")
	String descricao;

	@JsonAlias(value = "data")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate data;
	
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
