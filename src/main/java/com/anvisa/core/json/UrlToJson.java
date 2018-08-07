package com.anvisa.core.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.anvisa.core.type.TypeArea;
import com.anvisa.core.type.TypeSearchProductCosmetic;
import com.anvisa.rest.Content;
import com.anvisa.rest.ContentProcesso;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.QueryRecordProcessParameter;
import com.anvisa.rest.RootObject;
import com.anvisa.rest.RootObjectProduto;
import com.anvisa.rest.model.Assunto;
import com.anvisa.rest.model.ContentProduto;
import com.anvisa.rest.model.ContentProdutoNotificado;
import com.anvisa.rest.model.ContentProdutoRegistrado;
import com.anvisa.rest.model.ContentProdutoRegularizado;
import com.anvisa.rest.model.Empresa;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlToJson {

	public static String URL_PROCESS = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=1000&page=1";
	public static String URL_COSMETIC_REGISTER = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/registrados?count=1000&page=1";
	public static String URL_COSMETIC_NOTIFY = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados?count=1000&page=1";
	public static String URL_COSMETIC_REGULARIZED = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/regularizados?count=1000&page=1";
	public static String URL_FOOD = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=1000&page=1";
	public static String URL_SANEANTE = "https://consultas.anvisa.gov.br/api/consulta/produtos/3?count=1000&page=1";
	public static String URL_SANEANTE_NOTIFICADOS = "https://consultas.anvisa.gov.br/api/consulta/saneantes/notificados?count=1000&page=1";

	/*
	 * public static void main(String[] args) { findFoodSaneate("55323448000138",
	 * "AVEIA", TypeSearch.FOOD_PRODUCT); }
	 */

	public static RootObject findProcess(QueryRecordProcessParameter queryRecordParameter) {

		RootObject rootObjectProcesso = new RootObject();

		OkHttpClient client = new OkHttpClient.Builder()
		        .connectTimeout(30, TimeUnit.SECONDS)
		        .writeTimeout(30, TimeUnit.SECONDS)
		        .readTimeout(30, TimeUnit.SECONDS)
		        .build();
		

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

				/*Content content = new Content();

				content.setTipo(JsonToObject.getTipo(jsonNode));

				content.setDataEntrada(JsonToObject.getDataEntrada(jsonNode));

				content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

				content.setProcesso(JsonToObject.getProcesso(jsonNode));

				content.setPeticao(JsonToObject.getPeticao(jsonNode));

				content.setArea(JsonToObject.getArea(jsonNode));

				content.setProduto(JsonToObject.getProduto(jsonNode));*/

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

		url = new Request.Builder().url(validParameterProduct(queryRecordParameter.getCnpj(),
				queryRecordParameter.getNumberProcess(), queryRecordParameter.getRegisterNumber(),
				queryRecordParameter.getProductName(), queryRecordParameter.getCategory(),
				queryRecordParameter.getOption(), queryRecordParameter.getBrand(),
				queryRecordParameter.getAuthorizationNumber(),
				queryRecordParameter.getExpedientProcess(),
				queryRecordParameter.getGeneratedTransaction(),
				queryRecordParameter.getExpeditionPetition(),
				queryRecordParameter.getDateInitial(),
				queryRecordParameter.getDateFinal())).get()
				.addHeader("authorization", "Guest").build();

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
					
					//contentProduto.setSituacao(content.getProduto().getCategoria().getDescricao());
					
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

		return url;
	}

	public static String validParameterProduct(String cnpj, String numeroProcesso, String numeroRegistro,
			String nomeProduto, Long categoria, Long opcao, String marca, String numeroAutorizacao,
			String expedienteProcesso, String transacao, String expedientePeticao,String dataInicial,String dataFinal) {

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
		} catch (IOException e) {
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
