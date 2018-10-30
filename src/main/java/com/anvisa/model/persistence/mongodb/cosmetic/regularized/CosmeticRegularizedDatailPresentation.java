package com.anvisa.model.persistence.mongodb.cosmetic.regularized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

public class CosmeticRegularizedDatailPresentation {
	
	@JsonAlias(value = "periodoValidade")
	String periodoValidade;
	
	@JsonAlias(value = "tipoValidade")
    String tipoValidade;
	
	@JsonAlias(value = "restricaoUso")
    String restricaoUso;

	@JsonAlias(value = "cuidadoConservacao")	
    String cuidadoConservacao;
	
	@JsonAlias(value = "embalagemPrimaria")	
    String embalagemPrimaria;
	
	@JsonAlias(value = "embalagemPrimaria")	
    String embalagemSecundaria;

	public String getPeriodoValidade() {
		return periodoValidade;
	}

	public void setPeriodoValidade(String periodoValidade) {
		this.periodoValidade = periodoValidade;
	}

	public String getTipoValidade() {
		return tipoValidade;
	}

	public void setTipoValidade(String tipoValidade) {
		this.tipoValidade = tipoValidade;
	}

	public String getRestricaoUso() {
		return restricaoUso;
	}

	public void setRestricaoUso(String restricaoUso) {
		this.restricaoUso = restricaoUso;
	}

	public String getCuidadoConservacao() {
		return cuidadoConservacao;
	}

	public void setCuidadoConservacao(String cuidadoConservacao) {
		this.cuidadoConservacao = cuidadoConservacao;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cuidadoConservacao == null) ? 0 : cuidadoConservacao.hashCode());
		result = prime * result + ((embalagemPrimaria == null) ? 0 : embalagemPrimaria.hashCode());
		result = prime * result + ((embalagemSecundaria == null) ? 0 : embalagemSecundaria.hashCode());
		result = prime * result + ((periodoValidade == null) ? 0 : periodoValidade.hashCode());
		result = prime * result + ((restricaoUso == null) ? 0 : restricaoUso.hashCode());
		result = prime * result + ((tipoValidade == null) ? 0 : tipoValidade.hashCode());
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
		if (!(obj instanceof CosmeticRegularizedDatailPresentation)) {
			return false;
		}
		CosmeticRegularizedDatailPresentation other = (CosmeticRegularizedDatailPresentation) obj;
		if (cuidadoConservacao == null) {
			if (other.cuidadoConservacao != null)
				return false;
		} else if (!cuidadoConservacao.equals(other.cuidadoConservacao))
			return false;
		if (embalagemPrimaria == null) {
			if (other.embalagemPrimaria != null)
				return false;
		} else if (!embalagemPrimaria.equals(other.embalagemPrimaria))
			return false;
		if (embalagemSecundaria == null) {
			if (other.embalagemSecundaria != null)
				return false;
		} else if (!embalagemSecundaria.equals(other.embalagemSecundaria))
			return false;
		if (periodoValidade == null) {
			if (other.periodoValidade != null)
				return false;
		} else if (!periodoValidade.equals(other.periodoValidade))
			return false;
		if (restricaoUso == null) {
			if (other.restricaoUso != null)
				return false;
		} else if (!restricaoUso.equals(other.restricaoUso))
			return false;
		if (tipoValidade == null) {
			if (other.tipoValidade != null)
				return false;
		} else if (!tipoValidade.equals(other.tipoValidade))
			return false;
		return true;
	}

}
