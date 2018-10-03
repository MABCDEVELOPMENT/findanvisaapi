package com.anvisa.model.persistence.rest.cosmetic.register;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntityAudit;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_register_detail")
public class ContentCosmeticRegisterDetail extends BaseEntityAudit {

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
	
	@Column(name = "category", length = 200, nullable = false)
	@JsonAlias(value = "categoria")
	String categoria;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "maturity_Registration", length = 20, nullable = false)
	@JsonAlias(value = "vencimentoRegistro")
	LocalDate vencimentoRegistro;
	
	@Column(name = "publication_Record", length = 20, nullable = false)
	@JsonAlias(value = "publicacaoRgistro")	
	LocalDate publicacaoRgistro;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	List<CosmeticRegisterPresentation> apresentacoes;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	List<CosmeticRegisterPetition> peticoes;
	
	@OneToMany
    private ContentCosmeticRegister contentCosmeticRegister;
	
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
}
