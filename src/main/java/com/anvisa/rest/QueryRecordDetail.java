package com.anvisa.rest;

public class QueryRecordDetail {

	 Long category; 
	 Long option;
	 String value;
	 
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
	public String getValue() {
		if (this.value!=null) {
			this.value = this.value.replace(".", "");
			this.value = this.value.replace("-", "");
			this.value = this.value.replace("/", "");
		}
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}