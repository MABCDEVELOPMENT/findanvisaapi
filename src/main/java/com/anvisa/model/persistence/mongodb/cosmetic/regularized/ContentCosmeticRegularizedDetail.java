package com.anvisa.model.persistence.mongodb.cosmetic.regularized;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("contentCosmeticRegularizedDetail")
@Document
public class ContentCosmeticRegularizedDetail {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field(value = "cnpj")
	String cnpj;
	
	@Field(value = "processo")
	String processo;
	
	@Field(value = "product")
	String produto;
	
	@Field(value = "tipo")
	String tipo;
	
	@Field(value = "situacao")
	String situacao;
	
	@Field(value = "data")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate data;
	
	@Field(value = "caracterizacaoVigente")
	CosmeticRegularizedDetailCharacterizationVigente caracterizacaoVigente;
	
	@Field(value = "empresaDetentora")
	CosmeticRegularizedDetailHoldingCompany empresaDetentora;
	
	@Field(value = "destinacoes")
	String destinacoes;
	
	@Field(value = "locaisNacionais")
	CosmeticRegularizedDetailLocalNational locaisNacionais;
	
	@Field(value = "apresentacoes")
	List<CosmeticRegularizedDatailPresentation> apresentacoes;
	
	public ContentCosmeticRegularizedDetail() {
		// TODO Auto-generated constructor stub
	}
	
	@PersistenceConstructor
	public ContentCosmeticRegularizedDetail(String cnpj, String processo, String produto, String tipo, String situacao,
			LocalDate data, CosmeticRegularizedDetailCharacterizationVigente caracterizacaoVigente,
			CosmeticRegularizedDetailHoldingCompany empresaDetentora, String destinacoes,
			CosmeticRegularizedDetailLocalNational locaisNacionais,
			List<CosmeticRegularizedDatailPresentation> apresentacoes) {
		super();
		this.cnpj = cnpj;
		this.processo = processo;
		this.produto = produto;
		this.tipo = tipo;
		this.situacao = situacao;
		this.data = data;
		this.caracterizacaoVigente = caracterizacaoVigente;
		this.empresaDetentora = empresaDetentora;
		this.destinacoes = destinacoes;
		this.locaisNacionais = locaisNacionais;
		this.apresentacoes = apresentacoes;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public CosmeticRegularizedDetailHoldingCompany getEmpresaDetentora() {
		return empresaDetentora;
	}

	public void setEmpresaDetentora(CosmeticRegularizedDetailHoldingCompany empresaDetentora) {
		this.empresaDetentora = empresaDetentora;
	}

	public String getDestinacoes() {
		return destinacoes;
	}

	public void setDestinacoes(String destinacoes) {
		this.destinacoes = destinacoes;
	}

	public CosmeticRegularizedDetailCharacterizationVigente getCaracterizacaoVigente() {
		return caracterizacaoVigente;
	}

	public void setCaracterizacaoVigente(CosmeticRegularizedDetailCharacterizationVigente caracterizacaoVigente) {
		this.caracterizacaoVigente = caracterizacaoVigente;
	}

	public CosmeticRegularizedDetailLocalNational getLocaisNacionais() {
		return locaisNacionais;
	}

	public void setLocaisNacionais(CosmeticRegularizedDetailLocalNational locaisNacionais) {
		this.locaisNacionais = locaisNacionais;
	}

	public List<CosmeticRegularizedDatailPresentation> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(List<CosmeticRegularizedDatailPresentation> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apresentacoes == null) ? 0 : apresentacoes.hashCode());
		result = prime * result + ((caracterizacaoVigente == null) ? 0 : caracterizacaoVigente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((destinacoes == null) ? 0 : destinacoes.hashCode());
		result = prime * result + ((empresaDetentora == null) ? 0 : empresaDetentora.hashCode());
		result = prime * result + ((locaisNacionais == null) ? 0 : locaisNacionais.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof ContentCosmeticRegularizedDetail)) {
			return false;
		}
		ContentCosmeticRegularizedDetail other = (ContentCosmeticRegularizedDetail) obj;
		if (apresentacoes == null) {
			if (other.apresentacoes != null)
				return false;
		} else if (!apresentacoes.equals(other.apresentacoes))
			return false;
		if (caracterizacaoVigente == null) {
			if (other.caracterizacaoVigente != null)
				return false;
		} else if (!caracterizacaoVigente.equals(other.caracterizacaoVigente))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (destinacoes == null) {
			if (other.destinacoes != null)
				return false;
		} else if (!destinacoes.equals(other.destinacoes))
			return false;
		if (empresaDetentora == null) {
			if (other.empresaDetentora != null)
				return false;
		} else if (!empresaDetentora.equals(other.empresaDetentora))
			return false;
		if (locaisNacionais == null) {
			if (other.locaisNacionais != null)
				return false;
		} else if (!locaisNacionais.equals(other.locaisNacionais))
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
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	

}
