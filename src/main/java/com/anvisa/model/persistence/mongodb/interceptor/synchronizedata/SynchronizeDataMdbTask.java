package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.repository.CosmeticNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegisterRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegularizedRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.FootRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteProductRepositoryMdb;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationPresentation;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProduct;
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
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

@Component
public class SynchronizeDataMdbTask implements Runnable {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	public static FootRepositoryMdb footRepositoryMdb;

	@Autowired
	public static SaneanteNotificationRepositoryMdb saneanteNotificationRepositoryMdb;

	@Autowired
	public static SaneanteProductRepositoryMdb saneanteProductRepositoryMdb;

	@Autowired
	public static ProcessRepositoryMdb processRepositoryMdb;

	@Autowired
	public static CosmeticRegisterRepositoryMdb cosmeticRegisterRepositoryMdb;

	@Autowired
	public static CosmeticNotificationRepositoryMdb cosmeticNotificationRepositoryMdb;

	@Autowired
	public static CosmeticRegularizedRepositoryMdb cosmeticRegularizedRepositoryMdb;

	private MongoClient mongoClient;

	private static MongoDatabase database;

	@Inject
	private static MongoTemplate mongoTemplate;

	private static String path = File.separator + "home" + File.separator + "findinfo" + File.separator + "base_tempo"
			+ File.separator;

	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository, LoggerRepositoryMdb loggerRepositoryMdb,
			SequenceDaoImpl sequence, MongoTemplate mongoTemplate, FootRepositoryMdb footRepositoryMdb,
			SaneanteNotificationRepositoryMdb saneanteNotificationRepositoryMdb,
			SaneanteProductRepositoryMdb saneanteProductRepositoryMdb, ProcessRepositoryMdb processRepositoryMdb,
			CosmeticRegisterRepositoryMdb cosmeticRegisterRepositoryMdb,
			CosmeticNotificationRepositoryMdb cosmeticNotificationRepositoryMdb,
			CosmeticRegularizedRepositoryMdb cosmeticRegularizedRepositoryMdb) {

		this.registerCNPJRepository = registerCNPJRepository;

		this.loggerRepositoryMdb = loggerRepositoryMdb;

		this.sequence = sequence;

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		this.mongoClient = new MongoClient("localhost",
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		this.footRepositoryMdb = footRepositoryMdb;

		this.database = mongoClient.getDatabase("findinfo02");

		this.database = database.withCodecRegistry(pojoCodecRegistry);

		this.mongoTemplate = mongoTemplate;

		this.saneanteNotificationRepositoryMdb = saneanteNotificationRepositoryMdb;

		this.saneanteProductRepositoryMdb = saneanteProductRepositoryMdb;

		this.processRepositoryMdb = processRepositoryMdb;

		this.cosmeticRegisterRepositoryMdb = cosmeticRegisterRepositoryMdb;

		this.cosmeticNotificationRepositoryMdb = cosmeticNotificationRepositoryMdb;

		this.cosmeticRegularizedRepositoryMdb = cosmeticRegularizedRepositoryMdb;

	}

	private static final Logger log = LoggerFactory.getLogger("SynchronizeData");

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	boolean foot = false;
	boolean saneantNotification = false;
	boolean saneantProduct = false;
	boolean process = false;
	boolean cosmeticRegister = false;
	boolean cosmeticNotification = false;
	boolean cosmeticRegularized = false;

	// @Scheduled(cron = "0 25 23 * * *")
	public static void synchronizeData() {

		Thread thread = new Thread(new SynchronizeDataMdbTask(), "SynchronizeDataMdbTask");
		thread.start();

	}

	public SynchronizeDataMdbTask(boolean foot, boolean saneantNotification, boolean saneantProduct, boolean process,
			boolean cosmeticRegister, boolean cosmeticNotification, boolean cosmeticRegularized) {
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
		this.foot = true;
		this.saneantNotification = true;
		this.saneantProduct = true;
		this.process = false;
		this.cosmeticRegister = true;
		this.cosmeticNotification = true;
		this.cosmeticRegularized = false;
	}

	@Override
	public void run() {

		log.info("SynchronizeData", dateFormat.format(new Date()));

		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(), new SynchronizeSaneanteNotificationMdb(),
				new SynchronizeSaneanteProductMdb(), new SynchronizeProcessMdb(), new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(), new SynchronizeCosmeticRegularizedMdb() };

		List<RegisterCNPJ> registerCNPJs;

		if (this.foot) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Foot " + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					genereteAndImportContentFootMdb(itens, "foot");

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

				log.info(
						"SynchronizeData => Start Saneante Notification " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					genereteAndImportSaneanteNotification(itens, "saneantNotification");

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

				log.info(
						"SynchronizeData => Start Saneante Product " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null) {

					genereteAndImportSaneanteProduct(itens, "saneantProduct");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Saneante - Registros Risco 2");
					 * loggerProcessing.setCategoria(2); loggerProcessing.setOpcao(1);
					 * loggerProcessing.setTotalAnvisa(new Long(itens.size()));
					 * loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing);
					 * 
					 * try { intSynchronize[2].persist(itens, loggerProcessing); } catch (Exception
					 * e) { // TODO: handle exception }
					 */

				}

			}

			log.info("SynchronizeData => End Saneante Product Total " + cont, dateFormat.format(new Date()));
		}

		if (this.process) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Process " + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[3].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null) {

					genereteAndImportProcess(itens, "process");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Processo"); loggerProcessing.setCategoria(0);
					 * loggerProcessing.setOpcao(0); loggerProcessing.setTotalAnvisa(new
					 * Long(itens.size())); loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing);
					 * 
					 * try { intSynchronize[3].persist(itens, loggerProcessing); } catch (Exception
					 * e) { // TODO: handle exception }
					 */

				}

			}

			log.info("SynchronizeData => End Process Total " + cont, dateFormat.format(new Date()));

		}

		if (this.cosmeticRegister) {

			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info(
						"SynchronizeData => Start Cosmetic Register " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					genereteAndImportCosmeticRegister(itens, "cosmeticRegisterBack");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Cosméticos Registrados");
					 * loggerProcessing.setCategoria(1); loggerProcessing.setOpcao(0);
					 * loggerProcessing.setTotalAnvisa(new Long(itens.size()));
					 * loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing);
					 * 
					 * try { intSynchronize[4].persist(itens, loggerProcessing); } catch (Exception
					 * e) { // TODO: handle exception }
					 */

				}

			}

			log.info("SynchronizeData => End Cosmetic Register Total " + cont, dateFormat.format(new Date()));

		}

		if (this.cosmeticNotification) {

			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info(
						"SynchronizeData => Start Cosmetic Notification " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					genereteAndImportCosmeticNotification(itens, "cosmeticNotificationBack");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Cosméticos Notificados");
					 * loggerProcessing.setCategoria(1); loggerProcessing.setOpcao(1);
					 * loggerProcessing.setTotalAnvisa(new Long(itens.size()));
					 * loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing);
					 * 
					 * try { intSynchronize[5].persist(itens, loggerProcessing); } catch (Exception
					 * e) { // TODO: handle exception }
					 */

				}

			}

			log.info("SynchronizeData => End Cosmetic Notification ", dateFormat.format(new Date()));
		}

		if (this.cosmeticRegularized) {
			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info(
						"SynchronizeData => Start Cosmetic Regularized " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<BaseEntityMongoDB> itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					genereteAndImportContentCosmeticRegularized(itens, "cosmeticRegularized");

					/*
					 * LoggerProcessing loggerProcessing = new LoggerProcessing();
					 * 
					 * loggerProcessing.setId(sequence.getNextSequenceId(LoggerProcessing.KEY_SEQ));
					 * loggerProcessing.setCnpj(registerCNPJ);
					 * loggerProcessing.setDescricao("Cosméticos Regularizados");
					 * loggerProcessing.setCategoria(1); loggerProcessing.setOpcao(2);
					 * loggerProcessing.setTotalAnvisa(new Long(itens.size()));
					 * loggerProcessing.setInsertDate(LocalDateTime.now());
					 * 
					 * loggerRepositoryMdb.save(loggerProcessing); try {
					 * intSynchronize[6].persist(itens, loggerProcessing); } catch (Exception e) {
					 * // TODO: handle exception }
					 */

				}

			}

			log.info("SynchronizeData => End Cosmetic Regularized Total " + cont, dateFormat.format(new Date()));
		}
	}

	public static String toJSON(ArrayList<Object> itens) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (Object d : itens) {
			sb.append(gson.toJson(d));
		}
		return sb.toString();
	}

	/*
	 * public static void ImportDB(String importPath, String filePath, String
	 * collection) {
	 * 
	 * Runtime r = Runtime.getRuntime(); Process p = null; String command =
	 * importPath +
	 * " --host localhost --db findinfo2 --username findinfo02 --password idkfa0101 --collection "
	 * +collection+" " + " --jsonArray --file " + filePath; try { p =
	 * r.exec(command); log.info(command);
	 * log.info("Reading "+collection+" json into Database");
	 * 
	 * } catch (Exception e) { log.error("Error executing " + command +
	 * e.toString()); }
	 * 
	 * }
	 */

	public static void genereteAndImport(ArrayList<BaseEntityMongoDB> itens, String filename, String collection) {
		/*
		 * Gson gson = new Gson(); File file = new File(filename); for (Iterator
		 * iterator = itens.iterator(); iterator.hasNext();) { BaseEntityMongoDB
		 * baseEntityMongoDB = (BaseEntityMongoDB) iterator.next(); String json =
		 * gson.toJson(iterator);
		 * 
		 * // String csv = CDL.toString(docs); FileUtils.writeStringToFile(file, json);
		 * }
		 */

		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			BaseEntityMongoDB baseEntityMongoDB = iterator.next();

			// MongoCollection<ContentCosmeticRegularized> dbcollection =
			// database.getCollection("cosmeticRegularized",ContentCosmeticRegularized.class);
			try {
				// cosmeticRegularizedRepositoryMdb.insert(baseEntityMongoDB);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		// SynchronizeDataMdbTask.ImportDB("mongoimport", filename, collection);
	}

	public static void genereteAndImportContentFootMdb(ArrayList<BaseEntityMongoDB> itens, String collection) {

		List<ContentFootMdb> listInsert = new ArrayList<ContentFootMdb>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			ContentFootMdb baseEntityMongoDB = (ContentFootMdb) iterator.next();
			try {
				if (i == 100) {
					footRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			footRepositoryMdb.insert(listInsert);
		}

	}

	public static void genereteAndImportSaneanteNotification(ArrayList<BaseEntityMongoDB> itens, String collection) {

		List<SaneanteNotification> listInsert = new ArrayList<SaneanteNotification>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			SaneanteNotification baseEntityMongoDB = (SaneanteNotification) iterator.next();
			try {
				if (i == 100) {
					saneanteNotificationRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			saneanteNotificationRepositoryMdb.insert(listInsert);
		}

	}

	public static void genereteAndImportSaneanteProduct(ArrayList<BaseEntityMongoDB> itens, String collection) {

		List<SaneanteProduct> listInsert = new ArrayList<SaneanteProduct>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			SaneanteProduct baseEntityMongoDB = (SaneanteProduct) iterator.next();
			try {
				if (i == 100) {
					saneanteProductRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			saneanteProductRepositoryMdb.insert(listInsert);
		}

	}

	public static void genereteAndImportProcess(ArrayList<BaseEntityMongoDB> itens, String collection) {

		List<Process> listInsert = new ArrayList<Process>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			Process baseEntityMongoDB = (Process) iterator.next();
			try {
				if (i == 500) {
					processRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			processRepositoryMdb.insert(listInsert);
		}

	}

	public static void genereteAndImportCosmeticRegister(ArrayList<BaseEntityMongoDB> itens, String collection) {

		List<ContentCosmeticRegister> listInsert = new ArrayList<ContentCosmeticRegister>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			ContentCosmeticRegister baseEntityMongoDB = (ContentCosmeticRegister) iterator.next();
			try {
				if (i == 200) {
					cosmeticRegisterRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			cosmeticRegisterRepositoryMdb.insert(listInsert);
		}

	}

	public static void genereteAndImportCosmeticNotification(ArrayList<BaseEntityMongoDB> itens, String collection) {

		List<ContentCosmeticNotification> listInsert = new ArrayList<ContentCosmeticNotification>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			ContentCosmeticNotification baseEntityMongoDB = (ContentCosmeticNotification) iterator.next();
			try {
				if (i == 200) {
					cosmeticNotificationRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			cosmeticNotificationRepositoryMdb.insert(listInsert);
		}

	}

	public static void genereteAndImportContentCosmeticRegularized(ArrayList<BaseEntityMongoDB> itens,
			String collection) {

		List<ContentCosmeticRegularized> listInsert = new ArrayList<ContentCosmeticRegularized>();
		int i = 1;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {
			ContentCosmeticRegularized baseEntityMongoDB = (ContentCosmeticRegularized) iterator.next();
			try {
				if (i == 200) {
					cosmeticRegularizedRepositoryMdb.insert(listInsert);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (listInsert.size() > 0) {
			cosmeticRegularizedRepositoryMdb.insert(listInsert);
		}

	}
}