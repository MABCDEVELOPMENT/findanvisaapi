package com.anvisa.interceptor.synchronizedata.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotificationDetail;
import com.anvisa.model.persistence.rest.cosmetic.notification.CosmeticNotificationPresentation;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.CosmeticRegisterPresentation;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.CosmeticRegisterPresentationDetail;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.model.persistence.rest.foot.ContentFootDetail;
import com.anvisa.repository.generic.CosmeticNotificationRepository;
import com.anvisa.repository.generic.FootDetailRepository;
import com.anvisa.repository.generic.FootRepository;
import com.anvisa.rest.detalhe.comestico.notificado.ApresentacaoCosmeticoNotificado;
import com.anvisa.rest.model.ContentProdutoNotificado;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class SynchronizeCosmeticNotification extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static CosmeticNotificationRepository cosmeticNotificationRepository;

	@Autowired
	public void setService(CosmeticNotificationRepository cosmeticNotificationRepository) {

		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		

	}

	public SynchronizeCosmeticNotification() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados?count=4000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados/";

	}

	@Override
	public BaseEntity parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticNotification contentCosmeticNotification = new ContentCosmeticNotification();

		String assunto = JsonToObject.getAssunto(jsonNode);

		contentCosmeticNotification.setAssunto(assunto);

		contentCosmeticNotification.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticNotification.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		contentCosmeticNotification
				.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expedienteProcesso"));

		contentCosmeticNotification.setExpedientePeticao(JsonToObject.getValue(jsonNode, "expedientePeticao"));

		contentCosmeticNotification.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		contentCosmeticNotification.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		contentCosmeticNotification.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		contentCosmeticNotification.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		contentCosmeticNotification.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento"));

		return  contentCosmeticNotification;
		

	}

	@Override
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
		contentCosmeticNotificationDetail
				.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));
		contentCosmeticNotificationDetail
				.setDataNotificacao(JsonToObject.getValueDate(jsonNode, "situacao", "data"));
		
		contentCosmeticNotificationDetail.setApresentacoes(this.parseApresentationData(jsonNode, "apresentacoes"));

		
		return contentCosmeticNotificationDetail;
	}
	
	
	
	public List<CosmeticNotificationPresentation> parseApresentationData(JsonNode jsonNode, String attribute) {

		ArrayNode element = (ArrayNode)jsonNode.findValue(attribute);
		
		List<CosmeticNotificationPresentation> apresentacoes = new ArrayList<CosmeticNotificationPresentation>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					CosmeticNotificationPresentation cosmeticNotificationPresentation = new CosmeticNotificationPresentation();
					
					cosmeticNotificationPresentation.setApresentacao(JsonToObject.getValue(nodeIt,"apresentacao"));
					cosmeticNotificationPresentation.setTonalidade(JsonToObject.getValue(nodeIt,"tonalidade"));
					cosmeticNotificationPresentation.setEans("");
					apresentacoes.add(cosmeticNotificationPresentation);
					
				}
			
			
			
		} 
		
		if(apresentacoes.isEmpty()) {
			return null;
		}
		
		return apresentacoes;

	}
	
	

	@Override
	public ArrayList<BaseEntity> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	@Override
	public BaseEntity loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}

	@Override
	public void persist(ArrayList<BaseEntity> itens) {
		int cont = 0;
		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticNotification baseEntity = (ContentCosmeticNotification) iterator.next();

			ContentCosmeticNotification localContentCosmeticNotification = cosmeticNotificationRepository.findByProcessCnpj(baseEntity.getProcesso(),
					baseEntity.getCnpj(),baseEntity.getExpedienteProcesso());

			boolean newNotification = (localContentCosmeticNotification == null);
			
			ContentCosmeticNotificationDetail contentCosmeticNotificationDetail = (ContentCosmeticNotificationDetail) this.loadDetailData(baseEntity.getProcesso());

			if (!newNotification) {
				
				if (localContentCosmeticNotification.getContentCosmeticNotificationDetail()!=null) {
					if (!contentCosmeticNotificationDetail.equals(localContentCosmeticNotification.getContentCosmeticNotificationDetail())){
						contentCosmeticNotificationDetail.setId(localContentCosmeticNotification.getContentCosmeticNotificationDetail().getId());
						baseEntity.setContentCosmeticNotificationDetail(contentCosmeticNotificationDetail);
					}
				}

				if (!localContentCosmeticNotification.equals(baseEntity)) {

					baseEntity.setId(localContentCosmeticNotification.getId());
					cosmeticNotificationRepository.saveAndFlush(baseEntity);
				}

			} else {
				baseEntity.setContentCosmeticNotificationDetail(contentCosmeticNotificationDetail);
				cosmeticNotificationRepository.saveAndFlush(baseEntity);
				
			}
			System.out.println(cont++);	
		}

	}

}
