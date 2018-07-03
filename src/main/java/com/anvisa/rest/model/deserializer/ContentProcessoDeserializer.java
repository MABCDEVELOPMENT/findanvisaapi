package com.anvisa.rest.model.deserializer;

import java.io.IOException;

import com.anvisa.rest.ContentProcesso;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ContentProcessoDeserializer extends JsonDeserializer<ContentProcesso> {

	@Override
	public ContentProcesso deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		final String strOrdem = node.get("ordem").asText();
		final String strEmpresa = node.get("empresa").asText();
		final String strProduto = node.get("produto").asText();
		final String strProcesso = node.get("processo").asText();

		return null;
	}
}
