package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticNotificationMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticRegisterMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticRegularizedMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeFootMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeSaneanteNotificationMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeSaneanteProductMdb;
import com.anvisa.repository.generic.RegisterCNPJRepository;

@Component
public class SynchronizeDataMdbTask {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;


	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository) {
		this.registerCNPJRepository = registerCNPJRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataMdbTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 1 1 ? * *")
	public static void synchronizeData() {

		log.info("SynchronizeData", dateFormat.format(new Date()));
		
		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(), new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(), new SynchronizeCosmeticRegularizedMdb(), new SynchronizeSaneanteNotificationMdb(), new SynchronizeSaneanteProductMdb(), new SynchronizeProcessMdb() };
		
		/*List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);

 		
 		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Foot "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());
 			
 			
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[0].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Foot Total ", dateFormat.format(new Date()));
 		
 		registerCNPJs = registerCNPJRepository.findAll(1);

 		
 		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Cosmetic Register "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());
 			
 			
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[1].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Cosmetic Register ", dateFormat.format(new Date()));
		
		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Cosmetic Notification "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[2].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Cosmetic Register ", dateFormat.format(new Date()));
		
		
		log.info("SynchronizeData => End Cosmetic Register ", dateFormat.format(new Date()));
		
		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Cosmetic Regularized "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[3].loadData(registerCNPJ.getCnpj());
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[3].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Cosmetic Regularized ", dateFormat.format(new Date()));
		
	
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(2);
	
		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Saneante Notification "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[4].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Saneante Notification ", dateFormat.format(new Date()));
		
 		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(2);
 		
		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Saneante Product "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[5].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Saneante Product ", dateFormat.format(new Date()));*/
		
		
		
 		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll();
 		
		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Saneante Product "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[6].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Saneante Product ", dateFormat.format(new Date()));

	
	}
	
	
}