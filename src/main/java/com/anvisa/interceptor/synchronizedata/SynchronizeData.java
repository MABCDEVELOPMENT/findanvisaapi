package com.anvisa.interceptor.synchronizedata;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.model.persistence.BaseEntityAudit;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SynchronizeData {
	
	public String URL = "";
	
	public String URL_DETAIL = "";
	
	public ArrayList<BaseEntityAudit> loadData(IntSynchronize intSynchronize,String cnpj) {
		// TODO Auto-generated method stub
		ArrayList<BaseEntityAudit> rootObject = new ArrayList<BaseEntityAudit>();

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
				
				BaseEntityAudit BaseEntityAudit = intSynchronize.parseData(jsonNode);
	
				rootObject.add(BaseEntityAudit);	

				
			}
			response.close();
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
		
		return null;
	}
	
	public BaseEntityAudit loadDetailData(IntSynchronize intSynchronize,String concat) {
		// TODO Auto-generated method stub
		BaseEntityAudit rootObject = null;
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
