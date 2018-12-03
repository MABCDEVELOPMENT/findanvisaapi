package com.anvisa.core.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

public class WriteJsonArrayByParts<T> {

    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
        	if (json!=null) {
        	  
        		LocalDate date = LocalDate.parse(json.getAsString().substring(0, 10));
        	
               return date;
        	} else {
        		return null;
        	}
        }

    }).create();
    JsonWriter writer;

    public WriteJsonArrayByParts(String fileNameWithPath, String indent) throws Exception {
        OutputStream os = new FileOutputStream(fileNameWithPath, false);
        BufferedOutputStream osb = new BufferedOutputStream(os, 8 * 1024);

        writer = new JsonWriter(new OutputStreamWriter(osb, Charsets.UTF_8));
        writer.setIndent(indent);
    }

    public void writeStart() throws IOException {
        writer.beginArray();
    }

    @SuppressWarnings("unchecked")
    public void writeObject(T t, Class<?> resultClass) throws IOException {
        ((TypeAdapter<Object>) gson.getAdapter(resultClass)).write(writer, t);
    }

    public void writeEnd() throws IOException {
        writer.endArray();
    }

    public void close() throws IOException {
        writer.close();
    }
}
