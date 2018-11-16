package com.anvisa.model.persistence.mongodb.loggerprocessing;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;

@Document(collection="processing")
public class Processing extends BaseEntityMongoDB {

	private RegisterCNPJ cnpj;
	private LocalDate dataProcessamento;
	
	public RegisterCNPJ getCnpj() {
		return cnpj;
	}
	public void setCnpj(RegisterCNPJ cnpj) {
		this.cnpj = cnpj;
	}
	public LocalDate getDataProcessamento() {
		return dataProcessamento;
	}
	public void setDataProcessamento(LocalDate dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

}
