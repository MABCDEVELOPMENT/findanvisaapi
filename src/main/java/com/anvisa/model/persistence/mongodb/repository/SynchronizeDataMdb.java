package com.anvisa.model.persistence.mongodb.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.inject.Inject;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.anvisa.interceptor.synchronizedata.SynchronizeDataTask;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.loggerprocessing.LogErroProcessig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SynchronizeDataMdb {
	
	public String SEQ_KEY = "";

	@Autowired
	public SequenceRepositoryMdb sequence;
	
	public String URL = "";
	
	public String URL_DETAIL = "";
	
	public static final Logger log = LoggerFactory.getLogger("SynchronizeData");

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Inject
	public static MongoTemplate mongoTemplate;

	
	public ArrayList<Document> loadDataDocument(IntSynchronizeMdb intSynchronize,String processo) {
		
		Gson gson = new Gson();
		
		ArrayList<Document>  documents = new ArrayList<Document>();
		
		ArrayList<BaseEntityMongoDB>  data = loadData(intSynchronize,processo);
		for (BaseEntityMongoDB baseEntityMongoDB : data) {
			Document document = Document.parse(gson.toJson(baseEntityMongoDB));
			documents.add(document);
		} 
		
		return documents;
	}
	
	public ArrayList<BaseEntityMongoDB> loadData(IntSynchronizeMdb intSynchronize,String cnpj) {
		// TODO Auto-generated method stub
		ArrayList<BaseEntityMongoDB> rootObject = new ArrayList<BaseEntityMongoDB>();

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
			
			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(cnpj,response.body().byteStream()));
			
			Iterator<JsonNode> elementsContents = rootNode.path("content").iterator();
			
			log.info("SynchronizeData Total Registros "+rootNode.get("totalElements"), dateFormat.format(new Date()));
			
			int i = 0;
			System.out.println("Load iniciando");
			while (elementsContents.hasNext()) {

				JsonNode jsonNode = (JsonNode) elementsContents.next();
				
				BaseEntityMongoDB BaseEntity = intSynchronize.parseData(cnpj,jsonNode);
	
				rootObject.add(BaseEntity);	
				i++;
				if (i>=400) {
					System.out.println(i);
					i=0;
				}
				
			}
			System.out.println("Load finalizando");
			response.close();
			client = null;
			System.gc();
			return rootObject;
			
	   } catch (Exception e) {
		// TODO: handle exception
		   //e.printStackTrace();
			LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(), this.getClass().getName(),this.getClass().getName(), e, LocalDateTime.now());
			mongoTemplate.save(log);
	   }
		
		return null;
	}
	
	public String getGZIPString(String cnpj,InputStream byteStream) {

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
			LogErroProcessig log = new LogErroProcessig(cnpj, "", e.getMessage(), this.getClass().getName(),this.getClass().getName(), e, LocalDateTime.now());
			mongoTemplate.save(log);
		}

		return json;
	}

}
