package com.anvisa.model.persistence.rest.cosmetic.register.presentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_country_manufacturer")
public class PresentationCountryManufacturer extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "cnpj", length = 14)
	@JsonAlias(value = "cnpj")
	private String cnpj;
	
	@Column(name = "social_reason", length = 200, nullable = false)
	@JsonAlias(value = "razaoSocial")
	private String razaoSocial;

	@Column(name = "city", length = 60)
	@JsonAlias(value = "cidade")
	private String cidade;
	
	@Column(name = "uf", length = 2)
	@JsonAlias(value = "uf")
	private String uf;
	
	@Column(name = "county", length = 20)
	@JsonAlias(value = "pais")
	private String pais;
	
	@Column(name = "type", length = 20)
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
