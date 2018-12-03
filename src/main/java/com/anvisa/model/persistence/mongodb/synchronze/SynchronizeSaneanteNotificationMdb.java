package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LogErroProcessig;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificadoPetition;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationDetail;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationLabel;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationPresentation;
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
public class SynchronizeSaneanteNotificationMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static SaneanteNotificationRepositoryMdb saneanteNotificationRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public void setService(SaneanteNotificationRepositoryMdb saneanteNotificationRepository, SequenceDaoImpl sequence,
			LoggerRepositoryMdb loggerRepositoryMdb, MongoTemplate mongoTemplate) {

		this.saneanteNotificationRepository = saneanteNotificationRepository;
		this.sequence = sequence;
		this.loggerRepositoryMdb = loggerRepositoryMdb;
		this.mongoTemplate = mongoTemplate;

	}

	public SynchronizeSaneanteNotificationMdb() {

		SEQ_KEY = "saneante_notification";

		URL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados?count=10000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados/";

	}

	@Override
	public BaseEntityMongoDB parseData(String cnpj, JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteNotification saneanteNotification = new SaneanteNotification();

		String assunto = JsonToObject.getAssunto(jsonNode);

		saneanteNotification.setAssunto(assunto);

		saneanteNotification.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		saneanteNotification.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		saneanteNotification.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expedienteProcesso"));

		saneanteNotification.setExpedientePeticao(JsonToObject.getValue(jsonNode, "expedientePeticao"));

		saneanteNotification.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		saneanteNotification.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		saneanteNotification.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		saneanteNotification.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		saneanteNotification.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento", "vencimento"));

		SaneanteNotificationDetail saneanteNotificationDetail = this.loadDetailData(cnpj,
				saneanteNotification.getProcesso());

		if (saneanteNotificationDetail != null) {
			saneanteNotification.setSaneanteNotificationDetail(saneanteNotificationDetail);
		}

		return saneanteNotification;

	}

	public SaneanteNotificationDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub

		SaneanteNotificationDetail saneanteNotificationDetail = new SaneanteNotificationDetail();

		String assunto = JsonToObject.getValue(jsonNode, "assunto", "codigo") + " - "
				+ JsonToObject.getValue(jsonNode, "assunto", "descricao");
		saneanteNotificationDetail.setAssunto(assunto);

		String empresa = JsonToObject.getValue(jsonNode, "empresa", "cnpj") + " - "
				+ JsonToObject.getValue(jsonNode, "empresa", "razaoSocial");
		saneanteNotificationDetail.setEmpresa(empresa);

		saneanteNotificationDetail.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		saneanteNotificationDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo"));
		saneanteNotificationDetail.setArea(JsonToObject.getValue(jsonNode, "area"));
		saneanteNotificationDetail.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));
		saneanteNotificationDetail.setDataNotificacao(JsonToObject.getValueDate(jsonNode, "situacao", "data"));

		ArrayList<String> strRotulos = JsonToObject.getArrayStringValue(jsonNode, "rotulos");

		ArrayList<SaneanteNotificationLabel> labels = new ArrayList<SaneanteNotificationLabel>();

		for (String strRotulo : strRotulos) {
			SaneanteNotificationLabel saneanteNotificationLabel = new SaneanteNotificationLabel();
			saneanteNotificationLabel.setValor(strRotulo);
			labels.add(saneanteNotificationLabel);
		}

		saneanteNotificationDetail.setRotulos(labels);

		saneanteNotificationDetail.setApresentacoes(this.parseApresentationData(jsonNode, "apresentacoes"));

		saneanteNotificationDetail.setPeticoes(this.parsePetitionnData(jsonNode, "peticoes"));

		return saneanteNotificationDetail;
	}

	public ArrayList<SaneanteNotificationPresentation> parseApresentationData(JsonNode jsonNode, String attribute) {

		ArrayNode element = (ArrayNode) jsonNode.findValue(attribute);

		ArrayList<SaneanteNotificationPresentation> apresentacoes = new ArrayList<SaneanteNotificationPresentation>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				SaneanteNotificationPresentation cosmeticNotificationPresentation = new SaneanteNotificationPresentation();

				cosmeticNotificationPresentation.setApresentacao(JsonToObject.getValue(nodeIt, "apresentacao"));
				cosmeticNotificationPresentation.setTonalidade(JsonToObject.getValue(nodeIt, "tonalidade"));
				cosmeticNotificationPresentation
						.setEans(JsonToObject.getArraySaneanteNotificationEanValue(nodeIt, "eans"));

				apresentacoes.add(cosmeticNotificationPresentation);

			}

		}

		return apresentacoes;

	}

	public ArrayList<SaneanteNotificadoPetition> parsePetitionnData(JsonNode jsonNode, String attribute) {

		ArrayNode element = (ArrayNode) jsonNode.findValue(attribute);

		ArrayList<SaneanteNotificadoPetition> peticoes = new ArrayList<SaneanteNotificadoPetition>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				SaneanteNotificadoPetition saneanteNotificadoPetition = new SaneanteNotificadoPetition();

				saneanteNotificadoPetition.setExpediente(JsonToObject.getValue(nodeIt, "expediente"));
				saneanteNotificadoPetition.setPublicacao(JsonToObject.getValueDate(nodeIt, "data"));
				saneanteNotificadoPetition.setTransacao(JsonToObject.getValue(nodeIt, "transacao"));
				saneanteNotificadoPetition.setAssunto(JsonToObject.getAssunto(nodeIt));
				saneanteNotificadoPetition.setSituacao(JsonToObject.getValue(nodeIt, "situacao", "situacao"));

				peticoes.add(saneanteNotificadoPetition);

			}

		}

		return peticoes;

	}

	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	public SaneanteNotificationDetail loadDetailData(String cnpj, String concat) {

		SaneanteNotificationDetail rootObject = null;

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
				System.gc();
				return null;
			}

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(concat, response.body().byteStream()));

			if (rootNode != null) {

				rootObject = this.parseDetailData(rootNode);
			}
			response.close();
			client = null;
			System.gc();
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			LogErroProcessig log = new LogErroProcessig(cnpj, concat, e.getMessage(),
					SaneanteNotification.class.getName(), this.getClass().getName(), e,
					LocalDateTime.now());
			mongoTemplate.save(log);
			System.gc();
		}

		return null;
	}

	@Override
	public void persist(String cnpj, ArrayList<BaseEntityMongoDB> itens, LoggerProcessing loggerProcessing) {

		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("localhost");

		// MongoCredential credential =
		// MongoCredential.createPlainCredential("findinfo01", "findinfo01",
		// "idkfa0101".toCharArray());

		MongoDatabase database = mongoClient.getDatabase("findinfo01");

		MongoCollection<Document> coll = database.getCollection("saneanteNotification");

		Gson gson = new Gson();

		ArrayList<Document> listSave = new ArrayList<Document>();

		int size = itens.size();
		int cont = 1;

		int totalInserido = 0;
		int totalAtualizado = 0;
		int totalErro = 0;

		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			SaneanteNotification baseEntity = (SaneanteNotification) iterator.next();
			try {

				log.info("Synchronize " + this.getClass().getName() + " " + baseEntity.getProcesso() + " - "
						+ baseEntity.getCnpj());

				SaneanteNotification localSaneanteNotification = saneanteNotificationRepository.findByProcesso(
						baseEntity.getProcesso(), baseEntity.getCnpj(), baseEntity.getExpedienteProcesso());

				boolean newNotification = (localSaneanteNotification == null);

				/*
				 * if (newNotification == false) continue;
				 */

				SaneanteNotificationDetail saneanteNotificationDetail = (SaneanteNotificationDetail) this
						.loadDetailData(cnpj, baseEntity.getProcesso());

				if (!newNotification) {

					if (localSaneanteNotification.getSaneanteNotificationDetail() != null) {
						if (!saneanteNotificationDetail
								.equals(localSaneanteNotification.getSaneanteNotificationDetail())) {
							baseEntity.setSaneanteNotificationDetail(saneanteNotificationDetail);
						}
					}

					if (!localSaneanteNotification.equals(baseEntity)) {

						baseEntity.setId(localSaneanteNotification.getId());
						baseEntity.setUpdateDate(LocalDate.now());
						// listSave.add(baseEntity);
						Document document = Document.parse(gson.toJson(baseEntity));
						coll.updateOne(
								new Document("_id",
										new ObjectId(localSaneanteNotification.getId().toString().getBytes())),
								document);
						totalAtualizado++;
					}

				} else {
					// baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setSaneanteNotificationDetail(saneanteNotificationDetail);
					baseEntity.setInsertDate(LocalDate.now());
					Document document = Document.parse(gson.toJson(baseEntity));
					// listSave.add(document);
					coll.insertOne(document);
					totalInserido++;
				}

			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso() + " cnpj "
						+ baseEntity.getCnpj());
				log.error(e.getMessage());
				LogErroProcessig log = new LogErroProcessig(cnpj, baseEntity.getProcesso(), e.getMessage(),
						SaneanteNotification.class.getName(), this.getClass().getName(), e,
						LocalDateTime.now());
				mongoTemplate.save(log);
				totalErro++;
			}

			/*
			 * try { if (cont % 200 == 0 || cont == size) {
			 * //saneanteNotificationRepository.saveAll(listSave);
			 * coll.insertMany(listSave); listSave = new ArrayList<Document>(); }
			 * 
			 * } catch (Exception e) { // TODO: handle exception
			 * log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso()
			 * + " cnpj " + baseEntity.getCnpj()); log.error(e.getMessage()); totalErro++; }
			 */
			cont++;
		}

		loggerProcessing.setTotalInserido(new Long(totalInserido));
		loggerProcessing.setTotalAtualizado(new Long(totalAtualizado));
		loggerProcessing.setTotalErro(new Long(totalErro));
		loggerRepositoryMdb.save(loggerProcessing);
		mongoClient.close();
	}

	@Override
	public ArrayList<Document> loadDataDocument(String processo) {
		// TODO Auto-generated method stub
		return super.loadDataDocument(this, processo);
	}

}
