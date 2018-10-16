package com.anvisa.model.persistence.rest.cosmetic.notification;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cosmetic_notification")
public class ContentCosmeticNotification extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "subject_matter", length = 300, nullable = false)
	@JsonAlias(value = "assunto")
	String assunto;

	@Column(name = "office_hour", length = 20, nullable = true)
	@JsonAlias(value = "expedientePeticao")
	String expedientePeticao;

	@Column(name = "transaction", length = 20, nullable = false)
	@JsonAlias(value = "transacao")
	String transacao;

	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;

	@Column(name = "expedient_process", length = 20, nullable = false)
	@JsonAlias(value = "expedienteProcesso")
	String expedienteProcesso;

	@Column(name = "product", length = 300, nullable = false)
	@JsonAlias(value = "produto")
	String produto;

	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;

	@Column(name = "social_reason", length = 300, nullable = false)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;

	@Column(name = "situation", length = 300, nullable = false)
	@JsonAlias(value = "situacao")
	String situacao;

	@Column(name = "maturity", length = 8, nullable = true)
	@JsonAlias(value = "vencimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate vencimento;

	@Column(name = "status_maturity", length = 60, nullable = true)
	@JsonAlias(value = "statusVencimento")
	String statusVencimento;

	@Column(name = "company", length = 200, nullable = true)
	@JsonAlias(value = "empresa")
	String empresa;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	ContentCosmeticNotificationDetail contentCosmeticNotificationDetail;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getExpedientePeticao() {
		return expedientePeticao;
	}

	public void setExpedientePeticao(String expedientePeticao) {
		this.expedientePeticao = expedientePeticao;
	}

	public String getTransacao() {
		return transacao;
	}

	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getExpedienteProcesso() {
		return expedienteProcesso;
	}

	public void setExpedienteProcesso(String expedienteProcesso) {
		this.expedienteProcesso = expedienteProcesso;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public String getStatusVencimento() {
		return statusVencimento;
	}

	public void setStatusVencimento(String statusVencimento) {
		this.statusVencimento = statusVencimento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public ContentCosmeticNotificationDetail getContentCosmeticNotificationDetail() {
		return contentCosmeticNotificationDetail;
	}

	public void setContentCosmeticNotificationDetail(ContentCosmeticNotificationDetail contentCosmeticNotificationDetail) {
		this.contentCosmeticNotificationDetail = contentCosmeticNotificationDetail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result
				+ ((contentCosmeticNotificationDetail == null) ? 0 : contentCosmeticNotificationDetail.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((expedientePeticao == null) ? 0 : expedientePeticao.hashCode());
		result = prime * result + ((expedienteProcesso == null) ? 0 : expedienteProcesso.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((statusVencimento == null) ? 0 : statusVencimento.hashCode());
		result = prime * result + ((transacao == null) ? 0 : transacao.hashCode());
		result = prime * result + ((vencimento == null) ? 0 : vencimento.hashCode());
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
		ContentCosmeticNotification other = (ContentCosmeticNotification) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (contentCosmeticNotificationDetail == null) {
			if (other.contentCosmeticNotificationDetail != null)
				return false;
		} else if (!contentCosmeticNotificationDetail.equals(other.contentCosmeticNotificationDetail))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (expedientePeticao == null) {
			if (other.expedientePeticao != null)
				return false;
		} else if (!expedientePeticao.equals(other.expedientePeticao))
			return false;
		if (expedienteProcesso == null) {
			if (other.expedienteProcesso != null)
				return false;
		} else if (!expedienteProcesso.equals(other.expedienteProcesso))
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
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (statusVencimento == null) {
			if (other.statusVencimento != null)
				return false;
		} else if (!statusVencimento.equals(other.statusVencimento))
			return false;
		if (transacao == null) {
			if (other.transacao != null)
				return false;
		} else if (!transacao.equals(other.transacao))
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
			return false;
		return true;
	}

}
