package com.anvisa.model.persistence.mongodb.interceptor.synchronizedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.mongodb.BulkWriteOperation;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

@Component
public class SynchronizeDataMdbTask implements Runnable {

	@Autowired
	private static RegisterCNPJRepository registerCNPJRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public static SequenceDaoImpl sequence;

	@Inject
	private static MongoTemplate mongoTemplate;

	@Autowired
	public void setService(RegisterCNPJRepository registerCNPJRepository, LoggerRepositoryMdb loggerRepositoryMdb,
			SequenceDaoImpl sequence, MongoTemplate mongoTemplate) {
		this.registerCNPJRepository = registerCNPJRepository;
		this.loggerRepositoryMdb = loggerRepositoryMdb;
		this.sequence = sequence;
		this.mongoTemplate = mongoTemplate;
	}

	private static final Logger log = LoggerFactory.getLogger("SynchronizeData");

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private MongoClient mongoClient;

	@Scheduled(cron = "0 10 02 * * *")
	public static void synchronizeData() {

		Thread thread = new Thread(new SynchronizeDataMdbTask(), "SynchronizeDataMdbTask");
		thread.start();

	}

	@Override
	public void run() {

		boolean foot = false;
		boolean saneantNotification = false;
		boolean saneantProduct = false;
		boolean process = true;
		boolean cosmeticRegister = false;
		boolean cosmeticNotification = false;
		boolean cosmeticRegularized = false;

		log.info("SynchronizeData", dateFormat.format(new Date()));

		IntSynchronizeMdb[] intSynchronize = { new SynchronizeFootMdb(), new SynchronizeSaneanteNotificationMdb(),
				new SynchronizeSaneanteProductMdb(), new SynchronizeProcessMdb(), new SynchronizeCosmeticRegisterMdb(),
				new SynchronizeCosmeticNotificationMdb(), new SynchronizeCosmeticRegularizedMdb() };

		List<RegisterCNPJ> registerCNPJs;

		if (foot) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;

			mongoClient = new MongoClient("localhost");

			MongoDatabase database = mongoClient.getDatabase("findinfo01");

			MongoCollection<Document> coll = database.getCollection("footBack");

			ArrayList<Document> itensFile = new ArrayList<Document>();

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Foot " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<Document> itens = intSynchronize[0].loadDataDocument(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {

					itensFile.addAll(itens);

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
			
			mongoClient.close();
			
			if (itensFile.size() > 0) {
				try {

					// String json = this.toJSON(itensFile);
					// JsonArray myCustomArray = new Gson().toJsonTree(itensFile).getAsJsonArray();
					// JSONObject output = new JSONObject(json);
					JSONArray docs = new JSONArray(itensFile);
					File file = new File("/home/findinfo/base_tempo/foot.cvs");
					String csv = CDL.toString(docs);
					FileUtils.writeStringToFile(file, csv);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				
				SynchronizeDataMdbTask.ImportDB("mongoimport", "\\home\\findinfo\\base_tempo\\foot.cvs","footBack");

			}

			// SynchronizeDataMdbTask.importJSONFileToDBUsingJavaDriver("\\home\\findinfo\\base_tempo\\foot.txt",
			// database,"footBack");

			log.info("SynchronizeData => End Foot Total " + cont, dateFormat.format(new Date()));

		}

		if (saneantNotification) {

			registerCNPJs = registerCNPJRepository.findAll(2);

			int cont = 0;
			
			mongoClient = new MongoClient("localhost");

			MongoDatabase database = mongoClient.getDatabase("findinfo01");

			MongoCollection<Document> coll = database.getCollection("saneantNotificationBack");

			ArrayList<Document> itensFile = new ArrayList<Document>();

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Saneante Notification " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<Document> itens = intSynchronize[1].loadDataDocument(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null && itens.size() > 0) {
					
					itensFile.addAll(itens);

					/*LoggerProcessing loggerProcessing = new LoggerProcessing();

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
					}*/

				}

			}
				
			mongoClient.close();
			
			if (itensFile.size() > 0) {
				try {

					// String json = this.toJSON(itensFile);
					// JsonArray myCustomArray = new Gson().toJsonTree(itensFile).getAsJsonArray();
					// JSONObject output = new JSONObject(json);
					JSONArray docs = new JSONArray(itensFile);
					File file = new File("/home/findinfo/base_tempo/saneanteNotification.cvs");
					String csv = CDL.toString(docs);
					FileUtils.writeStringToFile(file, csv);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				
				SynchronizeDataMdbTask.ImportDB("mongoimport", "\\home\\findinfo\\base_tempo\\saneanteNotification.cvs","saneantNotificationBack");

			}

			log.info("SynchronizeData => End Saneante Notification Total " + cont, dateFormat.format(new Date()));

		}

		if (saneantProduct) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;
			
			mongoClient = new MongoClient("localhost");

			MongoDatabase database = mongoClient.getDatabase("findinfo01");

			MongoCollection<Document> coll = database.getCollection("saneantProductBack");

			ArrayList<Document> itensFile = new ArrayList<Document>();


			for (RegisterCNPJ registerCNPJ : registerCNPJs) {

				log.info("SynchronizeData => Start Saneante Product " + registerCNPJ.getCnpj() + " "
						+ registerCNPJ.getFullName(), dateFormat.format(new Date()));

				ArrayList<Document> itens = intSynchronize[2].loadDataDocument(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null) {
					
					itensFile.addAll(itens);

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
			
			mongoClient.close();
			
			if (itensFile.size() > 0) {
				try {

					// String json = this.toJSON(itensFile);
					// JsonArray myCustomArray = new Gson().toJsonTree(itensFile).getAsJsonArray();
					// JSONObject output = new JSONObject(json);
					JSONArray docs = new JSONArray(itensFile);
					File file = new File("/home/findinfo/base_tempo/saneanteProduct.cvs");
					String csv = CDL.toString(docs);
					FileUtils.writeStringToFile(file, csv);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				
				SynchronizeDataMdbTask.ImportDB("mongoimport", "\\home\\findinfo\\base_tempo\\saneanteProduct.cvs","saneantProductBack");

			}

			log.info("SynchronizeData => End Saneante Product Total " + cont, dateFormat.format(new Date()));
		}

		if (process) {

			registerCNPJs = registerCNPJRepository.findAll();

			int cont = 0;
			
			mongoClient = new MongoClient("localhost");

			MongoDatabase database = mongoClient.getDatabase("findinfo01");

			MongoCollection<Document> coll = database.getCollection("processBack");

			ArrayList<Document> itensFile = new ArrayList<Document>();

			for (RegisterCNPJ registerCNPJ : registerCNPJs) {


				log.info(
						"SynchronizeData => Start Process " + registerCNPJ.getCnpj() + " " + registerCNPJ.getFullName(),
						dateFormat.format(new Date()));

				ArrayList<Document> itens = intSynchronize[3].loadDataDocument(registerCNPJ.getCnpj());

				log.info("SynchronizeData => Total " + itens.size(), dateFormat.format(new Date()));

				cont = cont + itens.size();

				if (itens != null) {
					
					
					itensFile.addAll(itens);

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
			
			mongoClient.close();
			
			if (itensFile.size() > 0) {
				try {

					// String json = this.toJSON(itensFile);
					// JsonArray myCustomArray = new Gson().toJsonTree(itensFile).getAsJsonArray();
					// JSONObject output = new JSONObject(json);
					JSONArray docs = new JSONArray(itensFile);
					File file = new File("/home/findinfo/base_tempo/process.cvs");
					String csv = CDL.toString(docs);
					FileUtils.writeStringToFile(file, csv);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				
				SynchronizeDataMdbTask.ImportDB("mongoimport", "\\home\\findinfo\\base_tempo\\process.cvs","processBack");

			}

			log.info("SynchronizeData => End Process Total " + cont, dateFormat.format(new Date()));
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

	public static void importJSONFileToDBUsingJavaDriver(String pathToFile, MongoDatabase database,
			String collectionName) {
		// open file
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(pathToFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("file not exist, exiting");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		// read it line by line
		String strLine;
		MongoCollection<Document> coll = database.getCollection(collectionName);

		try {
			while ((strLine = br.readLine()) != null) {
				// convert line by line to BSON
				/*
				 * Gson gson = new Gson(); Document bson =
				 * gson.fromJson(strLine,Document.class);
				 * 
				 * coll.insertOne(bson); // insert BSONs to database try { coll.insertOne(bson);
				 * } catch (MongoException e) { // duplicate key e.printStackTrace(); }
				 * 
				 */

				Document doc = Document.parse(strLine);
				List<Document> list = new ArrayList<>();
				list.add(doc);
				coll.insertMany(list);

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
		}

	}

	String toJSON(ArrayList<Document> list) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (Document d : list) {
			sb.append(gson.toJson(d));
		}
		return sb.toString();
	}

	public static void ImportDB(String importPath, String filePath, String collection) {
		Runtime r = Runtime.getRuntime();
		Process p = null;
		String command = importPath + " --db findinfo01 --collection "+collection+" "
				+ " --fields all --type csv --file " + filePath;
		try {
			p = r.exec(command);
			System.out.println("Reading "+collection+" csv into Database");

		} catch (Exception e) {
			System.out.println("Error executing " + command + e.toString());
		}
	}

}