package com.anvisa.rest;

import java.time.LocalDate;

public class QueryRecordLogParameter {
	
	String cnpj;
	Long category;
	Long option;
	String dateInitial;
	String dateFinal;
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public Long getOption() {
		return option;
	}
	public void setOption(Long option) {
		this.option = option;
	}
	public String getDateInitial() {
		return dateInitial;
	}
	public void setDateInitial(String dateInitial) {
		this.dateInitial = dateInitial;
	}
	public String getDateFinal() {
		return dateFinal;
	}
	public void setDateFinal(String dateFinal) {
		this.dateFinal = dateFinal;
	}
}
