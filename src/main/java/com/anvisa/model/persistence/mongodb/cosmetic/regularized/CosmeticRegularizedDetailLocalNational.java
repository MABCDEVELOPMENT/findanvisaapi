package com.anvisa.model.persistence.mongodb.cosmetic.regularized;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class CosmeticRegularizedDetailLocalNational {
	
	@Field(value = "cnpj")
	String cnpj;

	@Field(value = "razaoSocial")
	String razaoSocial;

	@Field(value = "autorizacao")
	String autorizacao;

	@Field(value = "uf")
	String uf;

	@Field(value = "cidade")
	String cidade;
	
	@Field(value = "codigoMunicipio")
	String codigoMunicipio;
	
	@Field(value = "pais")
	String pais;
	
	public CosmeticRegularizedDetailLocalNational() {
		// TODO Auto-generated constructor stub
	}
	
	
	@PersistenceConstructor
	public CosmeticRegularizedDetailLocalNational(String cnpj, String razaoSocial, String autorizacao, String uf,
			String cidade, String codigoMunicipio, String pais) {
		super();
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.autorizacao = autorizacao;
		this.uf = uf;
		this.cidade = cidade;
		this.codigoMunicipio = codigoMunicipio;
		this.pais = pais;
	}



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

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((autorizacao == null) ? 0 : autorizacao.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((codigoMunicipio == null) ? 0 : codigoMunicipio.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CosmeticRegularizedDetailLocalNational)) {
			return false;
		}
		CosmeticRegularizedDetailLocalNational other = (CosmeticRegularizedDetailLocalNational) obj;
		if (autorizacao == null) {
			if (other.autorizacao != null)
				return false;
		} else if (!autorizacao.equals(other.autorizacao))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (codigoMunicipio == null) {
			if (other.codigoMunicipio != null)
				return false;
		} else if (!codigoMunicipio.equals(other.codigoMunicipio))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}
	
	

}