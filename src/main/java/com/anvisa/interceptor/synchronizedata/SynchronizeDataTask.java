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

import com.anvisa.interceptor.synchronizedata.foot.SynchronizeFoot;
import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.repository.generic.RegisterCNPJRepository;
import com.anvisa.repository.generic.RepositoryScheduledEmail;
import com.anvisa.rest.RootObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class SynchronizeDataTask {

	@Autowired
	static RegisterCNPJRepository registerCNPJRepository;

	/*@Autowired
	private static JavaMailSender mailSender;*/

	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository) {
		this.registerCNPJRepository = registerCNPJRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 5000000)
	public static void synchronizeData() {

		log.info("scheduledEmail ", dateFormat.format(new Date()));
		
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll();
	
		IntSynchronize[]  intSynchronize = {new SynchronizeFoot()};
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
			
			ListArray<> intSynchronize[0].loadData(registerCNPJ.getCnpj());
			
		}
	
	}

	
}