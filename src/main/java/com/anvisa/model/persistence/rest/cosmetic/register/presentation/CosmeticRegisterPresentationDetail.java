package com.anvisa.model.persistence.rest.cosmetic.register.presentation;

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

@Entity
@Table(name = "cosmetic_register_presentation_dateil")
public class CosmeticRegisterPresentationDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "product_name", length = 300)
	@JsonAlias(value = "nomeProduto")
	private String nomeProduto;  
	
	@Column(name = "process", length = 20)
	@JsonAlias(value = "processo")
    private String processo;
	
	@Column(name = "presentation", length = 200)
	@JsonAlias(value = "apresentacao")
    private String apresentacao;
	
	@Column(name = "category", length = 200)
	@JsonAlias(value = "categoria")
    private String categoria;
	
	@OneToMany(cascade=CascadeType.ALL)
    private List<PresentationCountryManufacturer> fabricantesNacionais;
    
	@Column(name = "physical_form", length = 200)
	@JsonAlias(value = "formaFisica")
    private String formaFisica;
	
	@Column(name = "tonality", length = 60)
	@JsonAlias(value = "tonalidade")
    private String tonalidade;
	
	@Column(name = "due_date", length = 4)
	@JsonAlias(value = "prazoValidade")
    private String prazoValidade;
	
	@OneToMany(cascade=CascadeType.ALL)
    private List<PresentationConservation> conservacao;
	
	@OneToMany(cascade=CascadeType.ALL)
    private List<PresentationDestination> destinacao;
	
	@OneToMany(cascade=CascadeType.ALL)
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
