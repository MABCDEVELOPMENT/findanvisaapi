package com.anvisa.controller.mongodb.find;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.interceptor.synchronizedata.entity.SynchronizeProcess;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.repository.CosmeticNotificationRepositoryMdb;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticNotificationMdb {

	@Autowired
	private static CosmeticNotificationRepositoryMdb cosmeticNotificationRepository;

	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	private static ProcessRepository processRepository;

	@Autowired
	public void setService(CosmeticNotificationRepositoryMdb cosmeticNotificationRepository,
			MongoTemplate mongoTemplate, ProcessRepository processRepository) {
		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public static List<ContentCosmeticNotification> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticNotification> contentCosmeticNotificationsReturn = new ArrayList<ContentCosmeticNotification>();

		List<ContentCosmeticNotification> contentCosmeticNotifications = filter(queryRecordParameter);

		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();

		for (ContentCosmeticNotification contentCosmeticNotification : contentCosmeticNotifications) {

			Process process = processRepository.findByProcessCnpj(contentCosmeticNotification.getProcesso(),
					contentCosmeticNotification.getCnpj());
			if (process == null) {
				ArrayList<BaseEntity> processos = synchronizeProcess.loadData(contentCosmeticNotification.getCnpj()
						+ "&filter[processo]=" + contentCosmeticNotification.getProcesso(), 1);

				if (processos.size() > 0) {
					Process newProcess = (Process) processos.get(0);
					ArrayList<BaseEntity> processo = new ArrayList<BaseEntity>();
					processo.add(processos.get(0));
					synchronizeProcess.persist(processo);
					contentCosmeticNotification.lodaProcess(newProcess);
				}

			} else {

				contentCosmeticNotification.setProcess(process);
				contentCosmeticNotification.lodaProcess(process);
			}
			contentCosmeticNotificationsReturn.add(contentCosmeticNotification);
		}

		return contentCosmeticNotificationsReturn;

	}

	public static List<ContentCosmeticNotification> filter(QueryRecordParameter queryRecordParameter) {

		Query dynamicQuery = new Query();

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("cnpj").is(queryRecordParameter.getCnpj());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getNumberProcess() != null && !queryRecordParameter.getNumberProcess().isEmpty()) {
			Criteria nameCriteria = Criteria.where("processo").is(queryRecordParameter.getNumberProcess());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getAuthorizationNumber() != null
				&& !queryRecordParameter.getAuthorizationNumber().isEmpty()) {
			Criteria nameCriteria = Criteria.where("contentCosmeticRegisterDetail.autorizacao")
					.regex(queryRecordParameter.getAuthorizationNumber());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getExpedientProcess() != null
				&& !queryRecordParameter.getExpedientProcess().isEmpty()) {
			Criteria nameCriteria = Criteria.where("expedienteProcesso")
					.regex(queryRecordParameter.getExpedientProcess());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getExpeditionPetition() != null
				&& !queryRecordParameter.getExpeditionPetition().isEmpty()) {
			Criteria nameCriteria = Criteria.where("expedientePeticao")
					.regex(queryRecordParameter.getExpeditionPetition());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getGeneratedTransaction() != null
				&& !queryRecordParameter.getGeneratedTransaction().isEmpty()) {
			Criteria nameCriteria = Criteria.where("transacao").regex(queryRecordParameter.getBrand());
			dynamicQuery.addCriteria(nameCriteria);

		}

		if (queryRecordParameter.getExpeditionPetition() != null
				&& !queryRecordParameter.getExpeditionPetition().isEmpty()) {
			Criteria nameCriteria = Criteria.where("transacao").regex(queryRecordParameter.getExpeditionPetition());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getProductName() != null && !queryRecordParameter.getProductName().isEmpty()) {
			Criteria nameCriteria = Criteria.where("produto").regex(queryRecordParameter.getProductName());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getDateInitial() != null && !queryRecordParameter.getDateInitial().isEmpty()) {
			Criteria nameCriteria = Criteria.where("vencimento").gte(queryRecordParameter.getDateInitial());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getDateFinal() != null && !queryRecordParameter.getDateFinal().isEmpty()) {
			Criteria nameCriteria = Criteria.where("vencimento").gte(queryRecordParameter.getDateFinal());
			dynamicQuery.addCriteria(nameCriteria);
		}

		List<ContentCosmeticNotification> result = mongoTemplate.find(dynamicQuery, ContentCosmeticNotification.class,
				"cosmeticNotification");

		return result;

	}

}