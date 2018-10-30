package com.anvisa.model.persistence.mongodb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotification;

public interface SaneanteNotificationRepositoryMdb extends MongoRepository<SaneanteNotification, Long> {

	@Query("{'processo' : ?0, 'expedienteProcesso': ?1, 'cnpj':?2}")
	public SaneanteNotification findByProcesso(String processo, String expedienteProcesso, String cnpj);
}
