package com.anvisa.interceptor.synchronizedata.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotificationDetail;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotificationPresentation;
import com.anvisa.repository.generic.SaneanteNotificationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class SynchronizeSaneanteNotification extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static SaneanteNotificationRepository saneanteNotificationRepository;

	@Autowired
	public void setService(SaneanteNotificationRepository saneanteNotificationRepository) {

		this.saneanteNotificationRepository = saneanteNotificationRepository;
		

	}

	public SynchronizeSaneanteNotification() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados?count=1000&page=1&filter[cnpj]=";
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados/";

	}

	@Override
	public BaseEntity parseData(JsonNode jsonNode) {
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

	@Override
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
		
		saneanteNotificationDetail.setApresentacoes(this.parseApresentationData(jsonNode, "apresentacoes"));

		
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

			SaneanteNotification baseEntity = (SaneanteNotification) iterator.next();

			SaneanteNotification localSaneanteNotification = saneanteNotificationRepository.findByProcessCnpj(baseEntity.getProcesso(),
					baseEntity.getCnpj(),baseEntity.getExpedienteProcesso());

			boolean newNotification = (localSaneanteNotification == null);
			
			SaneanteNotificationDetail saneanteNotificationDetail = (SaneanteNotificationDetail) this.loadDetailData(baseEntity.getProcesso());
			
			if (saneanteNotificationDetail != null) {
			
				String strAno = baseEntity.getProcesso().substring(baseEntity.getProcesso().length() - 2);

				int ano = Integer.parseInt(strAno);

				if (ano >= 19 && ano <= 99) {
					ano = ano + 1900;
				} else {
					ano = ano + 2000;
				}
				
				LocalDate data = baseEntity.getVencimento()==null?saneanteNotificationDetail.getDataNotificacao():baseEntity.getVencimento();
				
				LocalDate dataAlteracao = LocalDate.of(ano, data.getMonthValue(),
						data.getDayOfMonth());

				baseEntity.setDataAlteracao(dataAlteracao);

				baseEntity.setDataRegistro(saneanteNotificationDetail.getDataNotificacao());
				
			}
			
			
			
			if (!newNotification) {
				
				if (localSaneanteNotification.getSaneanteNotificationDetail()!=null) {
					if (!saneanteNotificationDetail.equals(localSaneanteNotification.getSaneanteNotificationDetail())){
						saneanteNotificationDetail.setId(localSaneanteNotification.getSaneanteNotificationDetail().getId());
						baseEntity.setSaneanteNotificationDetail(saneanteNotificationDetail);
					}
				}

				if (!localSaneanteNotification.equals(baseEntity)) {

					baseEntity.setId(localSaneanteNotification.getId());
					saneanteNotificationRepository.saveAndFlush(baseEntity);
				}

			} else {
				baseEntity.setSaneanteNotificationDetail(saneanteNotificationDetail);
				saneanteNotificationRepository.saveAndFlush(baseEntity);
				
			}
			
			System.out.println(cont++);	
		}

	}

}
