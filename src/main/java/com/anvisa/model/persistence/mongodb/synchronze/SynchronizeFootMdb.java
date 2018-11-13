package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.foot.ContentFootDetailMdb;
import com.anvisa.model.persistence.mongodb.foot.ContentFootMdb;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.FootRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.anvisa.model.persistence.rest.Content;
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
public class SynchronizeFootMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static FootRepositoryMdb footRepository;
	
	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;
	


	@Autowired
	public void setService(FootRepositoryMdb footRepository, SequenceDaoImpl sequence, LoggerRepositoryMdb loggerRepositoryMdb) {

		this.footRepository = footRepository;
		this.sequence = sequence;
		this.loggerRepositoryMdb = loggerRepositoryMdb;

	}

	public SynchronizeFootMdb() {

		SEQ_KEY = "foot";

		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=10000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6/";

	}

	@Override
	public ContentFootMdb parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		Content content = new Content();

		content.setOrdem(JsonToObject.getOrdem(jsonNode));

		content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

		content.setProcesso(JsonToObject.getProcessoProduto(jsonNode));

		content.setProduto(JsonToObject.getProduto(jsonNode));

		ContentFootMdb contentProduto = new ContentFootMdb(content);

		contentProduto.setDataRegistro(JsonToObject.getValueDate(jsonNode, "dataRegistro"));
		contentProduto.setDataVencimento(JsonToObject.getValueDate(jsonNode, "dataVencimentoRegistro"));
		try {

			ContentFootDetailMdb contentFootDetailMdb = this.loadDetailData(contentProduto.getProcesso());
			
			contentProduto.setContentFootDetail(contentFootDetailMdb);

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return contentProduto;
		
	}

	public ContentFootDetailMdb parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentFootDetailMdb contentFootDetail = new ContentFootDetailMdb();

		contentFootDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		contentFootDetail.setClassesTerapeuticas(JsonToObject.getArrayValue(jsonNode, "classesTerapeuticas"));
		contentFootDetail.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
		contentFootDetail.setMarca(JsonToObject.getArrayValue(jsonNode, "marcas"));
		contentFootDetail.setNomeComercial(JsonToObject.getValue(jsonNode, "nomeComercial"));
		contentFootDetail.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
		contentFootDetail.setRegistro(JsonToObject.getValue(jsonNode, "numeroRegistro"));
		contentFootDetail.setMesAnoVencimento(JsonToObject.getValue(jsonNode, "mesAnoVencimento"));
		contentFootDetail.setPrincipioAtivo(JsonToObject.getValue(jsonNode, "principioAtivo"));
		contentFootDetail.setEmbalagemPrimaria(JsonToObject.getValue(jsonNode, "embalagemPrimaria", "tipo"));
		contentFootDetail.setViasAdministrativa(JsonToObject.getArrayValue(jsonNode, "viasAdministracao"));
		String ifaUnico = JsonToObject.getValue(jsonNode, "ifaUnico");
		contentFootDetail.setIfaUnico(ifaUnico.equals("true") ? "Sim" : "Não");
		contentFootDetail.setConservacao(JsonToObject.getArrayValue(jsonNode, "conservacao"));

		return contentFootDetail;
	}

	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	public ContentFootDetailMdb loadDetailData(String concat) {

		// TODO Auto-generated method stub
		ContentFootDetailMdb rootObject = null;
		OkHttpClient client = new OkHttpClient();

		Request url = null;

		url = new Request.Builder().url(URL_DETAIL + concat).get().addHeader("authorization", "Guest")
				.addHeader("Accept-Encoding", "gzip").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));

			if (rootNode != null) {

				rootObject = this.parseDetailData(rootNode);
			}
			response.close();
			client = null;
			return rootObject;

		} catch (Exception e) {

			log.error(e.getMessage());
			
		}

		return null;
	}

	@Override
	public void persist(ArrayList<BaseEntityMongoDB> itens, LoggerProcessing loggerProcessing) {
		
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("localhost");	
		
		//MongoCredential credential = MongoCredential.createPlainCredential("findinfo01", "findinfo01", "idkfa0101".toCharArray());
		
		MongoDatabase database = mongoClient.getDatabase("findinfo01");
		
		MongoCollection<Document> coll = database.getCollection("foot");
		
		Gson gson = new Gson();
		
		
		ArrayList<Document>  listSave = new ArrayList<Document>();
		
		
		int totalInserido   = 0;
		int totalAtualizado = 0;
		int totalErro       = 0;
		
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			ContentFootMdb baseEntity = (ContentFootMdb) iterator.next();
			try {
				
				log.info("Synchronize "+this.getClass().getName()+" "+baseEntity.getProcesso() + " - " + baseEntity.getCnpj());
				
				ContentFootMdb localFoot = footRepository.findByProcesso(baseEntity.getProcesso(), baseEntity.getCnpj(),
						baseEntity.getCodigo(), baseEntity.getRegistro(), baseEntity.getDataVencimento());

				boolean newFoot = (localFoot == null);

				ContentFootDetailMdb detail = (ContentFootDetailMdb) this.loadDetailData(baseEntity.getProcesso());

				if (detail != null) {

					if (!newFoot) {

						if (localFoot.getContentFootDetail() != null
								&& !detail.equals(localFoot.getContentFootDetail())) {
							detail.setId(localFoot.getContentFootDetail().getId());
							baseEntity.setContentFootDetail(detail);
						} else {
							detail.setId(localFoot.getContentFootDetail().getId());
						}
					} else {

						baseEntity.setContentFootDetail(detail);
					}

				}

				if (localFoot != null) {

					if (!localFoot.equals(baseEntity)) {

						//baseEntity.setId(localFoot.getId());
						detail.setId(localFoot.getContentFootDetail().getId());
						baseEntity.setContentFootDetail(detail);
						baseEntity.setUpdateDate(LocalDate.now());
						Document document = Document.parse(gson.toJson(baseEntity));
						coll.updateOne(new Document("_id", new ObjectId(localFoot.getId().toString().getBytes())), document);
						//listSave.add(document);
						totalAtualizado++;
					}

				} else {
					
					//baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setInsertDate(LocalDate.now());
					Document document = Document.parse(gson.toJson(baseEntity));
					//listSave.add(document);
					coll.insertOne(document);
					totalInserido++;

				}

			} catch (Exception e) {
				
				// TODO: handle exception
				log.error(this.getClass().getName() +" Processo "+baseEntity.getProcesso()+" cnpj "+ baseEntity.getCnpj()+" Codigo "
						+baseEntity.getCodigo()+" Registro "+baseEntity.getRegistro()+" Vencimento "+baseEntity.getDataVencimento());
				log.error(e.getMessage());
				
				totalErro++;
				
			}
			
			/*try {
				
//				if (cont % 10 == 0) {
//				    //footRepository.saveAll(listSave);
//					coll.insertMany(listSave);
//				}
				
			} catch (Exception e) {
				
				// TODO: handle exception
				log.error(this.getClass().getName() +" Processo "+baseEntity.getProcesso()+" cnpj "+ baseEntity.getCnpj()+" Codigo "
						+baseEntity.getCodigo()+" Registro "+baseEntity.getRegistro()+" Vencimento "+baseEntity.getDataVencimento());
				log.error(e.getMessage());
				
				totalErro++;
				
			}	*/
			
		}
		

		loggerProcessing.setTotalInserido(new Long(totalInserido));
		loggerProcessing.setTotalAtualizado(new Long(totalAtualizado));
		loggerProcessing.setTotalErro(new Long(totalErro));
		loggerRepositoryMdb.save(loggerProcessing);
		mongoClient.close();
		

	}

	@Override
	public ArrayList<Document> loadDataDocument(String cnpj) {
		// TODO Auto-generated method stub
		return super.loadDataDocument(this,cnpj);
	}

}
