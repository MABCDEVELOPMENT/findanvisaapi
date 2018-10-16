package com.anvisa.model.persistence.rest.cosmetic.notification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.cosmetic.register.petition.CosmeticRegisterPetitionPresentation;
import com.anvisa.rest.detalhe.comestico.notificado.ApresentacaoCosmeticoNotificado;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cosmetic_notification_detail")
public class ContentCosmeticNotificationDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "subject_matter", length = 300, nullable = false)
	@JsonAlias(value = "assunto")
	String assunto;
	
	@Column(name = "product", length = 300, nullable = false)
	@JsonAlias(value = "produto")
	String produto;
	
	@Column(name = "company", length = 200, nullable = true)
	@JsonAlias(value = "empresa")
	String empresa;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	

	@Column(name = "area", length = 20, nullable = false)
	@JsonAlias(value = "area")
	String area;
	
	@Column(name = "situation", length = 300, nullable = false)
	@JsonAlias(value = "situacao")
	String situacao;

	@Column(name = "date_notification", length = 8, nullable = true)
	@JsonAlias(value = "dataNotificacao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate dataNotificacao;
	
	public ContentCosmeticNotificationDetail() {
		// TODO Auto-generated constructor stub
	}
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<CosmeticNotificationPresentation> apresentacoes;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public LocalDate getDataNotificacao() {
		return dataNotificacao;
	}

	public void setDataNotificacao(LocalDate dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}

	public List<CosmeticNotificationPresentation> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(List<CosmeticNotificationPresentation> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apresentacoes == null) ? 0 : apresentacoes.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((dataNotificacao == null) ? 0 : dataNotificacao.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		ContentCosmeticNotificationDetail other = (ContentCosmeticNotificationDetail) obj;
		if (apresentacoes == null) {
			if (other.apresentacoes != null)
				return false;
		} else if (!apresentacoes.equals(other.apresentacoes))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (dataNotificacao == null) {
			if (other.dataNotificacao != null)
				return false;
		} else if (!dataNotificacao.equals(other.dataNotificacao))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		return true;
	}

}
