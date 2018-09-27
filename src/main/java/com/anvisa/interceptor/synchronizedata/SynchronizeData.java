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
	
	public String URL = "";
	
	public String URL_DETAIL = "";
	
	public ArrayList<AbstractBaseEntity> loadData(IntSynchronize intSynchronize,String cnpj) {
		// TODO Auto-generated method stub
		ArrayList<AbstractBaseEntity> rootObject = new ArrayList<AbstractBaseEntity>();

		OkHttpClient client = new OkHttpClient();
		
		Request url = null;


		url = new Request.Builder()
				.url(URL+cnpj)
				.get().addHeader("authorization", "Guest").build();
		
		try {
			
			Response response = client.newCall(url).execute();
			
			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());

			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();

			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();
				
				AbstractBaseEntity abstractBaseEntity = intSynchronize.parseData(jsonNode);
	
				rootObject.add(abstractBaseEntity);	

				
			}
			response.close();
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
		
		return null;
	}
	
	public AbstractBaseEntity loadDetailData(IntSynchronize intSynchronize,String concat) {
		// TODO Auto-generated method stub
		AbstractBaseEntity rootObject = null;
		OkHttpClient client = new OkHttpClient();

		Request url = null;


		url = new Request.Builder()
				.url(URL_DETAIL+concat)
				.get().addHeader("authorization", "Guest").build();
		
		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(response.body().string());
			
			if (rootNode!=null) {

				rootObject = intSynchronize.parseDetailData(rootNode);
			}
			response.close();
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
		
		return null;
	}

}
