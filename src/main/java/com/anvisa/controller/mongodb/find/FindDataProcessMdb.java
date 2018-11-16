package com.anvisa.controller.mongodb.find;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.rest.QueryRecordProcessParameter;

@Component
public class FindDataProcessMdb {
	
	@Autowired
	private static ProcessRepositoryMdb processRepository;
	
	@Inject
	private static MongoTemplate mongoTemplate;
	
    @Autowired
	public void setService(ProcessRepositoryMdb processRepository,
						   MongoTemplate mongoTemplate) {
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate; 
	}
	
	public static List<Process> find(QueryRecordProcessParameter queryRecordParameter){
		
		List<Process> contentProcessReturn = new ArrayList<Process>();
	
		List<Process> contentProcessos = filter(queryRecordParameter);
		
		for (Process process : contentProcessos) {
			process.lodaDateProcess();
			contentProcessReturn.add(process);
		}
		
		
		return contentProcessReturn;
		
	}
	
	public static List<Process> filter(QueryRecordProcessParameter queryRecordParameter){
		
		Query dynamicQuery = new Query();
		
		 if(queryRecordParameter.getArea() > 0) {
			 Criteria nameCriteria = Criteria.where("area").is(queryRecordParameter.getArea());
			   dynamicQuery.addCriteria(nameCriteria);
         }
		
		if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("cnpj").is(queryRecordParameter.getCnpj());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
        if(queryRecordParameter.getProcess()!=null && !queryRecordParameter.getProcess().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("processo").is(queryRecordParameter.getProcess());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
        if (queryRecordParameter.getOfficehour() != null && !queryRecordParameter.getOfficehour().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("expediente").is(queryRecordParameter.getOfficehour());
			   dynamicQuery.addCriteria(nameCriteria);
		}
        
        if(queryRecordParameter.getTransaction()!=null && !queryRecordParameter.getTransaction().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("transacao").is(queryRecordParameter.getTransaction());
			   dynamicQuery.addCriteria(nameCriteria);
        }
        
		if (queryRecordParameter.getProtocol() != null && !queryRecordParameter.getProtocol().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("protocolo").is(queryRecordParameter.getProtocol());
			   dynamicQuery.addCriteria(nameCriteria);
		}
		
		if (queryRecordParameter.getKnowledge() != null && !queryRecordParameter.getKnowledge().isEmpty()) {
        	Criteria nameCriteria = Criteria.where("conheicimento").is(queryRecordParameter.getKnowledge());
			   dynamicQuery.addCriteria(nameCriteria);
   		}
		
        List<Process> result = mongoTemplate.find(dynamicQuery, Process.class, "process");
        
		return result;
		
    }
	

}