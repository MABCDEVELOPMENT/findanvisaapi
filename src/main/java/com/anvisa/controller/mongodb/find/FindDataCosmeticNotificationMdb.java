package com.anvisa.controller.mongodb.find;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.repository.CosmeticNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticNotificationMdb {

	@Autowired
	private static CosmeticNotificationRepositoryMdb cosmeticNotificationRepository;

	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	private static ProcessRepositoryMdb processRepository;

	@Autowired
	public void setService(CosmeticNotificationRepositoryMdb cosmeticNotificationRepository,
			MongoTemplate mongoTemplate, ProcessRepositoryMdb processRepository) {
		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public static List<ContentCosmeticNotification> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticNotification> contentCosmeticNotifications = filter(queryRecordParameter);
		
/*		for (Iterator iterator = contentCosmeticNotifications.iterator(); iterator.hasNext();) {
			ContentCosmeticNotification contentCosmeticNotification = (ContentCosmeticNotification) iterator.next();
			contentCosmeticNotification.lodaProcess();
		}
*/
		/*SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

		for (ContentCosmeticNotification contentCosmeticNotification : contentCosmeticNotifications) {

			ArrayList<Process> process = processRepository.findByProcesso(contentCosmeticNotification.getProcesso(),
					contentCosmeticNotification.getCnpj());
			if (process == null) {
				
				 * ArrayList<BaseEntityMongoDB> processos =
				 * synchronizeProcess.loadData(contentCosmeticNotification.getCnpj() +
				 * "&filter[processo]=" + contentCosmeticNotification.getProcesso(), 1);
				 * 
				 * if (processos.size() > 0) { Process newProcess = (Process) processos.get(0);
				 * ArrayList<BaseEntityMongoDB> processo = new ArrayList<BaseEntityMongoDB>();
				 * processo.add(processos.get(0)); synchronizeProcess.persist(processo,null);
				 * contentCosmeticNotification.lodaProcess(newProcess); }
				 

			} else {
				try {
					contentCosmeticNotification.setProcess(process.get(0));
					contentCosmeticNotification.lodaProcess(process.get(0));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			contentCosmeticNotificationsReturn.add(contentCosmeticNotification);
		}
*/
		return contentCosmeticNotifications;

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
			Criteria nameCriteria = Criteria.where("contentCosmeticNotificationDetail.autorizacao")
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