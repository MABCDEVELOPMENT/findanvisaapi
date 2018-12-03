package com.anvisa.controller.mongodb.find;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.rest.QueryRecordParameter;
import com.mongodb.client.MongoDatabase;

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


		List<ContentCosmeticRegularized> contentCosmeticRegularizeds = filter(queryRecordParameter);
		
		ArrayList<ContentCosmeticRegularized> cosmeticRegularizeds = new ArrayList<ContentCosmeticRegularized>();
		
		for (Iterator iterator = contentCosmeticRegularizeds.iterator(); iterator.hasNext();) {
			
			ContentCosmeticRegularized contentCosmeticRegularized = (ContentCosmeticRegularized) iterator.next();
			
/*			if (contentCosmeticRegularized.getProcess()!=null && contentCosmeticRegularized.getProcess().getProcessDetail()!=null) {
				cosmeticRegularizeds.add(contentCosmeticRegularized);
				contentCosmeticRegularized.lodaProcess();
			}*/
			/*if (contentCosmeticRegularized.getProcess()==null) {
				ArrayList<Process> process = processRepository.findByProcesso(contentCosmeticRegularized.getProcesso(),
						contentCosmeticRegularized.getContentCosmeticRegularizedDetail().getCnpj());
				if (process.size()>0) {
					contentCosmeticRegularized.setProcess(process.get(0));
					contentCosmeticRegularized.lodaProcess();
				} else {
					SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();
					ArrayList<BaseEntityMongoDB> processos = synchronizeProcess.loadData(contentCosmeticRegularized.getContentCosmeticRegularizedDetail().getCnpj()
							+ "&filter[processo]=" + contentCosmeticRegularized.getProcesso(),1);
	
					if (processos.size() > 0) {
						Process newProcess = (Process) processos.get(0);
						processRepository.insert(newProcess);
						contentCosmeticRegularized.lodaProcess();
					}
				}
				
			}*/
			
		}
		

		/*SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

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
*/
		return contentCosmeticRegularizeds;

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

