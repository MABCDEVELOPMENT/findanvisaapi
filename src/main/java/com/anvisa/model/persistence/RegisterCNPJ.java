package com.anvisa.model.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "RegisterCNPJ")
public class RegisterCNPJ extends AbstractBaseEntity  {


	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	private String cnpj;

	@Column(name = "full_name", length = 60, nullable = false)
	@JsonAlias(value = "fullName")
	private String fullName;

	@Column(name = "category", length = 1, nullable = false)
	@JsonAlias(value = "category")
	private int category;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
