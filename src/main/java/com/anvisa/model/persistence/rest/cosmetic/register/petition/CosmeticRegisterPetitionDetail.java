package com.anvisa.model.persistence.rest.cosmetic.register.petition;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cosmetic_register_petition_detail")
public class CosmeticRegisterPetitionDetail extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "social_reason", length = 400, nullable = false)
	@JsonAlias(value = "razaoSocial")
	private String razaoSocial;
	
	@Column(name = "autorization", length = 20)
	@JsonAlias(value = "autorizacao")
	private String autorizacao;
	
	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "product_name", length = 600, nullable = false)
	@JsonAlias(value = "nomeProduto")
	String nomeProduto;
	
	@Column(name = "category", length = 400)
	@JsonAlias(value = "categoria")
	String categoria;
	
	@Column(name = "register", length = 20)
	@JsonAlias(value = "registro")
	private String registro;
	
	@Column(name = "petition", length = 20)
	@JsonAlias(value = "peticao")
	private String peticao;
	
	@Column(name = "maturity")
	@JsonAlias(value = "vencimento")
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate vencimento;
	

	@OneToMany(cascade=CascadeType.ALL)
    private List<PetitionCountryManufacturer> fabricantesNacionais;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<CosmeticRegisterPetitionPresentation> apresentacoes;

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getPeticao() {
		return peticao;
	}

	public void setPeticao(String peticao) {
		this.peticao = peticao;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public List<PetitionCountryManufacturer> getFabricantesNacionais() {
		return fabricantesNacionais;
	}

	public void setFabricantesNacionais(List<PetitionCountryManufacturer> fabricantesNacionais) {
		this.fabricantesNacionais = fabricantesNacionais;
	}

	public List<CosmeticRegisterPetitionPresentation> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(List<CosmeticRegisterPetitionPresentation> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}
	
}
