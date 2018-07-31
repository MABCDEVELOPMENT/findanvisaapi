package com.anvisa.rest;

public class QueryRecordParameter {
	
	String cnpj;
	
	String numberProcess;
	
	String productName;
	
	String brand;
	
	Long category;
	
	Long option;
	
	String registerNumber;
	
	Long typeProdutc;
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNumberProcess() {
		if (this.numberProcess!=null) {
			this.numberProcess = this.numberProcess.replace(".", "");
			this.numberProcess = this.numberProcess.replace("-", "");
			this.numberProcess = this.numberProcess.replace("/", "");
		}
		return numberProcess;
	}
	public void setNumberProcess(String numberProcess) {
		this.numberProcess = numberProcess;
	}
	public String getProductName() {
		return this.productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
	public String getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	public Long getTypeProdutc() {
		return typeProdutc;
	}
	public void setTypeProdutc(Long typeProdutc) {
		this.typeProdutc = typeProdutc;
	}
	
	

}
