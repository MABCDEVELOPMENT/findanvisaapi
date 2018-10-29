package com.anvisa.model.persistence.mongodb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;

public interface CosmeticNotificationRepositoryMdb extends MongoRepository<ContentCosmeticNotification, Long> {

	@Query("{'processo' : ?0, 'expedienteProcesso': ?1, 'cnpj':?2}")
	public ContentCosmeticNotification findByProcesso(String processo, String expedienteProcesso, String cnpj);

}
