package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;


import java.util.ArrayList;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronizeMdb {
	
	
	ArrayList<BaseEntityMongoDB> loadData(String cnpj);
		
	BaseEntityMongoDB parseData(JsonNode node);
	
	void persist(ArrayList<BaseEntityMongoDB> itens);

	
}
