package com.anvisa.model.persistence.rest.foot;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.AbstractBaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "foot")
public class ContentFoot extends AbstractBaseEntity {

	@Column(name = "code", length = 20, nullable = false)
	@JsonAlias(value = "codigo")
	int codigo;
	
	@Column(name = "product", nullable = false)
	@JsonAlias(value = "product")
	String produto;

	@Column(name = "register", length = 20, nullable = false)
	@JsonAlias(value = "registro")
	String registro;
	
	@Column(name = "process",  length = 20, nullable = false)
	@JsonAlias(value = "processo")	
	String processo;

	@Column(name = "company", nullable = false)
	@JsonAlias(value = "empresa")
	String empresa;

	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "situation", length = 60, nullable = false)
	@JsonAlias(value = "situacao")
	String situacao;

	@Column(name = "vencimento", length = 8, nullable = true)
	@JsonAlias(value = "vencimento")
	String vencimento;
	
	@Column(name = "statusVencimento", length = 60, nullable = true)
	@JsonAlias(value = "statusVencimento")	
	String statusVencimento;
	
	@Column(name = "dataVencimento", nullable = true)
	@JsonAlias(value = "dataVencimento")	
	LocalDate dataVencimento;
	
	@JoinColumn
	ContentDetalFoot contentDateil;
	
	
	public ContentFoot(Content content) {
		// TODO Auto-generated constructor stub
		
		this.setCodigo(content.getProduto().getCodigo());
		this.setProduto(content.getProduto().getNome());
		this.setRegistro(content.getProduto().getNumeroRegistro());
		this.setProcesso(content.getProcesso().getNumero());
		this.setEmpresa(content.getEmpresa().getRazaoSocial());
		this.setCnpj(content.getEmpresa().getCnpj());
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

	public ContentDetalFoot getContentDateil() {
		return contentDateil;
	}

	public void setContentDateil(ContentDetalFoot contentDateil) {
		this.contentDateil = contentDateil;
	}

}

