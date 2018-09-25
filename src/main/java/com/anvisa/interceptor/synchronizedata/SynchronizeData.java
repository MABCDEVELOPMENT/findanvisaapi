package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.model.persistence.AbstractBaseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SynchronizeData {
	

	public ArrayList<AbstractBaseEntity> loadData(IntSynchronize intSynchronize,String cnpj) {
		// TODO Auto-generated method stub
		ArrayList<AbstractBaseEntity> rootObject = new ArrayList<AbstractBaseEntity>();

		OkHttpClient client = new OkHttpClient();

		Request url = null;


		url = new Request.Builder()
				.url(intSynchronize.URL.replace("[cnpj]", cnpj))
				.get().addHeader("authorization", "Guest").build();
		
		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();

				rootObject.add(intSynchronize.parseData(jsonNode));
			}
			
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
		
		return null;
	}

}
