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
import com.anvisa.model.persistence.mongodb.repository.SaneanteProductRepositoryMdb;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProduct;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.rest.QueryRecordParameter;
@Component
public class FindDataSaneanteProductMdb {

	@Autowired
	private static SaneanteProductRepositoryMdb saneanteProductRepository;
	
	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	private static ProcessRepositoryMdb processRepository;

	@Autowired
	public void setService(SaneanteProductRepositoryMdb saneanteProductRepository, ProcessRepositoryMdb processRepository,MongoTemplate mongoTemplate) {
		this.saneanteProductRepository = saneanteProductRepository;
		this.processRepository = processRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public static List<SaneanteProduct> find(QueryRecordParameter queryRecordParameter) {

		List<SaneanteProduct> saneanteProductsReturn = new ArrayList<SaneanteProduct>();

		if (queryRecordParameter.getCnpj() == null || queryRecordParameter.getCnpj().isEmpty()) {

			return saneanteProductsReturn;
		}

		List<SaneanteProduct> saneanteProducts = filter(queryRecordParameter);

		SynchronizeProcessMdb synchronizeProcess = new SynchronizeProcessMdb();

		for (SaneanteProduct saneanteProduct : saneanteProducts) {

			Process process = processRepository.findByProcesso(saneanteProduct.getProcesso(),
					queryRecordParameter.getCnpj());
			if (process == null) {
/*				ArrayList<BaseEntityMongoDB> processos = synchronizeProcess
						.loadData(saneanteProduct.getCnpj() + "&filter[processo]="
								+ saneanteProduct.getProcesso(),1);

				if (processos.size() > 0) {
					Process newProcess = (Process) processos.get(0);
					ArrayList<BaseEntityMongoDB> processo = new ArrayList<BaseEntityMongoDB>();
					processo.add(processos.get(0));
				    //synchronizeProcess.persist(processo);
					saneanteProduct.lodaProcess(newProcess);
					break;
				}*/

			} else {

				saneanteProduct.lodaProcess(process);
			}
			saneanteProductsReturn.add(saneanteProduct);
		}

		return saneanteProductsReturn;

	}

	public static List<SaneanteProduct> filter(QueryRecordParameter queryRecordParameter) {
		
		
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
			Criteria nameCriteria = Criteria.where("saneanteProductDetail.numeroAutorizacao")
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

		List<SaneanteProduct> result = mongoTemplate.find(dynamicQuery, SaneanteProduct.class,
				"saneanteProduct");

		return result;

	}

}