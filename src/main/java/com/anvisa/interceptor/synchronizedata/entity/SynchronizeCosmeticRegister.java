package com.anvisa.interceptor.synchronizedata.entity;

import java.text.SimpleDateFormat;
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
import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegisterDetail;
import com.anvisa.model.persistence.rest.cosmetic.register.CosmeticRegisterPetition;
import com.anvisa.model.persistence.rest.cosmetic.register.CosmeticRegisterPresentation;
import com.anvisa.repository.generic.CosmeticRegisterDetailRepository;
import com.anvisa.repository.generic.CosmeticRegisterRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class SynchronizeCosmeticRegister extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static CosmeticRegisterRepository cosmeticRegisterRepository;
	
	@Autowired
	private static CosmeticRegisterDetailRepository cosmeticRegisterDetailRepository;

	@Autowired
	public void setService(CosmeticRegisterRepository cosmeticRegisterRepository,
						   CosmeticRegisterDetailRepository cosmeticRegisterDetailRepository) {

		this.cosmeticRegisterRepository = cosmeticRegisterRepository;
		this.cosmeticRegisterDetailRepository = cosmeticRegisterDetailRepository;

	}

	public SynchronizeCosmeticRegister() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados?count=10&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/";

	}

	@Override
	public ContentCosmeticRegister parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegister contentCosmeticRegister = new ContentCosmeticRegister();

		String assunto = JsonToObject.getAssunto(jsonNode);

		contentCosmeticRegister.setAssunto(assunto);

		contentCosmeticRegister.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticRegister.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		contentCosmeticRegister.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expediente"));

		// contentProdutoRegistrado.setExpedientePeticao(JsonToObject.getValue(jsonNode,
		// "expedientePeticao"));

		contentCosmeticRegister.setProduto(JsonToObject.getValue(jsonNode, "nomeProduto"));

		contentCosmeticRegister.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		contentCosmeticRegister.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		contentCosmeticRegister.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		contentCosmeticRegister.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento", "vencimento"));

		return contentCosmeticRegister;
	}

	@Override
	public ContentCosmeticRegisterDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegisterDetail contentCosmeticRegisterDetail = new ContentCosmeticRegisterDetail();

		contentCosmeticRegisterDetail.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));
		contentCosmeticRegisterDetail.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));
		contentCosmeticRegisterDetail.setAutorizacao(JsonToObject.getValue(jsonNode, "empresa", "autorizacao"));
		contentCosmeticRegisterDetail.setNomeProduto(JsonToObject.getValue(jsonNode, "nomeProduto"));
		contentCosmeticRegisterDetail.setCategoria(JsonToObject.getValue(jsonNode, "categoria"));
		contentCosmeticRegisterDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo"));
		contentCosmeticRegisterDetail
				.setVencimentoRegistro(JsonToObject.getValueDate(jsonNode, "vencimento", "vencimento"));
		contentCosmeticRegisterDetail
				.setPublicacaoRgistro(JsonToObject.getValueDate(jsonNode, "publicacao"));
		contentCosmeticRegisterDetail.setApresentacoes(parseDetailApresentationData(jsonNode, "apresentacoes"));
		contentCosmeticRegisterDetail.setPeticoes(parseDetailPetitionData(jsonNode, "peticoes"));


		return contentCosmeticRegisterDetail;
	}

	
	public ArrayList<CosmeticRegisterPresentation> parseDetailApresentationData(JsonNode jsonNode,String attribute) {
		
		ArrayNode element = (ArrayNode)jsonNode.findValue(attribute);
		
		ArrayList<CosmeticRegisterPresentation> apresentacoes = new ArrayList<CosmeticRegisterPresentation>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					CosmeticRegisterPresentation apresentacaoCosmeticoRegistrado = new CosmeticRegisterPresentation();
					
					apresentacaoCosmeticoRegistrado.setCodigo(JsonToObject.getValue(nodeIt,"codigo"));
					apresentacaoCosmeticoRegistrado.setNumero(JsonToObject.getValue(nodeIt,"numero"));
					apresentacaoCosmeticoRegistrado.setEmbalagemPrimaria(JsonToObject.getValue(nodeIt,"embalagemPrimaria"));
					apresentacaoCosmeticoRegistrado.setEmbalagemSecundaria(JsonToObject.getValue(nodeIt,"embalagemSecundaria"));
					apresentacaoCosmeticoRegistrado.setTonalidade(JsonToObject.getValue(nodeIt,"tonalidade"));
					apresentacaoCosmeticoRegistrado.setRegistro(JsonToObject.getValue(nodeIt, "registro", "registro"));
					apresentacaoCosmeticoRegistrado.setSituacao(JsonToObject.getValue(nodeIt, "registro", "situacao"));
					apresentacoes.add(apresentacaoCosmeticoRegistrado);
					
				}
			
			
			
		}
		
		return apresentacoes;
		
	}
	
	public ArrayList<CosmeticRegisterPetition> parseDetailPetitionData(JsonNode jsonNode,String attribute) {
		
		ArrayNode element = (ArrayNode)jsonNode.findValue(attribute);
		
		ArrayList<CosmeticRegisterPetition> peticoes = new ArrayList<CosmeticRegisterPetition>();
		
		if (element!=null) {
				
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					CosmeticRegisterPetition cosmeticRegisterPetition = new CosmeticRegisterPetition();
					
					cosmeticRegisterPetition.setExpediente(JsonToObject.getValue(nodeIt,"expediente"));
					cosmeticRegisterPetition.setPublicacao(JsonToObject.getValueDate(nodeIt,"publicacao"));
					cosmeticRegisterPetition.setTransacao(JsonToObject.getValue(nodeIt,"transacao"));
					
					String assunto = JsonToObject.getValue(nodeIt, "assunto", "codigo")+" "+JsonToObject.getValue(nodeIt, "assunto", "descricao");
					cosmeticRegisterPetition.setAssunto(assunto);
					
					String situacao = JsonToObject.getValue(nodeIt, "situacao", "situacao")+" "+JsonToObject.getValueDateToString(nodeIt, "situacao", "data");
					cosmeticRegisterPetition.setSituacao(situacao);
					
					peticoes.add(cosmeticRegisterPetition);
					
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
	
	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void persist(ArrayList<BaseEntity> itens) {

		log.info("SynchronizeData", dateFormat.format(new Date()));
		
		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticRegister baseEntity = (ContentCosmeticRegister) iterator.next();

			ContentCosmeticRegister localCosmetic = cosmeticRegisterRepository.findByProcessCnpjVencimento(
					baseEntity.getProcesso(), baseEntity.getCnpj());
			
			

			boolean newFoot = (localCosmetic == null);

			ContentCosmeticRegisterDetail detail = (ContentCosmeticRegisterDetail) this
					.loadDetailData(baseEntity.getProcesso());
			
			if (detail != null) {

				if (!newFoot) {

					if (localCosmetic.getContentCosmeticRegisterDetail() != null && !detail.equals(localCosmetic.getContentCosmeticRegisterDetail())) {
						detail.setId(localCosmetic.getContentCosmeticRegisterDetail().getId());
						cosmeticRegisterDetailRepository.saveAndFlush(detail);
					}
					
				} else {
 					 baseEntity.setContentCosmeticRegisterDetail(detail);
				}

			}

			if (localCosmetic != null) {

				if (!localCosmetic.equals(baseEntity)) {

					baseEntity.setId(localCosmetic.getId());
					detail.setId(localCosmetic.getContentCosmeticRegisterDetail().getId());
					baseEntity.setContentCosmeticRegisterDetail(detail);
					cosmeticRegisterRepository.saveAndFlush(baseEntity);

				}

			} else {

				cosmeticRegisterRepository.saveAndFlush(baseEntity);

			}
			
			log.info(baseEntity.toString());

		}
		
	}

}
