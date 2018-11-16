package com.anvisa.controller.mongodb.find;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq;
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
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;

@Component
public class FindDataCosmeticRegularizedMdb  {
	
	@Inject
	private static MongoTemplate mongoTemplate;
	
	@Autowired
	private static  ProcessRepositoryMdb processRepository;
	
	 
	private static MongoDatabase database;
	
    @Autowired
	public void setService(ProcessRepositoryMdb processRepository,
													MongoTemplate mongoTemplate) {
    	
		
		this.processRepository = processRepository;
		
		this.mongoTemplate = mongoTemplate;
		
		
	}
	
	public static List<ContentCosmeticRegularized> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticRegularized> contentCosmeticRegularizedsReturn = new ArrayList<ContentCosmeticRegularized>();

		List<ContentCosmeticRegularized> contentCosmeticRegularizeds = filter(queryRecordParameter);

		SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

		for (ContentCosmeticRegularized contentCosmeticRegularized : contentCosmeticRegularizeds) {

			ArrayList<Process> process = processRepository.findByProcesso(contentCosmeticRegularized.getProcesso(),
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
				try {
					contentCosmeticRegularized.setProcess(process.get(0));
					contentCosmeticRegularized.lodaProcess(process.get(0));
				} catch (Exception e) {
					// TODO: handle exception
				}	
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
		
		
		 List<ContentCosmeticRegularized> resultdocs = mongoTemplate.find(dynamicQuery, ContentCosmeticRegularized.class, "cosmeticRegularized");
		 
		 

	        
		return resultdocs;

	}



}

