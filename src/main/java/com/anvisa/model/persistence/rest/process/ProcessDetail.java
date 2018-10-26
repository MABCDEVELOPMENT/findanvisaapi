package com.anvisa.model.persistence.rest.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Entity
@Table(name = "process_Detail")
public class ProcessDetail extends BaseEntity {

	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonAlias(value = "processCompanyDetail")
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name="ProcessCompanyDetailFK")
	ProcessCompanyDetail empresa;
	
	@JsonAlias(value = "processDetailProcess")
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name="ProcessDetailProcessFK")
	ProcessDetailProcess processo;
	
	@OneToMany(cascade=CascadeType.ALL)
	List<ProcessPetition> peticoes;


	
	public ProcessCompanyDetail getEmpresa() {
		return empresa;
	}
	public void setEmpresa(ProcessCompanyDetail empresa) {
		this.empresa = empresa;
	}
	public ProcessDetailProcess getProcesso() {
		return processo;
	}
	public void setProcesso(ProcessDetailProcess processo) {
		this.processo = processo;
	}
	public List<ProcessPetition> getPeticoes() {
		if (peticoes==null) {
			peticoes = new ArrayList<ProcessPetition>();
		}
		return peticoes;
	}
	public void setPeticoes(ArrayList<ProcessPetition> peticoes) {
		this.peticoes = peticoes;
	}
	
	public void setEmpresa(JsonNode node,String attribute) {
			
			
		JsonNode element = (JsonNode) node.findValue(attribute);
		ProcessCompanyDetail empresa = new ProcessCompanyDetail();

		if (element != null) {

			empresa.setCnpj(JsonToObject.getValue(node, "cnpj"));
			empresa.setRazaoSocial(JsonToObject.getValue(node, "razaoSocial"));
		}

		this.setEmpresa(empresa);


	
	}


    public void setProcesso(JsonNode node,String attribute) {
    	
		JsonNode element = (JsonNode) node.findValue(attribute);
		ProcessDetailProcess processo = new ProcessDetailProcess();

		if (element != null) {

			processo.setNumero(JsonToObject.getValue(node, "numero"));
			ProcessPetition peticao = ProcessPetition.getPeticao(node, "peticao");
			processo.setPeticao(peticao);
		}

		this.setProcesso(processo);
    	
    }
    
    public void setPeticoes(JsonNode node,String attribute) {
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		ArrayList<ProcessPetition> peticoes = new ArrayList<ProcessPetition>();
		
		if (element!=null) {
				
		
				
				for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {
					
					JsonNode nodeIt = it.next();
					
					ProcessPetition peticao = ProcessPetition.getPeticao(nodeIt, "peticao");
					
					peticoes.add(peticao);
					
				}
			
			
			
		}
		
		this.setPeticoes(peticoes);
		
	}
    
    public void build(JsonNode node) {
    	
    	this.setEmpresa(node,"empresa");
    	this.setProcesso(node, "processo");
    	this.setPeticoes(node,"peticoes");
    	
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((peticoes == null) ? 0 : peticoes.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProcessDetail)) {
			return false;
		}
		ProcessDetail other = (ProcessDetail) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (peticoes == null) {
			if (other.peticoes != null)
				return false;
		} else if (!peticoes.equals(other.peticoes))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		return true;
	}
    
    
	
}
