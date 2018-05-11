package com.anvisa.model;

public class Categoria {
	private String codigo;

	public Categoria(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
