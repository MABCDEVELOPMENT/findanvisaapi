package com.anvisa.model.persistence.mongodb.cosmetic.register.presentation;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

public class CosmeticRegisterPresentationDetail {

	
	@JsonAlias(value = "nomeProduto")
	private String nomeProduto;  
	
	@JsonAlias(value = "processo")
    private String processo;
	
	@JsonAlias(value = "apresentacao")
    private String apresentacao;
	
	@JsonAlias(value = "categoria")
    private String categoria;
	
    private List<PresentationCountryManufacturer> fabricantesNacionais;
    
	@JsonAlias(value = "formaFisica")
    private String formaFisica;
	
	@JsonAlias(value = "tonalidade")
    private String tonalidade;
	
	@JsonAlias(value = "prazoValidade")
    private String prazoValidade;
	
    private List<PresentationConservation> conservacao;
	
    private List<PresentationDestination> destinacao;
	
    private List<PresentationRestriction> restricao;
	

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<PresentationCountryManufacturer> getFabricantesNacionais() {
		return fabricantesNacionais;
	}

	public void setFabricantesNacionais(List<PresentationCountryManufacturer> fabricantesNacionais) {
		this.fabricantesNacionais = fabricantesNacionais;
	}

	public String getFormaFisica() {
		return formaFisica;
	}

	public void setFormaFisica(String formaFisica) {
		this.formaFisica = formaFisica;
	}

	public String getTonalidade() {
		return tonalidade;
	}

	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}

	public String getPrazoValidade() {
		return prazoValidade;
	}

	public void setPrazoValidade(String prazoValidade) {
		this.prazoValidade = prazoValidade;
	}

	public List<PresentationConservation> getConservacao() {
		return conservacao;
	}

	public void setConservacao(List<PresentationConservation> conservacao) {
		this.conservacao = conservacao;
	}

	public List<PresentationDestination> getDestinacao() {
		return destinacao;
	}

	public void setDestinacao(List<PresentationDestination> destinacao) {
		this.destinacao = destinacao;
	}

	public List<PresentationRestriction> getRestricao() {
		return restricao;
	}

	public void setRestricao(List<PresentationRestriction> restricao) {
		this.restricao = restricao;
	}


}
