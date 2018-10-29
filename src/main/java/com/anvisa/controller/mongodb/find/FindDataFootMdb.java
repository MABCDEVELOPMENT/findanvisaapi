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
import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.repository.FootRepositoryMdb;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataFootMdb {
	
	@Autowired
	private static FootRepositoryMdb footRepository;
	
	@Inject
	private static MongoTemplate mongoTemplate;
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(FootRepositoryMdb footRepository,
						   ProcessRepository processRepository,
						   MongoTemplate mongoTemplate) {
		this.footRepository = footRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}
	
	public static List<ContentFootMdb> find(QueryRecordParameter queryRecordParameter){
		
		List<ContentFootMdb> contentFootsReturn = new ArrayList<ContentFootMdb>();
	
		List<ContentFootMdb> contentFoots = filter(queryRecordParameter);
		
		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();
		
		for (ContentFootMdb contentFoot : contentFoots) {
			
			Process process = processRepository.findByProcessCnpj(contentFoot.getProcesso(), contentFoot.getCnpj());
			if (process==null) {
				ArrayList<BaseEntity> processos =  synchronizeProcess.loadData(contentFoot.getCnpj()+"&filter[processo]="+contentFoot.getProcesso(),1);
				
				if(processos.size()>0) {
					Process newProcess = (Process) processos.get(0);
					ArrayList<BaseEntity> processo = new ArrayList<BaseEntity>();
					processo.add(processos.get(0));
				    //synchronizeProcess.persist(processo);
					contentFoot.lodaProcess(newProcess);
				   break;
				}
				
			} else {
				   
				contentFoot.setProcess(process);
				contentFoot.lodaProcess(process);
			}
			contentFootsReturn.add(contentFoot);
		}
		
		return contentFootsReturn;
		
	}
	
	private static List<ContentFootMdb> filter(QueryRecordParameter queryRecordParameter){
		
		Query dynamicQuery = new Query();
		
		
		
		if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("cnpj").is(queryRecordParameter.getCnpj());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
        if(queryRecordParameter.getNumberProcess()!=null && !queryRecordParameter.getNumberProcess().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("processo").is(queryRecordParameter.getNumberProcess());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
        if(queryRecordParameter.getProductName()!=null && !queryRecordParameter.getProductName().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("produto").regex(queryRecordParameter.getProductName());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
        if(queryRecordParameter.getBrand()!=null && !queryRecordParameter.getBrand().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("contentDetalFoot.marca").regex(queryRecordParameter.getBrand().toUpperCase());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
        if(queryRecordParameter.getRegisterNumber()!=null && !queryRecordParameter.getRegisterNumber().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("registro").is(queryRecordParameter.getRegisterNumber());
			   dynamicQuery.addCriteria(nameCriteria);
        }
		
        List<ContentFootMdb> result = mongoTemplate.find(dynamicQuery, ContentFootMdb.class, "foot");
        
		return result;
		
    }

	
	

}

