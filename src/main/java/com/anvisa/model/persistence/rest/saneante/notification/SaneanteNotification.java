package com.anvisa.model.persistence.rest.saneante.notification;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "saneante_notification")
public class SaneanteNotification extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "subject_matter", length = 600, nullable = false)
	@JsonAlias(value = "assunto")
	String assunto;
	
	@Column(name = "office_hour", length = 20, nullable = true)
	@JsonAlias(value = "expedientePeticao")
	String expedientePeticao;
	
	@Column(name = "transaction", length = 20, nullable = false)
	@JsonAlias(value = "transacao")
	String transacao;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "expedient_process", length = 20, nullable = false)
	@JsonAlias(value = "expedienteProcesso")
	String expedienteProcesso;
	
	@Column(name = "product", length = 600, nullable = false)
	@JsonAlias(value = "produto")
	String produto;
	
	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;

	@Column(name = "social_reason", length = 600, nullable = false)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;

	@Column(name = "situation", length = 600, nullable = false)
	@JsonAlias(value = "situacao")
	String situacao;

	@Column(name = "maturity", length = 8, nullable = true)
	@JsonAlias(value = "vencimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate vencimento;

	@Column(name = "status_maturity", length = 60, nullable = true)
	@JsonAlias(value = "statusVencimento")
	String statusVencimento;

	@Column(name = "company", length = 600, nullable = true)
	@JsonAlias(value = "empresa")
	String empresa;
	
	@Column(name = "dateUpdateRegister")
	@JsonAlias(value = "dataAlteracao")	
	@JsonFormat(pattern="dd/MM/yyyy")	
	LocalDate dataAlteracao;
	
	@Column(name = "dateRegister")
	@JsonAlias(value = "dataRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataRegistro;
	
	@Column(name = "qtdRecord")
	@JsonAlias(value = "qtdRegistro")	
	int qtdRegistro;

	@JsonAlias(value = "saneanteNotificationDetail")
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name="SaneanteNotificationDetailFK")
	SaneanteNotificationDetail saneanteNotificationDetail;

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

	public LocalDate getDataAlteracao() {
		if(this.dataAlteracao!=null && this.dataRegistro!=null) {
			if (this.dataAlteracao.isBefore(this.dataRegistro)) {
				return this.dataRegistro;
			}
		}
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public int getQtdRegistro() {
		return qtdRegistro;
	}

	public void setQtdRegistro(int qtdRegistro) {
		this.qtdRegistro = qtdRegistro;
	}

	public SaneanteNotificationDetail getSaneanteNotificationDetail() {
		return saneanteNotificationDetail;
	}

	public void setSaneanteNotificationDetail(SaneanteNotificationDetail saneanteNotificationDetail) {
		this.saneanteNotificationDetail = saneanteNotificationDetail;
	}

}
