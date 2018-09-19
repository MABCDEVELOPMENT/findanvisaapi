package com.anvisa.rest.detalhe.comestico.registrado.detalhe.peticao;

import java.time.LocalDate;
import java.util.ArrayList;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.rest.detalhe.comestico.registrado.detalhe.apresentacao.RegistradoApresentacaoFabricantesNacionais;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class DetalheCosmeticoRegistradoPeticao {

	private String razaoSocial;
	private String autorizacao;
	private String cnpj;
	
	private String nomeProduto;
	private String categoria;
	
	private String registro;
	private String peticao;
	private LocalDate vencimento;
	

	private ArrayList<RegistradoPeticaoFabricantesNacionais> fabricantesNacionais;
	private ArrayList<RegistradoPeticaoApresentacao> apresentacoes;
	
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getPeticao() {
		return peticao;
	}

	public void setPeticao(String peticao) {
		this.peticao = peticao;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}


	public ArrayList<RegistradoPeticaoFabricantesNacionais> getFabricantesNacionais() {
		return fabricantesNacionais;
	}

	public void setFabricantesNacionais(ArrayList<RegistradoPeticaoFabricantesNacionais> fabricantesNacionais) {
		this.fabricantesNacionais = fabricantesNacionais;
	}

	public ArrayList<RegistradoPeticaoApresentacao> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(ArrayList<RegistradoPeticaoApresentacao> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}



	public void load(JsonNode node, String attribute) {

		JsonNode element = node.findValue(attribute);

		if (element != null) {

			this.setRazaoSocial(JsonToObject.getValue(element,"empresa","razaoSocial"));
			this.setCnpj(JsonToObject.getValue(element,"empresa","cnpj"));
			this.setAutorizacao(JsonToObject.getValue(element,"empresa","autorizacao"));
			
			this.setNomeProduto(JsonToObject.getValue(element, "nomeProduto"));
			this.setCategoria(JsonToObject.getValue(element, "categoria"));
			
			this.setRegistro(JsonToObject.getValue(node, "registro"));
			this.setPeticao(JsonToObject.getValue(node, "expediente"));
			this.setVencimento(JsonToObject.getValueDate(node,"vencimento" ,"vencimento"));

			ArrayList<RegistradoPeticaoFabricantesNacionais> fabricantes = new ArrayList<RegistradoPeticaoFabricantesNacionais>();

			ArrayNode elementLocalFabricacao = (ArrayNode) node.findValue("fabricantesNacionais");

			if (elementLocalFabricacao != null) {

				for (JsonNode jsonNode : elementLocalFabricacao) {

					RegistradoPeticaoFabricantesNacionais fabricante = new RegistradoPeticaoFabricantesNacionais();

					fabricante.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
					fabricante.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
					fabricante.setCidade(JsonToObject.getValue(jsonNode, "cidade"));
					fabricante.setUf(JsonToObject.getValue(jsonNode, "uf"));
					fabricante.setPais(JsonToObject.getValue(jsonNode, "pais"));
					fabricante.setTipo(JsonToObject.getValue(jsonNode, "tipo"));

					fabricantes.add(fabricante);
				}

			}

			this.setFabricantesNacionais(fabricantes);

			
			ArrayList<RegistradoPeticaoApresentacao> registradoPeticaoApresentacao = new ArrayList<RegistradoPeticaoApresentacao>();

			ArrayNode elementApresentacao = (ArrayNode) node.get("apresentacoes");

			if (elementApresentacao != null) {

				for (JsonNode jsonNode : elementApresentacao) {

					RegistradoPeticaoApresentacao apresentacao = new RegistradoPeticaoApresentacao();

					apresentacao.setNome(JsonToObject.getValue(jsonNode, "apresentacao"));
					apresentacao.setEmbalagemPrimaria(JsonToObject.getValue(jsonNode, "embalagemPrimaria"));
					apresentacao.setEmbalagemSecundaria(JsonToObject.getValue(jsonNode, "embalagemSecundaria"));
					apresentacao.setFormaFisica(JsonToObject.getValue(jsonNode, "formaFisica"));
					apresentacao.setNumero(JsonToObject.getValue(jsonNode, "numero"));
					apresentacao.setPrazoValidade(JsonToObject.getValue(jsonNode, "prazoValidade"));
					apresentacao.setRegistro(JsonToObject.getValue(jsonNode, "registro"));
					apresentacao.setTipoValidade(JsonToObject.getValue(jsonNode, "tipoValidade"));
					apresentacao.setTonalidade(JsonToObject.getValue(jsonNode, "tonalidade"));

					registradoPeticaoApresentacao.add(apresentacao);
				}

			}

			this.setApresentacoes(registradoPeticaoApresentacao);

		}

	}

}
