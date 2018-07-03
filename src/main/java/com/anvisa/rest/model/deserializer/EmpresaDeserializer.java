package com.anvisa.rest.model.deserializer;

import java.io.IOException;

import com.anvisa.rest.model.Empresa;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class EmpresaDeserializer extends JsonDeserializer<Empresa> {

	@Override
	public Empresa deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);

		final String cnpj = node.get("cnpj").asText();
		final String socialReason = node.get("socialReason").asText();
		final String authorizationNumber = node.get("authorizationNumber").asText();
		final String cnpjFormatado = node.get("cnpjFormatado").asText();

		return new Empresa(cnpj, socialReason, authorizationNumber, cnpjFormatado);
	}

}
