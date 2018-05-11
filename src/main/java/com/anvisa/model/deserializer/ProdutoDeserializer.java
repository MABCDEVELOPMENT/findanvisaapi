package com.anvisa.model.deserializer;

import java.io.IOException;
import java.util.Date;

import com.anvisa.model.Categoria;
import com.anvisa.model.Produto;
import com.anvisa.model.Tipo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ProdutoDeserializer extends JsonDeserializer<Produto> {

	@Override
	public Produto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		Produto produto = new Produto();

		final int codigo = node.get("codigo").asInt();
		produto.setCodigo(codigo);

		final String nome = node.get("nome").asText();
		produto.setNome(nome);

		final String numeroRegistro = node.get("nome").asText();
		produto.setNumeroRegistro(numeroRegistro);

		final Tipo tipo;
		final Categoria categoria;

		final String situacaoRotulo = node.get("situacaoRotulo").asText();
		produto.setSituacaoRotulo(situacaoRotulo);

		final Date dataVencimento;
		final String mesAnoVencimento;
		final Date dataVencimentoRegistro;

		final String principioAtivo = node.get("principioAtivo").asText();
		produto.setPrincipioAtivo(principioAtivo);

		final String situacaoApresentacao = node.get("situacaoApresentacao").asText();
		produto.setSituacaoApresentacao(situacaoApresentacao);

		final Date dataRegistro;
		final String numeroRegistroFormatado = node.get("numeroRegistroFormatado").asText();
		produto.setNumeroRegistroFormatado(numeroRegistroFormatado);

		final String mesAnoVencimentoFormatado = node.get("mesAnoVencimentoFormatado").asText();
		produto.setMesAnoVencimento(mesAnoVencimentoFormatado);

		final boolean acancelar = node.get("acancelar").asBoolean();
		produto.setAcancelar(acancelar);

		return produto;

	}

}
