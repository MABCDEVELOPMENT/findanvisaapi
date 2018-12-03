package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;


import java.util.ArrayList;

import org.bson.Document;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronizeMdb {
	
	
	ArrayList<BaseEntityMongoDB> loadData(String cnpj);
	
	ArrayList<Document> loadDataDocument(String cnpj);
		
	BaseEntityMongoDB parseData(String cnpj,JsonNode node);
	
	void persist(String cnpj, ArrayList<BaseEntityMongoDB> itens, LoggerProcessing loggerProcessing);
	
}
