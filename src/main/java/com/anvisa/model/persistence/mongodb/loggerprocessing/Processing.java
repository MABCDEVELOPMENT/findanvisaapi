package com.anvisa.model.persistence.mongodb.loggerprocessing;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;

@Document(collection="processing")
public class Processing extends BaseEntityMongoDB {

	private RegisterCNPJ cnpj;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFinal;
	
	public RegisterCNPJ getCnpj() {
		return cnpj;
	}
	public void setCnpj(RegisterCNPJ cnpj) {
		this.cnpj = cnpj;
	}
	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public LocalDateTime getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(LocalDateTime horaFinal) {
		this.horaFinal = horaFinal;
	}

}
