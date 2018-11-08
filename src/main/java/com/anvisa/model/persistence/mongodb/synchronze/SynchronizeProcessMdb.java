package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.process.ProcessDetail;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.ProcessRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProduct;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class SynchronizeProcessMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	
	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static ProcessRepositoryMdb processRepository;
	
	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public void setService(ProcessRepositoryMdb processRepository, SequenceDaoImpl sequence) {

		this.processRepository = processRepository;
		this.sequence = sequence;

	}

	public SynchronizeProcessMdb() {

		SEQ_KEY = "process";

		URL = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=10000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/documento/tecnico/";

	}

	@Override
	public BaseEntityMongoDB parseData(JsonNode jsonNode) {

		Process process = new Process();

		process.setOrdem(0);

		Peticao peticao = JsonToObject.getPeticao(jsonNode);
		process.setAssunto(peticao.getAssunto().toString());

		process.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		process.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		Processo processo = JsonToObject.getProcesso(jsonNode);
		process.setProcesso(processo.getNumero());

		ProcessDetail processDetail = this.loadDetailData(processo.getNumero());
		
		if (processDetail!=null) {
			process.setProcessDetail(processDetail);
		}
		
		return process;

	}

	public ProcessDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub

		ProcessDetail processDetail = new ProcessDetail();

		processDetail.build(jsonNode);

		return processDetail;

	}

	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String concat) {
		return super.loadData(this, concat);
	}

	public ProcessDetail loadDetailData(String concat) {

		ProcessDetail rootObject = null;

		OkHttpClient client = new OkHttpClient();

		Request url = null;

		url = new Request.Builder().url(URL_DETAIL + concat).get().addHeader("authorization", "Guest")
				.addHeader("Accept-Encoding", "gzip").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			if (response.code() == 500) {
				response.close();
				client = null;
				return null;
			}

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));

			if (rootNode != null) {

				rootObject = this.parseDetailData(rootNode);
			}
			response.close();
			client = null;
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void persist(ArrayList<BaseEntityMongoDB> itens, LoggerProcessing loggerProcessing) {
		
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("localhost");	
		
		//MongoCredential credential = MongoCredential.createPlainCredential("findinfo01", "findinfo01", "idkfa0101".toCharArray());
		
		MongoDatabase database = mongoClient.getDatabase("findinfo01");
		
		MongoCollection<Document> coll = database.getCollection("process");
		
		Gson gson = new Gson();
		
		
		ArrayList<Document>  listSave = new ArrayList<Document>();
		
		int size = itens.size();
		int cont = 0;

		int totalInserido   = 0;
		int totalAtualizado = 0;
		int totalErro       = 0;
		
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			Process baseEntity = (Process) iterator.next();
			try {
				
				log.info("Synchronize "+this.getClass().getName()+" "+baseEntity.getProcesso() + " - " + baseEntity.getCnpj());
				
				Process localProcess = processRepository.findByProcesso(baseEntity.getProcesso(), baseEntity.getCnpj());

				boolean newNotification = (localProcess == null);

/*				if (newNotification == false)
					continue;*/

				ProcessDetail processDetail = (ProcessDetail) this.loadDetailData(baseEntity.getProcesso());

				if (!newNotification) {

					if (localProcess.getProcessDetail() != null) {
						if (!processDetail.equals(localProcess.getProcessDetail())) {
							baseEntity.setProcessDetail(processDetail);
						}
					}

					if (!localProcess.equals(baseEntity)) {

						baseEntity.setId(localProcess.getId());
						baseEntity.setUpdateDate(LocalDateTime.now());
						try {
							Document document = Document.parse(gson.toJson(baseEntity));
							coll.updateOne(new Document("_id", localProcess.getId()), document);
							//listSave.add(document);
							totalAtualizado++;

							log.info("SynchronizeData => Update Process cnpj " + baseEntity.getCnpj() + "  process "
									+ baseEntity.getProcesso(), dateFormat.format(new Date()));
						} catch (Exception e) {
							// TODO: handle exception
							log.info("SynchronizeData => Update Process cnpj " + baseEntity.getCnpj() + "  process "
									+ baseEntity.getProcesso(), dateFormat.format(new Date()));
							log.error(e.getMessage());
						}

					}

				} else {
					baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setProcessDetail(processDetail);
					try {
						baseEntity.setInsertDate(LocalDateTime.now());
						Document document = Document.parse(gson.toJson(baseEntity));
						listSave.add(document);
						totalInserido++;
						log.info("SynchronizeData => Insert Process cnpj " + baseEntity.getCnpj() + "  process "
								+ baseEntity.getProcesso(), dateFormat.format(new Date()));
					} catch (Exception e) {
						log.info("SynchronizeData => Insert Process cnpj " + baseEntity.getCnpj() + "  process "
								+ baseEntity.getProcesso(), dateFormat.format(new Date()));
						log.error(e.getMessage());// TODO: handle exception

					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso() + " cnpj "
						+ baseEntity.getCnpj());
				log.error(e.getMessage());
				totalErro++;
			}
			
			try {
				if (cont % 500 == 0 || cont == size) {
					//seneanteProductRepository.saveAll(listSave);    
					coll.insertMany(listSave);
					listSave = new ArrayList<Document>();
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso() + " cnpj "
						+ baseEntity.getCnpj());
				
				log.error(e.getMessage());
				totalErro++;
			}	
	
			cont++;
		}

		if (loggerProcessing!=null) {
		
			loggerProcessing.setTotalInserido(new Long(totalInserido));
			loggerProcessing.setTotalAtualizado(new Long(totalAtualizado));
			loggerProcessing.setTotalErro(new Long(totalErro));
			loggerRepositoryMdb.save(loggerProcessing);
			
		}
		
		mongoClient.close();

	}

	public ArrayList<BaseEntityMongoDB> loadData(String cnpj, int qtd) {
		// TODO Auto-generated method stub
		ArrayList<BaseEntityMongoDB> rootObject = new ArrayList<BaseEntityMongoDB>();

		OkHttpClient client = new OkHttpClient();

		client.newBuilder().readTimeout(30, TimeUnit.MINUTES);

		Request url = null;

		url = new Request.Builder().url(URL + cnpj).get().addHeader("Accept-Encoding", "gzip")
				.addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();
			log.info("SynchronizeData Total Registros " + rootNode.get("totalElements"), dateFormat.format(new Date()));
			int i = 0;
			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				Process baseEntity = (Process) this.parseData(jsonNode);
				ProcessDetail drocessDetail = this.loadDetailData(baseEntity.getProcesso());

				rootObject.add(baseEntity);

				System.out.println(i++);
				if (qtd == 1)
					break;
			}
			response.close();
			client = null;
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<Document> loadDataDocument(String processo) {
		// TODO Auto-generated method stub
		return super.loadDataDocument(this, processo);
	}

}
