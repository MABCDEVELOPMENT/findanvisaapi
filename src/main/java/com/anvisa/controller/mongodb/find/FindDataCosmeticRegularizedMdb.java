package com.anvisa.controller.mongodb.find;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegularizedRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticRegularizedMdb {
	

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	private  ProcessRepositoryMdb processRepository;
	
    @Autowired
	public void setService(ProcessRepositoryMdb processRepository,
													MongoTemplate mongoTemplate) {
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}
	
	public List<ContentCosmeticRegularized> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticRegularized> contentCosmeticRegularizedsReturn = new ArrayList<ContentCosmeticRegularized>();

		List<ContentCosmeticRegularized> contentCosmeticRegularizeds = filter(queryRecordParameter);

		SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

		for (ContentCosmeticRegularized contentCosmeticRegularized : contentCosmeticRegularizeds) {

			Process process = processRepository.findByProcesso(contentCosmeticRegularized.getProcesso(),
					queryRecordParameter.getCnpj());
			if (process == null) {
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
	
	public List<ContentCosmeticRegularized> filter(QueryRecordParameter queryRecordParameter) {
		
			
		Query dynamicQuery = new Query();

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("cnpj")
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
		
		
		 List<ContentCosmeticRegularized> result = this.mongoTemplate.find(dynamicQuery, ContentCosmeticRegularized.class, "cosmeticRegularized");
		 
		 /*List<ContentCosmeticRegularized> result =  new ArrayList<ContentCosmeticRegularized>();
		 
		 for (Document doc : resultDocs) {
			 Gson gson = new Gson();
			 ObjectMapper mapper = new ObjectMapper();
			 String json = gson.toJson(doc);
			 try {
				ContentCosmeticRegularized contentCosmeticRegularized = mapper.reader(ContentCosmeticRegularized.class).readValue(json);
				result.add(contentCosmeticRegularized);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }*/
	        
		return result;

	}	

}

