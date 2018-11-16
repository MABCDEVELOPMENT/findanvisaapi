package com.anvisa.model.persistence.mongodb.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anvisa.model.persistence.mongodb.process.Process;

public interface ProcessRepositoryMdb extends MongoRepository<Process, Long> {
		
		@Query("{'processo' : ?0, 'cnpj': ?1}")
		public ArrayList<Process> findByProcesso(String processo, String cnpj);
	
}
