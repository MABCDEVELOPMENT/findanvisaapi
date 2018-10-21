package com.anvisa.interceptor.synchronizedata.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.interceptor.synchronizedata.SynchronizeDataTask;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.cosmetic.regularized.ContentCosmeticRegularized;
import com.anvisa.model.persistence.rest.cosmetic.regularized.ContentCosmeticRegularizedDetail;
import com.anvisa.model.persistence.rest.cosmetic.regularized.CosmeticRegularizedDatailPresentation;
import com.anvisa.model.persistence.rest.cosmetic.regularized.CosmeticRegularizedDetailCharacterizationVigente;
import com.anvisa.model.persistence.rest.cosmetic.regularized.CosmeticRegularizedDetailHoldingCompany;
import com.anvisa.model.persistence.rest.cosmetic.regularized.CosmeticRegularizedDetailLocalNational;
import com.anvisa.repository.generic.CosmeticRegularizedRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class SynchronizeCosmeticRegularized extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static CosmeticRegularizedRepository cosmeticRegularizedRepository;

	@Autowired
	public void setService(CosmeticRegularizedRepository cosmeticRegularizedRepository) {

		this.cosmeticRegularizedRepository = cosmeticRegularizedRepository;
	}

	public SynchronizeCosmeticRegularized() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados?count=1000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados/";
	
	}

	@Override
	public ContentCosmeticRegularized parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegularized contentCosmeticRegularized = new ContentCosmeticRegularized();
		
		contentCosmeticRegularized.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		contentCosmeticRegularized.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticRegularized.setSituacao(JsonToObject.getValue(jsonNode, "situacao"));

		contentCosmeticRegularized.setTipo(JsonToObject.getValue(jsonNode, "tipo"));

		contentCosmeticRegularized.setVencimento(JsonToObject.getValueDate(jsonNode, "data"));

		return contentCosmeticRegularized;
	}

	@Override
	public ContentCosmeticRegularizedDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail = new ContentCosmeticRegularizedDetail();

		CosmeticRegularizedDetailHoldingCompany cosmeticRegularizedDetailHoldingCompany = new CosmeticRegularizedDetailHoldingCompany();

		cosmeticRegularizedDetailHoldingCompany.setCnpj(JsonToObject.getValue(jsonNode, "empresaDetentora", "cnpj"));
		cosmeticRegularizedDetailHoldingCompany.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresaDetentora", "razaoSocial"));
		cosmeticRegularizedDetailHoldingCompany.setAutorizacao(JsonToObject.getValue(jsonNode, "empresaDetentora", "autorizacao"));

		cosmeticRegularizedDetailHoldingCompany.setUf(JsonToObject.getValue(jsonNode, "empresaDetentora", "uf"));
		cosmeticRegularizedDetailHoldingCompany.setCidade(JsonToObject.getValue(jsonNode, "empresaDetentora", "cidade"));
		cosmeticRegularizedDetailHoldingCompany
				.setCodigoMunicipio(JsonToObject.getValue(jsonNode, "empresaDetentora", "codigoMunicipio"));
		contentCosmeticRegularizedDetail.setEmpresaDetentora(cosmeticRegularizedDetailHoldingCompany);

		CosmeticRegularizedDetailCharacterizationVigente cosmeticRegularizedDetailCharacterizationVigente = new CosmeticRegularizedDetailCharacterizationVigente();
		cosmeticRegularizedDetailCharacterizationVigente
				.setProcesso(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "processo"));
		cosmeticRegularizedDetailCharacterizationVigente.setGrupo(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "grupo"));
		cosmeticRegularizedDetailCharacterizationVigente
				.setProduto(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "produto"));
		cosmeticRegularizedDetailCharacterizationVigente
				.setFormaFisica(JsonToObject.getValue(jsonNode, "caracterizacaoVigente", "formaFisica"));

		contentCosmeticRegularizedDetail.setCaracterizacaoVigente(cosmeticRegularizedDetailCharacterizationVigente);

		contentCosmeticRegularizedDetail
				.setDestinacoes(JsonToObject.getArrayValue(jsonNode, "destinacoes"));

		
		CosmeticRegularizedDetailLocalNational cosmeticRegularizedDetailLocalNational = lodalLocalNacional(jsonNode, "locaisNacionais");
		contentCosmeticRegularizedDetail.setLocaisNacionais(cosmeticRegularizedDetailLocalNational);
		if (cosmeticRegularizedDetailLocalNational!=null && cosmeticRegularizedDetailLocalNational.getCnpj()!=null) {
		   contentCosmeticRegularizedDetail.setCnpj(cosmeticRegularizedDetailLocalNational.getCnpj());
		}
		ArrayNode element = (ArrayNode)jsonNode.findValue("apresentacoes");
		
		ArrayList<CosmeticRegularizedDatailPresentation> apresentacoes = new ArrayList<CosmeticRegularizedDatailPresentation>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					CosmeticRegularizedDatailPresentation cosmeticRegularizedDatailPresentation = new CosmeticRegularizedDatailPresentation();
					
					cosmeticRegularizedDatailPresentation.setPeriodoValidade(JsonToObject.getValue(nodeIt,"periodoValidade")+" "+JsonToObject.getValue(nodeIt,"tipoValidade"));
					cosmeticRegularizedDatailPresentation.setRestricaoUso(JsonToObject.getValue(nodeIt,"restricaoUso"));
					cosmeticRegularizedDatailPresentation.setCuidadoConservacao(JsonToObject.getValue(nodeIt,"cuidadoConservacao"));
					cosmeticRegularizedDatailPresentation.setEmbalagemPrimaria(JsonToObject.getValue(nodeIt,"embalagemPrimaria"));
					cosmeticRegularizedDatailPresentation.setEmbalagemSecundaria(JsonToObject.getValue(nodeIt,"embalagemSecundaria"));

					

					apresentacoes.add(cosmeticRegularizedDatailPresentation);
					
				}
			
			
			
		}

		contentCosmeticRegularizedDetail.setApresentacoes(apresentacoes);

		return contentCosmeticRegularizedDetail;
	}

	public CosmeticRegularizedDetailLocalNational lodalLocalNacional(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		CosmeticRegularizedDetailLocalNational localNacional = null;
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					localNacional = new CosmeticRegularizedDetailLocalNational();
					localNacional.setCnpj(JsonToObject.getValue(nodeIt,"cnpj"));
					localNacional.setRazaoSocial(JsonToObject.getValue(nodeIt,"razaoSocial"));
					localNacional.setUf( JsonToObject.getValue(nodeIt,"uf"));
					localNacional.setCidade( JsonToObject.getValue(nodeIt,"cidade"));
					localNacional.setCodigoMunicipio( JsonToObject.getValue(nodeIt,"codigoMunicipio"));
					localNacional.setAutorizacao(JsonToObject.getValue(nodeIt,"autorizacao"));
					
					break;
					
				}
			
			
			
		}
		
		return localNacional;
		
	}
	
	@Override
	public ArrayList<BaseEntity> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	@Override
	public BaseEntity loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}



	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void persist(ArrayList<BaseEntity> itens) {

		int cont = 0;
		
		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticRegularized baseEntity = (ContentCosmeticRegularized) iterator.next();

			ContentCosmeticRegularized localContentCosmeticRegularized = cosmeticRegularizedRepository.findByProcess(baseEntity.getProcesso());

			boolean newRegularized = (localContentCosmeticRegularized == null);
			
			ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail = (ContentCosmeticRegularizedDetail) this.loadDetailData(baseEntity.getProcesso());
			
			if (contentCosmeticRegularizedDetail != null && (contentCosmeticRegularizedDetail.getData()!=null || baseEntity.getVencimento()!=null)) {
				
				String strAno = baseEntity.getProcesso().substring(baseEntity.getProcesso().length() - 2);

				int ano = Integer.parseInt(strAno);

				if (ano >= 19 && ano <= 99) {
					ano = ano + 1900;
				} else {
					ano = ano + 2000;
				}
				
				LocalDate data = baseEntity.getVencimento()==null?contentCosmeticRegularizedDetail.getData():baseEntity.getVencimento();
				
				LocalDate dataAlteracao = LocalDate.of(ano, data.getMonthValue(),
						data.getDayOfMonth());

				baseEntity.setDataAlteracao(dataAlteracao);

				baseEntity.setDataRegistro(contentCosmeticRegularizedDetail.getData());
				
			}


			if (!newRegularized) {
				
				if (localContentCosmeticRegularized.getContentCosmeticRegularizedDetail()!=null) {
					if (!contentCosmeticRegularizedDetail.equals(localContentCosmeticRegularized.getContentCosmeticRegularizedDetail())){
						contentCosmeticRegularizedDetail.setId(localContentCosmeticRegularized.getContentCosmeticRegularizedDetail().getId());
						baseEntity.setContentCosmeticRegularizedDetail(contentCosmeticRegularizedDetail);
					}
				}

				if (!localContentCosmeticRegularized.equals(baseEntity)) {

					baseEntity.setId(localContentCosmeticRegularized.getId());
					cosmeticRegularizedRepository.saveAndFlush(baseEntity);
				}

			} else {
				baseEntity.setContentCosmeticRegularizedDetail(contentCosmeticRegularizedDetail);
				cosmeticRegularizedRepository.saveAndFlush(baseEntity);
				
			}
			System.out.println(cont++);
		}
		
		
	}

}

