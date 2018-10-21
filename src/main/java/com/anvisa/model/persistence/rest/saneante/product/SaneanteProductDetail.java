package com.anvisa.model.persistence.rest.saneante.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.foot.ContentFootDetail;
import com.anvisa.rest.detalhe.saneante.product.SaneanteProductApresentacao;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Entity
@Table(name = "saneante_product_detail")
public class SaneanteProductDetail  extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "social_reason", length = 200)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;
	
	@Column(name = "cnpj", length = 14)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "autorizationNumber", length = 20)
	@JsonAlias(value = "numeroAutorizacao")
	String numeroAutorizacao;
	
	@Column(name = "comercial_name", length = 200)
	@JsonAlias(value = "nomeComercial")
	String nomeComercial;
	
	@Column(name = "therapeutic_classes", length = 200, nullable = false)
	@JsonAlias(value = "classesTerapeuticas")
	String classesTerapeuticas;
	
	@Column(name = "register", length = 30, nullable = false)
	@JsonAlias(value = "registro")
	String registro;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "month_Year_maturity", length = 6, nullable = false)
	@JsonAlias(value = "mesAnoVencimento")
	String mesAnoVencimento;

	@OneToMany(cascade=CascadeType.ALL)
	List<SaneanteProductLabel> rotulos;

	@OneToMany(cascade=CascadeType.ALL)
	List<SaneanteProductPresentation> apresentacoes;

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

	public String getNumeroAutorizacao() {
		return numeroAutorizacao;
	}

	public void setNumeroAutorizacao(String numeroAutorizacao) {
		this.numeroAutorizacao = numeroAutorizacao;
	}

	public String getNomeComercial() {
		return nomeComercial;
	}

	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}

	public String getClassesTerapeuticas() {
		return classesTerapeuticas;
	}

	public void setClassesTerapeuticas(String classesTerapeuticas) {
		this.classesTerapeuticas = classesTerapeuticas;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getMesAnoVencimento() {
		return mesAnoVencimento;
	}

	public void setMesAnoVencimento(String mesAnoVencimento) {
		this.mesAnoVencimento = mesAnoVencimento;
	}

	public List<SaneanteProductLabel> getRotulos() {
		return rotulos;
	}

	public void setRotulos(ArrayList<SaneanteProductLabel> rotulos) {
		this.rotulos = rotulos;
	}

	public List<SaneanteProductPresentation> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(ArrayList<SaneanteProductPresentation> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apresentacoes == null) ? 0 : apresentacoes.hashCode());
		result = prime * result + ((classesTerapeuticas == null) ? 0 : classesTerapeuticas.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((mesAnoVencimento == null) ? 0 : mesAnoVencimento.hashCode());
		result = prime * result + ((nomeComercial == null) ? 0 : nomeComercial.hashCode());
		result = prime * result + ((numeroAutorizacao == null) ? 0 : numeroAutorizacao.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((registro == null) ? 0 : registro.hashCode());
		result = prime * result + ((rotulos == null) ? 0 : rotulos.hashCode());
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
		if (!(obj instanceof SaneanteProductDetail)) {
			return false;
		}
		SaneanteProductDetail other = (SaneanteProductDetail) obj;
		if (apresentacoes == null) {
			if (other.apresentacoes != null)
				return false;
		} else if (!apresentacoes.equals(other.apresentacoes))
			return false;
		if (classesTerapeuticas == null) {
			if (other.classesTerapeuticas != null)
				return false;
		} else if (!classesTerapeuticas.equals(other.classesTerapeuticas))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (mesAnoVencimento == null) {
			if (other.mesAnoVencimento != null)
				return false;
		} else if (!mesAnoVencimento.equals(other.mesAnoVencimento))
			return false;
		if (nomeComercial == null) {
			if (other.nomeComercial != null)
				return false;
		} else if (!nomeComercial.equals(other.nomeComercial))
			return false;
		if (numeroAutorizacao == null) {
			if (other.numeroAutorizacao != null)
				return false;
		} else if (!numeroAutorizacao.equals(other.numeroAutorizacao))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (registro == null) {
			if (other.registro != null)
				return false;
		} else if (!registro.equals(other.registro))
			return false;
		if (rotulos == null) {
			if (other.rotulos != null)
				return false;
		} else if (!rotulos.equals(other.rotulos))
			return false;
		return true;
	}
	
	public void loadApresentaçoes(JsonNode node, String attribute) {

		this.apresentacoes = new ArrayList<SaneanteProductPresentation>();

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		if (element != null) {

			for (JsonNode jsonNode : element) {

				SaneanteProductPresentation apresentacao = new SaneanteProductPresentation();
				apresentacao.loadApresentacao(jsonNode);
				this.apresentacoes.add(apresentacao);

			}
		}

	}

}
