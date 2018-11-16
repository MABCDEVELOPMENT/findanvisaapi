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
import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.repository.FootRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataFootMdb {

	@Autowired
	private static FootRepositoryMdb footRepository;

	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	private static ProcessRepositoryMdb processRepository;

	@Autowired
	public void setService(FootRepositoryMdb footRepository, ProcessRepositoryMdb processRepository,
			MongoTemplate mongoTemplate) {
		this.footRepository = footRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public static List<ContentFootMdb> find(QueryRecordParameter queryRecordParameter) {

		List<ContentFootMdb> contentFootsReturn = new ArrayList<ContentFootMdb>();

		List<ContentFootMdb> contentFoots = filter(queryRecordParameter);

		SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

		for (ContentFootMdb contentFoot : contentFoots) {

			ArrayList<Process> process = processRepository.findByProcesso(contentFoot.getProcesso(),
					contentFoot.getCnpj());
			if (process == null) {
				/*
				 * ArrayList<BaseEntityMongoDB> processos =
				 * synchronizeProcess.loadData(contentFoot.getCnpj()+"&filter[processo]="+
				 * contentFoot.getProcesso(),1);
				 * 
				 * if(processos.size()>0) { Process newProcess = (Process) processos.get(0);
				 * ArrayList<BaseEntityMongoDB> processo = new ArrayList<BaseEntityMongoDB>();
				 * newProcess.lodaDateProcess(); processo.add(processos.get(0));
				 * //synchronizeProcess.persist(processo); contentFoot.lodaProcess(newProcess);
				 * break; }
				 */

			} else {
				try {
					contentFoot.setProcess(process.get(0));
					contentFoot.lodaProcess(process.get(0));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			contentFootsReturn.add(contentFoot);
		}

		return contentFootsReturn;

	}

	private static List<ContentFootMdb> filter(QueryRecordParameter queryRecordParameter) {

		Query dynamicQuery = new Query();

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			Criteria nameCriteria = Criteria.where("cnpj").is(queryRecordParameter.getCnpj());
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

		if (queryRecordParameter.getBrand() != null && !queryRecordParameter.getBrand().isEmpty()) {
			Criteria nameCriteria = Criteria.where("contentDetalFoot.marca")
					.regex(queryRecordParameter.getBrand().toUpperCase());
			dynamicQuery.addCriteria(nameCriteria);
		}

		if (queryRecordParameter.getRegisterNumber() != null && !queryRecordParameter.getRegisterNumber().isEmpty()) {
			Criteria nameCriteria = Criteria.where("registro").is(queryRecordParameter.getRegisterNumber());
			dynamicQuery.addCriteria(nameCriteria);
		}

		List<ContentFootMdb> result = mongoTemplate.find(dynamicQuery, ContentFootMdb.class, "foot");

		return result;

	}

}
