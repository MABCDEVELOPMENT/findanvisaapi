package com.anvisa.model.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Transient
	private boolean sendNotification;

	public boolean isSendNotification() {
		return sendNotification;
	}

	public void setSendNotification(boolean sendNotification) {
		this.sendNotification = sendNotification;
	}

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
