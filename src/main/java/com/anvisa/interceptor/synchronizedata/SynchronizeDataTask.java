package com.anvisa.interceptor.synchronizedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anvisa.interceptor.synchronizedata.entity.SynchonizeSaneanteProduct;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticNotification;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticRegister;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticRegularized;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeFoot;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeProcess;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeSaneanteNotification;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.repository.generic.CosmeticRegisterRepository;
import com.anvisa.repository.generic.RegisterCNPJRepository;

@Component
public class SynchronizeDataTask {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;


	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository,
			               CosmeticRegisterRepository cosmeticRegisterRepository) {
		this.registerCNPJRepository = registerCNPJRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 1 1 ? * *")
	public static void synchronizeData() {

		log.info("SynchronizeData", dateFormat.format(new Date()));
		
		IntSynchronize[] intSynchronize = { new SynchronizeFoot(), new SynchronizeCosmeticRegister(),
				new SynchronizeCosmeticNotification(), new SynchronizeCosmeticRegularized(),
				new SynchonizeSaneanteProduct(),
				new SynchronizeSaneanteNotification(),new SynchronizeProcess()};
		
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);

 		
 		int cont = 0;
	/*	
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Foot "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntity> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());
 			
 			
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[0].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Foot Total ", dateFormat.format(new Date()));
*/ 		

 		
		
	
		/*registerCNPJs  = registerCNPJRepository.findAll(1);
		
		int total = 0;
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			
			log.info("SynchronizeData => Start Cosmetic Register "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
			ArrayList<BaseEntity> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());
			
			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
			
			if (itens!=null)
				intSynchronize[1].persist(itens);
			total = total + itens.size();
		}
		
		log.info("SynchronizeData => End Register Total "+total, dateFormat.format(new Date()));*/
		
		
		
		
/*		log.info("SynchronizeData => End Cosmetic Notification ", dateFormat.format(new Date()));
		
		
		registerCNPJs = registerCNPJRepository.findAll(1);
		
		int total = 0;
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			
			log.info("SynchronizeData => Start Cosmetic Notification "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
			ArrayList<BaseEntity> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());
			
			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
			if(itens!=null)
			intSynchronize[2].persist(itens);
			total++;
		}
		
		log.info("SynchronizeData => End Cosmetic Notification "+total, dateFormat.format(new Date()));*/
		
		
		
/*		registerCNPJs = registerCNPJRepository.findAll(1);
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			log.info("SynchronizeData => Start Cosmetic Regularized "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			ArrayList<BaseEntity> itens = intSynchronize[3].loadData(registerCNPJ.getCnpj());
			
			log.info("SynchronizeData => End Cosmetic Regularized Total "+itens.size(), dateFormat.format(new Date()));
			if(itens!=null)
			intSynchronize[3].persist(itens);
		}
		*/

		
//		log.info("SynchronizeData => End Cosmetic Regularized ", dateFormat.format(new Date()));
//		
//		 registerCNPJs = registerCNPJRepository.findAll(2);
//		
//		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
//			log.info("SynchronizeData => Start Saneante Product "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
//			ArrayList<BaseEntity> itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());
//			
//			 
//			
//			log.info("SynchronizeData => End Saneante Product Total "+itens.size(), dateFormat.format(new Date()));
//			if(itens!=null)
//			intSynchronize[4].persist(itens);
//		}
//		
//
//		
//		log.info("SynchronizeData => End Saneante Product ", dateFormat.format(new Date()));
//		
//		registerCNPJs = registerCNPJRepository.findAll(2);
//		
//		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
//			log.info("SynchronizeData => Start Saneante Notification "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
//			ArrayList<BaseEntity> itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());
//			
//			 
//			
//			log.info("SynchronizeData => End Saneante Notification Total "+itens.size(), dateFormat.format(new Date()));
//			if(itens!=null)
//			intSynchronize[5].persist(itens);
//		}
//		
//
//		
//		log.info("SynchronizeData => End Saneante Product ", dateFormat.format(new Date()));
		
		/*registerCNPJs = registerCNPJRepository.findAll();
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			log.info("SynchronizeData => Start Process "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			ArrayList<BaseEntity> itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());
			
			log.info("SynchronizeData => End Process Total "+itens.size(), dateFormat.format(new Date()));
			if(itens!=null)
			intSynchronize[6].persist(itens);
		}*/
		
	
	}
	
	
}