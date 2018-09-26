package com.anvisa.model.persistence.rest.foot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "foot_detail")
public class ContentDetalFoot extends AbstractBaseEntity {
	

	@Column(name = "social_reason", length = 100, nullable = false)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;
	
	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "comercial_name", length = 100, nullable = false)
	@JsonAlias(value = "nomeComercial")
	String nomeComercial;
	
	@Column(name = "therapeutic_classes", length = 100, nullable = false)
	@JsonAlias(value = "classesTerapeuticas")
	String classesTerapeuticas;
	
	@Column(name = "register", length = 20, nullable = false)
	@JsonAlias(value = "registro")
	String registro;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "month_Year_maturity", length = 6, nullable = false)
	@JsonAlias(value = "mesAnoVencimento")
	String mesAnoVencimento;
	

	@Column(name = "brand", length = 20, nullable = false)
	@JsonAlias(value = "marca")
	String marca;
	
	
	@Column(name = "active principle", length = 60, nullable = false)
	@JsonAlias(value = "principioAtivo")
	String principioAtivo;
	
	@Column(name = "active_principle", length = 60, nullable = false)
	@JsonAlias(value = "principioAtivo")
	String embalagemPrimaria;
	
	@Column(name = "manufacturing_site", length = 60, nullable = false)
	@JsonAlias(value = "localFabricacao")
	String localFabricacao;
	
	@Column(name = "administrative_routes", length = 60, nullable = false)
	@JsonAlias(value = "viasAdministrativa")	
	String viasAdministrativa;

	@Column(name = "ifa_unique", length = 3, nullable = false)
	@JsonAlias(value = "ifaUnico")	
	String ifaUnico;
	
	@Column(name = "conservation", length = 60, nullable = false)
	@JsonAlias(value = "conservacao")	
	String conservacao;
	
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


}
