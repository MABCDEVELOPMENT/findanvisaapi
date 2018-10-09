package com.anvisa.model.persistence.rest.cosmetic.register;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_register_detail")
public class ContentCosmeticRegisterDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "social_reason", length = 200, nullable = false)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;
	
	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "autorization", length = 20, nullable = false)
	@JsonAlias(value = "autorizacao")
	String autorizacao;
	
	@Column(name = "product_name", length = 300, nullable = false)
	@JsonAlias(value = "nomeProduto")
	String nomeProduto;
	
	@Column(name = "category", length = 400, nullable = false)
	@JsonAlias(value = "categoria")
	String categoria;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "maturity_Registration", length = 20, nullable = true)
	@JsonAlias(value = "vencimentoRegistro")
	LocalDate vencimentoRegistro;
	
	@Column(name = "publication_Record", length = 20, nullable = true)
	@JsonAlias(value = "publicacaoRgistro")	
	LocalDate publicacaoRgistro;
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	List<CosmeticRegisterPresentation> apresentacoes;
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	List<CosmeticRegisterPetition> peticoes;
	
	@ManyToOne
    private ContentCosmeticRegister contentCosmeticRegister;
	
	public ContentCosmeticRegisterDetail() {
		// TODO Auto-generated constructor stub
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public LocalDate getVencimentoRegistro() {
		return vencimentoRegistro;
	}
	public void setVencimentoRegistro(LocalDate vencimentoRegistro) {
		this.vencimentoRegistro = vencimentoRegistro;
	}
	public LocalDate getPublicacaoRgistro() {
		return publicacaoRgistro;
	}
	public void setPublicacaoRgistro(LocalDate publicacaoRgistro) {
		this.publicacaoRgistro = publicacaoRgistro;
	}
	public List<CosmeticRegisterPresentation> getApresentacoes() {
		return apresentacoes;
	}
	public void setApresentacoes(List<CosmeticRegisterPresentation> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}
	public List<CosmeticRegisterPetition> getPeticoes() {
		return peticoes;
	}
	public void setPeticoes(List<CosmeticRegisterPetition> peticoes) {
		this.peticoes = peticoes;
	}
	public ContentCosmeticRegister getContentCosmeticRegister() {
		return contentCosmeticRegister;
	}
	public void setContentCosmeticRegister(ContentCosmeticRegister contentCosmeticRegister) {
		this.contentCosmeticRegister = contentCosmeticRegister;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apresentacoes == null) ? 0 : apresentacoes.hashCode());
		result = prime * result + ((autorizacao == null) ? 0 : autorizacao.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((contentCosmeticRegister == null) ? 0 : contentCosmeticRegister.hashCode());
		result = prime * result + ((nomeProduto == null) ? 0 : nomeProduto.hashCode());
		result = prime * result + ((peticoes == null) ? 0 : peticoes.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((publicacaoRgistro == null) ? 0 : publicacaoRgistro.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((vencimentoRegistro == null) ? 0 : vencimentoRegistro.hashCode());
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
		if (!(obj instanceof ContentCosmeticRegisterDetail)) {
			return false;
		}

		ContentCosmeticRegisterDetail other = (ContentCosmeticRegisterDetail) obj;
		if (apresentacoes == null) {
			if (other.apresentacoes != null)
				return false;
		} else if (!apresentacoes.equals(other.getApresentacoes()))
			return false;
		if (autorizacao == null) {
			if (other.autorizacao != null)
				return false;
		} else if (!autorizacao.equals(other.autorizacao))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
/*		if (contentCosmeticRegister == null) {
			if (other.contentCosmeticRegister != null)
				return false;
		} else if (!contentCosmeticRegister.equals(other.contentCosmeticRegister))
			return false;
*/		if (nomeProduto == null) {
			if (other.nomeProduto != null)
				return false;
		} else if (!nomeProduto.equals(other.nomeProduto))
			return false;
		if (peticoes == null) {
			if (other.peticoes != null)
				return false;
		} else if (!peticoes.equals(other.getPeticoes()))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (publicacaoRgistro == null) {
			if (other.publicacaoRgistro != null)
				return false;
		} else if (!publicacaoRgistro.equals(other.publicacaoRgistro))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (vencimentoRegistro == null) {
			if (other.vencimentoRegistro != null)
				return false;
		} else if (!vencimentoRegistro.equals(other.vencimentoRegistro))
			return false;
		return true;
	}
	
	
}
