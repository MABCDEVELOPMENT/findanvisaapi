package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronize {
	
	
	ArrayList<BaseEntity> loadData(String cnpj);
	BaseEntity loadDetailData(String concat);
	
	BaseEntity parseData(JsonNode node);
	BaseEntity parseDetailData(JsonNode node);
	void persist(ArrayList<BaseEntity> itens);
	
}
