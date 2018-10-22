package com.anvisa.model.persistence.rest.saneante.notification;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "saneante_notification_petition")
public class SaneanteNotificadoPetition  extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "expediente", length = 20)
	@JsonAlias(value = "expediente")
	String expediente;
	
	@Column(name = "publication")
	@JsonAlias(value = "publicacao")
	@JsonFormat(pattern="dd/MM/yyyy")
    LocalDate publicacao;
	
	@Column(name = "transaction", length = 20)
	@JsonAlias(value = "transacao")
    String transacao;
	
	@Column(name = "subject", length = 600)
	@JsonAlias(value = "assunto")
    String assunto;
	
	@Column(name = "situation", length = 600)
	@JsonAlias(value = "situacao")
    String situacao;
    
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public LocalDate getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(LocalDate publicacao) {
		this.publicacao = publicacao;
	}
	public String getTransacao() {
		return transacao;
	}
	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((expediente == null) ? 0 : expediente.hashCode());
		result = prime * result + ((publicacao == null) ? 0 : publicacao.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((transacao == null) ? 0 : transacao.hashCode());
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
		if (!(obj instanceof SaneanteNotificadoPetition)) {
			return false;
		}
		SaneanteNotificadoPetition other = (SaneanteNotificadoPetition) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (expediente == null) {
			if (other.expediente != null)
				return false;
		} else if (!expediente.equals(other.expediente))
			return false;
		if (publicacao == null) {
			if (other.publicacao != null)
				return false;
		} else if (!publicacao.equals(other.publicacao))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (transacao == null) {
			if (other.transacao != null)
				return false;
		} else if (!transacao.equals(other.transacao))
			return false;
		return true;
	}

	
}
