package com.anvisa.interceptor.synchronizedata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

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
		
		client.newBuilder().readTimeout(30, TimeUnit.MINUTES);
		
		
		Request url = null;


		url = new Request.Builder()
				.url(URL+cnpj)
				.get()
				.addHeader("Accept-Encoding", "gzip")
				.addHeader("authorization", "Guest").build();
		       
		
		try {
			
			Response response = client.newCall(url).execute();
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));
			
			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();
			log.info("SynchronizeData Total Registros "+rootNode.get("totalElements"), dateFormat.format(new Date()));
			int i = 0;
			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();
				
				BaseEntity BaseEntity = intSynchronize.parseData(jsonNode);
	
				rootObject.add(BaseEntity);	

				//System.out.println(i++);
			}
			response.close();
			client = null;
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
				.get()
				.addHeader("authorization", "Guest")
				.addHeader("Accept-Encoding", "gzip").build();
		
		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));
			
			if (rootNode!=null) {

				rootObject = intSynchronize.parseDetailData(rootNode);
			}
			response.close();
			client = null;
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
	   }
		
		return null;
	}
	
	
	public String getGZIPString(InputStream byteStream) {

		GZIPInputStream gzipStream;

		String json = "";

		try {
			gzipStream = new GZIPInputStream(byteStream);

			Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);

			BufferedReader reader = new BufferedReader(decoder);

			while (true) {

				String line = reader.readLine();

				json += line;

				if (line == null) {
					break;
				}

			}
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}

}
