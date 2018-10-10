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

import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticRegister;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeFoot;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.repository.generic.RegisterCNPJRepository;

@Component
public class SynchronizeDataTask {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;
	
	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository) {
		this.registerCNPJRepository = registerCNPJRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 1 1 ? * *")
	public static void synchronizeData() {

		log.info("SynchronizeData", dateFormat.format(new Date()));
		
		IntSynchronize[] intSynchronize = { new SynchronizeFoot(), new SynchronizeCosmeticRegister() };
		
 		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);
 	
 		
 		
 		
 		int cont = 0;
		
 		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			log.info("SynchronizeData => Start Foot "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			ArrayList<BaseEntity> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

			intSynchronize[0].persist(itens);
			cont++;
			
		}
		
		log.info("SynchronizeData => End Foot Total "+cont, dateFormat.format(new Date()));
		
		cont = 0;
		
		registerCNPJs = registerCNPJRepository.findAll(1);
	
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			log.info("SynchronizeData => Start Cosmetic "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			ArrayList<BaseEntity> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());

			intSynchronize[1].persist(itens);
			
		}
		
		log.info("SynchronizeData => End Cosmetic Register Total "+cont, dateFormat.format(new Date()));
	}
	
	
}