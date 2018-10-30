package com.anvisa.model.persistence.mongodb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;

public interface CosmeticRegularizedRepositoryMdb extends MongoRepository<ContentCosmeticRegularized, Long> {

	@Query("{'processo' : ?0}")
	public ContentCosmeticRegularized findByProcesso(String processo);

}