package com.anvisa.rest.model;

import java.time.LocalDate;

import com.anvisa.rest.Content;

public class ContentProduto {

	int codigo;
	String produto;
	String registro;
	String processo;
	String empresa;
	String cnpj;
	String situacao;
	String vencimento;
	String statusVencimento;
	LocalDate dataVencimento;
	
	public ContentProduto(Content content) {
		// TODO Auto-generated constructor stub
		
		this.setCodigo(content.getProduto().getCodigo());
		this.setProduto(content.getProduto().getNome());
		this.setRegistro(content.getProduto().getNumeroRegistroFormatado());
		this.setProcesso(content.getProcesso().getNumeroProcessoFormatado());
		this.setEmpresa(content.getEmpresa().getRazaoSocial());
		this.setCnpj(content.getEmpresa().getCnpjFormatado());
		this.setSituacao(content.getProcesso().getSituacao());
		this.setVencimento(content.getProduto().getMesAnoVencimentoFormatado());
		this.setDataVencimento(content.getProduto().getDataVencimentoRegistro());
		
		
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getStatusVencimento() {
		return statusVencimento;
	}

	public void setStatusVencimento(String statusVencimento) {
		this.statusVencimento = statusVencimento;
	}
	
	
	
}
