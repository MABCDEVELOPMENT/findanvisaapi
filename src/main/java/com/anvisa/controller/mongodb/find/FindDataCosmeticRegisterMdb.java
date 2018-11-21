package com.anvisa.controller.mongodb.find;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegisterRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticRegisterMdb {

	@Autowired
	private static CosmeticRegisterRepositoryMdb cosmeticRegisterRepository;

	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	private static ProcessRepositoryMdb processRepository;

	@Autowired
	public void setService(CosmeticRegisterRepositoryMdb cosmeticRegisterRepository, MongoTemplate mongoTemplate,
			ProcessRepositoryMdb processRepository) {
		this.cosmeticRegisterRepository = cosmeticRegisterRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public static List<ContentCosmeticRegister> find(QueryRecordParameter queryRecordParameter) {


		List<ContentCosmeticRegister> contentCosmeticRegisters = filter(queryRecordParameter);

		for (Iterator iterator = contentCosmeticRegisters.iterator(); iterator.hasNext();) {
			ContentCosmeticRegister contentCosmeticRegister = (ContentCosmeticRegister) iterator.next();
			contentCosmeticRegister.lodaProcess();
		}

		/*for (ContentCosmeticRegister contentCosmeticRegister : contentCosmeticRegisters) {

			ArrayList<Process> process = processRepository.findByProcesso(contentCosmeticRegister.getProcesso(),
					contentCosmeticRegister.getCnpj());
			if (process == null) {
				
				 * ArrayList<BaseEntityMongoDB> processos =
				 * synchronizeProcess.loadData(contentCosmeticRegister.getCnpj() +
				 * "&filter[processo]=" + contentCosmeticRegister.getProcesso(),1);
				 * 
				 * if (processos.size() > 0) { Process newProcess = (Process) processos.get(0);
				 * ArrayList<BaseEntityMongoDB> processo = new ArrayList<BaseEntityMongoDB>();
				 * processo.add(processos.get(0)); //synchronizeProcess.persist(processo);
				 * contentCosmeticRegister.lodaProcess(newProcess);
				 * 
				 * }
				 

			} else {
				try {
					contentCosmeticRegister.setProcess(process.get(0));
					contentCosmeticRegister.lodaProcess(process.get(0));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			contentCosmeticRegistersReturn.add(contentCosmeticRegister);
		}*/

		return contentCosmeticRegisters;

	}

	public static List<ContentCosmeticRegister> filter(QueryRecordParameter queryRecordParameter) {

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

		List<ContentCosmeticRegister> result = mongoTemplate.find(dynamicQuery, ContentCosmeticRegister.class,
				"cosmeticRegister");

		return result;

	}

}