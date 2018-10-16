package com.anvisa.interceptor.synchronizedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticNotification;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticRegister;
import com.anvisa.interceptor.synchronizedata.entity.SynchronizeCosmeticRegularized;
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
		
		IntSynchronize[] intSynchronize = { new SynchronizeFoot(), new SynchronizeCosmeticRegister(), new SynchronizeCosmeticNotification(), new SynchronizeCosmeticRegularized() };
		
 		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);

 		ArrayList<BaseEntity> itensGeralFoot = new ArrayList<BaseEntity>();
 	
 		
 		int cont = 0;
		
 		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Foot "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntity> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

 			itensGeralFoot.addAll(itens);
			
			
		}
 		
 		intSynchronize[0].persist(itensGeralFoot);
 		
 		log.info("SynchronizeData => End Foot Total "+itensGeralFoot.size(), dateFormat.format(new Date()));
		
		cont = 0;
		
		registerCNPJs = registerCNPJRepository.findAll(1);
		
		ArrayList<BaseEntity> itensGeralCometicRegister = new ArrayList<BaseEntity>();
	
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			log.info("SynchronizeData => Start Cosmetic Register "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			ArrayList<BaseEntity> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());

			itensGeralCometicRegister.addAll(itens);
			
		}
		
		intSynchronize[1].persist(itensGeralCometicRegister);
		
		log.info("SynchronizeData => End Cosmetic Notification Total "+itensGeralCometicRegister.size(), dateFormat.format(new Date()));
		
		
		registerCNPJs = registerCNPJRepository.findAll(1);
		
		ArrayList<BaseEntity> itensGeralNotification = new ArrayList<BaseEntity>();
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			log.info("SynchronizeData => Start Cosmetic Notification "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			//ArrayList<BaseEntity> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());
			itensGeralNotification.addAll(intSynchronize[2].loadData(registerCNPJ.getCnpj()));
		}
		
		intSynchronize[2].persist(itensGeralNotification);
		log.info("SynchronizeData => End Cosmetic Notification Total "+itensGeralNotification.size(), dateFormat.format(new Date()));
		
		registerCNPJs = registerCNPJRepository.findAll(1);
		ArrayList<BaseEntity> itensGeralRegularized = new ArrayList<BaseEntity>();
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			log.info("SynchronizeData => Start Cosmetic Regularized "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			//ArrayList<BaseEntity> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());
			itensGeralRegularized.addAll(intSynchronize[3].loadData(registerCNPJ.getCnpj()));
		}
		
		intSynchronize[3].persist(itensGeralRegularized);
		
		log.info("SynchronizeData => End Cosmetic Regularized Total "+itensGeralRegularized.size(), dateFormat.format(new Date()));
		
	}
	
	
}