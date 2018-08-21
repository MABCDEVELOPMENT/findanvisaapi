package com.anvisa.rest.detalhe.processo;

import java.util.ArrayList;
import java.util.Iterator;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ContentProcessoDetalhe {
	
	ContentProcessoDetalhe detalhe;
	
	ProcessoEmpresaDetalhe empresa;
	
	ProcessoDetalhe processo;
	
	ArrayList<ProcessoPeticaoDetalhe> peticoes;

	private ContentProcessoDetalhe processoDetalhe;
	
	public ProcessoEmpresaDetalhe getEmpresa() {
		return empresa;
	}
	public void setEmpresa(ProcessoEmpresaDetalhe empresa) {
		this.empresa = empresa;
	}
	public ProcessoDetalhe getProcesso() {
		return processo;
	}
	public void setProcesso(ProcessoDetalhe processo) {
		this.processo = processo;
	}
	public ArrayList<ProcessoPeticaoDetalhe> getPeticoes() {
		return peticoes;
	}
	public void setPeticoes(ArrayList<ProcessoPeticaoDetalhe> peticoes) {
		this.peticoes = peticoes;
	}
	
	public void setEmpresa(JsonNode node,String attribute) {
			
			
		JsonNode element = (JsonNode) node.findValue(attribute);
		ProcessoEmpresaDetalhe empresa = new ProcessoEmpresaDetalhe();

		if (element != null) {

			empresa.setCnpj(JsonToObject.getValue(node, "cnpj"));
			empresa.setRazaoSocial(JsonToObject.getValue(node, "razaoSocial"));
		}

		this.setEmpresa(empresa);


	
	}


    public void setProcesso(JsonNode node,String attribute) {
    	
		JsonNode element = (JsonNode) node.findValue(attribute);
		ProcessoDetalhe processo = new ProcessoDetalhe();

		if (element != null) {

			processo.setNumero(JsonToObject.getValue(node, "numero"));
			ProcessoPeticaoDetalhe peticao = ProcessoPeticaoDetalhe.getPeticao(node, "peticao");
			processo.setPeticao(peticao);
		}

		this.setProcesso(processo);
    	
    }
    
    public void setPeticoes(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		ArrayList<ProcessoPeticaoDetalhe> peticoes = new ArrayList<ProcessoPeticaoDetalhe>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					ProcessoPeticaoDetalhe peticao = ProcessoPeticaoDetalhe.getPeticao(nodeIt, "peticao");
					
					peticoes.add(peticao);
					
				}
			
			
			
		}
		
		this.setPeticoes(peticoes);
		
	}
    
    public ContentProcessoDetalhe build(JsonNode node) {
    	
    	if (processoDetalhe==null) {
    		processoDetalhe = new ContentProcessoDetalhe();
    	}

        
    	this.setEmpresa(node,"empresa");
    	this.setProcesso(node, "processo");
    	this.setPeticoes(node,"peticoes");
    	return this.processoDetalhe;
    	
    }
    

 }	
