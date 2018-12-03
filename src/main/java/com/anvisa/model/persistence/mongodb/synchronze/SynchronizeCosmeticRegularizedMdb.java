package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.ContentCosmeticRegularizedDetail;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.CosmeticRegularizedDatailPresentation;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.CosmeticRegularizedDetailCharacterizationVigente;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.CosmeticRegularizedDetailHoldingCompany;
import com.anvisa.model.persistence.mongodb.cosmetic.regularized.CosmeticRegularizedDetailLocalNational;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LogErroProcessig;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegularizedRepositoryMdb;
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
public class SynchronizeCosmeticRegularizedMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static CosmeticRegularizedRepositoryMdb cosmeticRegularizedRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public void setService(CosmeticRegularizedRepositoryMdb cosmeticRegularizedRepository, SequenceDaoImpl sequence,
			LoggerRepositoryMdb loggerRepositoryMdb, MongoTemplate mongoTemplate) {

		this.cosmeticRegularizedRepository = cosmeticRegularizedRepository;

		this.sequence = sequence;

		this.loggerRepositoryMdb = loggerRepositoryMdb;

		this.mongoTemplate = mongoTemplate;
	}

	public SynchronizeCosmeticRegularizedMdb() {

		SEQ_KEY = "cometic_regularized";

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados?count=1000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados/";

	}

	@Override
	public ContentCosmeticRegularized parseData(String cnpj, JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegularized contentCosmeticRegularized = new ContentCosmeticRegularized();

		contentCosmeticRegularized.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		contentCosmeticRegularized.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticRegularized.setSituacao(JsonToObject.getValue(jsonNode, "situacao"));

		contentCosmeticRegularized.setTipo(JsonToObject.getValue(jsonNode, "tipo"));

		contentCosmeticRegularized.setVencimento(JsonToObject.getValueDate(jsonNode, "data"));

		ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail = this.loadDetailData(cnpj,
				contentCosmeticRegularized.getProcesso());

		if (contentCosmeticRegularizedDetail != null) {

			contentCosmeticRegularized.setContentCosmeticRegularizedDetail(contentCosmeticRegularizedDetail);

		}

		return contentCosmeticRegularized;
	}

	public ContentCosmeticRegularizedDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail = new ContentCosmeticRegularizedDetail();

		CosmeticRegularizedDetailHoldingCompany cosmeticRegularizedDetailHoldingCompany = new CosmeticRegularizedDetailHoldingCompany();

		cosmeticRegularizedDetailHoldingCompany.setCnpj(JsonToObject.getValue(jsonNode, "empresaDetentora", "cnpj"));
		cosmeticRegularizedDetailHoldingCompany
				.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresaDetentora", "razaoSocial"));
		cosmeticRegularizedDetailHoldingCompany
				.setAutorizacao(JsonToObject.getValue(jsonNode, "empresaDetentora", "autorizacao"));

		cosmeticRegularizedDetailHoldingCompany.setUf(JsonToObject.getValue(jsonNode, "empresaDetentora", "uf"));
		cosmeticRegularizedDetailHoldingCompany
				.setCidade(JsonToObject.getValue(jsonNode, "empresaDetentora", "cidade"));
		cosmeticRegularizedDetailHoldingCompany
				.setCodigoMunicipio(JsonToObject.getValue(jsonNode, "empresaDetentora", "codigoMunicipio"));
		contentCosmeticRegularizedDetail.setEmpresaDetentora(cosmeticRegularizedDetailHoldingCompany);

		CosmeticRegularizedDetailCharacterizationVigente cosmeticRegularizedDetailCharacterizationVigente = new CosmeticRegularizedDetailCharacterizationVigente();
		cosmeticRegularizedDetailCharacterizationVigente
				.setProcesso(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "processo"));
		cosmeticRegularizedDetailCharacterizationVigente
				.setGrupo(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "grupo"));
		cosmeticRegularizedDetailCharacterizationVigente
				.setProduto(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "produto"));
		cosmeticRegularizedDetailCharacterizationVigente
				.setFormaFisica(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "formaFisica"));

		contentCosmeticRegularizedDetail.setCaracterizacaoVigente(cosmeticRegularizedDetailCharacterizationVigente);

		contentCosmeticRegularizedDetail.setDestinacoes(JsonToObject.getArrayValue(jsonNode, "destinacoes"));

		CosmeticRegularizedDetailLocalNational cosmeticRegularizedDetailLocalNational = lodalLocalNacional(jsonNode,
				"locaisNacionais");
		contentCosmeticRegularizedDetail.setLocaisNacionais(cosmeticRegularizedDetailLocalNational);
		if (cosmeticRegularizedDetailLocalNational != null
				&& cosmeticRegularizedDetailLocalNational.getCnpj() != null) {
			contentCosmeticRegularizedDetail.setCnpj(cosmeticRegularizedDetailLocalNational.getCnpj());
		}
		ArrayNode element = (ArrayNode) jsonNode.findValue("apresentacoes");

		ArrayList<CosmeticRegularizedDatailPresentation> apresentacoes = new ArrayList<CosmeticRegularizedDatailPresentation>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				CosmeticRegularizedDatailPresentation cosmeticRegularizedDatailPresentation = new CosmeticRegularizedDatailPresentation();

				cosmeticRegularizedDatailPresentation
						.setPeriodoValidade(JsonToObject.getValue(nodeIt, "periodoValidade") + " "
								+ JsonToObject.getValue(nodeIt, "tipoValidade"));
				cosmeticRegularizedDatailPresentation.setRestricaoUso(JsonToObject.getValue(nodeIt, "restricaoUso"));
				cosmeticRegularizedDatailPresentation
						.setCuidadoConservacao(JsonToObject.getValue(nodeIt, "cuidadoConservacao"));
				cosmeticRegularizedDatailPresentation
						.setEmbalagemPrimaria(JsonToObject.getValue(nodeIt, "embalagemPrimaria"));
				cosmeticRegularizedDatailPresentation
						.setEmbalagemSecundaria(JsonToObject.getValue(nodeIt, "embalagemSecundaria"));

				apresentacoes.add(cosmeticRegularizedDatailPresentation);

			}

		}

		contentCosmeticRegularizedDetail.setApresentacoes(apresentacoes);

		return contentCosmeticRegularizedDetail;
	}

	public CosmeticRegularizedDetailLocalNational lodalLocalNacional(JsonNode node, String attribute) {

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		CosmeticRegularizedDetailLocalNational localNacional = null;

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				localNacional = new CosmeticRegularizedDetailLocalNational();
				localNacional.setCnpj(JsonToObject.getValue(nodeIt, "cnpj"));
				localNacional.setRazaoSocial(JsonToObject.getValue(nodeIt, "razaoSocial"));
				localNacional.setUf(JsonToObject.getValue(nodeIt, "uf"));
				localNacional.setCidade(JsonToObject.getValue(nodeIt, "cidade"));
				localNacional.setCodigoMunicipio(JsonToObject.getValue(nodeIt, "codigoMunicipio"));
				localNacional.setAutorizacao(JsonToObject.getValue(nodeIt, "autorizacao"));

				break;

			}

		}

		return localNacional;

	}

	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	public ContentCosmeticRegularizedDetail loadDetailData(String cnpj, String concat) {

		ContentCosmeticRegularizedDetail rootObject = null;

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

			if (response.body() != null) {

				JsonNode rootNode = objectMapper.readTree(this.getGZIPString(cnpj, response.body().byteStream()));

				if (rootNode != null) {

					rootObject = this.parseDetailData(rootNode);
				}
			}

			response.close();
			client = null;
			System.gc();
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			LogErroProcessig log = new LogErroProcessig(cnpj, concat, e.getMessage(),
					ContentCosmeticRegularized.class.getName(), this.getClass().getName(), e,
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

		MongoCollection<Document> coll = database.getCollection("cosmeticRegularized");

		Gson gson = new Gson();

		ArrayList<Document> listSave = new ArrayList<Document>();

		int cont = 0;
		int size = itens.size();
		int totalInserido = 0;
		int totalAtualizado = 0;
		int totalErro = 0;

		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticRegularized baseEntity = (ContentCosmeticRegularized) iterator.next();
			try {

				log.info("Synchronize " + this.getClass().getName() + " " + baseEntity.getProcesso());

				ContentCosmeticRegularized localContentCosmeticRegularized = cosmeticRegularizedRepository
						.findByProcesso(baseEntity.getProcesso());

				boolean newRegularized = (localContentCosmeticRegularized == null);

				ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail = (ContentCosmeticRegularizedDetail) this
						.loadDetailData(cnpj, baseEntity.getProcesso());

				if (!newRegularized) {

					if (localContentCosmeticRegularized.getContentCosmeticRegularizedDetail() != null) {
						if (!contentCosmeticRegularizedDetail
								.equals(localContentCosmeticRegularized.getContentCosmeticRegularizedDetail())) {
							baseEntity.setContentCosmeticRegularizedDetail(contentCosmeticRegularizedDetail);
						}
					}

					if (!localContentCosmeticRegularized.equals(baseEntity)) {

						baseEntity.setId(localContentCosmeticRegularized.getId());
						// baseEntity.setUpdateDate(LocalDate.now());
						Document document = Document.parse(gson.toJson(baseEntity));
						coll.updateOne(new Document("_id", localContentCosmeticRegularized.getId()), document);
						totalAtualizado++;
					}

				} else {
					// baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setContentCosmeticRegularizedDetail(contentCosmeticRegularizedDetail);
					// baseEntity.setInsertDate(LocalDate.now());
					Document document = Document.parse(gson.toJson(baseEntity));
					// listSave.add(document);
					coll.insertOne(document);
					totalInserido++;

				}

			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso());
				log.error(e.getMessage());
				totalErro++;
			}

			/*
			 * try { if (cont % 100 == 0 || cont == size) { coll.insertMany(listSave);
			 * listSave = new ArrayList<Document>(); } } catch (Exception e) { // TODO:
			 * handle exception log.error(this.getClass().getName() + " Processo " +
			 * baseEntity.getProcesso()); log.error(e.getMessage()); totalErro++; }
			 */
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
