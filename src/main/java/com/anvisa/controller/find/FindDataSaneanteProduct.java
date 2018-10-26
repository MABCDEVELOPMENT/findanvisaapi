package com.anvisa.controller.find;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.anvisa.interceptor.synchronizedata.entity.SynchronizeProcess;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.model.persistence.rest.saneante.product.SaneanteProduct;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.repository.generic.SaneanteProductRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataSaneanteProduct {

	@Autowired
	private static SaneanteProductRepository saneanteProductRepository;

	@Autowired
	private static ProcessRepository processRepository;

	@Autowired
	public void setService(SaneanteProductRepository saneanteProductRepository, ProcessRepository processRepository) {
		this.saneanteProductRepository = saneanteProductRepository;
		this.processRepository = processRepository;
	}

	public static List<SaneanteProduct> find(QueryRecordParameter queryRecordParameter) {

		List<SaneanteProduct> saneanteProductsReturn = new ArrayList<SaneanteProduct>();

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {

			return saneanteProductsReturn;
		}

		List<SaneanteProduct> saneanteProducts = filter(queryRecordParameter);

		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();

		for (SaneanteProduct saneanteProduct : saneanteProducts) {

			Process process = processRepository.findByProcessCnpj(saneanteProduct.getProcesso(),
					queryRecordParameter.getCnpj());
			if (process == null) {
				ArrayList<BaseEntity> processos = synchronizeProcess
						.loadData(saneanteProduct.getCnpj() + "&filter[processo]="
								+ saneanteProduct.getProcesso());

				if (processos.size() > 0) {
					synchronizeProcess.persist(processos);
					Process newProcess = (Process) processos.get(0);
					saneanteProduct.setProcess(newProcess);
					saneanteProduct.lodaProcess(newProcess);
					break;
				}

			} else {

				saneanteProduct.setProcess(process);
				saneanteProduct.lodaProcess(process);
			}
			saneanteProductsReturn.add(saneanteProduct);
		}

		return saneanteProductsReturn;

	}

	public static List<SaneanteProduct> filter(QueryRecordParameter queryRecordParameter) {

		return saneanteProductRepository.findAll(new Specification<SaneanteProduct>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SaneanteProduct> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				if (queryRecordParameter.getNumberProcess() != null
						&& !queryRecordParameter.getNumberProcess().isEmpty()) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getNumberProcess())));
				}

				if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("cnpj"), queryRecordParameter.getCnpj())));
				}

				if (queryRecordParameter.getAuthorizationNumber() != null
						&& !queryRecordParameter.getAuthorizationNumber().isEmpty()) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("saneanteProductDetail").get("numeroAutorizacao"),
									queryRecordParameter.getAuthorizationNumber())));
				}

				if (queryRecordParameter.getExpedientProcess() != null
						&& !queryRecordParameter.getExpedientProcess().isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("expedienteProcesso"),
							queryRecordParameter.getExpedientProcess())));
				}

				if (queryRecordParameter.getGeneratedTransaction() != null
						&& !queryRecordParameter.getGeneratedTransaction().isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("transacao"),
							queryRecordParameter.getGeneratedTransaction())));
				}

				if (queryRecordParameter.getExpeditionPetition() != null
						&& !queryRecordParameter.getExpeditionPetition().isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("expedientePeticao"),
							queryRecordParameter.getExpeditionPetition())));
				}

				if (queryRecordParameter.getProductName() != null && !queryRecordParameter.getProductName().isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("produto"),
							"%" + queryRecordParameter.getProductName().toUpperCase() + "%")));
				}

				// if(queryRecordParameter.getBrand()!=null &&
				// !queryRecordParameter.getBrand().isEmpty()) {
				// predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("contentDetalFoot").get("marca"),
				// "%"+queryRecordParameter.getBrand()+"%")));
				// }

				if (queryRecordParameter.getDateInitial() != null && !queryRecordParameter.getDateInitial().isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("vencimento"),
							queryRecordParameter.getDateInitial())));
				}

				if (queryRecordParameter.getDateFinal() != null && !queryRecordParameter.getDateFinal().isEmpty()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("vencimento"),
							queryRecordParameter.getDateFinal())));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

			}
		});
	}

}