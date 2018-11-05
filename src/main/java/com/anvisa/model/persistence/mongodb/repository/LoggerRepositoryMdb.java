package com.anvisa.model.persistence.mongodb.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;

public interface LoggerRepositoryMdb extends MongoRepository<LoggerProcessing,Long> {
	
	@Query("{'dataProcessamento' : ?0}")
	public ContentFootMdb findByDataProcessamento(LocalDate dataVencimento);

}
