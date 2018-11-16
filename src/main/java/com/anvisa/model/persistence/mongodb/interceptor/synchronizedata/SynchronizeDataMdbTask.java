package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import com.anvisa.model.persistence.mongodb.loggerprocessing.Processing;
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
	boolean notProcess = true;
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
		this.notProcess = true;
		this.foot = false;
		this.saneantNotification = false;
		this.saneantProduct = false;
		this.process = false;
		this.cosmeticRegister = false;
		this.cosmeticNotification = false;
		this.cosmeticRegularized = false;
	}

	int totalCnpjFoot = 0;
	int contfoot = 0;

	int totalCnpjSaneantNotification = 0;
	int contSaneantNotification = 0;

	int totalCnpjSaneantProduct = 0;
	int contCnpjSaneantProduct = 0;

	int totalCnpjProcess = 0;
	int contCnpjProcess = 0;

	int totalCnpjCosmeticRegister = 0;
	int contCnpjCosmeticRegister = 0;

	int totalCnpjCosmeticNotification = 0;
	int contCnpjCosmeticNotification = 0;

	int totalCnpjcosmeticRegularized = 0;
	int contCnpjCosmeticRegularized = 0;

	@Override
	public void run() {
		
		if (notProcess) {
			return;
		}

		log.info("Starti SynchronizeData", dateFormat.format(new Date()));

		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(), new SynchronizeSaneanteNotificationMdb(),
				new SynchronizeSaneanteProductMdb(), new SynchronizeProcessMdb(), new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(), new SynchronizeCosmeticRegularizedMdb() };
		
		List<RegisterCNPJ> registerCNPJs = registerCNPJRepository.findAll();

		for (RegisterCNPJ registerCNPJ : registerCNPJs) {

			new Thread(new Runnable() {

				public void run() {

					Processing loggerProcessing = new Processing();

					loggerProcessing.setCnpj(registerCNPJ);
					loggerProcessing.setInsertDate(LocalDate.now());

					mongoTemplate.insert(loggerProcessing);

					if (foot) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => Capturando Anvisa " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());

						itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);

							genereteAndImport(registerCNPJ, "foot", ContentFootMdb.class, log, 100);
						}

					}

					if (saneantNotification) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => ssaneantNotification Capturando Anvisa " + registerCNPJ.getId()
								+ " " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
						itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);
							genereteAndImport(registerCNPJ, "saneanteNotification", SaneanteNotification.class, log,
									300);
						}

					}

					if (saneantProduct) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => SaneantProduct Capturando Anvisa " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
						itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);
							genereteAndImport(registerCNPJ, "saneanteProduct", SaneanteProduct.class, log, 300);
						}

					}

					if (process) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => process Capturando Anvisa " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
						itens = intSynchronize[3].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);
							genereteAndImport(registerCNPJ, "process", Process.class, log, 1000);

						}

					}

					if (cosmeticRegister) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => cosmeticRegister Capturando Anvisa " + registerCNPJ.getId() + " "
								+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
						itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);
							genereteAndImport(registerCNPJ, "cosmeticRegister", ContentCosmeticRegister.class, log,
									500);
						}

					}

					if (cosmeticNotification) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => cosmeticNotification Capturando Anvisa " + registerCNPJ.getId()
								+ " " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
						itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);
							genereteAndImport(registerCNPJ, "cosmeticNotification", ContentCosmeticNotification.class,
									log, 500);
						}

					}

					if (cosmeticRegularized) {

						ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

						log.info("SynchronizeData => cosmeticRegularized Capturando Anvisa " + registerCNPJ.getId()
								+ " " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
						itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());

						if (itens.size() > 0) {
							registerCNPJ.setItensImport(itens);
							genereteAndImport(registerCNPJ, "cosmeticRegularized", ContentCosmeticRegularized.class,
									log, 500);
						}

					}
					
					loggerProcessing.setUpdateDate(LocalDate.now());
					
					mongoTemplate.save(loggerProcessing);

				}
			}).start();
			log.info("End SynchronizeData ", dateFormat.format(new Date()));
		}
	}

	public static void genereteAndImport(RegisterCNPJ registerCNPJ, String collection, Class classe, Logger Log,
			int qtdPersistence) {

		int i = 1, cont = 1;

		ArrayList<BaseEntityMongoDB> itens = registerCNPJ.getItensImport();

		List<BaseEntityMongoDB> listInsert = new ArrayList<BaseEntityMongoDB>();

		for (BaseEntityMongoDB baseEntityMongoDB : itens) {

			try {

				if (i == qtdPersistence) {
					Log.info("SynchronizeData => " + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
							+ registerCNPJ.getFullName() + "Inserindo Register " + collection);

					mongoTemplate.insert(listInsert, classe);
					listInsert.clear();
					i = 0;
				} else {
					listInsert.add(baseEntityMongoDB);

				}
				i++;
				cont = cont + 1;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		if (listInsert.size() > 0) {
			Log.info("SynchronizeData => " + registerCNPJ.getId() + " " + registerCNPJ.getCnpj() + " "
					+ registerCNPJ.getFullName() + " Inserindo register e finalizando " + collection);

			mongoTemplate.insert(listInsert, classe);

		}

		Log.info("SynchronizeData => Inserindo register e finalizando " + collection + " : Total Geral " + cont);

	}

}