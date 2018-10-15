package com.anvisa.rest.model;

import java.time.LocalDate;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ContentCosmeticRegularized {
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
    String processo;

     String produto;
     String tipo; 
     String situacao;
     LocalDate vencimento;

}
