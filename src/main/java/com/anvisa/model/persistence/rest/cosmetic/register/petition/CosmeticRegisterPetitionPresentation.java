package com.anvisa.model.persistence.rest.cosmetic.register.petition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_petition_presentation")
public class CosmeticRegisterPetitionPresentation extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name", length = 600)
	@JsonAlias(value = "nome")
	private String nome;
	
	@Column(name = "tonality", length = 100)
	@JsonAlias(value = "tonalidade")
	private String tonalidade;
	
	@Column(name = "physical_form", length = 400)
	@JsonAlias(value = "formaFisica")
    private String formaFisica;
	
	@Column(name = "number", length = 20)
	@JsonAlias(value = "numero")
	private String numero;

	@Column(name = "due_date", length = 4)
	@JsonAlias(value = "prazoValidade")
    private String prazoValidade;
	
	
	@Column(name = "type_validity", length = 20)
	@JsonAlias(value = "tipoValidade")
	private String tipoValidade;
	
	@Column(name = "register", length = 20)
	@JsonAlias(value = "registro")
	private String registro;
	
	@Column(name = "primary_package", length = 400)
	@JsonAlias(value = "embalagemPrimaria")
	private String embalagemPrimaria;
	
	@Column(name = "secondary_packaging", length = 400)
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
