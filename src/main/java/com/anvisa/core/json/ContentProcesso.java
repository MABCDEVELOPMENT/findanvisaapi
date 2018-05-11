package com.anvisa.core.json;

import java.util.Date;

import com.anvisa.model.Empresa;
import com.anvisa.model.Peticao;
import com.anvisa.model.Processo;
import com.anvisa.model.Tipo;

public class ContentProcesso {
	private Tipo tipo;

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	private Date dataEntrada;

	public Date getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
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
}