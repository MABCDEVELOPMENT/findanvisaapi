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
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticRegisterMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeFootMdb;
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
		
		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(),new SynchronizeCosmeticRegisterMdb()};
		
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);

 		
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

 		
 		cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Cosmetic Register "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());
 			
 			
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			if(itens!=null)
			intSynchronize[1].persist(itens);
			
		}
	
 		log.info("SynchronizeData => End Cosmetic Register ", dateFormat.format(new Date()));
	
	}
	
	
}