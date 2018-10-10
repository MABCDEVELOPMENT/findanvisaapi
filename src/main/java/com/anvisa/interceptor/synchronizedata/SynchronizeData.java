package com.anvisa.interceptor.synchronizedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SynchronizeData {
	
	public String URL = "";
	
	public String URL_DETAIL = "";
	
	private static final Logger log = LoggerFactory.getLogger(SynchronizeDataTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public ArrayList<BaseEntity> loadData(IntSynchronize intSynchronize,String cnpj) {
		// TODO Auto-generated method stub
		ArrayList<BaseEntity> rootObject = new ArrayList<BaseEntity>();

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
			log.info("SynchronizeData Total Registros "+rootNode.get("totalElements"), dateFormat.format(new Date()));
			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();
				
				BaseEntity BaseEntity = intSynchronize.parseData(jsonNode);
	
				rootObject.add(BaseEntity);	

				
			}
			response.close();
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
		
		return null;
	}
	
	public BaseEntity loadDetailData(IntSynchronize intSynchronize,String concat) {
		// TODO Auto-generated method stub
		BaseEntity rootObject = null;
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
