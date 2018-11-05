package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegisterDetail;
import com.anvisa.model.persistence.mongodb.cosmetic.register.petition.CosmeticRegisterPetition;
import com.anvisa.model.persistence.mongodb.cosmetic.register.petition.CosmeticRegisterPetitionDetail;
import com.anvisa.model.persistence.mongodb.cosmetic.register.petition.CosmeticRegisterPetitionPresentation;
import com.anvisa.model.persistence.mongodb.cosmetic.register.petition.PetitionCountryManufacturer;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.CosmeticRegisterPresentation;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.CosmeticRegisterPresentationDetail;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.PresentationConservation;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.PresentationCountryManufacturer;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.PresentationDestination;
import com.anvisa.model.persistence.mongodb.cosmetic.register.presentation.PresentationRestriction;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LoggerProcessing;
import com.anvisa.model.persistence.mongodb.repository.CosmeticRegisterRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.LoggerRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class SynchronizeCosmeticRegisterMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {

	String URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO = "";
	String URL_COSMETIC_REGISTER_DETAIL_PETICAO = "";

	@Autowired
	public static SequenceDaoImpl sequence;

	@Autowired
	private static CosmeticRegisterRepositoryMdb cosmeticRegisterRepository;
	
	@Autowired
	private static LoggerRepositoryMdb loggerRepositoryMdb;

	@Autowired
	public void setService(CosmeticRegisterRepositoryMdb cosmeticRegisterRepository, SequenceDaoImpl sequence, LoggerRepositoryMdb loggerRepositoryMdb) {

		this.cosmeticRegisterRepository = cosmeticRegisterRepository;
		this.sequence = sequence;
		this.loggerRepositoryMdb = loggerRepositoryMdb;

	}

	public SynchronizeCosmeticRegisterMdb() {

		SEQ_KEY = "cosmetic_register";

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados?count=1000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/";

		URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/[processo]/apresentacao/[apresentacao]";

		URL_COSMETIC_REGISTER_DETAIL_PETICAO = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/[processo]/peticao/[peticao]";

	}

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

		contentCosmeticRegister.setDataRegistro(JsonToObject.getValueDate(jsonNode, "publicacao"));

		return contentCosmeticRegister;
	}

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
		contentCosmeticRegisterDetail.setApresentacoes(
				parseApresentationData(jsonNode, "apresentacoes", contentCosmeticRegisterDetail.getProcesso()));
		contentCosmeticRegisterDetail
				.setPeticoes(parsePetitionData(jsonNode, "peticoes", contentCosmeticRegisterDetail.getProcesso()));

		return contentCosmeticRegisterDetail;
	}

	public ArrayList<CosmeticRegisterPresentation> parseApresentationData(JsonNode jsonNode, String attribute,
			String processo) {

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

				CosmeticRegisterPresentationDetail cosmeticRegisterPresentationDetail = (CosmeticRegisterPresentationDetail) this
						.loadPresentationDetail(processo, apresentacaoCosmeticoRegistrado.getCodigo());
				apresentacaoCosmeticoRegistrado
						.setCosmeticRegisterPresentationDetail(cosmeticRegisterPresentationDetail);

				apresentacoes.add(apresentacaoCosmeticoRegistrado);

			}

			return apresentacoes;

		} else {

			return null;
		}

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

				CosmeticRegisterPetitionDetail cosmeticRegisterPresentationDetail = this.loadPetitionDetail(processo,
						cosmeticRegisterPetition.getExpediente());

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

			cosmeticRegisterPetitionDetail.setRazaoSocial(JsonToObject.getValue(element, "empresa", "razaoSocial"));
			cosmeticRegisterPetitionDetail.setCnpj(JsonToObject.getValue(element, "empresa", "cnpj"));
			cosmeticRegisterPetitionDetail.setAutorizacao(JsonToObject.getValue(element, "empresa", "autorizacao"));

			cosmeticRegisterPetitionDetail.setNomeProduto(JsonToObject.getValue(element, "nomeProduto"));
			cosmeticRegisterPetitionDetail.setCategoria(JsonToObject.getValue(element, "categoria"));

			cosmeticRegisterPetitionDetail.setRegistro(JsonToObject.getValue(node, "registro"));
			cosmeticRegisterPetitionDetail.setPeticao(JsonToObject.getValue(node, "expediente"));
			cosmeticRegisterPetitionDetail.setVencimento(JsonToObject.getValueDate(node, "vencimento", "vencimento"));

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
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		ArrayList<BaseEntityMongoDB> rootObject = new ArrayList<BaseEntityMongoDB>();

		OkHttpClient client = new OkHttpClient();

		client.newBuilder().readTimeout(30, TimeUnit.MINUTES);

		Request url = null;

		url = new Request.Builder().url(URL + cnpj).get().addHeader("Accept-Encoding", "gzip")
				.addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			if (response.code() == 500) {
				response.close();
				client = null;
				return null;
			}

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			log.info("SynchronizeData Total Registros " + rootNode.get("totalElements"), dateFormat.format(new Date()));

			int i = 0;
			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				BaseEntityMongoDB baseEntity = this.parseData(jsonNode);

				String processo = ((ContentCosmeticRegister) baseEntity).getProcesso();

				rootObject.add(baseEntity);

				System.out.println(i++);

			}
			response.close();
			client = null;
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return rootObject;
		// return super.loadData(this, cnpj);
	}

	public ContentCosmeticRegisterDetail loadDetailData(String concat) {
		ContentCosmeticRegisterDetail rootObject = null;
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
		// return super.loadDetailData(this, concat);
	}

	public CosmeticRegisterPresentationDetail loadPresentationDetail(String processo, String apresentacao) {

		CosmeticRegisterPresentationDetail rootObject = null;
		OkHttpClient client = new OkHttpClient();

		Request url = null;

		String strUrl = URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO.replace("[processo]", processo)
				.replace("[apresentacao]", apresentacao);
		url = new Request.Builder().url(strUrl).get().addHeader("Accept-Encoding", "gzip")
				.addHeader("authorization", "Guest").build();

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

				rootObject = this.parseDetailAprentation(rootNode, "produto");
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

		String strUrl = URL_COSMETIC_REGISTER_DETAIL_PETICAO.replace("[processo]", processo).replace("[peticao]",
				peticao);
		url = new Request.Builder().url(strUrl).get().addHeader("Accept-Encoding", "gzip")
				.addHeader("authorization", "Guest").build();

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

				rootObject = this.parsePetitionDetailData(rootNode, "produto");
			}
			response.close();
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void persist(ArrayList<BaseEntityMongoDB> itens, LoggerProcessing loggerProcessing) {

				
		int totalInserido   = 0;
		int totalAtualizado = 0;
		int totalErro       = 0;

		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			ContentCosmeticRegister baseEntity = (ContentCosmeticRegister) iterator.next();
			
			try {
				
				log.info("Synchronize "+this.getClass().getName()+" "+baseEntity.getProcesso() + " - " + baseEntity.getCnpj());

				ContentCosmeticRegister localCosmetic = cosmeticRegisterRepository.findByProcesso(
						baseEntity.getProcesso(), baseEntity.getExpedienteProcesso(), baseEntity.getCnpj());

				boolean newFoot = (localCosmetic == null);

				/*if (newFoot == false)
					continue;*/

				// ContentCosmeticRegisterDetail detail =
				// baseEntity.getContentCosmeticRegisterDetail();

				ContentCosmeticRegisterDetail detail = this.loadDetailData(baseEntity.getProcesso());
				if (detail != null) {
					ContentCosmeticRegisterDetail contentCosmeticRegisterDetail = detail;
					((ContentCosmeticRegister) baseEntity)
							.setContentCosmeticRegisterDetail(contentCosmeticRegisterDetail);
				}

				if (detail != null) {

					if (!newFoot) {

						if (localCosmetic.getContentCosmeticRegisterDetail() != null
								&& !detail.equals(localCosmetic.getContentCosmeticRegisterDetail())) {
						}

					} else {
						baseEntity.setContentCosmeticRegisterDetail(detail);
					}

				}

				if (localCosmetic != null) {

					if (!localCosmetic.equals(baseEntity)) {

						baseEntity.setId(localCosmetic.getId());
						baseEntity.setUpdateDate(LocalDateTime.now());
						cosmeticRegisterRepository.save(baseEntity);
						totalAtualizado++;
					}

				} else {
					baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
					baseEntity.setInsertDate(LocalDateTime.now());
					cosmeticRegisterRepository.save(baseEntity);
					totalInserido++;
				}

			} catch (Exception e) {
				// TODO: handle exception
				log.error(this.getClass().getName() + " Cnpj " + baseEntity.getCnpj() + " Processo "
						+ baseEntity.getProcesso() + " ExpedienteProcesso " + baseEntity.getExpedienteProcesso());
				log.error(e.getMessage());
				
				totalErro++;
			}

		}

		loggerProcessing.setTotalInserido(new Long(totalInserido));
		loggerProcessing.setTotalAtualizado(new Long(totalAtualizado));
		loggerProcessing.setTotalErro(new Long(totalErro));
		this.loggerRepositoryMdb.save(loggerProcessing);
	}

}
