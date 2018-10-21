package com.anvisa.model.persistence.rest.foot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "foot_detail")
public class ContentFootDetail extends BaseEntity {
	
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
	
	@Column(name = "comercial_name", length = 200, nullable = false)
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
	

	@Column(name = "brand", length = 60, nullable = true)
	@JsonAlias(value = "marca")
	String marca;
	
	
	@Column(name = "active_principle", length = 60, nullable = true)
	@JsonAlias(value = "principioAtivo")
	String principioAtivo;
	
	@Column(name = "primary_brand", length = 60, nullable = true)
	@JsonAlias(value = "embalagemPrimaria")
	String embalagemPrimaria;
	
	@Column(name = "manufacturing_site", length = 60, nullable = true)
	@JsonAlias(value = "localFabricacao")
	String localFabricacao;
	
	@Column(name = "administrative_routes", length = 60, nullable = true)
	@JsonAlias(value = "viasAdministrativa")	
	String viasAdministrativa;

	@Column(name = "ifa_unique", length = 3, nullable = true)
	@JsonAlias(value = "ifaUnico")	
	String ifaUnico;
	
	@Column(name = "conservation", length = 60, nullable = true)
	@JsonAlias(value = "conservacao")	
	String conservacao;
	

	
	public ContentFootDetail() {
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
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPrincipioAtivo() {
		return principioAtivo;
	}
	public void setPrincipioAtivo(String principioAtivo) {
		this.principioAtivo = principioAtivo;
	}
	public String getEmbalagemPrimaria() {
		return embalagemPrimaria;
	}
	public void setEmbalagemPrimaria(String embalagemPrimaria) {
		this.embalagemPrimaria = embalagemPrimaria;
	}
	public String getLocalFabricacao() {
		return localFabricacao;
	}
	public void setLocalFabricacao(String localFabricacao) {
		this.localFabricacao = localFabricacao;
	}
	public String getViasAdministrativa() {
		return viasAdministrativa;
	}
	public void setViasAdministrativa(String viasAdministrativa) {
		this.viasAdministrativa = viasAdministrativa;
	}
	public String getIfaUnico() {
		return ifaUnico;
	}
	public void setIfaUnico(String ifaUnico) {
		this.ifaUnico = ifaUnico;
	}
	public String getConservacao() {
		return conservacao;
	}
	public void setConservacao(String conservacao) {
		this.conservacao = conservacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classesTerapeuticas == null) ? 0 : classesTerapeuticas.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((conservacao == null) ? 0 : conservacao.hashCode());
		result = prime * result + ((embalagemPrimaria == null) ? 0 : embalagemPrimaria.hashCode());
		result = prime * result + ((ifaUnico == null) ? 0 : ifaUnico.hashCode());
		result = prime * result + ((localFabricacao == null) ? 0 : localFabricacao.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((mesAnoVencimento == null) ? 0 : mesAnoVencimento.hashCode());
		result = prime * result + ((nomeComercial == null) ? 0 : nomeComercial.hashCode());
		result = prime * result + ((principioAtivo == null) ? 0 : principioAtivo.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((registro == null) ? 0 : registro.hashCode());
		result = prime * result + ((viasAdministrativa == null) ? 0 : viasAdministrativa.hashCode());
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
		if (!(obj instanceof ContentFootDetail)) {
			return false;
		}
		ContentFootDetail other = (ContentFootDetail) obj;
		if (classesTerapeuticas == null) {
			if (other.classesTerapeuticas != null) {
				return false;
			}
		} else if (!classesTerapeuticas.equals(other.classesTerapeuticas)) {
			return false;
		}
		if (cnpj == null) {
			if (other.cnpj != null) {
				return false;
			}
		} else if (!cnpj.equals(other.cnpj)) {
			return false;
		}
		if (conservacao == null) {
			if (other.conservacao != null) {
				return false;
			}
		} else if (!conservacao.equals(other.conservacao)) {
			return false;
		}
		if (embalagemPrimaria == null) {
			if (other.embalagemPrimaria != null) {
				return false;
			}
		} else if (!embalagemPrimaria.equals(other.embalagemPrimaria)) {
			return false;
		}
		if (ifaUnico == null) {
			if (other.ifaUnico != null) {
				return false;
			}
		} else if (!ifaUnico.equals(other.ifaUnico)) {
			return false;
		}
		if (localFabricacao == null) {
			if (other.localFabricacao != null) {
				return false;
			}
		} else if (!localFabricacao.equals(other.localFabricacao)) {
			return false;
		}
		if (marca == null) {
			if (other.marca != null) {
				return false;
			}
		} else if (!marca.equals(other.marca)) {
			return false;
		}
		if (mesAnoVencimento == null) {
			if (other.mesAnoVencimento != null) {
				return false;
			}
		} else if (!mesAnoVencimento.equals(other.mesAnoVencimento)) {
			return false;
		}
		if (nomeComercial == null) {
			if (other.nomeComercial != null) {
				return false;
			}
		} else if (!nomeComercial.equals(other.nomeComercial)) {
			return false;
		}
		if (principioAtivo == null) {
			if (other.principioAtivo != null) {
				return false;
			}
		} else if (!principioAtivo.equals(other.principioAtivo)) {
			return false;
		}
		if (processo == null) {
			if (other.processo != null) {
				return false;
			}
		} else if (!processo.equals(other.processo)) {
			return false;
		}
		if (razaoSocial == null) {
			if (other.razaoSocial != null) {
				return false;
			}
		} else if (!razaoSocial.equals(other.razaoSocial)) {
			return false;
		}
		if (registro == null) {
			if (other.registro != null) {
				return false;
			}
		} else if (!registro.equals(other.registro)) {
			return false;
		}
		if (viasAdministrativa == null) {
			if (other.viasAdministrativa != null) {
				return false;
			}
		} else if (!viasAdministrativa.equals(other.viasAdministrativa)) {
			return false;
		}
		return true;
	}

}
