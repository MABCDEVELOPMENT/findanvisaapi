package com.anvisa.model.persistence.rest.saneante.notification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.saneante.product.SaneanteProduct;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "saneante_notification_presentation")
public class SaneanteNotificationPresentation extends BaseEntity {

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
	
	@OneToMany(cascade=CascadeType.ALL)
    List<SaneanteNotificationEan> eans;
	
	@Column(name = "version", length = 10)
	@JsonAlias(value = "versao")
    String versao;
	
	@Column(name = "justExport")
	@JsonAlias(value = "apenasExportacao")
    boolean apenasExportacao;
	
	
	@Column(name = "acceptedLabel")
	@JsonAlias(value = "rotuloAprovado")
    boolean rotuloAprovado;
    
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
	public List<SaneanteNotificationEan> getEans() {
		return eans;
	}
	public void setEans(ArrayList<SaneanteNotificationEan> eans) {
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
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (apenasExportacao ? 1231 : 1237);
		result = prime * result + ((apresentacao == null) ? 0 : apresentacao.hashCode());
		result = prime * result + ((eans == null) ? 0 : eans.hashCode());
		result = prime * result + (rotuloAprovado ? 1231 : 1237);
		result = prime * result + ((tonalidade == null) ? 0 : tonalidade.hashCode());
		result = prime * result + ((versao == null) ? 0 : versao.hashCode());
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
		if (!(obj instanceof SaneanteNotificationPresentation)) {
			return false;
		}
		
		SaneanteNotificationPresentation other = (SaneanteNotificationPresentation) obj;
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
		if (versao == null) {
			if (other.versao != null)
				return false;
		} else if (!versao.equals(other.versao))
			return false;
		return true;
	}
	
	
}
