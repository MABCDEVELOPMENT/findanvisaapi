package com.anvisa.interceptor.synchronizedata.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.interceptor.synchronizedata.SynchronizeDataTask;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.model.persistence.rest.process.ProcessDetail;
import com.anvisa.model.persistence.rest.process.ProcessPetition;
import com.anvisa.repository.generic.ProcessRepository;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SynchronizeProcess extends SynchronizeData implements IntSynchronize {
	
	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	private static ProcessRepository processRepository;

	@Autowired
	public void setService(ProcessRepository processRepository) {

		this.processRepository = processRepository;
		

	}

	public SynchronizeProcess() {

		URL = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=10000&page=1&filter[cnpj]=";
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/documento/tecnico/";

	}

	@Override
	public BaseEntity parseData(JsonNode jsonNode) {
		
		Process process = new Process();
		
		process.setOrdem(0);
		

		Peticao peticao = JsonToObject.getPeticao(jsonNode);
		process.setAssunto(peticao.getAssunto().toString());

		process.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		process.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		Processo processo = JsonToObject.getProcesso(jsonNode);
		process.setProcesso(processo.getNumero());

		return  process;

	}

	@Override
	public ProcessDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		
		ProcessDetail processDetail = new ProcessDetail();

		processDetail.build(jsonNode);
		
		return processDetail;
		
	}
	
	@Override
	public ArrayList<BaseEntity> loadData(String concat) {
		return super.loadData(this, concat);
	}

	@Override
	public BaseEntity loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}

	@Override
	public void persist(ArrayList<BaseEntity> itens) {
		int cont = 0;

		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			Process baseEntity = (Process) iterator.next();

			Process localProcess = processRepository.findByProcessCnpj(baseEntity.getProcesso(),
					baseEntity.getCnpj());

			boolean newNotification = (localProcess == null);
			
			ProcessDetail processDetail = (ProcessDetail) this.loadDetailData(baseEntity.getProcesso());
			
			
			
			if (processDetail != null) {

				ArrayList<ProcessPetition> peticoes = (ArrayList<ProcessPetition>) processDetail
						.getPeticoes();

				for (ProcessPetition processPetition : peticoes) {
					if(processPetition.getDataEntrada()!=null) {
						baseEntity.setDataAlteracao(processPetition.getDataPublicacao());
					}	
				}

				baseEntity.setQtdRegistro(peticoes.size());
				
				if (processDetail!=null) {
					
					baseEntity.setDataRegistro(processDetail.getProcesso().getPeticao().getDataEntrada());

				}
				
				if (baseEntity.getDataAlteracao() == null) {
					
					String strAno = baseEntity.getProcesso().substring(baseEntity.getProcesso().length() - 2);

					int ano = Integer.parseInt(strAno);

					if (ano >= 19 && ano <= 99) {
						ano = ano + 1900;
					} else {
						ano = ano + 2000;
					}
					
					if (processDetail!=null) {
					
						LocalDate data = processDetail.getProcesso().getPeticao().getDataEntrada();

						LocalDate dataAlteracao = LocalDate.of(ano, data.getMonthValue(), data.getDayOfMonth());

						baseEntity.setDataAlteracao(dataAlteracao);
					}

						
				}
				
				
			}
			
			
			
			if (!newNotification) {
				
				if (localProcess.getProcessDetail()!=null) {
					if (!processDetail.equals(localProcess.getProcessDetail())){
						processDetail.setId(localProcess.getProcessDetail().getId());
						baseEntity.setProcessDetail(processDetail);
					}
				}

				if (!localProcess.equals(baseEntity)) {

					baseEntity.setId(localProcess.getId());
					try {
						processRepository.save(baseEntity);	
						log.info("SynchronizeData => Update Process cnpj "+baseEntity.getCnpj()+"  process "+baseEntity.getProcesso(), dateFormat.format(new Date()));
					} catch (Exception e) {
						// TODO: handle exception
						log.info("SynchronizeData => Update Process cnpj "+baseEntity.getCnpj()+"  process "+baseEntity.getProcesso(), dateFormat.format(new Date()));
						log.error(e.getMessage());
					}
					
				}

			} else {
				baseEntity.setProcessDetail(processDetail);
				try {
					processRepository.save(baseEntity);	
					log.info("SynchronizeData => Insert Process cnpj "+baseEntity.getCnpj()+"  process "+baseEntity.getProcesso(), dateFormat.format(new Date()));
				} catch (Exception e) {
					log.info("SynchronizeData => Insert Process cnpj "+baseEntity.getCnpj()+"  process "+baseEntity.getProcesso(), dateFormat.format(new Date()));
					log.error(e.getMessage());// TODO: handle exception
					
				}
				
			}
			
			//System.out.println(cont++);	
		}

	}

}
