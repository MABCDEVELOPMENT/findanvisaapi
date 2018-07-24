package com.anvisa.core.json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

import com.anvisa.rest.model.Assunto;
import com.anvisa.rest.model.Categoria;
import com.anvisa.rest.model.Empresa;
import com.anvisa.rest.model.Peticao;
import com.anvisa.rest.model.Processo;
import com.anvisa.rest.model.Produto;
import com.anvisa.rest.model.Tipo;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonToObject {

	public static Tipo getTipo(JsonNode node) {

		Tipo tipo = null;

		JsonNode element = node.findValue("tipo");

		if (element!=null) {

			tipo = new Tipo(element.get("codigo").asInt(), element.get("descricao").asText());

		}

		return tipo;
	}

	public static Date getDataEntrada(JsonNode node) {

		Date dataEntrada = null;

		JsonNode element = node.findValue("dataEntrada");

		if (element!=null) {

			dataEntrada = new Date(element.asText());

		}

		return dataEntrada;
	}

	public static Empresa getEmpresa(JsonNode node) {

		Empresa empresa = null;

		JsonNode element = node.findValue("empresa");

		if (element!=null) {

			empresa = new Empresa(element.get("cnpj").asText(), element.get("razaoSocial").asText(), null, element.get("cnpjFormatado").asText());

		}

		return empresa;
	}

	public static Processo getProcesso(JsonNode node) {

		Processo processo = null;

		JsonNode element = node.findValue("processo");

		if (element!=null) {

			processo = new Processo(element.get("numero").asText(), element.get("ativo").asBoolean());

		}

		return processo;
	}

	public static Processo getProcessoProduto(JsonNode node) {

		Processo processo = null;

		JsonNode element = node.findValue("processo");

		if (element!=null) {
			String situacao = element.get("situacao").asText();
			if ("29".equals(situacao)) {
				situacao = "Publicado Deferimento";
			} else if ("31".equals(situacao)) {
				situacao = "";
			}
			processo = new Processo(element.get("numero").asText(), situacao,
					element.get("numeroProcessoFormatado").asText());

		}

		return processo;
	}

	public static Peticao getPeticao(JsonNode node) {

		Peticao peticao = null;

		JsonNode element = node.findValue("peticao");

		if (element!=null) {

			JsonNode elementAssunto = node.findValue("assunto");

			Assunto assunto = new Assunto(elementAssunto.get("codigo").asText(),
					elementAssunto.get("descricao").asText());

			peticao = new Peticao(element.get("expediente").asText(), element.get("protocolo").asText(),
					element.get("remetente").asText(), assunto);

		}

		return peticao;
	}

	public static Assunto getAssunto(JsonNode node) {
		
		JsonNode element = node.findValue("assunto");
		
		Assunto assunto = null;
		
		if (element!=null) {
			assunto = new Assunto(element.get("codigo").asText(),
				element.get("descricao").asText());
		}	
		
		return assunto;
		
	}
	
	public static String getArea(JsonNode node) {

		String area = null;

		JsonNode element = node.findValue("area");

		if (element!=null) {

			area = element.get("area").asText();

		}

		return area;
	}

	public static int getOrdem(JsonNode node) {

		int ordem = 0;

		JsonNode element = node.findValue("ordem");

		if (element!=null) {

			ordem = element.asInt();

		}

		return ordem;
	}

	public static Produto getProduto(JsonNode node) {

		Produto produto = null;

		JsonNode element = node.findValue("produto");

		//DateTimeFormatter formatterBR = DateTimeFormatter.ofPattern("dd/mm/yyyy").withLocale(locale);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(
			        FormatStyle.MEDIUM).withLocale(Locale.getDefault());

		LocalDate date;
		
		if (element!=null) {

			produto = new Produto();

			produto.setCodigo(element.get("codigo").asInt());
			produto.setNome(element.get("nome").asText());
			produto.setNumeroRegistro(element.get("numeroRegistro").asText());
			produto.setNumeroRegistroFormatado(element.get("numeroRegistroFormatado").asText());
			produto.setTipo(getTipo(element));
			produto.setCategoria(getCategoria(element));
			produto.setSituacaoRotulo(element.get("situacaoRotulo").asText());

			try {
				
				if (!element.get("dataVencimento").isNull()) {
	
					LocalDate dataRegistro = LocalDate.parse(element.get("dataVencimento").asText().substring(0, 10));
					produto.setDataVencimento(dataRegistro);
					
				}
			
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			produto.setMesAnoVencimento(element.get("mesAnoVencimento").asText());

			try {

				if (!element.get("dataVencimentoRegistro").isNull()) {
					
					LocalDate dataVencimentoRegistro = LocalDate.parse(element.get("dataVencimentoRegistro").asText().substring(0,10));
					produto.setDataVencimentoRegistro(dataVencimentoRegistro);

					/*DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				    String text = dataVencimentoRegistro.format(formatters);
				    LocalDate parsedDate = LocalDate.parse(text, formatters);*/
					
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
				
			produto.setPrincipioAtivo(element.get("principioAtivo").asText());
			produto.setSituacaoApresentacao(element.get("situacaoApresentacao").asText());
			
			try {
			
				if (!element.get("dataRegistro").isNull()) {
	
					LocalDate dataRegistro = LocalDate.parse(element.get("dataRegistro").asText().substring(0, 10));
					produto.setDataRegistro(dataRegistro);
					produto.setAno(element.get("dataRegistro").asText().substring(0,4));
					
				}
			
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
			
			produto.setNumeroRegistroFormatado(element.get("numeroRegistroFormatado").asText());
			produto.setMesAnoVencimentoFormatado(element.get("mesAnoVencimentoFormatado").asText());
			produto.setAcancelar(element.get("acancelar").asBoolean());

		}

		return produto;
	}

	
	
	public static Categoria getCategoria(JsonNode node) {

		Categoria categoria = null;

		JsonNode element = node.findValue("categoria");

		if (element!=null) {

			categoria = new Categoria(element.get("codigo").asText(), element.get("descricao").asText());

		}

		return categoria;
	}
	
	public static String getValue(JsonNode node,String attribute) {
		JsonNode element = node.findValue(attribute);
		if (element!=null) {
			return element.asText();
		}
		return null;
	}
	
	public static String getValue(JsonNode node,String content,String attribute) {
		JsonNode element = node.findValue(content);
		if (element!=null) {
			return element.get(attribute).asText();
		}
		return null;
	}

	public static LocalDate getValueDate(JsonNode node,String attribute) {
		JsonNode element = node.findValue(attribute);
		if (element!=null) {
			try {
				LocalDate date = LocalDate.parse(element.asText().substring(0, 10));
				return date;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	
	public static LocalDate getValueDate(JsonNode node,String content,String attribute) {
		JsonNode element = node.findValue(content);
		if (element!=null) {
			try {
				LocalDate date = LocalDate.parse(element.get(attribute).asText().substring(0, 10));
				return date;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	
	

}
