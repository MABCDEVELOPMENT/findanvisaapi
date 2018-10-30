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
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegularizedRepositoryMdb;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.model.persistence.mongodb.process.Process;

@Component
public class FindDataCosmeticRegularizedMdb {
	
	@Autowired
	private static CosmeticRegularizedRepositoryMdb cosmeticRegularizedRepository;
	
	@Inject
	private static MongoTemplate mongoTemplate;
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(CosmeticRegularizedRepositoryMdb cosmeticRegularizedRepository,
													ProcessRepository processRepository,
													MongoTemplate mongoTemplate) {
		this.cosmeticRegularizedRepository = cosmeticRegularizedRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}
	
	public static List<ContentCosmeticRegularized> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticRegularized> contentCosmeticRegularizedsReturn = new ArrayList<ContentCosmeticRegularized>();

		List<ContentCosmeticRegularized> contentCosmeticRegularizeds = filter(queryRecordParameter);

		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();

		for (ContentCosmeticRegularized contentCosmeticRegularized : contentCosmeticRegularizeds) {

			Process process = processRepository.findByProcessCnpj(contentCosmeticRegularized.getProcesso(),
					queryRecordParameter.getCnpj());
			if (process == null) {
				continue;
//				ArrayList<BaseEntity> processos = synchronizeProcess.loadData(contentCosmeticRegularized.getContentCosmeticRegularizedDetail().getCnpj()
//						+ "&filter[processo]=" + contentCosmeticRegularized.getProcesso(),1);
//
//				if (processos.size() > 0) {
//					Process newProcess = (Process) processos.get(0);
//					ArrayList<BaseEntity> processo = new ArrayList<BaseEntity>();
//					processo.add(processos.get(0));
//				    //synchronizeProcess.persist(processo);
//					contentCosmeticRegularized.lodaProcess(newProcess);
//					break;
//				}

			} else {

				contentCosmeticRegularized.setProcess(process);
				contentCosmeticRegularized.lodaProcess(process);
			}
			contentCosmeticRegularizedsReturn.add(contentCosmeticRegularized);
		}

		return contentCosmeticRegularizedsReturn;

	}
	
	public static List<ContentCosmeticRegularized> filter(QueryRecordParameter queryRecordParameter) {

		Query dynamicQuery = new Query();

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("contentCosmeticRegularizedDetail.cnpj")
					.is(queryRecordParameter.getCnpj());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getNumberProcess() != null && !queryRecordParameter.getNumberProcess().isEmpty()) {
			Criteria nameCriteria = Criteria.where("processo").is(queryRecordParameter.getNumberProcess());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getProductName() != null && !queryRecordParameter.getProductName().isEmpty()) {
			Criteria nameCriteria = Criteria.where("produto").regex(queryRecordParameter.getProductName());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getRegisterNumber() != null && !queryRecordParameter.getRegisterNumber().isEmpty()) {
			Criteria nameCriteria = Criteria.where("contentCosmeticRegularizedDetail.caracterizacaoVigente.registro")
					.is(queryRecordParameter.getNumberProcess());
			dynamicQuery.addCriteria(nameCriteria);
		}
		
		 List<ContentCosmeticRegularized> result = mongoTemplate.find(dynamicQuery, ContentCosmeticRegularized.class, "cosmeticRegularized");
	        
		return result;

	}	

}

