package com.anvisa.model.persistence.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegister;

public interface CosmeticRegisterRepositoryMdb extends MongoRepository<ContentCosmeticRegister, Long>{

	@Query("{'processo' : ?0, 'expedienteProcesso': ?1, 'cnpj':?2}")
	public ContentCosmeticRegister findByProcesso(String processo,String expedienteProcesso ,String cnpj);

}
