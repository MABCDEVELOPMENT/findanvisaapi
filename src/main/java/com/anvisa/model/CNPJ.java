package com.anvisa.model;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CNPJ extends AbstractBaseEntity {

	@Column(name = "full_name", length = 60, nullable = false)
	@JsonAlias(value = "fullName")
	private String fullName;

}
