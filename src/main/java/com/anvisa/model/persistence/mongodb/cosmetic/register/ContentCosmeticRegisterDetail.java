package com.anvisa.model.persistence.mongodb.cosmetic.register;

import java.time.LocalDate;
import java.util.List;

import com.anvisa.model.persistence.mongodb.cosmetic.register.petition.CosmeticRegisterPetition;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.CosmeticRegisterPresentation;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;


public class ContentCosmeticRegisterDetail {



	@JsonAlias(value = "razaoSocial")
	String razaoSocial;
	
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@JsonAlias(value = "autorizacao")
	String autorizacao;
	
	@JsonAlias(value = "nomeProduto")
	String nomeProduto;
	
	@JsonAlias(value = "categoria")
	String categoria;
	
	@JsonAlias(value = "processo")
	String processo;
	
	@JsonAlias(value = "vencimentoRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate vencimentoRegistro;
	
	@JsonAlias(value = "publicacaoRgistro")	
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate publicacaoRgistro;
	
	List<CosmeticRegisterPresentation> apresentacoes;
	
	List<CosmeticRegisterPetition> peticoes;
	
	
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apresentacoes == null) ? 0 : apresentacoes.hashCode());
		result = prime * result + ((autorizacao == null) ? 0 : autorizacao.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		//result = prime * result + ((contentCosmeticRegister == null) ? 0 : contentCosmeticRegister.hashCode());
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