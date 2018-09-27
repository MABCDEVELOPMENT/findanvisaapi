package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;

import com.anvisa.model.persistence.BaseEntityAudit;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronize {
	
	
	ArrayList<BaseEntityAudit> loadData(String cnpj);
	BaseEntityAudit loadDetailData(String concat);
	
	BaseEntityAudit parseData(JsonNode node);
	BaseEntityAudit parseDetailData(JsonNode node);
	void persist(ArrayList<BaseEntityAudit> itens);
	
}
