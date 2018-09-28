package com.anvisa.model.persistence.rest.cosmetic.register;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntityAudit;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_register")
public class ContentCosmeticRegister extends BaseEntityAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "subject_matter", length = 300, nullable = false)
	@JsonAlias(value = "assunto")
	String assunto; 
	
	@Column(name = "office_hour", length = 20, nullable = false)
	@JsonAlias(value = "expedientePeticao")
    String expedientePeticao;
	
	@Column(name = "transaction", length = 20, nullable = false)
	@JsonAlias(value = "transacao")
    String transacao;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
    String processo;
	
    String expedienteProcesso;
    String produto;
    String cnpj;
    String razaoSocial;
    String situacao;
    LocalDate vencimento;
    String statusVencimento;
    String empresa;
    
    
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getExpedientePeticao() {
		return expedientePeticao;
	}
	public void setExpedientePeticao(String expedientePeticao) {
		this.expedientePeticao = expedientePeticao;
	}
	public String getTransacao() {
		return transacao;
	}
	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getExpedienteProcesso() {
		return expedienteProcesso;
	}
	public void setExpedienteProcesso(String expedienteProcesso) {
		this.expedienteProcesso = expedienteProcesso;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
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
	public String getStatusVencimento() {
		return statusVencimento;
	}
	public void setStatusVencimento(String statusVencimento) {
		this.statusVencimento = statusVencimento;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

}
