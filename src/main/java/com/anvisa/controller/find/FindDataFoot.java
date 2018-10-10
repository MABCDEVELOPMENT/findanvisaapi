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

import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.repository.generic.FootRepository;
import com.anvisa.repository.generic.RegisterCNPJRepository;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.RootObject;

@Component
public class FindDataFoot {
	
	@Autowired
	private static FootRepository footRepository;
	
	@Autowired
	public void setService(FootRepository footRepository) {
		this.footRepository = footRepository;
	}
	
	public static List<ContentFoot> find(QueryRecordParameter queryRecordParameter){
        
		return footRepository.findAll(new Specification<ContentFoot>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<ContentFoot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
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
