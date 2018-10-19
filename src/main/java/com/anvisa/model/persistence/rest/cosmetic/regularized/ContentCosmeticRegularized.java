package com.anvisa.model.persistence.rest.cosmetic.regularized;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cosmetic_regularized")
public class ContentCosmeticRegularized extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
    String processo;

	@Column(name = "product", length = 300, nullable = false)
	@JsonAlias(value = "product")
    String produto;
	
	@Column(name = "type",      length = 100)
	@JsonAlias(value = "tipo")
    String tipo; 

	@Column(name = "situation", length = 300)
	@JsonAlias(value = "situacao")
    String situacao;
	
	@Column(name = "maturity")
	@JsonAlias(value = "vencimento")
    LocalDate vencimento;
	
	@Column(name = "dateUpdateRegister")
	@JsonAlias(value = "dataAlteracao")	
	@JsonFormat(pattern="dd/MM/yyyy")	
	LocalDate dataAlteracao;
	
	@Column(name = "dateRegister")
	@JsonAlias(value = "dataRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataRegistro;
	
	@Column(name = "qtdRecord")
	@JsonAlias(value = "qtdRegistro")	
	int qtdRegistro;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail;

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public LocalDate getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public int getQtdRegistro() {
		return qtdRegistro;
	}

	public void setQtdRegistro(int qtdRegistro) {
		this.qtdRegistro = qtdRegistro;
	}

	public ContentCosmeticRegularizedDetail getContentCosmeticRegularizedDetail() {
		return contentCosmeticRegularizedDetail;
	}

	public void setContentCosmeticRegularizedDetail(ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail) {
		this.contentCosmeticRegularizedDetail = contentCosmeticRegularizedDetail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((contentCosmeticRegularizedDetail == null) ? 0 : contentCosmeticRegularizedDetail.hashCode());
		result = prime * result + ((dataAlteracao == null) ? 0 : dataAlteracao.hashCode());
		result = prime * result + ((dataRegistro == null) ? 0 : dataRegistro.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + qtdRegistro;
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof ContentCosmeticRegularized)) {
			return false;
		}
		ContentCosmeticRegularized other = (ContentCosmeticRegularized) obj;
		if (contentCosmeticRegularizedDetail == null) {
			if (other.contentCosmeticRegularizedDetail != null)
				return false;
		} else if (!contentCosmeticRegularizedDetail.equals(other.contentCosmeticRegularizedDetail))
			return false;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null)
				return false;
		} else if (!dataAlteracao.equals(other.dataAlteracao))
			return false;
		if (dataRegistro == null) {
			if (other.dataRegistro != null)
				return false;
		} else if (!dataRegistro.equals(other.dataRegistro))
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
		if (qtdRegistro != other.qtdRegistro)
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
			return false;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null)
				return false;
		} else if (!dataAlteracao.equals(other.dataAlteracao))
			return false;
		if (dataRegistro == null) {
			if (other.dataRegistro != null)
				return false;
		} else if (!dataRegistro.equals(other.dataRegistro))
			return false;
		if (qtdRegistro!=other.qtdRegistro)
			return false;
		return true;
	}
	
	

}
