package com.anvisa.rest.model.deserializer;

import java.io.IOException;

import com.anvisa.rest.model.Tipo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TipoDeserializer extends JsonDeserializer<Tipo> {

	@Override
	public Tipo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		final int codigo = node.get("codigo").asInt();
		final String descricao = node.get("descricao").asText();

		return new Tipo(codigo, descricao);
	}

}
