package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotificationDetail;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.CosmeticNotificationPresentation;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.CosmeticNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class SynchronizeCosmeticNotificationMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static CosmeticNotificationRepositoryMdb cosmeticNotificationRepository;
	
	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public void setService(CosmeticNotificationRepositoryMdb cosmeticNotificationRepository, SequenceDaoImpl sequence, 	@Autowired
			LoggerRepositoryMdb loggerRepositoryMdb) {

		this.cosmeticNotificationRepository = cosmeticNotificationRepository;

		this.sequence = sequence;
		
		this.loggerRepositoryMdb = loggerRepositoryMdb;
		

	}

	public SynchronizeCosmeticNotificationMdb() {

		SEQ_KEY = "cosmetic_notification";

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados?count=1000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados/";

	}

	@Override
	public BaseEntityMongoDB parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticNotification contentCosmeticNotification = new ContentCosmeticNotification();

		String assunto = JsonToObject.getAssunto(jsonNode);

		contentCosmeticNotification.setAssunto(assunto);

		contentCosmeticNotification.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticNotification.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		contentCosmeticNotification.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expedienteProcesso"));

		contentCosmeticNotification.setExpedientePeticao(JsonToObject.getValue(jsonNode, "expedientePeticao"));

		contentCosmeticNotification.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		contentCosmeticNotification.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		contentCosmeticNotification.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		contentCosmeticNotification.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		contentCosmeticNotification.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento", "vencimento"));
		
		ContentCosmeticNotificationDetail contentCosmeticNotificationDetail = this.loadDetailData(contentCosmeticNotification.getProcesso());
				
		if (contentCosmeticNotificationDetail!=null) {
			contentCosmeticNotification.setContentCosmeticNotificationDetail(contentCosmeticNotificationDetail);
		}

		return contentCosmeticNotification;

	}

	public ContentCosmeticNotificationDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub

		ContentCosmeticNotificationDetail contentCosmeticNotificationDetail = new ContentCosmeticNotificationDetail();

		String assunto = JsonToObject.getValue(jsonNode, "assunto", "codigo") + " - "
				+ JsonToObject.getValue(jsonNode, "assunto", "descricao");
		contentCosmeticNotificationDetail.setAssunto(assunto);

		String empresa = JsonToObject.getValue(jsonNode, "empresa", "cnpj") + " - "
				+ JsonToObject.getValue(jsonNode, "empresa", "razaoSocial");
		contentCosmeticNotificationDetail.setEmpresa(empresa);

		contentCosmeticNotificationDetail.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		contentCosmeticNotificationDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo"));
		contentCosmeticNotificationDetail.setArea(JsonToObject.getValue(jsonNode, "area"));
		contentCosmeticNotificationDetail.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));
		contentCosmeticNotificationDetail.setDataNotificacao(JsonToObject.getValueDate(jsonNode, "situacao", "data"));

		contentCosmeticNotificationDetail.setApresentacoes(this.parseApresentationData(jsonNode, "apresentacoes"));

		return contentCosmeticNotificationDetail;
	}

	public List<CosmeticNotificationPresentation> parseApresentationData(JsonNode jsonNode, String attribute) {

		ArrayNode element = (ArrayNode) jsonNode.findValue(attribute);

		List<CosmeticNotificationPresentation> apresentacoes = new ArrayList<CosmeticNotificationPresentation>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				CosmeticNotificationPresentation cosmeticNotificationPresentation = new CosmeticNotificationPresentation();

				cosmeticNotificationPresentation.setApresentacao(JsonToObject.getValue(nodeIt, "apresentacao"));
				cosmeticNotificationPresentation.setTonalidade(JsonToObject.getValue(nodeIt, "tonalidade"));
				cosmeticNotificationPresentation.setEans("");
				apresentacoes.add(cosmeticNotificationPresentation);

			}

		}

		if (apresentacoes.isEmpty()) {
			return null;
		}

		return apresentacoes;

	}

	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	public ContentCosmeticNotificationDetail loadDetailData(String concat) {

		ContentCosmeticNotificationDetail rootObject = null;

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
			if (response.body()!=null) {
				
				JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));
	
				if (rootNode != null) {
	
					rootObject = this.parseDetailData(rootNode);
				}
			}	
			response.close();
			client = null;
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
		}

		return null;
	}

	@Override
	public void persist(ArrayList<BaseEntityMongoDB> itens, LoggerProcessing loggerProcessing ) {
		
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("localhost");	
		
		//MongoCredential credential = MongoCredential.createPlainCredential("findinfo01", "findinfo01", "idkfa0101".toCharArray());
		
		MongoDatabase database = mongoClient.getDatabase("findinfo01");
		
		MongoCollection<Document> coll = database.getCollection("cosmeticNotification");
		
		Gson gson = new Gson();
		
		
		ArrayList<Document>  listSave = new ArrayList<Document>();
		int size = itens.size();
		int cont = 0;
		int totalInserido   = 0;
		int totalAtualizado = 0;
		int totalErro       = 0;
		
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticNotification baseEntity = (ContentCosmeticNotification) iterator.next();

			try {

				log.info("Synchronize "+this.getClass().getName()+" "+baseEntity.getProcesso() + " - " + baseEntity.getCnpj());
				
				ContentCosmeticNotification localContentCosmeticNotification = cosmeticNotificationRepository
						.findByProcesso(baseEntity.getProcesso(), baseEntity.getCnpj(),
								baseEntity.getExpedienteProcesso());

				boolean newNotification = (localContentCosmeticNotification == null);

				/*if (newNotification == false)
					continue;*/

				ContentCosmeticNotificationDetail contentCosmeticNotificationDetail = (ContentCosmeticNotificationDetail) this
						.loadDetailData(baseEntity.getProcesso());

				if (!newNotification) {

					if (localContentCosmeticNotification.getContentCosmeticNotificationDetail() != null) {
						if (!contentCosmeticNotificationDetail
								.equals(localContentCosmeticNotification.getContentCosmeticNotificationDetail())) {
							// contentCosmeticNotificationDetail.setId(localContentCosmeticNotification.getContentCosmeticNotificationDetail().getId());
							baseEntity.setContentCosmeticNotificationDetail(contentCosmeticNotificationDetail);
						}
					}

					if (!localContentCosmeticNotification.equals(baseEntity)) {

						// baseEntity.setId(localContentCosmeticNotification.getId());
						baseEntity.setUpdateDate(LocalDate.now());
						Document document = Document.parse(gson.toJson(baseEntity));
						coll.updateOne(new Document("_id", new ObjectId(localContentCosmeticNotification.getId().toString().getBytes())), document);
						totalAtualizado++;
						
					}

				} else {
					
					//baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setContentCosmeticNotificationDetail(contentCosmeticNotificationDetail);
					baseEntity.setInsertDate(LocalDate.now());
					Document document = Document.parse(gson.toJson(baseEntity));
					//listSave.add(document);
					coll.insertOne(document);
					totalInserido++;

				}

			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Cnpj " + baseEntity.getCnpj() + " Processo "
						+ baseEntity.getProcesso() + " ExpedienteProcesso " + baseEntity.getExpedienteProcesso());
				log.error(e.getMessage());
				totalErro++;
			}
			
/*			try {
				if (cont % 100 == 0 || cont == size) {
					coll.insertMany(listSave);
					listSave = new ArrayList<Document>();
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso());
				log.error(e.getMessage());				
				totalErro++;
			}*/
			cont++;
		}
		
		loggerProcessing.setTotalInserido(new Long(totalInserido));
		loggerProcessing.setTotalAtualizado(new Long(totalAtualizado));
		loggerProcessing.setTotalErro(new Long(totalErro));
		loggerRepositoryMdb.save(loggerProcessing);
	}

	@Override
	public ArrayList<Document> loadDataDocument(String processo) {
		// TODO Auto-generated method stub
		return super.loadDataDocument(this, processo);
	}

}
