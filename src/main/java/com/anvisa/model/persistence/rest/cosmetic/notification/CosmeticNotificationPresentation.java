package com.anvisa.model.persistence.rest.cosmetic.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_notification_presemtaton")
public class CosmeticNotificationPresentation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "presentation", length = 200, nullable = false)
	@JsonAlias(value = "apresentacao")
	String apresentacao;
	
	@Column(name = "tonality", length = 100)
	@JsonAlias(value = "tonalidade")
    String tonalidade;

	@Column(name = "eans", length = 100)
	@JsonAlias(value = "eans")
    String eans;

	@Column(name = "only_exportation")
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
}
