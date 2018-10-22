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
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotificadoPetition;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotification;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotificationDetail;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotificationLabel;
import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotificationPresentation;
import com.anvisa.persistence.rest.ContentProcesso;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.repository.generic.SaneanteNotificationRepository;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.model.persistence.rest.process.ProcessDetail;
import com.anvisa.model.persistence.rest.process.ProcessPetition;

@Component
public class SynchronizeProcess extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static ProcessRepository processRepository;

	@Autowired
	public void setService(ProcessRepository processRepository) {

		this.processRepository = processRepository;
		

	}

	public SynchronizeProcess() {

		URL = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=1000&page=1&filter[cnpj]=";
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/documento/tecnico/";


	}

	@Override
	public BaseEntity parseData(JsonNode jsonNode) {
		
		Process process = new Process();


		
		
		
		return  process;

	}

	@Override
	public ProcessDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		
		ProcessDetail processDetail = new ProcessDetail();
		
		return processDetail;
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
		/*for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			SaneanteNotification baseEntity = (SaneanteNotification) iterator.next();

			SaneanteNotification localSaneanteNotification = saneanteNotificationRepository.findByProcessCnpj(baseEntity.getProcesso(),
					baseEntity.getCnpj(),baseEntity.getExpedienteProcesso());

			boolean newNotification = (localSaneanteNotification == null);
			
			SaneanteNotificationDetail saneanteNotificationDetail = (SaneanteNotificationDetail) this.loadDetailData(baseEntity.getProcesso());
			
			
			
			if (saneanteNotificationDetail != null) {

				ArrayList<SaneanteNotificadoPetition> peticoes = (ArrayList<SaneanteNotificadoPetition>) saneanteNotificationDetail
						.getPeticoes();

				for (SaneanteNotificadoPetition saneanteNotificadoPetition : peticoes) {
					baseEntity.setDataAlteracao(saneanteNotificadoPetition.getPublicacao());
				}

				baseEntity.setQtdRegistro(peticoes.size());

				if (baseEntity.getDataAlteracao() == null) {
					String strAno = baseEntity.getProcesso().substring(baseEntity.getProcesso().length() - 2);

					int ano = Integer.parseInt(strAno);

					if (ano >= 19 && ano <= 99) {
						ano = ano + 1900;
					} else {
						ano = ano + 2000;
					}

					LocalDate data = baseEntity.getVencimento() == null
							? saneanteNotificationDetail.getDataNotificacao()
							: baseEntity.getVencimento();

					LocalDate dataAlteracao = LocalDate.of(ano, data.getMonthValue(), data.getDayOfMonth());

					baseEntity.setDataAlteracao(dataAlteracao);
				}
				
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
		}*/

	}

}
