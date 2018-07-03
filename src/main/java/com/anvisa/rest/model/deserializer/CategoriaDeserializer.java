package com.anvisa.rest.model.deserializer;

import java.io.IOException;

import com.anvisa.rest.model.Categoria;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CategoriaDeserializer extends JsonDeserializer<Categoria> {

	@Override
	public Categoria deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		final String codigo = node.get("codigo").asText();
		final String descricao = node.get("descricao").asText();

		return new Categoria(codigo, descricao);
	}

}
