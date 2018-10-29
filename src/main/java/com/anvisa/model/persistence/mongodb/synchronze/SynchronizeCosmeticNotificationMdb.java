package com.anvisa.model.persistence.mongodb.synchronze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.ContentCosmeticNotificationDetail;
import com.anvisa.model.persistence.mongodb.cosmetic.notification.CosmeticNotificationPresentation;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.repository.CosmeticNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	public void setService(CosmeticNotificationRepositoryMdb cosmeticNotificationRepository, SequenceDaoImpl sequence) {

		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		
		this.sequence = sequence; 

	}

	public SynchronizeCosmeticNotificationMdb() {

		SEQ_KEY = "cosmetic_notification";

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados?count=4000&page=1&filter[cnpj]=";

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
	public void persist(ArrayList<BaseEntityMongoDB> itens) {
		int cont = 0;
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticNotification baseEntity = (ContentCosmeticNotification) iterator.next();

			ContentCosmeticNotification localContentCosmeticNotification = cosmeticNotificationRepository
					.findByProcesso(baseEntity.getProcesso(), baseEntity.getCnpj(), baseEntity.getExpedienteProcesso());

			boolean newNotification = (localContentCosmeticNotification == null);
			
			if(newNotification == false) continue;

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

					//baseEntity.setId(localContentCosmeticNotification.getId());
					cosmeticNotificationRepository.save(baseEntity);
				}

			} else {
				baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
				baseEntity.setContentCosmeticNotificationDetail(contentCosmeticNotificationDetail);
				cosmeticNotificationRepository.save(baseEntity);

			}

			System.out.println(cont++);
		}

	}

}
