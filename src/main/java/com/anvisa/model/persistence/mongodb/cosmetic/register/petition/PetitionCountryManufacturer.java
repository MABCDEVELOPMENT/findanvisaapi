package com.anvisa.model.persistence.mongodb.cosmetic.register.petition;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PetitionCountryManufacturer {
	
	@JsonAlias(value = "cnpj")
	private String cnpj;
	
	@JsonAlias(value = "razaoSocial")
	private String razaoSocial;

	@JsonAlias(value = "cidade")
	private String cidade;
	
	@JsonAlias(value = "uf")
	private String uf;
	
	@JsonAlias(value = "pais")
	private String pais;
	
	@JsonAlias(value = "type")
	private String tipo;

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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
