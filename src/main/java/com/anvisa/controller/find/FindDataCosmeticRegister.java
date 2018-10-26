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
import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.repository.generic.CosmeticRegisterRepository;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.QueryRecordParameter;

@Component
public class FindDataCosmeticRegister {
	
	@Autowired
	private static CosmeticRegisterRepository cosmeticRegisterRepository;
	@Autowired
	private static ProcessRepository processRepository;
	
	@Autowired
	public void setService(CosmeticRegisterRepository cosmeticRegisterRepository,
			ProcessRepository processRepository) {
		this.cosmeticRegisterRepository = cosmeticRegisterRepository;
		this.processRepository = processRepository;
	}
public static List<ContentCosmeticRegister> find(QueryRecordParameter queryRecordParameter){
		
		List<ContentCosmeticRegister> contentCosmeticRegistersReturn = new ArrayList<ContentCosmeticRegister>();
	
		List<ContentCosmeticRegister> contentCosmeticRegisters = filter(queryRecordParameter);
		
		SynchronizeProcess synchronizeProcess = new SynchronizeProcess();
		
		for (ContentCosmeticRegister contentCosmeticRegister : contentCosmeticRegisters) {
			
			Process process = processRepository.findByProcessCnpj(contentCosmeticRegister.getProcesso(), contentCosmeticRegister.getCnpj());
			if (process==null) {
				ArrayList<BaseEntity> processos =  synchronizeProcess.loadData(contentCosmeticRegister.getCnpj()+"&filter[processo]="+contentCosmeticRegister.getProcesso());
				
				if(processos.size()>0) {
				   synchronizeProcess.persist(processos);
				   Process newProcess = (Process) processos.get(0);
				   contentCosmeticRegister.setProcess(newProcess);
				   contentCosmeticRegister.lodaProcess(newProcess);
				   break;
				}
				
			} else {
				   
				contentCosmeticRegister.setProcess(process);
				contentCosmeticRegister.lodaProcess(process);
			}
			contentCosmeticRegistersReturn.add(contentCosmeticRegister);
		}
		
		return contentCosmeticRegistersReturn;
		
	}
	

	public static List<ContentCosmeticRegister> filter(QueryRecordParameter queryRecordParameter){
        
		return cosmeticRegisterRepository.findAll(new Specification<ContentCosmeticRegister>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<ContentCosmeticRegister> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
				List<Predicate> predicates = new ArrayList<>();
                
                if(queryRecordParameter.getCnpj()!=null && !queryRecordParameter.getCnpj().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cnpj"), queryRecordParameter.getCnpj())));
                }
                
                if(queryRecordParameter.getNumberProcess()!=null && !queryRecordParameter.getNumberProcess().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processo"), queryRecordParameter.getNumberProcess())));
                }
                
                if(queryRecordParameter.getAuthorizationNumber()!=null && !queryRecordParameter.getAuthorizationNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("contentCosmeticRegisterDetail").get("autorizacao"), queryRecordParameter.getAuthorizationNumber())));
                }
                
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