package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LogErroProcessig;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteProductRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProduct;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProductDetail;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProductLabel;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteStringListGeneric;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
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
public class SynchronizeSaneanteProductMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static SaneanteProductRepositoryMdb seneanteProductRepository;

	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public void setService(SaneanteProductRepositoryMdb seneanteProductRepository, SequenceDaoImpl sequence,
			LoggerRepositoryMdb loggerRepositoryMdb, MongoTemplate mongoTemplate) {

		this.seneanteProductRepository = seneanteProductRepository;

		this.sequence = sequence;

		this.loggerRepositoryMdb = loggerRepositoryMdb;

		this.mongoTemplate = mongoTemplate;

	}

	public SynchronizeSaneanteProductMdb() {

		SEQ_KEY = "saneante_product";

		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3?count=10000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3/";

	}

	public SaneanteProduct parseData(String cnpj, JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteProduct saneanteProduct = new SaneanteProduct();

		String codigo = JsonToObject.getValue(jsonNode, "produto", "codigo");
		if (codigo != null && !"".equals(codigo)) {
			saneanteProduct.setCodigo(Integer.parseInt(codigo));
		}
		saneanteProduct.setProduto(JsonToObject.getValue(jsonNode, "produto", "nome"));
		saneanteProduct.setRegistro(JsonToObject.getValue(jsonNode, "produto", "numeroRegistro"));
		saneanteProduct.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		saneanteProduct.setEmpresa(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));
		saneanteProduct.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));
		saneanteProduct.setSituacao(JsonToObject.getValue(jsonNode, "processo", "situacao"));
		saneanteProduct.setVencimento(JsonToObject.getValue(jsonNode, "produto", "dataVencimento"));
		saneanteProduct.setDataVencimento(JsonToObject.getValueDate(jsonNode, "produto", "dataVencimentoRegistro"));

		saneanteProduct.setDataRegistro(JsonToObject.getValueDate(jsonNode, "produto", "dataRegistro"));

		/*
		 * if (saneanteProduct.getDataVencimento() != null ||
		 * saneanteProduct.getDataRegistro() != null) {
		 * 
		 * String strAno =
		 * saneanteProduct.getProcesso().substring(saneanteProduct.getProcesso().length(
		 * ) - 2);
		 * 
		 * int ano = Integer.parseInt(strAno);
		 * 
		 * if (ano >= 19 && ano <= 99) { ano = ano + 1900; } else { ano = ano + 2000; }
		 * 
		 * LocalDate data =
		 * saneanteProduct.getDataVencimento()==null?saneanteProduct.getDataRegistro():
		 * saneanteProduct.getDataVencimento(); if (data!=null) { LocalDate
		 * dataAlteracao = LocalDate.of(ano, data.getMonthValue(),
		 * data.getDayOfMonth()); saneanteProduct.setDataAlteracao(dataAlteracao); } }
		 * 
		 */

		SaneanteProductDetail saneanteProductDetail = this.loadDetailData(cnpj, saneanteProduct.getProcesso());

		if (saneanteProductDetail != null) {
			saneanteProduct.setSaneanteProductDetail(saneanteProductDetail);
		}

		return saneanteProduct;
	}

	public SaneanteProductDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteProductDetail saneanteProductDetail = new SaneanteProductDetail();

		saneanteProductDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		saneanteProductDetail.setClassesTerapeuticas(JsonToObject.getArrayValue(jsonNode, "classesTerapeuticas"));
		saneanteProductDetail.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
		saneanteProductDetail.setNumeroAutorizacao(JsonToObject.getValue(jsonNode, "numeroAutorizacao"));
		saneanteProductDetail.setNomeComercial(JsonToObject.getValue(jsonNode, "nomeComercial"));
		saneanteProductDetail.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
		saneanteProductDetail.setRegistro(JsonToObject.getValue(jsonNode, "numeroRegistro"));
		saneanteProductDetail.setMesAnoVencimento(JsonToObject.getValue(jsonNode, "mesAnoVencimento"));
		saneanteProductDetail.loadApresentaçoes(jsonNode, "apresentacoes");

		List<SaneanteStringListGeneric> stringListGenericrotulos = JsonToObject
				.getArraySaneanteStringListGeneric(jsonNode, "rotulos");

		ArrayList<SaneanteProductLabel> rotulos = new ArrayList<SaneanteProductLabel>();

		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericrotulos) {
			rotulos.add(new SaneanteProductLabel(saneanteStringListGeneric.getValor()));
		}

		saneanteProductDetail.setRotulos(rotulos);

		/*
		 * ArrayList<String> rotulos = contentDetalheSaneanteProduct.getRotulos();
		 * 
		 * for (String rotulo : rotulos) {
		 * downloadLabel(contentDetalheSaneanteProduct.getProcesso(), rotulo); }
		 */

		return saneanteProductDetail;
	}

	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	public SaneanteProductDetail loadDetailData(String cnpj, String concat) {

		SaneanteProductDetail rootObject = null;

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

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(cnpj, response.body().byteStream()));

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
			LogErroProcessig log = new LogErroProcessig(cnpj, concat, e.getMessage(), SaneanteProduct.class.getName(),
					this.getClass().getName(), e, LocalDateTime.now());
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

		MongoCollection<Document> coll = database.getCollection("saneanteProduct");

		Gson gson = new Gson();

		ArrayList<Document> listSave = new ArrayList<Document>();

		int size = itens.size();
		int cont = 0;

		int totalInserido = 0;
		int totalAtualizado = 0;
		int totalErro = 0;

		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			SaneanteProduct baseEntity = (SaneanteProduct) iterator.next();
			try {

				log.info("Synchronize " + this.getClass().getName() + " " + baseEntity.getProcesso() + " - "
						+ baseEntity.getCnpj());

				SaneanteProduct localSaneanteProduct = seneanteProductRepository.findByProcesso(
						baseEntity.getProcesso(), baseEntity.getCnpj(), baseEntity.getCodigo(),
						baseEntity.getRegistro(), baseEntity.getDataVencimento());

				boolean newRegularized = (localSaneanteProduct == null);

				SaneanteProductDetail saneanteProductDetail = (SaneanteProductDetail) this.loadDetailData(cnpj,
						baseEntity.getProcesso());

				if (!newRegularized) {

					if (localSaneanteProduct.getSaneanteProductDetail() != null) {
						if (!saneanteProductDetail.equals(localSaneanteProduct.getSaneanteProductDetail())) {
							// saneanteProductDetail.setId(localSaneanteProduct.getSaneanteProductDetail().getId());
							baseEntity.setSaneanteProductDetail(saneanteProductDetail);
						}
					}

					if (!localSaneanteProduct.equals(baseEntity)) {

						baseEntity.setId(localSaneanteProduct.getId());
						baseEntity.setUpdateDate(LocalDate.now());
						Document document = Document.parse(gson.toJson(baseEntity));
						coll.updateOne(new Document("_id", localSaneanteProduct.getId()), document);
						// listSave.add(document);
						totalAtualizado++;
					}

				} else {
					// baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setSaneanteProductDetail(saneanteProductDetail);
					baseEntity.setInsertDate(LocalDate.now());
					Document document = Document.parse(gson.toJson(baseEntity));
					// listSave.add(document);
					coll.insertOne(document);
					cont++;
					totalInserido++;

				}

			} catch (Exception e) {
				// TODO: handle exception

				log.error(this.getClass().getName() + " Processo " + baseEntity.getProcesso() + " cnpj "
						+ baseEntity.getCnpj() + " Codigo " + baseEntity.getCodigo() + " Registro "
						+ baseEntity.getRegistro() + " Vencimento " + baseEntity.getDataVencimento());
				log.error(e.getMessage());

				LogErroProcessig log = new LogErroProcessig(cnpj, baseEntity.getProcesso(), e.getMessage(),
						SaneanteProduct.class.getName(), this.getClass().getName(), e,
						LocalDateTime.now());
				mongoTemplate.save(log);

				totalErro++;

			}

			/*
			 * try { if (cont % 200 == 0 || cont == size) {
			 * //seneanteProductRepository.saveAll(listSave); coll.insertMany(listSave);
			 * listSave = new ArrayList<Document>(); } } catch (Exception e) { // TODO:
			 * handle exception log.error(this.getClass().getName() + " Processo " +
			 * baseEntity.getProcesso() + " cnpj " + baseEntity.getCnpj() + " Codigo " +
			 * baseEntity.getCodigo() + " Registro " + baseEntity.getRegistro() +
			 * " Vencimento " + baseEntity.getDataVencimento());
			 * 
			 * log.error(e.getMessage()); totalErro++; }
			 */ cont++;
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
