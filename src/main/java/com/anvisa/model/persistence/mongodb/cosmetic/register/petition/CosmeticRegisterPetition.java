package com.anvisa.model.persistence.mongodb.cosmetic.register.petition;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CosmeticRegisterPetition {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@JsonAlias(value = "expediente")
	String expediente;
	

	@JsonAlias(value = "publicacao")
	@JsonFormat(pattern="dd/MM/yyyy")
    LocalDate publicacao;
	

	@JsonAlias(value = "transacao")
    String transacao;
	
	@JsonAlias(value = "assunto")
    String assunto;
	

	@JsonAlias(value = "situacao")
    String situacao;
	

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
