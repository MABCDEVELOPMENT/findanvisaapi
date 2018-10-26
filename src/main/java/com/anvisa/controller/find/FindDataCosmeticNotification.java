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
import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.CosmeticNotificationRepository;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticNotification {
	
	@Autowired
	private static CosmeticNotificationRepository cosmeticNotificationRepository;
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(CosmeticNotificationRepository cosmeticNotificationRepository,
			ProcessRepository processRepository) {
		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		this.processRepository = processRepository;
	}
	public static List<ContentCosmeticNotification> find(QueryRecordParameter queryRecordParameter) {

		List<ContentCosmeticNotification> contentCosmeticNotificationsReturn = new ArrayList<ContentCosmeticNotification>();

		List<ContentCosmeticNotification> contentCosmeticNotifications = filter(queryRecordParameter);

		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();

		for (ContentCosmeticNotification contentCosmeticNotification : contentCosmeticNotifications) {

			Process process = processRepository.findByProcessCnpj(contentCosmeticNotification.getProcesso(),
					contentCosmeticNotification.getCnpj());
			if (process == null) {
				ArrayList<BaseEntity> processos = synchronizeProcess.loadData(contentCosmeticNotification.getCnpj()
						+ "&filter[processo]=" + contentCosmeticNotification.getProcesso());

				if (processos.size() > 0) {
					synchronizeProcess.persist(processos);
					Process newProcess = (Process) processos.get(0);
					contentCosmeticNotification.setProcess(newProcess);
					contentCosmeticNotification.lodaProcess(newProcess);
					break;
				}

			} else {

				contentCosmeticNotification.setProcess(process);
				contentCosmeticNotification.lodaProcess(process);
			}
			contentCosmeticNotificationsReturn.add(contentCosmeticNotification);
		}

		return contentCosmeticNotificationsReturn;

	}

	public static List<ContentCosmeticNotification> filter(QueryRecordParameter queryRecordParameter){
        
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
                
                if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cnpj"), queryRecordParameter.getCnpj())));
                }
                
/*                if(queryRecordParameter.getAuthorizationNumber()!=null && !queryRecordParameter.getAuthorizationNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("contentCosmeticNotificationDetail").get("autorizacao"), queryRecordParameter.getAuthorizationNumber())));
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