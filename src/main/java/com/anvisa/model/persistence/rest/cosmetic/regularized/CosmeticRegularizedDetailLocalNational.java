package com.anvisa.model.persistence.rest.cosmetic.regularized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_regularized_detail_local_national")
public class CosmeticRegularizedDetailLocalNational  extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "cnpj", length = 20)
	@JsonAlias(value = "cnpj")
	String cnpj;

	@Column(name = "social_reason", length = 300)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;

	@Column(name = "autorizatioin", length = 20)
	@JsonAlias(value = "autorizacao")
	String autorizacao;

	@Column(name = "state", length = 2)
	@JsonAlias(value = "uf")
	String uf;

	@Column(name = "city", length = 60)
	@JsonAlias(value = "cidade")
	String cidade;
	
	@Column(name = "codeZip", length = 10)
	@JsonAlias(value = "codigoMunicipio")
	String codigoMunicipio;
	
	@Column(name = "country", length = 30)
	@JsonAlias(value = "pais")
	String pais;

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