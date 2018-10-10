package com.anvisa.interceptor.synchronizedata.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.anvisa.model.persistence.rest.cosmetic.register.petition.CosmeticRegisterPetition;
import com.anvisa.model.persistence.rest.cosmetic.register.petition.CosmeticRegisterPetitionDetail;
import com.anvisa.model.persistence.rest.cosmetic.register.petition.CosmeticRegisterPetitionPresentation;
import com.anvisa.model.persistence.rest.cosmetic.register.petition.PetitionCountryManufacturer;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.CosmeticRegisterPresentation;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.CosmeticRegisterPresentationDetail;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.PresentationConservation;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.PresentationCountryManufacturer;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.PresentationDestination;
import com.anvisa.model.persistence.rest.cosmetic.register.presentation.PresentationRestriction;
import com.anvisa.repository.generic.CosmeticRegisterDetailRepository;
import com.anvisa.repository.generic.CosmeticRegisterRepository;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.peticao.RegistradoPeticaoApresentacao;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.peticao.RegistradoPeticaoFabricantesNacionais;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class SynchronizeCosmeticRegister extends SynchronizeData implements IntSynchronize {

	String URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO = "";
	String URL_COSMETIC_REGISTER_DETAIL_PETICAO      = "";

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

		URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/[processo]/apresentacao/[apresentacao]";
		
		URL_COSMETIC_REGISTER_DETAIL_PETICAO = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/[processo]/peticao/[peticao]";
		
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
		contentCosmeticRegisterDetail.setPublicacaoRgistro(JsonToObject.getValueDate(jsonNode, "publicacao"));
		contentCosmeticRegisterDetail.setApresentacoes(parseApresentationData(jsonNode, "apresentacoes",contentCosmeticRegisterDetail.getProcesso()));
		contentCosmeticRegisterDetail.setPeticoes(parsePetitionData(jsonNode, "peticoes", contentCosmeticRegisterDetail.getProcesso()));

		return contentCosmeticRegisterDetail;
	}

	public ArrayList<CosmeticRegisterPresentation> parseApresentationData(JsonNode jsonNode, String attribute, String processo) {

		ArrayNode element = (ArrayNode) jsonNode.findValue(attribute);

		ArrayList<CosmeticRegisterPresentation> apresentacoes = new ArrayList<CosmeticRegisterPresentation>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				CosmeticRegisterPresentation apresentacaoCosmeticoRegistrado = new CosmeticRegisterPresentation();

				apresentacaoCosmeticoRegistrado.setCodigo(JsonToObject.getValue(nodeIt, "codigo"));
				apresentacaoCosmeticoRegistrado.setNumero(JsonToObject.getValue(nodeIt, "numero"));
				apresentacaoCosmeticoRegistrado
						.setEmbalagemPrimaria(JsonToObject.getValue(nodeIt, "embalagemPrimaria"));
				apresentacaoCosmeticoRegistrado
						.setEmbalagemSecundaria(JsonToObject.getValue(nodeIt, "embalagemSecundaria"));
				apresentacaoCosmeticoRegistrado.setTonalidade(JsonToObject.getValue(nodeIt, "tonalidade"));
				apresentacaoCosmeticoRegistrado.setRegistro(JsonToObject.getValue(nodeIt, "registro", "registro"));
				apresentacaoCosmeticoRegistrado.setSituacao(JsonToObject.getValue(nodeIt, "registro", "situacao"));
				
				CosmeticRegisterPresentationDetail cosmeticRegisterPresentationDetail = (CosmeticRegisterPresentationDetail) this.loadPresentationDetail(processo, apresentacaoCosmeticoRegistrado.getCodigo());
				apresentacaoCosmeticoRegistrado.setCosmeticRegisterPresentationDetail(cosmeticRegisterPresentationDetail);
				
				apresentacoes.add(apresentacaoCosmeticoRegistrado);

			}

		}

		return apresentacoes;

	}

	public CosmeticRegisterPresentationDetail parseDetailAprentation(JsonNode jsonNode, String attribute) {
		// TODO Auto-generated method stub
		
		JsonNode element = jsonNode.findValue(attribute);
		
		CosmeticRegisterPresentationDetail cosmeticRegisterPresentationDetail = null;
		
		if (element != null) {
			
			cosmeticRegisterPresentationDetail = new CosmeticRegisterPresentationDetail();
			
			cosmeticRegisterPresentationDetail.setNomeProduto(JsonToObject.getValue(element, "nomeProduto"));
			cosmeticRegisterPresentationDetail.setProcesso(JsonToObject.getValue(element, "processo"));
			cosmeticRegisterPresentationDetail
					.setApresentacao(JsonToObject.getValue(jsonNode, "apresentacao", "embalagemPrimaria"));
			cosmeticRegisterPresentationDetail.setCategoria(JsonToObject.getValue(element, "categoria"));

			cosmeticRegisterPresentationDetail.setFormaFisica(JsonToObject.getValue(jsonNode, "formaFisica"));
			cosmeticRegisterPresentationDetail.setTonalidade(JsonToObject.getValue(jsonNode, "tonalidade"));
			cosmeticRegisterPresentationDetail.setPrazoValidade(JsonToObject.getValue(jsonNode, "prazoValidade"));

			ArrayList<PresentationCountryManufacturer> fabricantes = new ArrayList<PresentationCountryManufacturer>();

			ArrayNode elementLocalFabricacao = (ArrayNode) jsonNode.findValue("fabricantesNacionais");

			if (elementLocalFabricacao != null) {

				for (JsonNode jsonNode2 : elementLocalFabricacao) {

					PresentationCountryManufacturer fabricante = new PresentationCountryManufacturer();

					fabricante.setCnpj(JsonToObject.getValue(jsonNode2, "cnpj"));
					fabricante.setRazaoSocial(JsonToObject.getValue(jsonNode2, "razaoSocial"));
					fabricante.setCidade(JsonToObject.getValue(jsonNode2, "cidade"));
					fabricante.setUf(JsonToObject.getValue(jsonNode2, "uf"));
					fabricante.setPais(JsonToObject.getValue(jsonNode, "pais"));
					fabricante.setTipo(JsonToObject.getValue(jsonNode, "tipo"));

					fabricantes.add(fabricante);
				}

			}

			cosmeticRegisterPresentationDetail.setFabricantesNacionais(fabricantes);

			ArrayList<String> conservacao = JsonToObject.getArrayStringValue(jsonNode, "conservacao");
			List<PresentationConservation> presentationConservations = new ArrayList<PresentationConservation>();

			for (Iterator<String> iterator = conservacao.iterator(); iterator.hasNext();) {

				String valor = (String) iterator.next();

				presentationConservations.add(new PresentationConservation(valor));

			}
			cosmeticRegisterPresentationDetail.setConservacao(presentationConservations);

			ArrayList<String> destinacao = JsonToObject.getArrayStringValue(jsonNode, "destinacao");
			List<PresentationDestination> presentationDestinacoes = new ArrayList<PresentationDestination>();

			for (Iterator<String> iterator = destinacao.iterator(); iterator.hasNext();) {

				String valor = (String) iterator.next();

				presentationDestinacoes.add(new PresentationDestination(valor));

			}
			cosmeticRegisterPresentationDetail.setDestinacao(presentationDestinacoes);

			ArrayList<String> restricao = JsonToObject.getArrayStringValue(jsonNode, "restricao");
			List<PresentationRestriction> presentationRestricoes = new ArrayList<PresentationRestriction>();

			for (Iterator<String> iterator = restricao.iterator(); iterator.hasNext();) {

				String valor = (String) iterator.next();

				presentationRestricoes.add(new PresentationRestriction(valor));

			}

			cosmeticRegisterPresentationDetail.setRestricao(presentationRestricoes);


		}
		
		return cosmeticRegisterPresentationDetail;
		
	}

	public ArrayList<CosmeticRegisterPetition> parsePetitionData(JsonNode jsonNode, String attribute, String processo) {

		ArrayNode element = (ArrayNode) jsonNode.findValue(attribute);

		ArrayList<CosmeticRegisterPetition> peticoes = new ArrayList<CosmeticRegisterPetition>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				CosmeticRegisterPetition cosmeticRegisterPetition = new CosmeticRegisterPetition();

				cosmeticRegisterPetition.setExpediente(JsonToObject.getValue(nodeIt, "expediente"));
				cosmeticRegisterPetition.setPublicacao(JsonToObject.getValueDate(nodeIt, "publicacao"));
				cosmeticRegisterPetition.setTransacao(JsonToObject.getValue(nodeIt, "transacao"));

				String assunto = JsonToObject.getValue(nodeIt, "assunto", "codigo") + " "
						+ JsonToObject.getValue(nodeIt, "assunto", "descricao");
				cosmeticRegisterPetition.setAssunto(assunto);

				String situacao = JsonToObject.getValue(nodeIt, "situacao", "situacao") + " "
						+ JsonToObject.getValueDateToString(nodeIt, "situacao", "data");
				cosmeticRegisterPetition.setSituacao(situacao);
				
				CosmeticRegisterPetitionDetail cosmeticRegisterPresentationDetail = this.loadPetitionDetail(processo, cosmeticRegisterPetition.getExpediente());

				cosmeticRegisterPetition.setCosmeticRegisterPetitionDetail(cosmeticRegisterPresentationDetail);
				
				peticoes.add(cosmeticRegisterPetition);

			}

		}

		return peticoes;

	}
	
	
	public CosmeticRegisterPetitionDetail parsePetitionDetailData(JsonNode node, String attribute) {
		
		
		JsonNode element = node.findValue(attribute);
		
		CosmeticRegisterPetitionDetail cosmeticRegisterPetitionDetail = null;

		if (element != null) {
			
			cosmeticRegisterPetitionDetail = new CosmeticRegisterPetitionDetail();
			
			cosmeticRegisterPetitionDetail.setRazaoSocial(JsonToObject.getValue(element,"empresa","razaoSocial"));
			cosmeticRegisterPetitionDetail.setCnpj(JsonToObject.getValue(element,"empresa","cnpj"));
			cosmeticRegisterPetitionDetail.setAutorizacao(JsonToObject.getValue(element,"empresa","autorizacao"));
			
			cosmeticRegisterPetitionDetail.setNomeProduto(JsonToObject.getValue(element, "nomeProduto"));
			cosmeticRegisterPetitionDetail.setCategoria(JsonToObject.getValue(element, "categoria"));
			
			cosmeticRegisterPetitionDetail.setRegistro(JsonToObject.getValue(node, "registro"));
			cosmeticRegisterPetitionDetail.setPeticao(JsonToObject.getValue(node, "expediente"));
			cosmeticRegisterPetitionDetail.setVencimento(JsonToObject.getValueDate(node,"vencimento" ,"vencimento"));

			ArrayList<PetitionCountryManufacturer> fabricantes = new ArrayList<PetitionCountryManufacturer>();

			ArrayNode elementLocalFabricacao = (ArrayNode) node.findValue("fabricantesNacionais");

			if (elementLocalFabricacao != null) {

				for (JsonNode jsonNode : elementLocalFabricacao) {

					PetitionCountryManufacturer fabricante = new PetitionCountryManufacturer();

					fabricante.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
					fabricante.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
					fabricante.setCidade(JsonToObject.getValue(jsonNode, "cidade"));
					fabricante.setUf(JsonToObject.getValue(jsonNode, "uf"));
					fabricante.setPais(JsonToObject.getValue(jsonNode, "pais"));
					fabricante.setTipo(JsonToObject.getValue(jsonNode, "tipo"));

					fabricantes.add(fabricante);
				}

			}

			cosmeticRegisterPetitionDetail.setFabricantesNacionais(fabricantes);

			
			ArrayList<CosmeticRegisterPetitionPresentation> registradoPeticaoApresentacao = new ArrayList<CosmeticRegisterPetitionPresentation>();

			ArrayNode elementApresentacao = (ArrayNode) node.get("apresentacoes");

			if (elementApresentacao != null) {

				for (JsonNode jsonNode : elementApresentacao) {

					CosmeticRegisterPetitionPresentation apresentacao = new CosmeticRegisterPetitionPresentation();

					apresentacao.setNome(JsonToObject.getValue(jsonNode, "apresentacao"));
					apresentacao.setEmbalagemPrimaria(JsonToObject.getValue(jsonNode, "embalagemPrimaria"));
					apresentacao.setEmbalagemSecundaria(JsonToObject.getValue(jsonNode, "embalagemSecundaria"));
					apresentacao.setFormaFisica(JsonToObject.getValue(jsonNode, "formaFisica"));
					apresentacao.setNumero(JsonToObject.getValue(jsonNode, "numero"));
					apresentacao.setPrazoValidade(JsonToObject.getValue(jsonNode, "prazoValidade"));
					apresentacao.setRegistro(JsonToObject.getValue(jsonNode, "registro"));
					apresentacao.setTipoValidade(JsonToObject.getValue(jsonNode, "tipoValidade"));
					apresentacao.setTonalidade(JsonToObject.getValue(jsonNode, "tonalidade"));

					registradoPeticaoApresentacao.add(apresentacao);
				}

			}

			cosmeticRegisterPetitionDetail.setApresentacoes(registradoPeticaoApresentacao);

		}
		return cosmeticRegisterPetitionDetail;
	}

	@Override
	public ArrayList<BaseEntity> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	@Override
	public BaseEntity loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}

	public BaseEntity loadPresentationDetail(String processo, String apresentacao) {

		BaseEntity rootObject = null;
		OkHttpClient client = new OkHttpClient();

		Request url = null;

		String strUrl = URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO
				.replace("[processo]", processo)
				.replace("[apresentacao]", apresentacao);
		url = new Request.Builder().url(strUrl).get().addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			if (rootNode != null) {

				rootObject = this.parseDetailAprentation(rootNode,"produto");
			}
			response.close();
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	public CosmeticRegisterPetitionDetail loadPetitionDetail(String processo, String peticao) {

		CosmeticRegisterPetitionDetail rootObject = null;
		OkHttpClient client = new OkHttpClient();

		Request url = null;

		String strUrl = URL_COSMETIC_REGISTER_DETAIL_PETICAO
				.replace("[processo]", processo)
				.replace("[peticao]", peticao);
		url = new Request.Builder().url(strUrl).get().addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			if (rootNode != null) {

				rootObject = this.parsePetitionDetailData(rootNode,"produto");
			}
			response.close();
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void persist(ArrayList<BaseEntity> itens) {

		log.info("SynchronizeData", dateFormat.format(new Date()));

		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticRegister baseEntity = (ContentCosmeticRegister) iterator.next();

			ContentCosmeticRegister localCosmetic = cosmeticRegisterRepository
					.findByProcessCnpjVencimento(baseEntity.getProcesso(), baseEntity.getCnpj());

			boolean newFoot = (localCosmetic == null);

			ContentCosmeticRegisterDetail detail = (ContentCosmeticRegisterDetail) this
					.loadDetailData(baseEntity.getProcesso());

			if (detail != null) {

				if (!newFoot) {

					if (localCosmetic.getContentCosmeticRegisterDetail() != null
							&& !detail.equals(localCosmetic.getContentCosmeticRegisterDetail())) {
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
