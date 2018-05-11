package com.anvisa.model;

import java.util.Date;

public class Produto {
	private int codigo;

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	private String nome;

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	private String numeroRegistro;

	public String getNumeroRegistro() {
		return this.numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	private Tipo tipo;

	public Tipo getTipo() {
		return this.tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	private Categoria categoria;

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	private String situacaoRotulo;

	public String getSituacaoRotulo() {
		return this.situacaoRotulo;
	}

	public void setSituacaoRotulo(String situacaoRotulo) {
		this.situacaoRotulo = situacaoRotulo;
	}

	private Date dataVencimento;

	public Date getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	private String mesAnoVencimento;

	public String getMesAnoVencimento() {
		return this.mesAnoVencimento;
	}

	public void setMesAnoVencimento(String mesAnoVencimento) {
		this.mesAnoVencimento = mesAnoVencimento;
	}

	private Date dataVencimentoRegistro;

	public Date getDataVencimentoRegistro() {
		return this.dataVencimentoRegistro;
	}

	public void setDataVencimentoRegistro(Date dataVencimentoRegistro) {
		this.dataVencimentoRegistro = dataVencimentoRegistro;
	}

	private String principioAtivo;

	public String getPrincipioAtivo() {
		return this.principioAtivo;
	}

	public void setPrincipioAtivo(String principioAtivo) {
		this.principioAtivo = principioAtivo;
	}

	private String situacaoApresentacao;

	public String getSituacaoApresentacao() {
		return this.situacaoApresentacao;
	}

	public void setSituacaoApresentacao(String situacaoApresentacao) {
		this.situacaoApresentacao = situacaoApresentacao;
	}

	private Date dataRegistro;

	public Date getDataRegistro() {
		return this.dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	private String numeroRegistroFormatado;

	public String getNumeroRegistroFormatado() {
		return this.numeroRegistroFormatado;
	}

	public void setNumeroRegistroFormatado(String numeroRegistroFormatado) {
		this.numeroRegistroFormatado = numeroRegistroFormatado;
	}

	private String mesAnoVencimentoFormatado;

	public String getMesAnoVencimentoFormatado() {
		return this.mesAnoVencimentoFormatado;
	}

	public void setMesAnoVencimentoFormatado(String mesAnoVencimentoFormatado) {
		this.mesAnoVencimentoFormatado = mesAnoVencimentoFormatado;
	}

	private boolean acancelar;

	public boolean getAcancelar() {
		return this.acancelar;
	}

	public void setAcancelar(boolean acancelar) {
		this.acancelar = acancelar;
	}
}
