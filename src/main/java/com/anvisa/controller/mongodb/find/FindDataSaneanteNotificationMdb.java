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
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataSaneanteNotificationMdb {
	
	@Autowired
	private static SaneanteNotificationRepositoryMdb saneanteNotificationRepository;
	
	@Inject
	private static MongoTemplate mongoTemplate;
	
	@Autowired
	private static ProcessRepositoryMdb processRepository;
	
	@Autowired
	public void setService(SaneanteNotificationRepositoryMdb saneanteNotificationRepository,
						   ProcessRepositoryMdb processRepository,
						   MongoTemplate mongoTemplate) {
		
		this.saneanteNotificationRepository = saneanteNotificationRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
		
	}
	
	public static List<SaneanteNotification> find(QueryRecordParameter queryRecordParameter) {
		
		

		List<SaneanteNotification> saneanteNotificationsReturn = new ArrayList<SaneanteNotification>();

		 if(queryRecordParameter.getCnpj()==null || queryRecordParameter.getCnpj().isEmpty()) {
			 
			 return saneanteNotificationsReturn;
		 }
		
		List<SaneanteNotification> saneanteNotifications = filter(queryRecordParameter);

		SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

		for (SaneanteNotification saneanteNotification : saneanteNotifications) {

			Process process = processRepository.findByProcesso(saneanteNotification.getProcesso(),
					queryRecordParameter.getCnpj());
			if (process == null) {
				ArrayList<BaseEntityMongoDB> processos = synchronizeProcess.loadData(saneanteNotification.getCnpj()
						+ "&filter[processo]=" + saneanteNotification.getProcesso(),1);
				
				if (processos.size() > 0) {
					Process newProcess = (Process) processos.get(0);
					ArrayList<BaseEntityMongoDB> processo = new ArrayList<BaseEntityMongoDB>();
					processo.add(processos.get(0));
				    //synchronizeProcess.persist(processo);
					saneanteNotification.lodaProcess(newProcess);
					break;
				}

			} else {

				saneanteNotification.lodaProcess(process);
			}
			saneanteNotificationsReturn.add(saneanteNotification);
		}

		return saneanteNotificationsReturn;

	}
	
	public static List<SaneanteNotification> filter(QueryRecordParameter queryRecordParameter){
		
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
			Criteria nameCriteria = Criteria.where("saneanteNotificationDetail.autorizacao")
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

		List<SaneanteNotification> result = mongoTemplate.find(dynamicQuery, SaneanteNotification.class,
				"saneanteNotification");

		return result;
		
        
		
    }
	

}
