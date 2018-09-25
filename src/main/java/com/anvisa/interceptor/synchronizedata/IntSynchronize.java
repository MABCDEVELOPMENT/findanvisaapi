package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;

import com.anvisa.model.persistence.AbstractBaseEntity;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronize {
	
	public String URL = "";
	
	ArrayList<AbstractBaseEntity> loadData(String cnpj);
	
	AbstractBaseEntity parseData(JsonNode node);
	
}
