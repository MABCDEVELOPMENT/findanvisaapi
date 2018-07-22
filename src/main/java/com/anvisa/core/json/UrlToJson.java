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

import com.anvisa.core.type.TypeArea;
import com.anvisa.core.type.TypeSearchProductCosmetic;
import com.anvisa.rest.Content;
import com.anvisa.rest.ContentProduto;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.RootObject;
import com.anvisa.rest.RootObjectProduto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlToJson {

	public static String URL_PROCESS = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=1000&page=1";
	public static String URL_COSMETIC = "https://consultas.anvisa.gov.br/api/consulta/produtos/cosmeticos/registrados?count=1000&page=1";
	public static String URL_FOOD = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=1000&page=1";
	public static String URL_SANEANTE = "https://consultas.anvisa.gov.br/api/consulta/produtos/3?count=1000&page=1";

	/*
	 * public static void main(String[] args) { findFoodSaneate("55323448000138",
	 * "AVEIA", TypeSearch.FOOD_PRODUCT); }
	 */

	public static RootObject findProcess(QueryRecordParameter queryRecordParameter) {

		RootObject rootObjectProcesso = new RootObject();

		OkHttpClient client = new OkHttpClient(); //
		// https://consultas.anvisa.gov.br/#/alimentos/q/?nomeProduto=AVEIA Request
		Request request = new Request.Builder().url(validParameterProcess(URL_PROCESS, queryRecordParameter)).get()
				.addHeader("authorization", "Guest").build();

		try {
			Response response = client.newCall(request).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				Content content = new Content();

				content.setTipo(JsonToObject.getTipo(jsonNode));

				content.setDataEntrada(JsonToObject.getDataEntrada(jsonNode));

				content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

				content.setProcesso(JsonToObject.getProcesso(jsonNode));

				content.setPeticao(JsonToObject.getPeticao(jsonNode));

				content.setArea(JsonToObject.getArea(jsonNode));
				
				content.setProduto(JsonToObject.getProduto(jsonNode));

				rootObjectProcesso.getContent().add(content);

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

		//queryRecordParameter.setCnpj("55323448000138");

			url = new Request.Builder().url(validParameterProduct(queryRecordParameter.getCnpj(), queryRecordParameter.getNumberProcess(), queryRecordParameter.getRegisterNumber(),
					queryRecordParameter.getProdutoName(), queryRecordParameter.getCategory(), queryRecordParameter.getBrand())).get().addHeader("authorization", "Guest").build();


		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				Content content = new Content();

				content.setOrdem(JsonToObject.getOrdem(jsonNode));

				content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

				content.setProcesso(JsonToObject.getProcessoProduto(jsonNode));

				content.setProduto(JsonToObject.getProduto(jsonNode));

				rootObject.getContent().add(content);

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

	public static String validParameterProcess(String url, QueryRecordParameter queryRecordParameter) {

		if (queryRecordParameter.getCnpj() != null && !queryRecordParameter.getCnpj().isEmpty()) {
			url = url + "&filter[cnpj]=" + queryRecordParameter.getCnpj();
		}

/*		if (area != null) {
			url = url + "&filter[area]=" + area.getId();
		}*/

		return url;
	}

	public static String validParameterProduct(String cnpj, String numeroProcesso, String numeroRegistro,
			String nomeProduto, Long categoria, String marca) {
		
		String url = "";
		
		if (categoria == 0) {
		
			url = URL_FOOD;
		
		} else if (categoria == 1) {

			url = URL_COSMETIC;
			
		} else if (categoria == 2) {

			url = URL_SANEANTE;
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
			url = url + "&filter[nomeProduto]=" + nomeProduto;
		}

		/*if (categoria != null) {
			url = url + "&filter[categoria]=" + categoria;
		}*/

		if (marca != null && !marca.isEmpty()) {
			url = url + "&filter[marca]=" + marca;
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
