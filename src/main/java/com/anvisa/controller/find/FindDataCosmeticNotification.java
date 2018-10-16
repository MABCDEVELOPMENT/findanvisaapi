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

import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.repository.generic.CosmeticNotificationRepository;
import com.anvisa.repository.generic.CosmeticRegisterRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticNotification {
	
	@Autowired
	private static CosmeticNotificationRepository cosmeticNotificationRepository;
	
	@Autowired
	public void setService(CosmeticNotificationRepository cosmeticNotificationRepository) {
		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
	}
	
	public static List<ContentCosmeticNotification> find(QueryRecordParameter queryRecordParameter){
        
		return cosmeticNotificationRepository.findAll(new Specification<ContentCosmeticNotification>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<ContentCosmeticNotification> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
                if(queryRecordParameter.getNumberProcess()!=null && !queryRecordParameter.getNumberProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getNumberProcess())));
                }
                
/*                if(queryRecordParameter.getAuthorizationNumber()!=null && !queryRecordParameter.getAuthorizationNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("contentCosmeticRegisterDetail").get("autorizacao"), queryRecordParameter.getAuthorizationNumber())));
                }*/
                
                if(queryRecordParameter.getExpedientProcess()!=null && !queryRecordParameter.getExpedientProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("expedienteProcesso"), queryRecordParameter.getExpedientProcess())));
                }
                
                if(queryRecordParameter.getGeneratedTransaction()!=null && !queryRecordParameter.getGeneratedTransaction().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("transacao"), queryRecordParameter.getGeneratedTransaction())));
                }
                
                if(queryRecordParameter.getExpeditionPetition()!=null && !queryRecordParameter.getExpeditionPetition().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("expedientePeticao"), queryRecordParameter.getExpeditionPetition())));
                }
                
                if(queryRecordParameter.getProductName()!=null && !queryRecordParameter.getProductName().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("produto"), "%"+queryRecordParameter.getProductName().toUpperCase()+"%")));
                }
                
//                if(queryRecordParameter.getBrand()!=null && !queryRecordParameter.getBrand().isEmpty()) {
//                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("contentDetalFoot").get("marca"), "%"+queryRecordParameter.getBrand()+"%")));
//                }
                
                if(queryRecordParameter.getDateInitial()!=null && !queryRecordParameter.getDateInitial().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("vencimento"), queryRecordParameter.getDateInitial())));
                }
                
                if(queryRecordParameter.getDateFinal()!=null && !queryRecordParameter.getDateFinal().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("vencimento"), queryRecordParameter.getDateFinal())));
                }
                
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                
            }
        });
    }
	

}