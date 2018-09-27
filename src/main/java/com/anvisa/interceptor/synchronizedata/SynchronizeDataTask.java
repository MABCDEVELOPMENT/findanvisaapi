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

import com.anvisa.interceptor.synchronizedata.foot.SynchronizeFoot;
import com.anvisa.model.persistence.BaseEntityAudit;
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

	@Scheduled(fixedRate = 500000000)
	public static void synchronizeData() {

		log.info("SynchronizeData ", dateFormat.format(new Date()));
		
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);
	
		IntSynchronize[]  intSynchronize = {new SynchronizeFoot()};
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			
			ArrayList<BaseEntityAudit> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

			intSynchronize[0].persist(itens);
			
			
			
		}
	
	}
	
	
}