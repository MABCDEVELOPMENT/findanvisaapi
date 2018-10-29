package com.anvisa.model.persistence.mongodb.cosmetic.register.petition;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;


public class CosmeticRegisterPetitionDetail {
	

	@JsonAlias(value = "razaoSocial")
	private String razaoSocial;
	

	@JsonAlias(value = "autorizacao")
	private String autorizacao;
	
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@JsonAlias(value = "nomeProduto")
	String nomeProduto;
	
	@JsonAlias(value = "categoria")
	String categoria;
	
	@JsonAlias(value = "registro")
	private String registro;
	

	@JsonAlias(value = "peticao")
	private String peticao;
	
	@JsonAlias(value = "vencimento")
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate vencimento;
	


    private List<PetitionCountryManufacturer> fabricantesNacionais;
	

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
