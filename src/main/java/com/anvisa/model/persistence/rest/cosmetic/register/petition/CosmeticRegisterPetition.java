package com.anvisa.model.persistence.rest.cosmetic.register.petition;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegisterDetail;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cosmetic_register_petition")
public class CosmeticRegisterPetition extends BaseEntity {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "expedient", length = 20)
	@JsonAlias(value = "expediente")
	String expediente;
	
	@Column(name = "publication", nullable = true)
	@JsonAlias(value = "publicacao")
	@JsonFormat(pattern="dd/MM/yyyy")
    LocalDate publicacao;
	
	@Column(name = "transaction", length = 20, nullable = true)
	@JsonAlias(value = "transacao")
    String transacao;
	
	@Column(name = "subject", length = 400 , nullable = true)
	@JsonAlias(value = "assunto")
    String assunto;
	
	@Column(name = "situation", length = 100 , nullable = true)
	@JsonAlias(value = "situacao")
    String situacao;
	
	@OneToOne(cascade=CascadeType.ALL)
    private CosmeticRegisterPetitionDetail cosmeticRegisterPetitionDetail;
    
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public LocalDate getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(LocalDate publicacao) {
		this.publicacao = publicacao;
	}
	public String getTransacao() {
		return transacao;
	}
	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public CosmeticRegisterPetitionDetail getCosmeticRegisterPetitionDetail() {
		return cosmeticRegisterPetitionDetail;
	}
	public void setCosmeticRegisterPetitionDetail(CosmeticRegisterPetitionDetail cosmeticRegisterPetitionDetail) {
		this.cosmeticRegisterPetitionDetail = cosmeticRegisterPetitionDetail;
	}

	
}
