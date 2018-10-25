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

import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordProcessParameter;

@Component
public class FindDataProcess {
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(ProcessRepository processRepository) {
		this.processRepository = processRepository;
	}
	
	public static List<Process> find(QueryRecordProcessParameter queryRecordParameter){
        
		return processRepository.findAll(new Specification<Process>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
                if(queryRecordParameter.getArea() > 0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("area"), queryRecordParameter.getArea())));
                }
				
                if(queryRecordParameter.getProcess()!=null && !queryRecordParameter.getProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getProcess())));
                }
                
                if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cnpj"), queryRecordParameter.getCnpj())));
                }
                
                if (queryRecordParameter.getOfficehour() != null && !queryRecordParameter.getOfficehour().isEmpty()) {
        			 predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("expediente"), queryRecordParameter.getOfficehour())));
        		}
                
                if(queryRecordParameter.getTransaction()!=null && !queryRecordParameter.getTransaction().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("transacao"), queryRecordParameter.getTransaction())));
                }
                
        		if (queryRecordParameter.getProtocol() != null && !queryRecordParameter.getProtocol().isEmpty()) {
        			 predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("protocolo"), queryRecordParameter.getProtocol())));
        		}
        		
        		if (queryRecordParameter.getKnowledge() != null && !queryRecordParameter.getKnowledge().isEmpty()) {
       			 predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("conheicimento"), queryRecordParameter.getProtocol())));
        		}
                
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                
            }
        });
    }
	

}