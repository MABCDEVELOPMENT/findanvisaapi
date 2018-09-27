package com.anvisa.rest.model;

import javax.persistence.Column;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.BaseEntityAudit;
import com.fasterxml.jackson.annotation.JsonAlias;

public class CNPJ extends BaseEntityAudit {

	@Column(name = "full_name", length = 60, nullable = false)
	@JsonAlias(value = "fullName")
	private String fullName;

}
