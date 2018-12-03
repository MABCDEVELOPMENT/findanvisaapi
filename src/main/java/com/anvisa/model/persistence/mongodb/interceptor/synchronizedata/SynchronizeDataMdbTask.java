package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LogErroProcessig;
import com.anvisa.model.persistence.mongodb.loggerprocessing.Processing;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.process.ProcessDetail;
import com.anvisa.model.persistence.mongodb.process.ProcessPetition;
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
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

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

	@Autowired
	private static DB database;

	@Inject
	private static MongoTemplate mongoTemplate;

	private static String path = File.separator + "home" + File.separator + "findinfo" + File.separator + "base_tempo"
			+ File.separator;
	
	@Value("${spring.data.mongodb.username}")
	private static String usuario = "findinfo01";
	
	@Value("${spring.data.mongodb.password}")
	private static String password = "idkfa0101";
	
	@Value("${spring.data.mongodb.hostForRestore}")
	private static String host 	   = "mongo71-farm68.kinghost.net";
	//private static String host 	   = "localhost";
	
	@Value("${spring.data.mongodb.database}")
	private static String banco    = "findinfo01";

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

		this.mongoClient = new MongoClient(host,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		this.footRepositoryMdb = footRepositoryMdb;

		this.database = mongoClient.getDB(banco);

		// this.database = database.withCodecRegistry(pojoCodecRegistry);

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
	boolean notProcess = false;
	boolean foot = true;
	boolean saneantNotification = true;
	boolean saneantProduct = true;
	boolean process = false;
	boolean cosmeticRegister = true;
	boolean cosmeticNotification = true;
	boolean cosmeticRegularized = true;
	
	public ArrayList<String> cnpjs;

	// @Scheduled(cron = "0 40 20 * * *")
	public void synchronizeData(ArrayList<String> cnpjs) {

		this.cnpjs = cnpjs;
		
		Thread thread = new Thread(new SynchronizeDataMdbTask(cnpjs), "SynchronizeDataMdbTask");
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

	public SynchronizeDataMdbTask(ArrayList<String> cnpjs) {
		// TODO Auto-generated constructor stub
		this.cnpjs = cnpjs;
		this.notProcess = false;
		this.foot = true;
		this.saneantNotification = true;
		this.saneantProduct = true;
		this.process = true;
		this.cosmeticRegister = true;
		this.cosmeticNotification = true;
		this.cosmeticRegularized = true;
	}
	public SynchronizeDataMdbTask() {
		// TODO Auto-generated constructor stub
		this.notProcess = false;
		this.foot = true;
		this.saneantNotification = true;
		this.saneantProduct = true;
		this.process = true;
		this.cosmeticRegister = true;
		this.cosmeticNotification = true;
		this.cosmeticRegularized = true;
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
				new SynchronizeSaneanteProductMdb(), null, new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(), new SynchronizeCosmeticRegularizedMdb() };

		

		// cnpjs.add("59748988000114");
		// cnpjs.add("00190373000172");
		//cnpjs.add("03993167000199");
		
		//cnpjs.add("61362182000135");
		
		List<RegisterCNPJ> registerCNPJs;
		
		if (cnpjs!=null) {

		    registerCNPJs = registerCNPJRepository.findCnpjs(cnpjs);

		}   else {
			
			registerCNPJs = registerCNPJRepository.findAll();
			
		}
		
		for (RegisterCNPJ registerCNPJ : registerCNPJs) {

			/*
			 * new Thread(new Runnable() {
			 * 
			 * public void run() {
			 */

			Processing loggerProcessing = new Processing();

			loggerProcessing.setCnpj(registerCNPJ);
			loggerProcessing.setHoraInicio(LocalDateTime.now());

			mongoTemplate.insert(loggerProcessing);

			ArrayList<BaseEntityMongoDB> processoList = new ArrayList<BaseEntityMongoDB>();

			String fileProcess = path + "process_" + registerCNPJ.getCnpj() + ".json";

			if (foot && registerCNPJ.getCategory() == 0) {

				ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

				ArrayList<BaseEntityMongoDB> itensPersist = new ArrayList<BaseEntityMongoDB>();

				log.info("SynchronizeData => Capturando Anvisa " + registerCNPJ.getId() + " " + registerCNPJ.getCnpj()
						+ " " + registerCNPJ.getFullName());

				itens = intSynchronize[0].loadData(registerCNPJ.getCnpj());

				if (itens.size() > 0) {

					for (BaseEntityMongoDB baseEntityMongoDB : itens) {

						String cnpj = registerCNPJ.getCnpj();
						String processo = ((ContentFootMdb) baseEntityMongoDB).getProcesso();
						
						ArrayList<BaseEntityMongoDB> processos = getProcess(cnpj, processo);;
						
						Process process = null;

						if (processos != null && processos.size() > 0) {
							process = (Process) processos.get(0);
							processoList.addAll(processos);
						}

						if (process == null) {
							itensPersist.add(baseEntityMongoDB);
							continue;
						}

						try {

							LocalDate[] dates = getDateProcess(process);

							int qtd = process.getProcessDetail().getPeticoes() == null ? 0
									: process.getProcessDetail().getPeticoes().size();

							process.setQtdRegistro(qtd);
							process.setDataRegistro(dates[0]);
							process.setDataAlteracao(dates[1]);

							((ContentFootMdb) baseEntityMongoDB).setProcess(process);

							((ContentFootMdb) baseEntityMongoDB).setQtdRegistro(qtd);
							((ContentFootMdb) baseEntityMongoDB).setDataRegistro(dates[0]);
							((ContentFootMdb) baseEntityMongoDB).setDataAlteracao(dates[1]);

						} catch (Exception e) {
							log.info("SynchronizeData => Foot Capturando Anvisa " + e.getMessage());
							LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(),
									ContentFootMdb.class.getName(), this.getClass().getName(),
									e, LocalDateTime.now());
							mongoTemplate.save(log);
						}

						itensPersist.add(baseEntityMongoDB);

					}

					registerCNPJ.setItensImport(itensPersist);

					genereteAndImport(registerCNPJ, "foot");

					// executeRestore(path + "foot_" + registerCNPJ.getCnpj() + ".json", "foot");
				}

			}

			if (saneantNotification && registerCNPJ.getCategory() == 2) {

				ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

				ArrayList<BaseEntityMongoDB> itensPersist = new ArrayList<BaseEntityMongoDB>();

				log.info("SynchronizeData => ssaneantNotification Capturando Anvisa " + registerCNPJ.getId() + " "
						+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
				itens = intSynchronize[1].loadData(registerCNPJ.getCnpj());

				if (itens.size() > 0) {

					for (BaseEntityMongoDB baseEntityMongoDB : itens) {

						String cnpj = registerCNPJ.getCnpj();
						String processo = ((SaneanteNotification) baseEntityMongoDB).getProcesso();


						ArrayList<BaseEntityMongoDB> processos = getProcess(cnpj, processo);;
						
						Process process = null;

						if (processos != null && processos.size() > 0) {
							process = (Process) processos.get(0);
							processoList.addAll(processos);
						}
						
						if (process == null) {
							itensPersist.add(baseEntityMongoDB);
							continue;
						}

						try {

							LocalDate[] dates = getDateProcess(process);

							int qtd = process.getProcessDetail().getPeticoes() == null ? 0
									: process.getProcessDetail().getPeticoes().size();

							process.setQtdRegistro(qtd);
							process.setDataRegistro(dates[0]);
							process.setDataAlteracao(dates[1]);

							((SaneanteNotification) baseEntityMongoDB).setProcess(process);

							((SaneanteNotification) baseEntityMongoDB).setQtdRegistro(qtd);
							((SaneanteNotification) baseEntityMongoDB).setDataRegistro(dates[0]);
							((SaneanteNotification) baseEntityMongoDB).setDataAlteracao(dates[1]);

						} catch (Exception e) {
							log.info("SynchronizeData => SaneanteNotification Capturando Anvisa " + e.getMessage());
							LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(),
									SaneanteNotification.class.getName(), this.getClass().getName(),
									e, LocalDateTime.now());
							mongoTemplate.save(log);
						}

						itensPersist.add(baseEntityMongoDB);

					}

					registerCNPJ.setItensImport(itensPersist);

					genereteAndImport(registerCNPJ, "saneanteNotification");

					// executeRestore(path + "saneanteNotification_" + registerCNPJ.getCnpj() +
					// ".json",
					// "saneanteNotification");
				}

			}

			if (saneantProduct && registerCNPJ.getCategory() == 2) {

				ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

				ArrayList<BaseEntityMongoDB> itensPersist = new ArrayList<BaseEntityMongoDB>();

				log.info("SynchronizeData => SaneantProduct Capturando Anvisa " + registerCNPJ.getId() + " "
						+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
				itens = intSynchronize[2].loadData(registerCNPJ.getCnpj());

				if (itens.size() > 0) {

					for (BaseEntityMongoDB baseEntityMongoDB : itens) {

						String cnpj = registerCNPJ.getCnpj();
						String processo = ((SaneanteProduct) baseEntityMongoDB).getProcesso();

						ArrayList<BaseEntityMongoDB> processos = getProcess(cnpj, processo);;
						
						Process process = null;

						if (processos != null && processos.size() > 0) {
							process = (Process) processos.get(0);
							processoList.addAll(processos);
						}
						
						if (process == null) {
							itensPersist.add(baseEntityMongoDB);
							continue;
						}


						try {

							LocalDate[] dates = getDateProcess(process);

							int qtd = process.getProcessDetail().getPeticoes() == null ? 0
									: process.getProcessDetail().getPeticoes().size();

							process.setQtdRegistro(qtd);
							process.setDataRegistro(dates[0]);
							process.setDataAlteracao(dates[1]);

							((SaneanteProduct) baseEntityMongoDB).setProcess(process);

							((SaneanteProduct) baseEntityMongoDB).setQtdRegistro(qtd);
							((SaneanteProduct) baseEntityMongoDB).setDataRegistro(dates[0]);
							((SaneanteProduct) baseEntityMongoDB).setDataAlteracao(dates[1]);

						} catch (Exception e) {
							log.info("SynchronizeData => SaneantProduct Capturando Anvisa " + e.getMessage());
							LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(),
									SaneanteProduct.class.getName(), this.getClass().getName(),
									e, LocalDateTime.now());
							mongoTemplate.save(log);

						}

						itensPersist.add(baseEntityMongoDB);

					}

					registerCNPJ.setItensImport(itensPersist);

					genereteAndImport(registerCNPJ, "saneanteProduct");

					// executeRestore(path + "saneanteProduct_" + registerCNPJ.getCnpj() + ".json",
					// "saneanteProduct");
				}

			}

			if (cosmeticRegister && registerCNPJ.getCategory() == 1) {

				ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

				ArrayList<BaseEntityMongoDB> itensPersist = new ArrayList<BaseEntityMongoDB>();

				log.info("SynchronizeData => cosmeticRegister Capturando Anvisa " + registerCNPJ.getId() + " "
						+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
				itens = intSynchronize[4].loadData(registerCNPJ.getCnpj());

				if (itens.size() > 0) {

					for (BaseEntityMongoDB baseEntityMongoDB : itens) {

						String cnpj = registerCNPJ.getCnpj();
						String processo = ((ContentCosmeticRegister) baseEntityMongoDB).getProcesso();

						ArrayList<BaseEntityMongoDB> processos = getProcess(cnpj, processo);;
						
						Process process = null;

						if (processos != null && processos.size() > 0) {
							process = (Process) processos.get(0);
							processoList.addAll(processos);
						}
						
						if (process == null) {
							itensPersist.add(baseEntityMongoDB);
							continue;
						}


						try {

							LocalDate[] dates = getDateProcess(process);

							int qtd = process.getProcessDetail().getPeticoes() == null ? 0
									: process.getProcessDetail().getPeticoes().size();

							process.setQtdRegistro(qtd);
							process.setDataRegistro(dates[0]);
							process.setDataAlteracao(dates[1]);

							((ContentCosmeticRegister) baseEntityMongoDB).setProcess(process);

							((ContentCosmeticRegister) baseEntityMongoDB).setQtdRegistro(qtd);
							((ContentCosmeticRegister) baseEntityMongoDB).setDataRegistro(dates[0]);
							((ContentCosmeticRegister) baseEntityMongoDB).setDataAlteracao(dates[1]);

						} catch (Exception e) {
							log.info("SynchronizeData => cosmeticRegister Capturando Anvisa " + e.getMessage());
							LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(),
									ContentCosmeticRegister.class.getName(), this.getClass().getName(),
									e, LocalDateTime.now());
							mongoTemplate.save(log);
						}

						itensPersist.add(baseEntityMongoDB);

					}

					registerCNPJ.setItensImport(itensPersist);

					genereteAndImport(registerCNPJ, "cosmeticRegister");

					// executeRestore(path + "cosmeticRegister_" + registerCNPJ.getCnpj() + ".json",
					// "cosmeticRegister");
				}

			}

			if (cosmeticNotification && registerCNPJ.getCategory() == 1) {

				ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

				ArrayList<BaseEntityMongoDB> itensPersist = new ArrayList<BaseEntityMongoDB>();

				log.info("SynchronizeData => cosmeticNotification Capturando Anvisa " + registerCNPJ.getId() + " "
						+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());

				itens = intSynchronize[5].loadData(registerCNPJ.getCnpj());

				if (itens.size() > 0) {

					for (BaseEntityMongoDB baseEntityMongoDB : itens) {

						String cnpj = registerCNPJ.getCnpj();
						String processo = ((ContentCosmeticNotification) baseEntityMongoDB).getProcesso();

						ArrayList<BaseEntityMongoDB> processos = getProcess(cnpj, processo);;
						
						Process process = null;

						if (processos != null && processos.size() > 0) {
							process = (Process) processos.get(0);
							processoList.addAll(processos);
						}
						
						if (process == null) {
							itensPersist.add(baseEntityMongoDB);
							continue;
						}
						
						try {
							LocalDate[] dates = getDateProcess(process);

							int qtd = process.getProcessDetail().getPeticoes() == null ? 0
									: process.getProcessDetail().getPeticoes().size();

							process.setQtdRegistro(qtd);
							process.setDataRegistro(dates[0]);
							process.setDataAlteracao(dates[1]);

							((ContentCosmeticNotification) baseEntityMongoDB).setProcess(process);

							((ContentCosmeticNotification) baseEntityMongoDB).setQtdRegistro(qtd);
							((ContentCosmeticNotification) baseEntityMongoDB).setDataRegistro(dates[0]);
							((ContentCosmeticNotification) baseEntityMongoDB).setDataAlteracao(dates[1]);
						} catch (Exception e) {
							log.info("SynchronizeData => cosmeticNotification Capturando Anvisa " + e.getMessage());
							LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(),
									ContentCosmeticNotification.class.getName(), this.getClass().getName(),
									e, LocalDateTime.now());
							mongoTemplate.save(log);
						}

						itensPersist.add(baseEntityMongoDB);

					}

					registerCNPJ.setItensImport(itensPersist);

					genereteAndImport(registerCNPJ, "cosmeticNotification");

				}

			}

			if (cosmeticRegularized && registerCNPJ.getCategory() == 1) {

				ArrayList<BaseEntityMongoDB> itens = new ArrayList<BaseEntityMongoDB>();

				ArrayList<BaseEntityMongoDB> itensPersist = new ArrayList<BaseEntityMongoDB>();

				log.info("SynchronizeData => cosmeticRegularized Capturando Anvisa " + registerCNPJ.getId() + " "
						+ registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName());
				itens = intSynchronize[6].loadData(registerCNPJ.getCnpj());

				if (itens.size() > 0) {

					for (BaseEntityMongoDB baseEntityMongoDB : itens) {

						String cnpj = registerCNPJ.getCnpj();
						String processo = ((ContentCosmeticRegularized) baseEntityMongoDB).getProcesso();

						ArrayList<BaseEntityMongoDB> processos = getProcess(cnpj, processo);;
						
						Process process = null;

						if (processos != null && processos.size() > 0) {
							process = (Process) processos.get(0);
							processoList.addAll(processos);
						}

						try {

							LocalDate[] dates = getDateProcess(process);

							int qtd = process.getProcessDetail().getPeticoes() == null ? 0
									: process.getProcessDetail().getPeticoes().size();

							process.setQtdRegistro(qtd);
							process.setDataRegistro(dates[0]);
							process.setDataAlteracao(dates[1]);

							((ContentCosmeticRegularized) baseEntityMongoDB).setProcess(process);

							((ContentCosmeticRegularized) baseEntityMongoDB).setQtdRegistro(qtd);
							((ContentCosmeticRegularized) baseEntityMongoDB).setDataRegistro(dates[0]);
							((ContentCosmeticRegularized) baseEntityMongoDB).setDataAlteracao(dates[1]);

						} catch (Exception e) {
							log.info("SynchronizeData => cosmeticNotification Capturando Anvisa " + e.getMessage());
							LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(),
									ContentCosmeticRegularized.class.getName(), this.getClass().getName(),
									e, LocalDateTime.now());
							mongoTemplate.save(log);
						}
						itensPersist.add(baseEntityMongoDB);

					}

					registerCNPJ.setItensImport(itensPersist);

					genereteAndImport(registerCNPJ, "cosmeticRegularized");

				}

			}
			
			if (processoList.size() > 0) {

				JSONArrayToFile(processoList, fileProcess);

				executeRestore(fileProcess, "process",banco,usuario);

				loggerProcessing.setHoraFinal(LocalDateTime.now());
			}
			
			mongoTemplate.save(loggerProcessing);

			// executeRestore(path + "process_" + registerCNPJ.getCnpj() + ".json",
			// "process");

		}
		log.info("End SynchronizeData ", dateFormat.format(new Date()));
	}

	public static void genereteAndImport(RegisterCNPJ registerCNPJ, String coll) {

		ArrayList<BaseEntityMongoDB> itens = registerCNPJ.getItensImport();

		if (itens.size() > 0) {

			String file = path + coll + "_" + registerCNPJ.getCnpj() + ".json";

			JSONArrayToFile(itens, file);

			executeRestore(file, coll,banco,usuario);

			log.info("SynchronizeData => Inserindo register e finalizando " + coll + " : Total Geral " + itens.size());

		} else {
			log.info("SynchronizeData => finalizando " + coll + " : Total Geral " + itens.size());
		}

	}

	static void JSONArrayToFile(ArrayList<BaseEntityMongoDB> insetList, String filename) {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			objectMapper.writeValue(new File(filename), insetList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<BaseEntityMongoDB> getProcess(String cnpj, String processo) {

		SynchronizeProcessMdb synchronizeProcessMdb = new SynchronizeProcessMdb();

		Process processReturn = null;

		int cont = 0;

		ArrayList<BaseEntityMongoDB> baseEntityMongoDBList = new ArrayList<BaseEntityMongoDB>();
		ArrayList<BaseEntityMongoDB> listInsert = new ArrayList<BaseEntityMongoDB>();
		try {



			baseEntityMongoDBList = synchronizeProcessMdb.loadData(cnpj + "&filter[processo]=" + processo, 0);

			if (baseEntityMongoDBList != null && baseEntityMongoDBList.size() > 0) {

				for (BaseEntityMongoDB baseEntityMongoDB : baseEntityMongoDBList) {

					Process process = (Process) baseEntityMongoDB;

					ProcessDetail processDetail = synchronizeProcessMdb.loadDetailData(cnpj, process.getProcesso());

					process.setProcessDetail(processDetail);

					if (processDetail != null && processReturn == null) {
						processReturn = process;
					}

					LocalDate[] dates = getDateProcess(process);

					int qtd = process.getProcessDetail().getPeticoes() == null ? 0
							: process.getProcessDetail().getPeticoes().size();

					process.setQtdRegistro(qtd);
					process.setDataRegistro(dates[0]);
					process.setDataAlteracao(dates[1]);

					listInsert.add(process);

					cont++;
					/*
					if (cont==100) {
						mongoTemplate.insert(listInsert, Process.class);
						System.gc();
						listInsert = new ArrayList<BaseEntityMongoDB>();
					}*/

				}

			}
			
			/*if (listInsert.size()>0) {
				mongoTemplate.insert(listInsert, Process.class);
				System.gc();
			}*/

		} catch (Exception e) {
			log.error(cnpj + " processo " + processo + " => " + e.getMessage());
		}
		return listInsert;

	}

	public LocalDate[] getDateProcess(Process process) {
		LocalDate[] dates = { null, null };
		if (process != null) {

			dates[0] = process.getProcessDetail().getProcesso().getPeticao().getDataEntrada();

			ArrayList<ProcessPetition> processPetitions = (ArrayList<ProcessPetition>) process.getProcessDetail()
					.getPeticoes();

			for (ProcessPetition processPetition : processPetitions) {

				if (processPetition.getDataPublicacao() != null) {

					if (dates[1] != null && processPetition.getDataPublicacao().isAfter(dates[1])) {
						dates[1] = processPetition.getDataPublicacao();
					} else {
						if (dates[1] == null)
							dates[1] = processPetition.getDataPublicacao();
					}
				}
			}

			try {

				if (dates[1] == null) {

					dates[1] = process.getProcessDetail().getProcesso().getPeticao().getDataPublicacao();

				}

			} catch (Exception e) {
				// TODO: handle exception
				try {
					System.out.println(
							this.getClass().getName() + " CNPJ " + process.getProcessDetail().getEmpresa().getCnpj()
									+ " Processo " + process.getProcesso() + " ERRO DE DATAS 1");
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println(
							this.getClass().getName() + " Processo " + process.getProcesso() + " ERRO DE DATAS 2");
				}

			}

		}

		return dates;

	}

	public static void loadDetailProcess() {

		SynchronizeProcessMdb synchronizeProcessMdb = new SynchronizeProcessMdb();

		ArrayList<Process> listProcess = processRepositoryMdb.findDetailIsNull();

		ArrayList<Process> listUpdateProcess = new ArrayList<Process>();

		log.info("List Process null " + listUpdateProcess.size());

		for (Process process : listProcess) {

			ProcessDetail processDetail = synchronizeProcessMdb.loadDetailData(process.getCnpj(),
					process.getProcesso());

			if (processDetail != null) {

				process.setProcessDetail(processDetail);

				listUpdateProcess.add(process);

			}

		}

		log.info("listUpdateProcess  " + listUpdateProcess.size());

		processRepositoryMdb.saveAll(listUpdateProcess);

	}

	public static void executeRestore(String file, String collection,String banco,String usuario) {

		final String CMD = "mongoimport --host "+host+" --port=27017 -u "+banco+" -p "+password+" --collection "
				+ collection + " --db "+usuario+" "+ file + " --jsonArray";

		try {

			log.info(CMD);
			// Run "netsh" Windows command
			java.lang.Process process = Runtime.getRuntime().exec(CMD);

			// Get input streams
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// Read command standard output
			String s;
			System.out.println("Standard output: ");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// Read command errors
			System.out.println("Standard error: ");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}