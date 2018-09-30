package com.anvisa.model.persistence.rest.cosmetic.register;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntityAudit;
import com.anvisa.rest.detalhe.comestico.registrado.ApresentacaoCosmeticoRegistrado;
import com.anvisa.rest.detalhe.comestico.registrado.PeticaoCosmeticoRegistrado;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_register_detail")
public class ContentDetailCosmeticRegister extends BaseEntityAudit {

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
	
	@Column(name = "comercial_name", length = 20, nullable = false)
	@JsonAlias(value = "nomeComercial")
	String autorizacao;
	
	@Column(name = "product_name", length = 300, nullable = false)
	@JsonAlias(value = "nomeProduto")
	String nomeProduto;
	
	@Column(name = "category", length = 60, nullable = false)
	@JsonAlias(value = "categoria")
	String categoria;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "maturity_Registration", length = 20, nullable = false)
	@JsonAlias(value = "vencimentoRegistro")
	String vencimentoRegistro;
	
	@Column(name = "publication_Record", length = 20, nullable = false)
	@JsonAlias(value = "publicacaoRgistro")	
	String publicacaoRgistro;
	
	@OneToMany(fetch = FetchType.LAZY)
	ArrayList<ApresentacaoCosmeticoRegistrado> apresentacoes;
	
	ArrayList<PeticaoCosmeticoRegistrado> peticoes;
	
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
	public String getVencimentoRegistro() {
		return vencimentoRegistro;
	}
	public void setVencimentoRegistro(String vencimentoRegistro) {
		this.vencimentoRegistro = vencimentoRegistro;
	}
	public String getPublicacaoRgistro() {
		return publicacaoRgistro;
	}
	public void setPublicacaoRgistro(String publicacaoRgistro) {
		this.publicacaoRgistro = publicacaoRgistro;
	}
	public ArrayList<ApresentacaoCosmeticoRegistrado> getApresentacoes() {
		return apresentacoes;
	}
	public void setApresentacoes(ArrayList<ApresentacaoCosmeticoRegistrado> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}
	public ArrayList<PeticaoCosmeticoRegistrado> getPeticoes() {
		return peticoes;
	}
	public void setPeticoes(ArrayList<PeticaoCosmeticoRegistrado> peticoes) {
		this.peticoes = peticoes;
	}
}
