package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvisa.model.persistence.AbstractBaseEntity;
import com.fasterxml.jackson.databind.JsonNode;

public interface IntSynchronize {
	
	
	JpaRepository<AbstractBaseEntity, Long> repository = null;

	ArrayList<AbstractBaseEntity> loadData(String cnpj);
	
	AbstractBaseEntity parseData(JsonNode node);
	
}
