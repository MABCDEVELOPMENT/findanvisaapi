package com.anvisa.controller.mongodb.find;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.repository.CosmeticNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.rest.QueryRecordLogParameter;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataLogMdb {

	@Autowired
	private static CosmeticNotificationRepositoryMdb cosmeticNotificationRepository;

	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	private static LoggerRepositoryMdb loggerProcessingRepositoryMdb;

/*	@Autowired
	public void setService(LoggerRepositoryMdb loggerProcessingRepositoryMdb,
			MongoTemplate mongoTemplate, ProcessRepositoryMdb processRepository) {
		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		this.loggerProcessingRepositoryMdb = loggerProcessingRepositoryMdb;
		this.mongoTemplate = mongoTemplate;
	}*/

	public static List<LoggerProcessing> find(QueryRecordLogParameter queryRecordParameter) {

		List<LoggerProcessing> loggerProcessings = filter(queryRecordParameter);

		
		return loggerProcessings;

	}

	public static List<LoggerProcessing> filter(QueryRecordLogParameter queryRecordParameter) {

		Query dynamicQuery = new Query();

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("cnpj").is(queryRecordParameter.getCnpj());
			dynamicQuery.addCriteria(nameCriteria);
		}

		

		if (queryRecordParameter.getDateInitial() != null && !queryRecordParameter.getDateInitial().isEmpty()) {
			Criteria nameCriteria = Criteria.where("insertDate").gte(queryRecordParameter.getDateInitial());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getDateFinal() != null && !queryRecordParameter.getDateFinal().isEmpty()) {
			Criteria nameCriteria = Criteria.where("insertDate").gte(queryRecordParameter.getDateFinal());
			dynamicQuery.addCriteria(nameCriteria);
		}

		List<LoggerProcessing> result = mongoTemplate.find(dynamicQuery, LoggerProcessing.class,
				"loggerProcessing");

		return result;

	}

}