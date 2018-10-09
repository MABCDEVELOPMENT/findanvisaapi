package com.anvisa.model.persistence.rest.cosmetic.register;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_register_apresentation")
public class CosmeticRegisterPresentation extends BaseEntity {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "code", length = 20)
	@JsonAlias(value = "codigo")
	String codigo;
	
	@Column(name = "number", length = 20, nullable = true)
	@JsonAlias(value = "numero")	
    String numero;
	
	@Column(name = "primary_package", length = 100, nullable = true)
	@JsonAlias(value = "embalagemPrimaria")	
    String embalagemPrimaria;
	
	@Column(name = "second_package", length = 100, nullable = true)
	@JsonAlias(value = "embalagemSecundaria")	
    String embalagemSecundaria;
	
	@Column(name = "tonality", length = 40, nullable = true)
	@JsonAlias(value = "tonalidade")	
    String tonalidade;

	@Column(name = "situation", length = 60, nullable = true)
	@JsonAlias(value = "situacao")
    String situacao;
	
	@Column(name = "featured", nullable = false)
	@JsonAlias(value = "destaque")
    boolean destaque;
	
	@Column(name = "register", length = 20, nullable = true)
	@JsonAlias(value = "registro")
    String registro;
    

    @JoinColumn(name = "detail_id")
    @MapKey(name = "Id")
    @MapKeyClass(Long.class)
    private ContentCosmeticRegisterDetail contentDetailCosmeticRegister;
    
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEmbalagemPrimaria() {
		return embalagemPrimaria+" - Primária";
	}
	public void setEmbalagemPrimaria(String embalagemPrimaria) {
		this.embalagemPrimaria = embalagemPrimaria;
	}
	public String getEmbalagemSecundaria() {
		return embalagemSecundaria+" - Secundária";
	}
	public void setEmbalagemSecundaria(String embalagemSecundaria) {
		this.embalagemSecundaria = embalagemSecundaria;
	}
	public String getTonalidade() {
		return tonalidade.equals("")?"Não se aplica para essa categoria":tonalidade;
	}
	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}
 	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public boolean isDestaque() {
		return destaque;
	}
	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public ContentCosmeticRegisterDetail getContentDetailCosmeticRegister() {
		return contentDetailCosmeticRegister;
	}
	public void setContentDetailCosmeticRegister(ContentCosmeticRegisterDetail contentDetailCosmeticRegister) {
		this.contentDetailCosmeticRegister = contentDetailCosmeticRegister;
	}
	
    
}
