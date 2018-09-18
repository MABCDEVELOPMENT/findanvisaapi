package com.anvisa.core.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.anvisa.FindAnvisaApplication;
import com.anvisa.core.type.TypeSearchProductCosmetic;
import com.anvisa.rest.Content;
import com.anvisa.rest.ContentProcesso;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.QueryRecordProcessParameter;
import com.anvisa.rest.RootObject;
import com.anvisa.rest.detalhe.alimento.ContentDetalheAlimento;
import com.anvisa.rest.detalhe.comestico.notificado.ContentDetalheCosmeticoNotificado;
import com.anvisa.rest.detalhe.comestico.registrado.ContentDetalheCosmeticoRegistrado;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.apresentacao.DetalheCosmeticoRegistradoApresentacao;
import com.anvisa.rest.detalhe.comestico.regularizado.CaracterizacaoVigente;
import com.anvisa.rest.detalhe.comestico.regularizado.ContentDetalheCosmeticoRegularizado;
import com.anvisa.rest.detalhe.comestico.regularizado.EmpresaDetentora;
import com.anvisa.rest.detalhe.processo.ContentProcessoDetalhe;
import com.anvisa.rest.detalhe.saneante.notificado.ContentDetalheSaneanteNotificado;
import com.anvisa.rest.detalhe.saneante.product.ContentDetalheSaneanteProduct;
import com.anvisa.rest.model.Assunto;
import com.anvisa.rest.model.ContentProduto;
import com.anvisa.rest.model.ContentProdutoNotificado;
import com.anvisa.rest.model.ContentProdutoRegistrado;
import com.anvisa.rest.model.ContentProdutoRegularizado;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlToJson {

	public static String URL_PROCESS = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=1000&page=1";
	public static String URL_PROCESS_DETAIL = "https://consultas.anvisa.gov.br/api/documento/tecnico/";

	public static String URL_COSMETIC_REGISTER = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados?count=1000&page=1";
	public static String URL_COSMETIC_REGISTER_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/";
	public static String URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/[processo]/apresentacao/[apresentacao]";
	public static String URL_COSMETIC_REGISTER_DETAIL_PETICAO = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados/[processo]/peticao/[peticao]";

	public static String URL_COSMETIC_NOTIFY = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados?count=1000&page=1";
	public static String URL_COSMETIC_NOTIFY_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados/";

	public static String URL_COSMETIC_REGULARIZED = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados?count=1000&page=1";
	public static String URL_COSMETIC_REGULARIZED_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados/";

	public static String URL_FOOD = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=1000&page=1";
	public static String URL_FOOD_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6/";

	public static String URL_SANEANTE = "https://consultas.anvisa.gov.br/api/consulta/produtos/3?count=1000&page=1";
	public static String URL_SANEANTE_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3/";
	public static String URL_SANEANTE_LABEL  = "https://consultas.anvisa.gov.br/api/consulta/produtos/3/[processo]/rotulo/[rotulo]?Authorization=Guest";
	public static String URL_SANEANTE_NOTIFICADOS = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados?count=1000&page=1";
	public static String URL_SANEANTE_NOTIFICADO_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados/";
	public static String URL_SANEANTE_NOTIFICADO_LABEL  = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados/[processo]/rotulo/[rotulo]?Authorization=Guest";
	/*
	 * public static void main(String[] args) { findFoodSaneate("55323448000138",
	 * "AVEIA", TypeSearch.FOOD_PRODUCT); }
	 */

	public static RootObject findProcess(QueryRecordProcessParameter queryRecordParameter) {

		RootObject rootObjectProcesso = new RootObject();

		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

		Request request = new Request.Builder().url(validParameterProcess(URL_PROCESS, queryRecordParameter)).get()
				.addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(request).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();
			int i = 1;
			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				ContentProcesso contentProcesso = new ContentProcesso();

				contentProcesso.setOrdem(i);
				i++;

				Peticao peticao = JsonToObject.getPeticao(jsonNode);
				contentProcesso.setAssunto(peticao.getAssunto().toString());

				contentProcesso.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

				contentProcesso.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

				Processo processo = JsonToObject.getProcesso(jsonNode);
				contentProcesso.setProcesso(processo.getNumero());

				rootObjectProcesso.getContent().add(contentProcesso);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObjectProcesso;
	}

	public static RootObject find(QueryRecordParameter queryRecordParameter) {

		RootObject rootObject = new RootObject();

		OkHttpClient client = new OkHttpClient();

		Request url = null;

		// queryRecordParameter.setCnpj("55323448000138");

		url = new Request.Builder()
				.url(validParameterProduct(queryRecordParameter.getCnpj(), queryRecordParameter.getNumberProcess(),
						queryRecordParameter.getRegisterNumber(), queryRecordParameter.getProductName(),
						queryRecordParameter.getCategory(), queryRecordParameter.getOption(),
						queryRecordParameter.getBrand(), queryRecordParameter.getAuthorizationNumber(),
						queryRecordParameter.getExpedientProcess(), queryRecordParameter.getGeneratedTransaction(),
						queryRecordParameter.getExpeditionPetition(), queryRecordParameter.getDateInitial(),
						queryRecordParameter.getDateFinal(), queryRecordParameter.getEanCode()))
				.get().addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();
				if (queryRecordParameter.getCategory() == 0) {

					Content content = new Content();

					content.setOrdem(JsonToObject.getOrdem(jsonNode));

					content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

					content.setProcesso(JsonToObject.getProcessoProduto(jsonNode));

					content.setProduto(JsonToObject.getProduto(jsonNode));

					ContentProduto contentProduto = new ContentProduto(content);

					// contentProduto.setSituacao(content.getProduto().getCategoria().getDescricao());

					rootObject.getContent().add(contentProduto);

				} else if (queryRecordParameter.getCategory() == 1 && queryRecordParameter.getOption() == 0) { // Saneantes
					// e Produtos
					// Registrados
					ContentProdutoRegistrado contentProdutoRegistrado = new ContentProdutoRegistrado();

					Assunto assunto = JsonToObject.getAssunto(jsonNode);

					contentProdutoRegistrado.setAssunto(assunto.toString());

					contentProdutoRegistrado.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

					contentProdutoRegistrado.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

					contentProdutoRegistrado.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expediente"));

					// contentProdutoRegistrado.setExpedientePeticao(JsonToObject.getValue(jsonNode,
					// "expedientePeticao"));

					contentProdutoRegistrado.setProduto(JsonToObject.getValue(jsonNode, "nomeProduto"));

					contentProdutoRegistrado.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

					contentProdutoRegistrado.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

					contentProdutoRegistrado.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

					contentProdutoRegistrado
							.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento", "vencimento"));

					rootObject.getContent().add(contentProdutoRegistrado);

				} else if (queryRecordParameter.getCategory() == 1 && queryRecordParameter.getOption() == 2) { // Cosmeticos
																												// e
																												// Produtos
																												// Regularizado
					ContentProdutoRegularizado contentProdutoRegularizado = new ContentProdutoRegularizado();

					contentProdutoRegularizado.setProduto(JsonToObject.getValue(jsonNode, "produto"));

					contentProdutoRegularizado.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

					contentProdutoRegularizado.setSituacao(JsonToObject.getValue(jsonNode, "situacao"));

					contentProdutoRegularizado.setTipo(JsonToObject.getValue(jsonNode, "tipo"));

					contentProdutoRegularizado.setVencimento(JsonToObject.getValueDate(jsonNode, "data"));

					rootObject.getContent().add(contentProdutoRegularizado);

				} else if (queryRecordParameter.getCategory() == 2 && queryRecordParameter.getOption() == 0) { // Saneantes
																												// Produtos
					Content content = new Content();

					content.setOrdem(JsonToObject.getOrdem(jsonNode));

					content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

					content.setProcesso(JsonToObject.getProcessoProduto(jsonNode));

					content.setProduto(JsonToObject.getProduto(jsonNode));

					ContentProduto contentProduto = new ContentProduto(content);

					rootObject.getContent().add(contentProduto);

				} else if ((queryRecordParameter.getCategory() == 1 || queryRecordParameter.getCategory() == 2)
						&& queryRecordParameter.getOption() == 1) { // Cosmeticos e Saneantes + Produtos Notificiados

					ContentProdutoNotificado contentProdutoNotificado = new ContentProdutoNotificado();

					Assunto assunto = JsonToObject.getAssunto(jsonNode);

					contentProdutoNotificado.setAssunto(assunto.toString());

					contentProdutoNotificado.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

					contentProdutoNotificado.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

					contentProdutoNotificado
							.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expedienteProcesso"));

					contentProdutoNotificado.setExpedientePeticao(JsonToObject.getValue(jsonNode, "expedientePeticao"));

					contentProdutoNotificado.setProduto(JsonToObject.getValue(jsonNode, "produto"));

					contentProdutoNotificado.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

					contentProdutoNotificado.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

					contentProdutoNotificado.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

					contentProdutoNotificado.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento"));
					rootObject.getContent().add(contentProdutoNotificado);

				}

			}

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObject;
	}

	public static RootObject findProcessDetail(String valor) {

		RootObject rootObject = new RootObject();

		OkHttpClient client = new OkHttpClient();

		Request url = null;

		url = new Request.Builder().url(URL_PROCESS_DETAIL + valor).get().addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.iterator();

			// while (elementsContents.hasNext()) {

			if (elementsContents.hasNext()) {

				ContentProcessoDetalhe contentProcessoDetalhe = new ContentProcessoDetalhe();

				contentProcessoDetalhe.build(rootNode);

				rootObject.setContentObject(contentProcessoDetalhe);

			}

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObject;

	}

	public static RootObject findDetail(Long categoria, Long opcao, String valor) {

		RootObject rootObject = new RootObject();

		OkHttpClient client = new OkHttpClient();

		Request url = null;

		url = new Request.Builder().url(validParameterProductDetail(categoria,  valor, opcao)).get()
				.addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.iterator();

			// while (elementsContents.hasNext()) {

			if (elementsContents.hasNext()) {

				// JsonNode jsonNode = (JsonNode) rootNode.iterator();

				if (categoria == 0 && opcao == null) {

					ContentDetalheAlimento contentDetalheAlimento = new ContentDetalheAlimento();

					contentDetalheAlimento.setProcesso(JsonToObject.getValue(rootNode, "processo", "numero"));
					contentDetalheAlimento
							.setClassesTerapeuticas(JsonToObject.getArrayValue(rootNode, "classesTerapeuticas"));
					contentDetalheAlimento.setCnpj(JsonToObject.getValue(rootNode, "cnpj"));
					contentDetalheAlimento.setMarca(JsonToObject.getArrayValue(rootNode, "marcas"));
					contentDetalheAlimento.setNomeComercial(JsonToObject.getValue(rootNode, "nomeComercial"));
					contentDetalheAlimento.setRazaoSocial(JsonToObject.getValue(rootNode, "razaoSocial"));
					contentDetalheAlimento.setRegistro(JsonToObject.getValue(rootNode, "numeroRegistro"));
					contentDetalheAlimento.setMesAnoVencimento(JsonToObject.getValue(rootNode, "mesAnoVencimento"));
					contentDetalheAlimento.setPrincipioAtivo(JsonToObject.getValue(rootNode, "principioAtivo"));
					contentDetalheAlimento
							.setEmbalagemPrimaria(JsonToObject.getValue(rootNode, "embalagemPrimaria", "tipo"));
					contentDetalheAlimento
							.setViasAdministrativa(JsonToObject.getArrayValue(rootNode, "viasAdministracao"));
					String ifaUnico = JsonToObject.getValue(rootNode, "ifaUnico");
					contentDetalheAlimento.setIfaUnico(ifaUnico.equals("true") ? "Sim" : "Não");
					contentDetalheAlimento.setConservacao(JsonToObject.getArrayValue(rootNode, "conservacao"));

					rootObject.setContentObject(contentDetalheAlimento);

				} else if (categoria == 1 && opcao == 0) {

					ContentDetalheCosmeticoRegistrado contentDetalheCosmeticoRegistrado = new ContentDetalheCosmeticoRegistrado();
					contentDetalheCosmeticoRegistrado
							.setRazaoSocial(JsonToObject.getValue(rootNode, "empresa", "razaoSocial"));
					contentDetalheCosmeticoRegistrado.setCnpj(JsonToObject.getValue(rootNode, "empresa", "cnpj"));
					contentDetalheCosmeticoRegistrado
							.setAutorizacao(JsonToObject.getValue(rootNode, "empresa", "autorizacao"));
					contentDetalheCosmeticoRegistrado.setNomeProduto(JsonToObject.getValue(rootNode, "nomeProduto"));
					contentDetalheCosmeticoRegistrado.setCategoria(JsonToObject.getValue(rootNode, "categoria"));
					contentDetalheCosmeticoRegistrado.setProcesso(JsonToObject.getValue(rootNode, "processo"));
					contentDetalheCosmeticoRegistrado.setVencimentoRegistro(
							JsonToObject.getValueDateToString(rootNode, "vencimento", "vencimento"));
					contentDetalheCosmeticoRegistrado
							.setPublicacaoRgistro(JsonToObject.getValueDateToString(rootNode, "publicacao"));
					contentDetalheCosmeticoRegistrado.setApresentacoes(rootNode, "apresentacoes");
					contentDetalheCosmeticoRegistrado.setPeticoes(rootNode, "peticoes");

					rootObject.setContentObject(contentDetalheCosmeticoRegistrado);

				} else if (categoria == 1 && opcao == 1) {

					ContentDetalheCosmeticoNotificado contentDetalheCosmeticoNotificado = new ContentDetalheCosmeticoNotificado();

					String assunto = JsonToObject.getValue(rootNode, "assunto", "codigo") + " - "
							+ JsonToObject.getValue(rootNode, "assunto", "descricao");
					contentDetalheCosmeticoNotificado.setAssunto(assunto);

					String empresa = JsonToObject.getValue(rootNode, "empresa", "cnpj") + " - "
							+ JsonToObject.getValue(rootNode, "empresa", "razaoSocial");
					contentDetalheCosmeticoNotificado.setEmpresa(empresa);

					contentDetalheCosmeticoNotificado.setProduto(JsonToObject.getValue(rootNode, "produto"));

					contentDetalheCosmeticoNotificado.setProcesso(JsonToObject.getValue(rootNode, "processo"));
					contentDetalheCosmeticoNotificado.setArea(JsonToObject.getValue(rootNode, "area"));
					contentDetalheCosmeticoNotificado
							.setSituacao(JsonToObject.getValue(rootNode, "situacao", "situacao"));
					contentDetalheCosmeticoNotificado
							.setDataNotificacao(JsonToObject.getValueDateToString(rootNode, "situacao", "data"));
					contentDetalheCosmeticoNotificado.setApresentacoes(rootNode, "apresentacoes");

					rootObject.setContentObject(contentDetalheCosmeticoNotificado);

				} else if (categoria == 1 && opcao == 2) {

					ContentDetalheCosmeticoRegularizado contentDetalheCosmeticoRegularizado = new ContentDetalheCosmeticoRegularizado();

					EmpresaDetentora empresaDetentora = new EmpresaDetentora();

					empresaDetentora.setCnpj(JsonToObject.getValue(rootNode, "empresaDetentora", "cnpj"));
					empresaDetentora.setRazaoSocial(JsonToObject.getValue(rootNode, "empresaDetentora", "razaoSocial"));
					empresaDetentora.setAutorizacao(JsonToObject.getValue(rootNode, "empresaDetentora", "autorizacao"));

					empresaDetentora.setUf(JsonToObject.getValue(rootNode, "empresaDetentora", "uf"));
					empresaDetentora.setCidade(JsonToObject.getValue(rootNode, "empresaDetentora", "cidade"));
					empresaDetentora
							.setCodigoMunicipio(JsonToObject.getValue(rootNode, "empresaDetentora", "codigoMunicipio"));
					contentDetalheCosmeticoRegularizado.setEmpresaDetentora(empresaDetentora);

					CaracterizacaoVigente caracterizacaoVigente = new CaracterizacaoVigente();
					caracterizacaoVigente
							.setProcesso(JsonToObject.getValue(rootNode, "caracterizacaoVigente", "processo"));
					caracterizacaoVigente.setGrupo(JsonToObject.getValue(rootNode, "caracterizacaoVigente", "grupo"));
					caracterizacaoVigente
							.setProduto(JsonToObject.getValue(rootNode, "caracterizacaoVigente", "produto"));
					caracterizacaoVigente
							.setFormaFisica(JsonToObject.getValue(rootNode, "caracterizacaoVigente", "formaFisica"));

					contentDetalheCosmeticoRegularizado.setCaracterizacaoVigente(caracterizacaoVigente);

					contentDetalheCosmeticoRegularizado
							.setDestinacoes(JsonToObject.getArrayValue(rootNode, "destinacoes"));

					contentDetalheCosmeticoRegularizado.setLocalNacional(rootNode, "locaisNacionais");

					contentDetalheCosmeticoRegularizado.setApresentacoes(rootNode, "apresentacoes");

					rootObject.setContentObject(contentDetalheCosmeticoRegularizado);

				} else if (categoria == 2 && opcao == 0) {

					ContentDetalheSaneanteProduct contentDetalheSaneanteProduct = new ContentDetalheSaneanteProduct();

					contentDetalheSaneanteProduct.setProcesso(JsonToObject.getValue(rootNode, "processo", "numero"));
					contentDetalheSaneanteProduct
							.setClassesTerapeuticas(JsonToObject.getArrayValue(rootNode, "classesTerapeuticas"));
					contentDetalheSaneanteProduct.setCnpj(JsonToObject.getValue(rootNode, "cnpj"));
					contentDetalheSaneanteProduct.setNumeroAutorizacao(JsonToObject.getValue(rootNode, "numeroAutorizacao"));
					contentDetalheSaneanteProduct.setNomeComercial(JsonToObject.getValue(rootNode, "nomeComercial"));
					contentDetalheSaneanteProduct.setRazaoSocial(JsonToObject.getValue(rootNode, "razaoSocial"));
					contentDetalheSaneanteProduct.setRegistro(JsonToObject.getValue(rootNode, "numeroRegistro"));
					contentDetalheSaneanteProduct.setMesAnoVencimento(JsonToObject.getValue(rootNode, "mesAnoVencimento"));
					contentDetalheSaneanteProduct.loadApresentaçoes(rootNode,"apresentacoes");
					contentDetalheSaneanteProduct.setRotulos(JsonToObject.getArrayStringValue(rootNode, "rotulos"));
					
					ArrayList<String> rotulos = contentDetalheSaneanteProduct.getRotulos();
					
					for (String rotulo : rotulos) {
						downloadLabel(contentDetalheSaneanteProduct.getProcesso(), rotulo);
					}
					
					rootObject.setContentObject(contentDetalheSaneanteProduct);

				} else if (categoria == 2 && opcao == 1) {

					ContentDetalheSaneanteNotificado contentDetalheSaneanteNotificado = new ContentDetalheSaneanteNotificado();

					String assunto = JsonToObject.getValue(rootNode, "assunto", "codigo") + " - "
							+ JsonToObject.getValue(rootNode, "assunto", "descricao");
					contentDetalheSaneanteNotificado.setAssunto(assunto);

					String empresa = JsonToObject.getValue(rootNode, "empresa", "cnpj") + " - "
							+ JsonToObject.getValue(rootNode, "empresa", "razaoSocial");
					contentDetalheSaneanteNotificado.setEmpresa(empresa);

					contentDetalheSaneanteNotificado.setProduto(JsonToObject.getValue(rootNode, "produto"));

					contentDetalheSaneanteNotificado.setProcesso(JsonToObject.getValue(rootNode, "processo"));
					contentDetalheSaneanteNotificado.setArea(JsonToObject.getValue(rootNode, "area"));
					contentDetalheSaneanteNotificado
							.setSituacao(JsonToObject.getValue(rootNode, "situacao", "situacao"));
					contentDetalheSaneanteNotificado
							.setDataNotificacao(JsonToObject.getValueDateToString(rootNode, "situacao", "data"));
					contentDetalheSaneanteNotificado.setApresentacoes(rootNode, "apresentacoes");
					contentDetalheSaneanteNotificado.setPeticoes(rootNode, "peticoes");
					contentDetalheSaneanteNotificado.setRotulos(JsonToObject.getArrayStringValue(rootNode, "rotulos"));
					
					ArrayList<String> rotulos = contentDetalheSaneanteNotificado.getRotulos();
					
					for (String rotulo : rotulos) {
						downloadSaneanteNotificadoLabel(contentDetalheSaneanteNotificado.getProcesso(),rotulo);
					}

					rootObject.setContentObject(contentDetalheSaneanteNotificado);

				}

			}

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObject;

	}
	
	public static RootObject findDetailCosmetic(String processo, String valor, Long opcao) {

		RootObject rootObject = new RootObject();

		OkHttpClient client = new OkHttpClient();

		Request url = null;

		url = new Request.Builder().url(validParameterProductDetailCosmetic(processo, valor, opcao)).get()
				.addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.iterator();

			// while (elementsContents.hasNext()) {

			if (elementsContents.hasNext()) {

				// JsonNode jsonNode = (JsonNode) rootNode.iterator();

				if (opcao != null && opcao == 0) {

					DetalheCosmeticoRegistradoApresentacao detalheRegistradoApresentacao = new DetalheCosmeticoRegistradoApresentacao();
				
					detalheRegistradoApresentacao.load(rootNode,"produto");

					rootObject.setContentObject(detalheRegistradoApresentacao);

				} else if (opcao != null && opcao == 1) {

					ContentDetalheCosmeticoRegistrado contentDetalheCosmeticoRegistrado = new ContentDetalheCosmeticoRegistrado();
					contentDetalheCosmeticoRegistrado
							.setRazaoSocial(JsonToObject.getValue(rootNode, "empresa", "razaoSocial"));
					contentDetalheCosmeticoRegistrado.setCnpj(JsonToObject.getValue(rootNode, "empresa", "cnpj"));
					contentDetalheCosmeticoRegistrado
							.setAutorizacao(JsonToObject.getValue(rootNode, "empresa", "autorizacao"));
					contentDetalheCosmeticoRegistrado.setNomeProduto(JsonToObject.getValue(rootNode, "nomeProduto"));
					contentDetalheCosmeticoRegistrado.setCategoria(JsonToObject.getValue(rootNode, "categoria"));
					contentDetalheCosmeticoRegistrado.setProcesso(JsonToObject.getValue(rootNode, "processo"));
					contentDetalheCosmeticoRegistrado.setVencimentoRegistro(
							JsonToObject.getValueDateToString(rootNode, "vencimento", "vencimento"));
					contentDetalheCosmeticoRegistrado
							.setPublicacaoRgistro(JsonToObject.getValueDateToString(rootNode, "publicacao"));
					contentDetalheCosmeticoRegistrado.setApresentacoes(rootNode, "apresentacoes");
					contentDetalheCosmeticoRegistrado.setPeticoes(rootNode, "peticoes");

					rootObject.setContentObject(contentDetalheCosmeticoRegistrado);

				}

			}

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObject;

	}
	
	public static void downloadLabel(String processo, String rotulo) {
		
		
		String urlString = URL_SANEANTE_LABEL.replace("[processo]", processo);
		urlString = urlString.replace("[rotulo]",rotulo);

			File dir = new File("/home/findinfo/findimage/produto" );
/*			try {
				System.out.println("Caminho "+dir.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			if (!dir.exists()) {
				dir.mkdirs();
				//System.out.println("Diretorio criado");
			} else {
				//System.out.println("Diretorio existe");
			}
			
			File file = new File(dir,"rotulo_"+rotulo+".jpg");
			if (!file.exists()) {
				downloadFileFromURL(urlString, file);	
			}
		    // System.out.println("Executou");
		
	}

public static void downloadSaneanteNotificadoLabel(String processo,String rotulo) {
		
		
		String urlString = URL_SANEANTE_NOTIFICADO_LABEL.replace("[processo]", processo);
		urlString = urlString.replace("[rotulo]",rotulo);

			File dir = new File("/home/findinfo/findimage/notificado" );
			/*try {
				System.out.println("Caminho "+dir.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			if (!dir.exists()) {
				dir.mkdirs();
				//System.out.println("Diretorio criado");
			} else {
				//System.out.println("Diretorio existe");
			}
			
			File file = new File(dir,"rotulo_"+rotulo+".jpg");
			if (!file.exists()) {
				downloadFileFromURL(urlString, file);	
				
			}
		    //System.out.println("Executou");
		
	}
	public static String validParameterTypeSearchProduct(String url, TypeSearchProductCosmetic typeSearchProduct) {

		if (typeSearchProduct != null) {
			url = url.replace("[typeSearchProduct]", typeSearchProduct.name());
		}

		return url;

	}

	public static String validParameterProcess(String url, QueryRecordProcessParameter queryRecordParameter) {

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			url = url + "&filter[cnpj]=" + queryRecordParameter.getCnpj();
		}

		if (queryRecordParameter.getArea() > 0) {
			url = url + "&filter[area]=" + queryRecordParameter.getArea();
		}

		if (queryRecordParameter.getOfficehour() != null && !queryRecordParameter.getOfficehour().isEmpty()) {
			url = url + "&filter[expediente]=" + queryRecordParameter.getOfficehour();
		}

		if (queryRecordParameter.getTransaction() != null && !queryRecordParameter.getTransaction().isEmpty()) {
			url = url + "&filter[transacao]=" + queryRecordParameter.getTransaction();
		}

		if (queryRecordParameter.getProcess() != null && !queryRecordParameter.getProcess().isEmpty()) {
			url = url + "&filter[processo]=" + queryRecordParameter.getProcess();
		}

		if (queryRecordParameter.getProtocol() != null && !queryRecordParameter.getProtocol().isEmpty()) {
			url = url + "&filter[protocolo]=" + queryRecordParameter.getProtocol();
		}

		if (queryRecordParameter.getKnowledge() != null && !queryRecordParameter.getKnowledge().isEmpty()) {
			url = url + "&filter[conheicimento]=" + queryRecordParameter.getKnowledge();
		}
		return url;
	}

	public static String validParameterProduct(String cnpj, String numeroProcesso, String numeroRegistro,
			String nomeProduto, Long categoria, Long opcao, String marca, String numeroAutorizacao,
			String expedienteProcesso, String transacao, String expedientePeticao, String dataInicial, String dataFinal,
			String codigoEAN) {

		String url = "";

		if (categoria == 0) {

			url = URL_FOOD;

		} else if (categoria == 1) { // Cosmeticos

			if (opcao == 0) {

				url = URL_COSMETIC_REGISTER;

			} else if (opcao == 1) {

				url = URL_COSMETIC_NOTIFY;

			} else if (opcao == 2) {

				url = URL_COSMETIC_REGULARIZED;
			}

		} else if (categoria == 2) { // Saneantes

			if (opcao == 0) {

				url = URL_SANEANTE;

			} else if (opcao == 1) {

				url = URL_SANEANTE_NOTIFICADOS;

			}

		}

		if (cnpj != null && !cnpj.isEmpty()) {
			url = url + "&filter[cnpj]=" + cnpj;
		}

		if (numeroProcesso != null && !numeroProcesso.isEmpty()) {
			url = url + "&filter[numeroProcesso]=" + numeroProcesso;
		}

		if (numeroRegistro != null && !numeroRegistro.isEmpty()) {
			url = url + "&filter[numeroRegistro]=" + numeroRegistro;
		}

		if (nomeProduto != null && !nomeProduto.isEmpty()) {
			url = url + "&filter[nomeProduto]=" + nomeProduto.toUpperCase();
		}

		/*
		 * if (categoria != null) { url = url + "&filter[categoria]=" + categoria; }
		 */

		if (marca != null && !marca.isEmpty()) {
			url = url + "&filter[marca]=" + marca.toUpperCase();
		}

		if (numeroAutorizacao != null && !numeroAutorizacao.isEmpty()) {
			url = url + "&filter[numeroAutorizacao]=" + numeroAutorizacao.toUpperCase();
		}

		if (expedienteProcesso != null && !expedienteProcesso.isEmpty()) {
			expedienteProcesso = expedienteProcesso.replace(".", "");
			url = url + "&filter[expedienteProcesso]=" + expedienteProcesso.toUpperCase();
		}

		if (transacao != null && !transacao.isEmpty()) {
			url = url + "&filter[transacao]=" + transacao.toUpperCase();
		}

		if (expedientePeticao != null && !expedientePeticao.isEmpty()) {
			url = url + "&filter[expedientePeticao]=" + expedientePeticao.toUpperCase();
		}

		if (dataInicial != null && !dataInicial.isEmpty()) {
			url = url + "&filter[dataInicial]=" + dataInicial;
		}

		if (dataFinal != null && !dataFinal.isEmpty()) {
			url = url + "&filter[dataFinal]=" + dataFinal;
		}

		if (codigoEAN != null && !codigoEAN.isEmpty()) {
			url = url + "&filter[codigoEAN]=" + codigoEAN;
		}

		return url;
	}

	public static String validParameterProductDetail(Long categoria, String valor, Long opcao) {

		String url = "";

		if (categoria == 0) {

			url = URL_FOOD_DETAIL;

		} else if (categoria == 1) {

			if (opcao == 0) {

				url = URL_COSMETIC_REGISTER_DETAIL;

			} else if (opcao == 1) {

				url = URL_COSMETIC_NOTIFY_DETAIL;

			} else if (opcao == 2) {

				url = URL_COSMETIC_REGULARIZED_DETAIL;
			}

		} else if (categoria == 2) { // Saneantes

			if (opcao == 0) {

				url = URL_SANEANTE_DETAIL;

			} else if (opcao == 1) {

				url = URL_SANEANTE_NOTIFICADO_DETAIL;

			}

		}

		return url + valor;
	}

	public static String validParameterProductDetailCosmetic(String processo, String valor, Long opcao) {

		String url = "";

		if (opcao == 0) {

			url = URL_COSMETIC_REGISTER_DETAIL_APRESENTACAO.replace("[apresentacao]", valor);

		} else if (opcao == 1) {

			url = URL_COSMETIC_REGISTER_DETAIL_PETICAO.replace("[peticao]", valor);;			

		}

		
		url = url.replace("[processo]", processo);
		
		return url;
		
	}


	public static void downloadFileFromURL(String urlString, File destination) {
		try {
			
			URL website = new URL(urlString);
			ReadableByteChannel rbc;
			rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(destination);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			
			System.out.println("Arquivo baixado");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String strDate = sdf.format(now);
		return strDate;
	}
}
