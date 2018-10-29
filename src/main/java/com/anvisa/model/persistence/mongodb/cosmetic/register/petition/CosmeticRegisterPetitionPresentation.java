package com.anvisa.model.persistence.mongodb.cosmetic.register.petition;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CosmeticRegisterPetitionPresentation {
	

	@JsonAlias(value = "nome")
	private String nome;
	
	@JsonAlias(value = "tonalidade")
	private String tonalidade;
	
	@JsonAlias(value = "formaFisica")
    private String formaFisica;
	
	@JsonAlias(value = "numero")
	private String numero;

	@JsonAlias(value = "prazoValidade")
    private String prazoValidade;
	
	@JsonAlias(value = "tipoValidade")
	private String tipoValidade;
	
	@JsonAlias(value = "registro")
	private String registro;
	
	@JsonAlias(value = "embalagemPrimaria")
	private String embalagemPrimaria;
	
	@JsonAlias(value = "embalagemSecundaria")
	private String embalagemSecundaria;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTonalidade() {
		return tonalidade;
	}

	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}

	public String getFormaFisica() {
		return formaFisica;
	}

	public void setFormaFisica(String formaFisica) {
		this.formaFisica = formaFisica;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPrazoValidade() {
		return prazoValidade;
	}

	public void setPrazoValidade(String prazoValidade) {
		this.prazoValidade = prazoValidade;
	}

	public String getTipoValidade() {
		return tipoValidade;
	}

	public void setTipoValidade(String tipoValidade) {
		this.tipoValidade = tipoValidade;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getEmbalagemPrimaria() {
		return embalagemPrimaria;
	}

	public void setEmbalagemPrimaria(String embalagemPrimaria) {
		this.embalagemPrimaria = embalagemPrimaria;
	}

	public String getEmbalagemSecundaria() {
		return embalagemSecundaria;
	}

	public void setEmbalagemSecundaria(String embalagemSecundaria) {
		this.embalagemSecundaria = embalagemSecundaria;
	}

}
