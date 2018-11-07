package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticNotificationMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticRegisterMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeCosmeticRegularizedMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeFootMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeProcessMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeSaneanteNotificationMdb;
import com.anvisa.model.persistence.mongodb.synchronze.SynchronizeSaneanteProductMdb;
import com.anvisa.repository.generic.RegisterCNPJRepository;

@Component
public class SynchronizeDataMdbTask implements Runnable {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public static SequenceDaoImpl sequence;
	
	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository,
						   LoggerRepositoryMdb loggerRepositoryMdb,
						   SequenceDaoImpl sequence) {
		this.registerCNPJRepository = registerCNPJRepository;
		this.loggerRepositoryMdb = loggerRepositoryMdb;
		this.sequence = sequence;
	}

	private static final Logger log = LoggerFactory.getLogger("SynchronizeData");

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 10 02 * * *")
	public static void  synchronizeData() {
			
		Thread  thread = new Thread(new SynchronizeDataMdbTask(),"SynchronizeDataMdbTask");
		thread.start();
	
	}
	
	@Override
	public void run() {
		boolean foot = false;
		boolean saneantNotification  = false;
		boolean saneantProduct       = false;
		boolean process = true;
		boolean cosmeticRegister = false;
		boolean cosmeticNotification = false;
		boolean cosmeticRegularized  = true;

		
		log.info("SynchronizeData", dateFormat.format(new Date()));
		
		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(), 
				new SynchronizeSaneanteNotificationMdb(), 
				new SynchronizeSaneanteProductMdb(), 
				new SynchronizeProcessMdb(),
				new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(),
				new SynchronizeCosmeticRegularizedMdb()};
		
		List<RegisterCNPJ> registerCNPJs;
		
		if (foot) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Foot " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				
				
				ArrayList<BaseEntityMongoDB> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));
				
				cont=cont+itens.size();
				
				if (itens != null && itens.size() > 0) {


						LoggerProcessing loggerProcessing = new LoggerProcessing();

						loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
						loggerProcessing.setCnpj(registerCNPJ);
						loggerProcessing.setDescricao("Alimentos");
						loggerProcessing.setCategoria(0);
						loggerProcessing.setOpcao(0);
						loggerProcessing.setTotalAnvisa(new Long(itens.size()));
						loggerProcessing.setInsertDate(LocalDateTime.now());

						loggerRepositoryMdb.save(loggerProcessing);
						try {
							intSynchronize[0].persist(itens, loggerProcessing);
						} catch (Exception e) {
								// TODO: handle exception
						}

				}
			}

			log.info("SynchronizeData => End Foot Total "+cont, dateFormat.format(new Date()));

		}
		
		if (saneantNotification) {

			registerCNPJs = registerCNPJRepository.findAll(2);
 		
		int cont = 0;

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {
 			
 			log.info("SynchronizeData => Start Saneante Notification "+registerCNPJ.getCnpj()+" "+registerCNPJ.getFullName(), dateFormat.format(new Date()));
			
 			ArrayList<BaseEntityMongoDB> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());
 			
 			log.info("SynchronizeData => Total "+itens.size(), dateFormat.format(new Date()));
 			
 			cont=cont+itens.size();
 			
			if (itens != null && itens.size()>0) {
					
					LoggerProcessing loggerProcessing = new LoggerProcessing();

					loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					loggerProcessing.setCnpj(registerCNPJ);
					loggerProcessing.setDescricao("Saneante - Registros Risco 1");
					loggerProcessing.setCategoria(2);
					loggerProcessing.setOpcao(0);
					loggerProcessing.setTotalAnvisa(new Long(itens.size()));
					loggerProcessing.setInsertDate(LocalDateTime.now());

	 	 	 		loggerRepositoryMdb.save(loggerProcessing); 
					
					try {
						intSynchronize[1].persist(itens, loggerProcessing);
					} catch (Exception e) {
							// TODO: handle exception
					}

			}
			
		}
	
 		log.info("SynchronizeData => End Saneante Notification Total "+cont, dateFormat.format(new Date()));
		
		}
		
		if (saneantProduct) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Saneante Product " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont=cont+itens.size();
				
				if (itens != null) {



						LoggerProcessing loggerProcessing = new LoggerProcessing();

						loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
						loggerProcessing.setCnpj(registerCNPJ);
						loggerProcessing.setDescricao("Saneante - Registros Risco 2");
						loggerProcessing.setCategoria(2);
						loggerProcessing.setOpcao(1);
						loggerProcessing.setTotalAnvisa(new Long(itens.size()));
						loggerProcessing.setInsertDate(LocalDateTime.now());

						loggerRepositoryMdb.save(loggerProcessing);

						try {
							intSynchronize[2].persist(itens, loggerProcessing);
						} catch (Exception e) {
								// TODO: handle exception
						}

				}

			}

	 		log.info("SynchronizeData => End Process Total "+cont, dateFormat.format(new Date()));
		}
		
		if (process) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {
				
				if (registerCNPJ.getId() >= 36) continue;
				
				log.info(
						"SynchronizeData => Start Process " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[3].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont=cont+itens.size();
				
				if (itens != null) {



						LoggerProcessing loggerProcessing = new LoggerProcessing();

						loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
						loggerProcessing.setCnpj(registerCNPJ);
						loggerProcessing.setDescricao("Processo");
						loggerProcessing.setCategoria(0);
						loggerProcessing.setOpcao(0);
						loggerProcessing.setTotalAnvisa(new Long(itens.size()));
						loggerProcessing.setInsertDate(LocalDateTime.now());

						loggerRepositoryMdb.save(loggerProcessing);

						try {
							intSynchronize[3].persist(itens, loggerProcessing);
						} catch (Exception e) {
								// TODO: handle exception
						}



				}

			}

			log.info("SynchronizeData => End Process Total "+cont, dateFormat.format(new Date()));
		}
		
		
		if (cosmeticRegister) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Cosmetic Register " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				if (itens != null && itens.size() > 0) {


						LoggerProcessing loggerProcessing = new LoggerProcessing();

						loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
						loggerProcessing.setCnpj(registerCNPJ);
						loggerProcessing.setDescricao("Cosméticos Registrados");
						loggerProcessing.setCategoria(1);
						loggerProcessing.setOpcao(0);
						loggerProcessing.setTotalAnvisa(new Long(itens.size()));
						loggerProcessing.setInsertDate(LocalDateTime.now());

						loggerRepositoryMdb.save(loggerProcessing);

						try {
							intSynchronize[4].persist(itens, loggerProcessing);
						} catch (Exception e) {
								// TODO: handle exception
						}


				}

			}

			log.info("SynchronizeData => End Cosmetic Register ", dateFormat.format(new Date()));

		}	
	 	
		if (cosmeticNotification) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Cosmetic Notification " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				if (itens != null && itens.size() > 0) {



						LoggerProcessing loggerProcessing = new LoggerProcessing();

						loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
						loggerProcessing.setCnpj(registerCNPJ);
						loggerProcessing.setDescricao("Cosméticos Notificados");
						loggerProcessing.setCategoria(1);
						loggerProcessing.setOpcao(1);
						loggerProcessing.setTotalAnvisa(new Long(itens.size()));
						loggerProcessing.setInsertDate(LocalDateTime.now());

						loggerRepositoryMdb.save(loggerProcessing);

						try {
							intSynchronize[5].persist(itens, loggerProcessing);
						} catch (Exception e) {
								// TODO: handle exception
						}



				}

			}

			log.info("SynchronizeData => End Cosmetic Notification ", dateFormat.format(new Date()));
		}
		
		if (cosmeticRegularized) {
			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Cosmetic Regularized " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				if (itens != null && itens.size() > 0) {



						LoggerProcessing loggerProcessing = new LoggerProcessing();

						loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
						loggerProcessing.setCnpj(registerCNPJ);
						loggerProcessing.setDescricao("Cosméticos Regularizados");
						loggerProcessing.setCategoria(1);
						loggerProcessing.setOpcao(2);
						loggerProcessing.setTotalAnvisa(new Long(itens.size()));
						loggerProcessing.setInsertDate(LocalDateTime.now());

						loggerRepositoryMdb.save(loggerProcessing);
						try {
						   intSynchronize[6].persist(itens, loggerProcessing);
						} catch (Exception e) {
							// TODO: handle exception
						}


				}

			}

			log.info("SynchronizeData => End Cosmetic Regularized ", dateFormat.format(new Date()));
		}
	}

	

	
}