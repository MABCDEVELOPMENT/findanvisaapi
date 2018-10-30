package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteNotificationRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificadoPetition;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationDetail;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationLabel;
import com.anvisa.model.persistence.mongodb.saneante.notification.SaneanteNotificationPresentation;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProductDetail;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	public void setService(SaneanteNotificationRepositoryMdb saneanteNotificationRepository,
						   SequenceDaoImpl sequence) {

		this.saneanteNotificationRepository = saneanteNotificationRepository;
		this.sequence = sequence;

	}

	public SynchronizeSaneanteNotificationMdb() {

		SEQ_KEY = "saneante_notification"; 
		
		URL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados?count=10000&page=1&filter[cnpj]=";
		
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados/";

	}

	@Override
	public BaseEntityMongoDB parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteNotification saneanteNotification = new SaneanteNotification();

		String assunto = JsonToObject.getAssunto(jsonNode);

		saneanteNotification.setAssunto(assunto);

		saneanteNotification.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		saneanteNotification.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		saneanteNotification
				.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expedienteProcesso"));

		saneanteNotification.setExpedientePeticao(JsonToObject.getValue(jsonNode, "expedientePeticao"));

		saneanteNotification.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		saneanteNotification.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		saneanteNotification.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		saneanteNotification.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		saneanteNotification.setVencimento(JsonToObject.getValueDate(jsonNode,"vencimento", "vencimento"));
		
		
		
		return  saneanteNotification;
		

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
		saneanteNotificationDetail
				.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));
		saneanteNotificationDetail
				.setDataNotificacao(JsonToObject.getValueDate(jsonNode, "situacao", "data"));
		
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

		ArrayNode element = (ArrayNode)jsonNode.findValue(attribute);
		
		ArrayList<SaneanteNotificationPresentation> apresentacoes = new ArrayList<SaneanteNotificationPresentation>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					SaneanteNotificationPresentation cosmeticNotificationPresentation = new SaneanteNotificationPresentation();
					
					cosmeticNotificationPresentation.setApresentacao(JsonToObject.getValue(nodeIt,"apresentacao"));
					cosmeticNotificationPresentation.setTonalidade(JsonToObject.getValue(nodeIt,"tonalidade"));
					cosmeticNotificationPresentation.setEans(JsonToObject.getArraySaneanteNotificationEanValue(nodeIt, "eans"));
					
					
					
					apresentacoes.add(cosmeticNotificationPresentation);
					
					
					
				}
			
			
			
		} 
		

		return apresentacoes;

	}
	
	public ArrayList<SaneanteNotificadoPetition> parsePetitionnData(JsonNode jsonNode, String attribute) {

		ArrayNode element = (ArrayNode)jsonNode.findValue(attribute);
		
		ArrayList<SaneanteNotificadoPetition> peticoes = new ArrayList<SaneanteNotificadoPetition>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					SaneanteNotificadoPetition saneanteNotificadoPetition = new SaneanteNotificadoPetition();
					
					saneanteNotificadoPetition.setExpediente(JsonToObject.getValue(nodeIt, "expediente"));
					saneanteNotificadoPetition.setPublicacao(JsonToObject.getValueDate(nodeIt, "data"));
					saneanteNotificadoPetition.setTransacao(JsonToObject.getValue(nodeIt, "transacao"));
					saneanteNotificadoPetition.setAssunto(JsonToObject.getAssunto(nodeIt));
					saneanteNotificadoPetition.setSituacao(JsonToObject.getValue(nodeIt,"situacao" ,"situacao"));
					
					peticoes.add(saneanteNotificadoPetition);
					
					
				}
			
			
			
		} 
		

		return peticoes;

	}
	@Override
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	
	public SaneanteNotificationDetail loadDetailData(String concat) {
		
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

			SaneanteNotification baseEntity = (SaneanteNotification) iterator.next();

			SaneanteNotification localSaneanteNotification = saneanteNotificationRepository.findByProcesso(baseEntity.getProcesso(),
					baseEntity.getCnpj(),baseEntity.getExpedienteProcesso());

			boolean newNotification = (localSaneanteNotification == null);
			
			if (newNotification == false) continue;
			
			SaneanteNotificationDetail saneanteNotificationDetail = (SaneanteNotificationDetail) this.loadDetailData(baseEntity.getProcesso());
			
			if (!newNotification) {
				
				if (localSaneanteNotification.getSaneanteNotificationDetail()!=null) {
					if (!saneanteNotificationDetail.equals(localSaneanteNotification.getSaneanteNotificationDetail())){
						baseEntity.setSaneanteNotificationDetail(saneanteNotificationDetail);
					}
				}

				if (!localSaneanteNotification.equals(baseEntity)) {

					baseEntity.setId(localSaneanteNotification.getId());
					saneanteNotificationRepository.save(baseEntity);
				}

			} else {
				baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
				baseEntity.setSaneanteNotificationDetail(saneanteNotificationDetail);
				saneanteNotificationRepository.save(baseEntity);
				
			}
			
			System.out.println(cont++);	
		}

	}

}
