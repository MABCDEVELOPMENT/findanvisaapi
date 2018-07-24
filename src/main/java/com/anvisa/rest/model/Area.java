package com.anvisa.rest.model;

import java.util.List;

public class Area {
	
	int id;
	String description;
	
	public Area(String description,int id) {
		this.id = id;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
