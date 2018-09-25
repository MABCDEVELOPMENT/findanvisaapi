package com.anvisa.rest;

import java.time.LocalDate;
import java.util.Date;

import com.anvisa.rest.model.Empresa;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.anvisa.rest.model.Produto;
import com.anvisa.rest.model.Tipo;

public class ContentSaneante {
	
	private int ordem;
	
	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	private Tipo tipo;

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	private LocalDate dataEntrada;

	public LocalDate getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	private Empresa empresa;

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	private Processo processo;

	public Processo getProcesso() {
		return this.processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	private Peticao peticao;

	public Peticao getPeticao() {
		return this.peticao;
	}

	public void setPeticao(Peticao peticao) {
		this.peticao = peticao;
	}

	private String area;

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	private Produto produto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
}