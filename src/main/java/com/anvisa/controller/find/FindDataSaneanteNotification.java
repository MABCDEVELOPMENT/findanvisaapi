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
import com.anvisa.model.persistence.rest.process.ProcessDetail;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotification;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.repository.generic.SaneanteNotificationRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataSaneanteNotification {
	
	@Autowired
	private static SaneanteNotificationRepository saneanteNotificationRepository;
	
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(SaneanteNotificationRepository saneanteNotificationRepository,
			ProcessRepository processRepository) {
		this.saneanteNotificationRepository = saneanteNotificationRepository;
		this.processRepository = processRepository;
	}
	
	public static List<SaneanteNotification> find(QueryRecordParameter queryRecordParameter) {
		
		

		List<SaneanteNotification> saneanteNotificationsReturn = new ArrayList<SaneanteNotification>();

		 if(queryRecordParameter.getCnpj()==null || queryRecordParameter.getCnpj().isEmpty()) {
			 
			 return saneanteNotificationsReturn;
		 }
		
		List<SaneanteNotification> saneanteNotifications = filter(queryRecordParameter);

		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();

		for (SaneanteNotification saneanteNotification : saneanteNotifications) {

			Process process = processRepository.findByProcessCnpj(saneanteNotification.getProcesso(),
					queryRecordParameter.getCnpj());
			if (process == null) {
				ArrayList<BaseEntity> processos = synchronizeProcess.loadData(saneanteNotification.getCnpj()
						+ "&filter[processo]=" + saneanteNotification.getProcesso(),1);
				
				if (processos.size() > 0) {
					Process newProcess = (Process) processos.get(0);
					ArrayList<BaseEntity> processo = new ArrayList<BaseEntity>();
					processo.add(processos.get(0));
				    //synchronizeProcess.persist(processo);
					saneanteNotification.lodaProcess(newProcess);
					break;
				}

			} else {

				saneanteNotification.lodaProcess(process);
			}
			saneanteNotificationsReturn.add(saneanteNotification);
		}

		return saneanteNotificationsReturn;

	}
	
	public static List<SaneanteNotification> filter(QueryRecordParameter queryRecordParameter){
        
		return saneanteNotificationRepository.findAll(new Specification<SaneanteNotification>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<SaneanteNotification> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
                if(queryRecordParameter.getNumberProcess()!=null && !queryRecordParameter.getNumberProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getNumberProcess())));
                }
                
                if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cnpj"), queryRecordParameter.getCnpj())));
                }
                
/*                if(queryRecordParameter.getAuthorizationNumber()!=null && !queryRecordParameter.getAuthorizationNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("saneanteNotificationDetail").get("autorizacao"), queryRecordParameter.getAuthorizationNumber())));
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