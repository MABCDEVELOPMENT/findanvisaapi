package com.anvisa.core.json;

import java.util.Date;

import com.anvisa.model.Assunto;
import com.anvisa.model.Categoria;
import com.anvisa.model.Empresa;
import com.anvisa.model.Peticao;
import com.anvisa.model.Processo;
import com.anvisa.model.Produto;
import com.anvisa.model.Tipo;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonToObject {

	public static Tipo getTipo(JsonNode node) {

		Tipo tipo = null;

		JsonNode element = node.findValue("tipo");

		if (!element.isNull()) {

			tipo = new Tipo(element.get("codigo").asInt(), element.get("descricao").asText());

		}

		return tipo;
	}

	public static Date getDataEntrada(JsonNode node) {

		Date dataEntrada = null;

		JsonNode element = node.findValue("dataEntrada");

		if (!element.isNull()) {

			dataEntrada = new Date(element.asText());

		}

		return dataEntrada;
	}

	public static Empresa getEmpresa(JsonNode node) {

		Empresa empresa = null;

		JsonNode element = node.findValue("empresa");

		if (!element.isNull()) {

			empresa = new Empresa(element.get("cnpj").asText(), element.get("razaoSocial").asText(), null, null);

		}

		return empresa;
	}

	public static Processo getProcesso(JsonNode node) {

		Processo processo = null;

		JsonNode element = node.findValue("processo");

		if (!element.isNull()) {

			processo = new Processo(element.get("numero").asText(), element.get("ativo").asBoolean());

		}

		return processo;
	}

	public static Processo getProcessoProduto(JsonNode node) {

		Processo processo = null;

		JsonNode element = node.findValue("processo");

		if (!element.isNull()) {

			processo = new Processo(element.get("numero").asText(), element.get("situacao").asText(),
					element.get("numeroProcessoFormatado").asText());

		}

		return processo;
	}

	public static Peticao getPeticao(JsonNode node) {

		Peticao peticao = null;

		JsonNode element = node.findValue("peticao");

		if (!element.isNull()) {

			JsonNode elementAssunto = node.findValue("assunto");

			Assunto assunto = new Assunto(elementAssunto.get("codigo").asText(),
					elementAssunto.get("descricao").asText());

			peticao = new Peticao(element.get("expediente").asText(), element.get("protocolo").asText(),
					element.get("remetente").asText(), assunto);

		}

		return peticao;
	}

	public static String getArea(JsonNode node) {

		String area = null;

		JsonNode element = node.findValue("area");

		if (!element.isNull()) {

			area = element.get("area").asText();

		}

		return area;
	}

	public static int getOrdem(JsonNode node) {

		int ordem = 0;

		JsonNode element = node.findValue("ordem");

		if (!element.isNull()) {

			ordem = element.asInt();

		}

		return ordem;
	}

	public static Produto getProduto(JsonNode node) {

		Produto produto = null;

		JsonNode element = node.findValue("produto");

		if (!element.isNull()) {

			produto = new Produto();

			produto.setCodigo(element.get("codigo").asInt());
			produto.setNome(element.get("nome").asText());
			produto.setNumeroRegistro(element.get("numeroRegistro").asText());
			produto.setNumeroRegistroFormatado(element.get("numeroRegistroFormatado").asText());
			produto.setTipo(getTipo(element));
			produto.setCategoria(getCategoria(element));
			produto.setSituacaoRotulo(element.get("situacaoRotulo").asText());

			/*
			 * if (!element.get("dataVencimento").isNull()) produto.setDataVencimento(new
			 * Date(element.get("dataVencimento").asText()));
			 */

			produto.setMesAnoVencimento(element.get("mesAnoVencimento").asText());

			/*
			 * if (!element.get("dataVencimentoRegistro").isNull())
			 * produto.setDataVencimentoRegistro(new
			 * Date(element.get("dataVencimentoRegistro").asText()));
			 */

			produto.setPrincipioAtivo(element.get("principioAtivo").asText());
			produto.setSituacaoApresentacao(element.get("situacaoApresentacao").asText());

			/*
			 * if (!element.get("dataRegistro").isNull()) produto.setDataRegistro(new
			 * Date(element.get("dataRegistro").asText()));
			 */
			produto.setNumeroRegistroFormatado(element.get("numeroRegistroFormatado").asText());
			produto.setMesAnoVencimentoFormatado(element.get("mesAnoVencimentoFormatado").asText());
			produto.setAcancelar(element.get("acancelar").asBoolean());

		}

		return produto;
	}

	public static Categoria getCategoria(JsonNode node) {

		Categoria categoria = null;

		JsonNode element = node.findValue("categoria");

		if (!element.isNull()) {

			categoria = new Categoria(element.get("codigo").asText(), element.get("descricao").asText());

		}

		return categoria;
	}

}
