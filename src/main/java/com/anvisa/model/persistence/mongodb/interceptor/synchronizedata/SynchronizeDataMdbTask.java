package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
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
import com.google.gson.Gson;
import com.mongodb.MongoClient;

@Component
public class SynchronizeDataMdbTask implements Runnable {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public static SequenceDaoImpl sequence;

	private static String path = File.separator+"home"+File.separator+"findinfo"+File.separator+"base_tempo"+File.separator;

	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository, LoggerRepositoryMdb loggerRepositoryMdb,
			SequenceDaoImpl sequence) {
		this.registerCNPJRepository = registerCNPJRepository;
		this.loggerRepositoryMdb = loggerRepositoryMdb;
		this.sequence = sequence;

	}

	private static final Logger log = LoggerFactory.getLogger("SynchronizeData");

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private MongoClient mongoClient;
	
	boolean foot = false;
	boolean saneantNotification = false;
	boolean saneantProduct = false;
	boolean process = false;
	boolean cosmeticRegister = true;
	boolean cosmeticNotification = true;
	boolean cosmeticRegularized = true;

	//@Scheduled(cron = "0 25 23 * * *")
	public static void synchronizeData() {

		Thread thread = new Thread(new SynchronizeDataMdbTask(), "SynchronizeDataMdbTask");
		thread.start();

	}
	
	
	

	public SynchronizeDataMdbTask(boolean foot, boolean saneantNotification,
			boolean saneantProduct, boolean process, boolean cosmeticRegister, boolean cosmeticNotification,
			boolean cosmeticRegularized) {
		super();

		this.foot = foot;
		this.saneantNotification = saneantNotification;
		this.saneantProduct = saneantProduct;
		this.process = process;
		this.cosmeticRegister = cosmeticRegister;
		this.cosmeticNotification = cosmeticNotification;
		this.cosmeticRegularized = cosmeticRegularized;
	}




	public SynchronizeDataMdbTask() {
		// TODO Auto-generated constructor stub
		this.foot = false;
		this.saneantNotification = false;
		this.saneantProduct = false;
		this.process = false;
		this.cosmeticRegister = false;
		this.cosmeticNotification = true;
		this.cosmeticRegularized = true;
	}




	@Override
	public void run() {


		log.info("SynchronizeData", dateFormat.format(new Date()));

		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(), new SynchronizeSaneanteNotificationMdb(),
				new SynchronizeSaneanteProductMdb(), new SynchronizeProcessMdb(), new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(), new SynchronizeCosmeticRegularizedMdb() };

		List<RegisterCNPJ> registerCNPJs;

		if (this.foot) {

			registerCNPJs = registerCNPJRepository.findAll(0);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Foot " + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					 genereteAndImport(itens, path+"foot_"+registerCNPJ.getCnpj()+".json", "footBack");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Alimentos"); loggerProcessing.setCategoria(0);
					 * loggerProcessing.setOpcao(0); loggerProcessing.setTotalAnvisa(new
					 * Long(itens.size())); loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing); try {
					 * intSynchronize[0].persist(itens, loggerProcessing); } catch (Exception e) {
					 * // TODO: handle exception }
					 */

				}
			}

			log.info("SynchronizeData => End Foot Total " + cont, dateFormat.format(new Date()));

		}

		if (this.saneantNotification) {

			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Saneante Notification "  + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					genereteAndImport(itens, path + "saneanteNotification_" + registerCNPJ.getCnpj()+".json",
							"saneantNotificationBack");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Saneante - Registros Risco 1");
					 * loggerProcessing.setCategoria(2); loggerProcessing.setOpcao(0);
					 * loggerProcessing.setTotalAnvisa(new Long(itens.size()));
					 * loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing);
					 * 
					 * try { intSynchronize[1].persist(itens, loggerProcessing); } catch (Exception
					 * e) { // TODO: handle exception }
					 */

				}

			}

			log.info("SynchronizeData => End Saneante Notification Total " + cont, dateFormat.format(new Date()));

		}

		if (this.saneantProduct) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;
			
			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Saneante Product "  + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null) {
					
					genereteAndImport(itens, path + "saneantProduct_" + registerCNPJ.getCnpj()+".json",
							"saneantProductBack");

					/*LoggerProcessing loggerProcessing = new LoggerProcessing();

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
					}*/

				}

			}
			
			log.info("SynchronizeData => End Saneante Product Total " + cont, dateFormat.format(new Date()));
		}

		if (this.process) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;
			
			for (RegisterCNPJ registerCNPJ : registerCNPJs) {


				log.info(
						"SynchronizeData => Start Process "  + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[3].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null) {
					
					
					genereteAndImport(itens, path + "process_" + registerCNPJ.getCnpj()+".json",
							"process");

					/*LoggerProcessing loggerProcessing = new LoggerProcessing();

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
					}*/

				}

			}
			
			log.info("SynchronizeData => End Process Total " + cont, dateFormat.format(new Date()));
			
		}

		if (this.cosmeticRegister) {

			registerCNPJs = registerCNPJRepository.findForId(25);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Cosmetic Register "  + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont=cont+itens.size();
				
				if (itens != null && itens.size() > 0) {

					genereteAndImport(itens, path + "cosmeticRegister_" + registerCNPJ.getCnpj()+".json",
							"cosmeticRegisterBack");
					
					/*LoggerProcessing loggerProcessing = new LoggerProcessing();

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
					}*/

				}

			}

			log.info("SynchronizeData => End Cosmetic Register Total "+cont, dateFormat.format(new Date()));

		}

		if (this.cosmeticNotification) {

			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Cosmetic Notification "  + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));
				
				cont=cont+itens.size();
				
				if (itens != null && itens.size() > 0) {
					
					genereteAndImport(itens, path + "cosmeticNotification_" + registerCNPJ.getCnpj()+".json",
							"cosmeticNotificationBack");

					/*LoggerProcessing loggerProcessing = new LoggerProcessing();

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
					}*/

				}

			}

			log.info("SynchronizeData => End Cosmetic Notification ", dateFormat.format(new Date()));
		}

		if (this.cosmeticRegularized) {
			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Cosmetic Regularized "  + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));
				
				cont=cont+itens.size();
				
				if (itens != null && itens.size() > 0) {
					
					genereteAndImport(itens, path + "cosmeticRegularized_" + registerCNPJ.getCnpj()+".json",
							"cosmeticRegularizedBack");

					/*LoggerProcessing loggerProcessing = new LoggerProcessing();

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
					}*/

				}

			}

			log.info("SynchronizeData => End Cosmetic Regularized Total "+cont, dateFormat.format(new Date()));
		}
	}

	

	public static String toJSON(ArrayList<BaseEntityMongoDB> itens) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (BaseEntityMongoDB d : itens) {
			sb.append(gson.toJson(d));
		}
		return sb.toString();
	}

	public static void ImportDB(String importPath, String filePath, String collection) {
		
		Runtime r = Runtime.getRuntime();
		Process p = null;
		String command = importPath + " --host mongo71-farm68.kinghost.net --db findinfo01 --username findinfo01 --password idkfa0101 --collection "+collection+" "
				+ " --jsonArray --file " + filePath;
		try {
			p = r.exec(command);
			log.info(command);
			log.info("Reading "+collection+" json into Database");

		} catch (Exception e) {
			log.error("Error executing " + command + e.toString());
		}
		
	}
	
	
	public static void genereteAndImport(ArrayList<BaseEntityMongoDB> itens, String filename, String collection ) {
		
		try {
			Gson gson = new Gson();
			String json = gson.toJson(itens);
			File file = new File(filename);
			// String csv = CDL.toString(docs);
			FileUtils.writeStringToFile(file, json);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		SynchronizeDataMdbTask.ImportDB("mongoimport", filename, collection);
	}
}