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
import com.anvisa.core.type.TypeSearch;
import com.anvisa.core.type.TypeSearchProduct;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlToJson {

	public static String URL_PROCESS = "https://consultas.anvisa.gov.br/api/documento/tecnico?count=1000&page=1";
	public static String URL_FOOD_PRODUCT = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=1000&page=1&filter[cnpj]=valueCnpj&filter[nomeProduto]=valueProduct";
	public static String URL_SANEANTE_PRODUCT = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/[typeSearch]?count=1000&page=1";

	/*
	 * public static void main(String[] args) { findFoodSaneate("55323448000138",
	 * "AVEIA", TypeSearch.FOOD_PRODUCT); }
	 */

	public static RootObjectProcesso findProcess(String cnpj, TypeArea area) {

		RootObjectProcesso rootObjectProcesso = new RootObjectProcesso();

		OkHttpClient client = new OkHttpClient(); //
		// https://consultas.anvisa.gov.br/#/alimentos/q/?nomeProduto=AVEIA Request
		Request request = new Request.Builder().url(validParameterProcess(URL_PROCESS, cnpj, area)).get()
				.addHeader("authorization", "Guest").build();

		try {
			Response response = client.newCall(request).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				ContentProcesso content = new ContentProcesso();

				content.setTipo(JsonToObject.getTipo(jsonNode));

				content.setDataEntrada(JsonToObject.getDataEntrada(jsonNode));

				content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

				content.setProcesso(JsonToObject.getProcesso(jsonNode));

				content.setPeticao(JsonToObject.getPeticao(jsonNode));

				content.setPeticao(JsonToObject.getPeticao(jsonNode));

				content.setArea(JsonToObject.getArea(jsonNode));

				rootObjectProcesso.getContentProcesso().add(content);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObjectProcesso;
	}

	public static RootObjectProduto findFoodSaneate(String cnpj, String fieldValue, TypeSearch typeSearch) {

		RootObjectProduto rootObjectProduto = new RootObjectProduto();

		OkHttpClient client = new OkHttpClient();

		String url = "";

		if (typeSearch.equals(TypeSearch.FOOD_PRODUCT)) {

			url = URL_FOOD_PRODUCT;

		} else {

			url = URL_SANEANTE_PRODUCT;

		}

		url = url.replace("valueCnpj", cnpj).replaceAll("valueProduct", fieldValue);

		Request request = new Request.Builder().url(url).get().addHeader("authorization", "Guest").build();

		try {

			Response response = client.newCall(request).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				ContentProduto content = new ContentProduto();

				content.setOrdem(JsonToObject.getOrdem(jsonNode));

				content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

				content.setProcesso(JsonToObject.getProcessoProduto(jsonNode));

				content.setProduto(JsonToObject.getProduto(jsonNode));

				rootObjectProduto.getContentProduto().add(content);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootObjectProduto;
	}

	public static String validParameterTypeSearchProduct(String url, TypeSearchProduct typeSearchProduct) {

		if (typeSearchProduct != null) {
			url = url + typeSearchProduct.name();
		}

		return url;

	}

	public static String validParameterProcess(String url, String cnpj, TypeArea area) {

		if (cnpj != null && !cnpj.isEmpty()) {
			url = url + "&filter[cnpj]=" + cnpj;
		}

		if (area != null) {
			url = url + "&filter[area]=" + area.getId();
		}

		return url;
	}

	public static String validParameterProduct(String url, String cnpj, String numeroProcesso, String numeroRegistro,
			String nomeProduto, String categoria, String marca) {

		if (cnpj != null && cnpj.isEmpty()) {
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

		if (categoria != null && !categoria.isEmpty()) {
			url = url + "&filter[categoria]=" + categoria;
		}

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
