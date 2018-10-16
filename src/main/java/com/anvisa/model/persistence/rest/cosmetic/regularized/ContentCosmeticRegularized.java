package com.anvisa.model.persistence.rest.cosmetic.regularized;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_regularized")
public class ContentCosmeticRegularized extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
    String processo;

	@Column(name = "product", length = 300, nullable = false)
	@JsonAlias(value = "product")
    String produto;
	
	@Column(name = "type",      length = 100)
	@JsonAlias(value = "tipo")
    String tipo; 

	@Column(name = "situation", length = 300)
	@JsonAlias(value = "situacao")
    String situacao;
	
	@Column(name = "maturity")
	@JsonAlias(value = "vencimento")
    LocalDate vencimento;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail;

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public ContentCosmeticRegularizedDetail getContentCosmeticRegularizedDetail() {
		return contentCosmeticRegularizedDetail;
	}

	public void setContentCosmeticRegularizedDetail(ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail) {
		this.contentCosmeticRegularizedDetail = contentCosmeticRegularizedDetail;
	}
	
	

}
