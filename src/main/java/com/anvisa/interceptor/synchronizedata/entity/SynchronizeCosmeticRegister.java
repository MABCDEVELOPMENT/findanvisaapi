package com.anvisa.interceptor.synchronizedata.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.BaseEntityAudit;
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

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados?count=1000&page=1&filter[cnpj]=";

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
		//contentCosmeticRegisterDetail.setPeticoes(parseDetailPetitionData(jsonNode, "peticoes"));


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
	public ArrayList<BaseEntityAudit> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	@Override
	public BaseEntityAudit loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}
	

	@Override
	public void persist(ArrayList<BaseEntityAudit> itens) {

		for (Iterator<BaseEntityAudit> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticRegister BaseEntity = (ContentCosmeticRegister) iterator.next();

			ContentCosmeticRegister localFoot = cosmeticRegisterRepository.findByProcessCnpjVencimento(
					BaseEntity.getProcesso(), BaseEntity.getCnpj(), BaseEntity.getVencimento());

			boolean newFoot = (localFoot == null);

			ContentCosmeticRegisterDetail detail = (ContentCosmeticRegisterDetail) this
					.loadDetailData(BaseEntity.getProcesso());

			if (detail != null) {

				if (!newFoot) {

					if (localFoot.getContentCosmeticRegisterDetail() != null && !detail.equals(localFoot.getContentCosmeticRegisterDetail())) {
						detail.setId(localFoot.getContentCosmeticRegisterDetail().getId());
						cosmeticRegisterDetailRepository.save(detail);
						BaseEntity.setContentCosmeticRegisterDetail(detail);
					} else {
						detail.setId(localFoot.getContentCosmeticRegisterDetail().getId());
					}
				} else {

					cosmeticRegisterDetailRepository.saveAndFlush(detail);

					BaseEntity.setContentCosmeticRegisterDetail(detail);
				}

			}

			if (localFoot != null) {

				if (!localFoot.equals(BaseEntity)) {

					BaseEntity.setId(localFoot.getId());
					detail.setId(localFoot.getContentCosmeticRegisterDetail().getId());
					BaseEntity.setContentCosmeticRegisterDetail(detail);
					cosmeticRegisterRepository.saveAndFlush(BaseEntity);

				}

			} else {

				cosmeticRegisterRepository.saveAndFlush(BaseEntity);

			}

		}

	}

}
