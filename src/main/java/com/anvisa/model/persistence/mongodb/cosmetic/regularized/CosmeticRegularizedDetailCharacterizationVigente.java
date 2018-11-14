package com.anvisa.model.persistence.mongodb.cosmetic.regularized;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class CosmeticRegularizedDetailCharacterizationVigente {

	@Field(value = "registro")
	String registro;
	
	@Field(value = "processo")
    String processo;
	
	@Field(value = "grupo")
    String grupo;
	
	@Field(value = "produto")
    String produto;

	@Field(value = "formaFisica")
    String formaFisica;
	
	@Field(value = "grupoDescartaveis")
    String grupoDescartaveis;

	public CosmeticRegularizedDetailCharacterizationVigente() {
		// TODO Auto-generated constructor stub
	}
	@PersistenceConstructor
	public CosmeticRegularizedDetailCharacterizationVigente(String registro, String processo, String grupo,
			String produto, String formaFisica, String grupoDescartaveis) {
		super();
		this.registro = registro;
		this.processo = processo;
		this.grupo = grupo;
		this.produto = produto;
		this.formaFisica = formaFisica;
		this.grupoDescartaveis = grupoDescartaveis;
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

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getFormaFisica() {
		return formaFisica;
	}

	public void setFormaFisica(String formaFisica) {
		this.formaFisica = formaFisica;
	}

	public String getGrupoDescartaveis() {
		return grupoDescartaveis;
	}

	public void setGrupoDescartaveis(String grupoDescartaveis) {
		this.grupoDescartaveis = grupoDescartaveis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((formaFisica == null) ? 0 : formaFisica.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((grupoDescartaveis == null) ? 0 : grupoDescartaveis.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((registro == null) ? 0 : registro.hashCode());
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
		if (!(obj instanceof CosmeticRegularizedDetailCharacterizationVigente)) {
			return false;
		}
		CosmeticRegularizedDetailCharacterizationVigente other = (CosmeticRegularizedDetailCharacterizationVigente) obj;
		if (formaFisica == null) {
			if (other.formaFisica != null)
				return false;
		} else if (!formaFisica.equals(other.formaFisica))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (grupoDescartaveis == null) {
			if (other.grupoDescartaveis != null)
				return false;
		} else if (!grupoDescartaveis.equals(other.grupoDescartaveis))
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
		if (registro == null) {
			if (other.registro != null)
				return false;
		} else if (!registro.equals(other.registro))
			return false;
		return true;
	}
	
	

}
