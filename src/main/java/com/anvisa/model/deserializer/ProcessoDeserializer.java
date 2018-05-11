package com.anvisa.model.deserializer;

import java.io.IOException;

import com.anvisa.model.Processo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ProcessoDeserializer extends JsonDeserializer<Processo> {

	@Override
	public Processo deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		final String numero = node.get("numero").asText();
		final boolean ativo = node.get("ativo").asBoolean();
		final String numeroProcessoFormatado = node.get("numeroProcessoFormatado").asText();

		return null;
	}
}
