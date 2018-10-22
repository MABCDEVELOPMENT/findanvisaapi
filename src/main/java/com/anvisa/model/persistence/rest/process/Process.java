package com.anvisa.model.persistence.rest.process;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "process")
public class Process extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "order")
	@JsonAlias(value = "ordem")
	int ordem;
	
	@Column(name = "cnpj", length = 14)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "social_reason", length = 300)
	@JsonAlias(value = "razaoSocial")
	String razaoSocial;
	
	@Column(name = "process", length = 20)
	@JsonAlias(value = "processo")
	String processo;
	
	@Column(name = "subject", length = 300)
	@JsonAlias(value = "assunto")
	String assunto;
	
	@JsonAlias(value = "processDetail")
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name="ProcessDetailFK")
	ProcessDetail processDetail;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ordem;
		result = prime * result + ((processDetail == null) ? 0 : processDetail.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
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
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		return true;
	}

	
	
	
	
}
