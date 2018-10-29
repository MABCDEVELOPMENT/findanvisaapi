package com.anvisa.model.persistence.mongodb.cosmetic.register.presentation;

import com.fasterxml.jackson.annotation.JsonAlias;


public class CosmeticRegisterPresentation {
   

	@JsonAlias(value = "codigo")
	String codigo;
	

	@JsonAlias(value = "numero")	
    String numero;
	

	@JsonAlias(value = "embalagemPrimaria")	
    String embalagemPrimaria;
	

	@JsonAlias(value = "embalagemSecundaria")	
    String embalagemSecundaria;
	

	@JsonAlias(value = "tonalidade")	
    String tonalidade;


	@JsonAlias(value = "situacao")
    String situacao;
	

	@JsonAlias(value = "destaque")
    boolean destaque;
	

	@JsonAlias(value = "registro")
    String registro;
    

    private CosmeticRegisterPresentationDetail cosmeticRegisterPresentationDetail;
    
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEmbalagemPrimaria() {
		return (embalagemPrimaria==null)?"Primária": embalagemPrimaria+" - Primária";
	}
	public void setEmbalagemPrimaria(String embalagemPrimaria) {
		this.embalagemPrimaria = embalagemPrimaria;
	}
	public String getEmbalagemSecundaria() {
		return (embalagemSecundaria==null)?"Secundária":embalagemSecundaria+" - Secundária";
	}
	public void setEmbalagemSecundaria(String embalagemSecundaria) {
		this.embalagemSecundaria = embalagemSecundaria;
	}
	public String getTonalidade() {
		return (tonalidade==null||tonalidade.equals(""))?"Não se aplica para essa categoria":tonalidade;
	}
	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}
 	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public boolean isDestaque() {
		return destaque;
	}
	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public CosmeticRegisterPresentationDetail getCosmeticRegisterPresentationDetail() {
		return cosmeticRegisterPresentationDetail;
	}
	public void setCosmeticRegisterPresentationDetail(
			CosmeticRegisterPresentationDetail cosmeticRegisterPresentationDetail) {
		this.cosmeticRegisterPresentationDetail = cosmeticRegisterPresentationDetail;
	}

	
	
    
}
