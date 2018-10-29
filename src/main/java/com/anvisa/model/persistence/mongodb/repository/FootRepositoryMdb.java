package com.anvisa.model.persistence.mongodb.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;

public interface FootRepositoryMdb extends MongoRepository<ContentFootMdb, Long> {
		
		@Query("{'processo' : ?0, 'cnpj': ?1, 'codigo':?2 , 'registro':?3, 'dataVencimento':?4} }")
		public ContentFootMdb findByProcesso(String processo, String cnpj,int codigo, String registro, LocalDate dataVencimento);
	
}

