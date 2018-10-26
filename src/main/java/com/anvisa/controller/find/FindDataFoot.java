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
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.FootRepository;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataFoot {
	
	@Autowired
	private static FootRepository footRepository;
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(FootRepository footRepository,
						   ProcessRepository processRepository) {
		this.footRepository = footRepository;
		this.processRepository = processRepository;
	}
	
	public static List<ContentFoot> find(QueryRecordParameter queryRecordParameter){
		
		List<ContentFoot> contentFootsReturn = new ArrayList<ContentFoot>();
	
		List<ContentFoot> contentFoots = filter(queryRecordParameter);
		
		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();
		
		for (ContentFoot contentFoot : contentFoots) {
			
			Process process = processRepository.findByProcessCnpj(contentFoot.getProcesso(), contentFoot.getCnpj());
			if (process==null) {
				ArrayList<BaseEntity> processos =  synchronizeProcess.loadData(contentFoot.getCnpj()+"&filter[processo]="+contentFoot.getProcesso());
				
				if(processos.size()>0) {
				   synchronizeProcess.persist(processos);
				   Process newProcess = (Process) processos.get(0);
				   contentFoot.setProcess(newProcess);
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
	
	private static List<ContentFoot> filter(QueryRecordParameter queryRecordParameter){
        
		return footRepository.findAll(new Specification<ContentFoot>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<ContentFoot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
				if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cnpj"), queryRecordParameter.getCnpj())));
                }
                
                if(queryRecordParameter.getNumberProcess()!=null && !queryRecordParameter.getNumberProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getNumberProcess())));
                }
                
                if(queryRecordParameter.getProductName()!=null && !queryRecordParameter.getProductName().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("produto"), "%"+queryRecordParameter.getProductName().toUpperCase()+"%")));
                }
                
                if(queryRecordParameter.getBrand()!=null && !queryRecordParameter.getBrand().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("contentDetalFoot").get("marca"), "%"+queryRecordParameter.getBrand().toUpperCase()+"%")));
                }
                
                if(queryRecordParameter.getRegisterNumber()!=null && !queryRecordParameter.getRegisterNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("registro"), queryRecordParameter.getRegisterNumber())));
                }
                
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                
            }
        });
    }
	
	
	

}
