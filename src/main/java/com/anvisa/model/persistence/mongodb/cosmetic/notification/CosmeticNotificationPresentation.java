package com.anvisa.model.persistence.mongodb.cosmetic.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;


public class CosmeticNotificationPresentation {

	@JsonAlias(value = "apresentacao")
	String apresentacao;
	
	@JsonAlias(value = "tonalidade")
    String tonalidade;

	@JsonAlias(value = "eans")
    String eans;

	@JsonAlias(value = "apenasExportacao")
    boolean apenasExportacao;

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public String getTonalidade() {
		return tonalidade;
	}

	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}

	public String getEans() {
		return eans;
	}

	public void setEans(String eans) {
		this.eans = eans;
	}

	public boolean isApenasExportacao() {
		return apenasExportacao;
	}

	public void setApenasExportacao(boolean apenasExportacao) {
		this.apenasExportacao = apenasExportacao;
	}

	public boolean isRotuloAprovado() {
		return rotuloAprovado;
	}

	public void setRotuloAprovado(boolean rotuloAprovado) {
		this.rotuloAprovado = rotuloAprovado;
	}

	@Column(name = "only_import")
	@JsonAlias(value = "rotuloAprovado")
    boolean rotuloAprovado;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (apenasExportacao ? 1231 : 1237);
		result = prime * result + ((apresentacao == null) ? 0 : apresentacao.hashCode());
		result = prime * result + ((eans == null) ? 0 : eans.hashCode());
		result = prime * result + (rotuloAprovado ? 1231 : 1237);
		result = prime * result + ((tonalidade == null) ? 0 : tonalidade.hashCode());
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
		if (!(obj instanceof ContentCosmeticNotification)) {
			return false;
		}
		CosmeticNotificationPresentation other = (CosmeticNotificationPresentation) obj;
		if (apenasExportacao != other.apenasExportacao)
			return false;
		if (apresentacao == null) {
			if (other.apresentacao != null)
				return false;
		} else if (!apresentacao.equals(other.apresentacao))
			return false;
		if (eans == null) {
			if (other.eans != null)
				return false;
		} else if (!eans.equals(other.eans))
			return false;
		if (rotuloAprovado != other.rotuloAprovado)
			return false;
		if (tonalidade == null) {
			if (other.tonalidade != null)
				return false;
		} else if (!tonalidade.equals(other.tonalidade))
			return false;
		return true;
	}
}
