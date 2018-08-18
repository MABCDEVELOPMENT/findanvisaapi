package com.anvisa.rest.detalhe.comestico.registrado;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ContentDetalheCosmeticoRegistrado {
	
	String razaoSocial;
	String cnpj;
	String autorizacao;
	String nomeProduto;
	String categoria;
	String processo;
	String vencimentoRegistro;
	String publicacaoRgistro;
	ArrayList<ApresentacaoCosmeticoRegistrado> apresentacoes;
	ArrayList<PeticaoCosmeticoRegistrado> peticoes;
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
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
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getVencimentoRegistro() {
		return vencimentoRegistro;
	}
	public void setVencimentoRegistro(String vencimentoRegistro) {
		this.vencimentoRegistro = vencimentoRegistro;
	}
	public String getPublicacaoRgistro() {
		return publicacaoRgistro;
	}
	public void setPublicacaoRgistro(String publicacaoRgistro) {
		this.publicacaoRgistro = publicacaoRgistro;
	}
	public ArrayList<ApresentacaoCosmeticoRegistrado> getApresentacoes() {
		return apresentacoes;
	}
	public void setApresentacoes(ArrayList<ApresentacaoCosmeticoRegistrado> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}
	public ArrayList<PeticaoCosmeticoRegistrado> getPeticoes() {
		return peticoes;
	}
	public void setPeticoes(ArrayList<PeticaoCosmeticoRegistrado> peticoes) {
		this.peticoes = peticoes;
	}
	
	public void setApresentacoes(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		ArrayList<ApresentacaoCosmeticoRegistrado> apresentacoes = new ArrayList<ApresentacaoCosmeticoRegistrado>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					ApresentacaoCosmeticoRegistrado apresentacaoCosmeticoRegistrado = new ApresentacaoCosmeticoRegistrado();
					
					apresentacaoCosmeticoRegistrado.setCodigo(JsonToObject.getValue(nodeIt,"codigo"));
					apresentacaoCosmeticoRegistrado.setNumero(JsonToObject.getValue(nodeIt,"numero"));
					apresentacaoCosmeticoRegistrado.setEmbalagemPrimaria(JsonToObject.getValue(nodeIt,"embalagemPrimaria"));
					apresentacaoCosmeticoRegistrado.setEmbalagemSecundaria(JsonToObject.getValue(nodeIt,"embalagemSecundaria"));
					apresentacaoCosmeticoRegistrado.setTonalidade(JsonToObject.getValue(nodeIt,"tonalidade"));
					apresentacaoCosmeticoRegistrado.setRegistro(JsonToObject.getValue(nodeIt, "registro", "registro"));
					apresentacaoCosmeticoRegistrado.setSituacao(JsonToObject.getValue(nodeIt, "registro", "situacao"));
					apresentacoes.add(apresentacaoCosmeticoRegistrado);
					
				}
			
			
			
		}
		
		this.setApresentacoes(apresentacoes);
		
	}
	
	public void setPeticoes(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		ArrayList<PeticaoCosmeticoRegistrado> peticoes = new ArrayList<PeticaoCosmeticoRegistrado>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					PeticaoCosmeticoRegistrado peticaoCosmeticoRegistrado = new PeticaoCosmeticoRegistrado();
					
					peticaoCosmeticoRegistrado.setExpediente(JsonToObject.getValue(nodeIt,"expediente"));
					peticaoCosmeticoRegistrado.setPublicacao(JsonToObject.getValue(nodeIt,"publicacao"));
					peticaoCosmeticoRegistrado.setTransacao(JsonToObject.getValue(nodeIt,"transacao"));
					
					String assunto = JsonToObject.getValue(nodeIt, "assunto", "codigo")+" "+JsonToObject.getValue(nodeIt, "assunto", "descricao");
					peticaoCosmeticoRegistrado.setAssunto(assunto);
					
					String situacao = JsonToObject.getValue(nodeIt, "situacao", "situacao")+" "+JsonToObject.getValue(nodeIt, "situacao", "data");
					peticaoCosmeticoRegistrado.setSituacao(situacao);
					
					peticoes.add(peticaoCosmeticoRegistrado);
					
				}
			
			
			
		}
		
		this.setPeticoes(peticoes);
		
	}
}
