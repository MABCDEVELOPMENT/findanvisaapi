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
import com.anvisa.model.persistence.rest.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.CosmeticRegularizedRepository;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticRegularized {
	
	@Autowired
	private static CosmeticRegularizedRepository cosmeticRegularizedRepository;
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(CosmeticRegularizedRepository cosmeticRegularizedRepository,
													ProcessRepository processRepository) {
		this.cosmeticRegularizedRepository = cosmeticRegularizedRepository;
		this.processRepository = processRepository;
	}
	
	public static List<ContentCosmeticRegularized> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticRegularized> contentCosmeticRegularizedsReturn = new ArrayList<ContentCosmeticRegularized>();

		List<ContentCosmeticRegularized> contentCosmeticRegularizeds = filter(queryRecordParameter);

		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();

		for (ContentCosmeticRegularized contentCosmeticRegularized : contentCosmeticRegularizeds) {

			Process process = processRepository.findByProcessCnpj(contentCosmeticRegularized.getProcesso(),
					contentCosmeticRegularized.getContentCosmeticRegularizedDetail().getCnpj());
			if (process == null) {
				ArrayList<BaseEntity> processos = synchronizeProcess.loadData(contentCosmeticRegularized.getContentCosmeticRegularizedDetail().getCnpj()
						+ "&filter[processo]=" + contentCosmeticRegularized.getProcesso());

				if (processos.size() > 0) {
					synchronizeProcess.persist(processos);
					Process newProcess = (Process) processos.get(0);
					contentCosmeticRegularized.setProcess(newProcess);
					contentCosmeticRegularized.lodaProcess(newProcess);
					break;
				}

			} else {

				contentCosmeticRegularized.setProcess(process);
				contentCosmeticRegularized.lodaProcess(process);
			}
			contentCosmeticRegularizedsReturn.add(contentCosmeticRegularized);
		}

		return contentCosmeticRegularizedsReturn;

	}
	
	public static List<ContentCosmeticRegularized> filter(QueryRecordParameter queryRecordParameter){
        
		return cosmeticRegularizedRepository.findAll(new Specification<ContentCosmeticRegularized>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<ContentCosmeticRegularized> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
//              if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("contentCosmeticRegularizedDetail").get("cnpj"), queryRecordParameter.getCnpj())));
//              }
                
                if(queryRecordParameter.getNumberProcess()!=null && !queryRecordParameter.getNumberProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getNumberProcess())));
                }
                
                if(queryRecordParameter.getProductName()!=null && !queryRecordParameter.getProductName().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("produto"), "%"+queryRecordParameter.getProductName().toUpperCase()+"%")));
                }
                
                /*if(queryRecordParameter.getBrand()!=null && !queryRecordParameter.getBrand().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("contentDetalFoot").get("marca"), "%"+queryRecordParameter.getBrand().toUpperCase()+"%")));
                }*/
                
                if(queryRecordParameter.getRegisterNumber()!=null && !queryRecordParameter.getRegisterNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("contentCosmeticRegularizedDetail").get("caracterizacaoVigente").get("registro"), queryRecordParameter.getRegisterNumber())));
                }
                
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                
            }
        });
    }
	

}

