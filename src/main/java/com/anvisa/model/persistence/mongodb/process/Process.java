package com.anvisa.model.persistence.mongodb.process;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Document(collection = "process")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Process extends BaseEntityMongoDB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field(value = "ordem")
	int ordem;
	
	@Field(value = "cnpj")
	String cnpj;
	
	@Field(value = "razaoSocial")
	String razaoSocial;
	

	@Field(value = "processo")
	String processo;
	
	@Field(value = "assunto")
	String assunto;
	
	@Field(value = "processDetail")
	ProcessDetail processDetail;
	
	@Field(value = "dataAlteracao")	
	@JsonFormat(pattern="dd/MM/yyyy")	
	LocalDate dataAlteracao;
	
	@Field(value = "dataRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataRegistro;
	
	@Field(value = "qtdRegistro")	
	int qtdRegistro;

	public Process() {
		// TODO Auto-generated constructor stub
	}
	
	@PersistenceConstructor
	public Process(int ordem, String cnpj, String razaoSocial, String processo, String assunto,
			ProcessDetail processDetail, LocalDate dataAlteracao, LocalDate dataRegistro, int qtdRegistro) {
		super();
		this.ordem = ordem;
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.processo = processo;
		this.assunto = assunto;
		this.processDetail = processDetail;
		this.dataAlteracao = dataAlteracao;
		this.dataRegistro = dataRegistro;
		this.qtdRegistro = qtdRegistro;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public ProcessDetail getProcessDetail() {
		if (processDetail==null) {
			return new ProcessDetail();
		}
		return processDetail ;
	}

	public void setProcessDetail(ProcessDetail processDetail) {
		this.processDetail = processDetail;
	}

	public LocalDate getDataAlteracao() {
		if(this.dataAlteracao!=null && this.dataRegistro!=null) {
			if (this.dataAlteracao.isBefore(this.dataRegistro)) {
				return this.dataRegistro;
			}
		}
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public int getQtdRegistro() {
		return qtdRegistro;
	}

	public void setQtdRegistro(int qtdRegistro) {
		this.qtdRegistro = qtdRegistro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((dataAlteracao == null) ? 0 : dataAlteracao.hashCode());
		result = prime * result + ((dataRegistro == null) ? 0 : dataRegistro.hashCode());
		result = prime * result + ordem;
		result = prime * result + ((processDetail == null) ? 0 : processDetail.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + qtdRegistro;
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
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
		if (!(obj instanceof Process)) {
			return false;
		}
		Process other = (Process) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null)
				return false;
		} else if (!dataAlteracao.equals(other.dataAlteracao))
			return false;
		if (dataRegistro == null) {
			if (other.dataRegistro != null)
				return false;
		} else if (!dataRegistro.equals(other.dataRegistro))
			return false;
		if (ordem != other.ordem)
			return false;
		if (processDetail == null) {
			if (other.processDetail != null)
				return false;
		} else if (!processDetail.equals(other.processDetail))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (qtdRegistro != other.qtdRegistro)
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		return true;
	}

	
	public void lodaDateProcess() {
		
		this.setDataAlteracao(null);
		
		ProcessDetail detail = this.getProcessDetail();
		
		List<ProcessPetition> peticoes = detail.getPeticoes();
		
		for (ProcessPetition processPetition : peticoes) {
			
			if (this.getDataAlteracao() != null
					&& processPetition.getDataPublicacao().isAfter(this.getDataAlteracao())) {
				this.setDataAlteracao(processPetition.getDataPublicacao());
			} else {
				if (this.getDataAlteracao() == null)
				   this.setDataAlteracao(processPetition.getDataPublicacao());
			}
			
		}
		
		this.setQtdRegistro(peticoes.size());
		try {
			
			if (this.getDataAlteracao()==null) {
				
				this.setDataAlteracao(detail.getProcesso().getPeticao().getDataPublicacao());
				
			}
			
		    this.setDataRegistro(detail.getProcesso().getPeticao().getDataEntrada());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(" lodaDateProcess() Process CNPJ "+this.getCnpj()+" Processo "+this.getProcesso()+" ERRO DE DATAS");
		}
	}
	
	
	
}
