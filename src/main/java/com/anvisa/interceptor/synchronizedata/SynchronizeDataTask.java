package com.anvisa.interceptor.synchronizedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anvisa.interceptor.synchronizedata.foot.SynchronizeFoot;
import com.anvisa.model.persistence.AbstractBaseEntity;
import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.rest.foot.ContentDetalFoot;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.repository.generic.FootRepository;
import com.anvisa.repository.generic.RegisterCNPJRepository;

@Component
public class SynchronizeDataTask {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;
	
	@Autowired
	private static FootRepository footRepository;

	/*@Autowired
	private static JavaMailSender mailSender;*/

	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository,
						   FootRepository footRepository) {
		this.registerCNPJRepository = registerCNPJRepository;
		this.footRepository         = footRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 500000000)
	public static void synchronizeData() {

		log.info("SynchronizeData ", dateFormat.format(new Date()));
		
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll(0);
	
		IntSynchronize[]  intSynchronize = {new SynchronizeFoot(footRepository)};
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			
			List<AbstractBaseEntity> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());
			for (Iterator<AbstractBaseEntity> iterator = itens.iterator(); iterator.hasNext();) {
				ContentFoot abstractBaseEntity = (ContentFoot) iterator.next();
				ContentDetalFoot detail = (ContentDetalFoot) intSynchronize[0].loadDetailData(abstractBaseEntity.getProcesso());
				abstractBaseEntity.setContentDetalFoot(detail);
				footRepository.saveAndFlush(abstractBaseEntity);
			}
			
		}
	
	}
	
	
}