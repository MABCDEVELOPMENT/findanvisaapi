package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;

import com.anvisa.model.persistence.AbstractBaseEntity;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronize {
	
	
	ArrayList<AbstractBaseEntity> loadData(String cnpj);
	AbstractBaseEntity loadDetailData(String concat);
	
	AbstractBaseEntity parseData(JsonNode node);
	AbstractBaseEntity parseDetailData(JsonNode node);
	
}
