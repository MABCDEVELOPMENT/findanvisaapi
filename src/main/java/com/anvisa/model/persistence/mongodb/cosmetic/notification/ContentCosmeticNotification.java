package com.anvisa.model.persistence.mongodb.cosmetic.notification;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.model.persistence.rest.process.ProcessDetail;
import com.anvisa.model.persistence.rest.process.ProcessPetition;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;


@Document(collection = "cosmeticNotification")
public class ContentCosmeticNotification extends BaseEntityMongoDB {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonAlias(value = "assunto")
	String assunto;

	@JsonAlias(value = "expedientePeticao")
	String expedientePeticao;

	@JsonAlias(value = "transacao")
	String transacao;

	@JsonAlias(value = "processo")
	String processo;

	@JsonAlias(value = "expedienteProcesso")
	String expedienteProcesso;

	@JsonAlias(value = "produto")
	String produto;

	@JsonAlias(value = "cnpj")
	String cnpj;

	@JsonAlias(value = "razaoSocial")
	String razaoSocial;

	@JsonAlias(value = "situacao")
	String situacao;

	@JsonAlias(value = "vencimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate vencimento;

	@JsonAlias(value = "statusVencimento")
	String statusVencimento;

	@JsonAlias(value = "empresa")
	String empresa;

	@JsonAlias(value = "dataAlteracao")	
	@JsonFormat(pattern="dd/MM/yyyy")	
	LocalDate dataAlteracao;
	
	@JsonAlias(value = "dataRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataRegistro;

	@JsonAlias(value = "qtdRegistro")	
	int qtdRegistro;

	ContentCosmeticNotificationDetail contentCosmeticNotificationDetail;
	
	@Transient
	Process process;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getExpedientePeticao() {
		return expedientePeticao;
	}

	public void setExpedientePeticao(String expedientePeticao) {
		this.expedientePeticao = expedientePeticao;
	}

	public String getTransacao() {
		return transacao;
	}

	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getExpedienteProcesso() {
		return expedienteProcesso;
	}

	public void setExpedienteProcesso(String expedienteProcesso) {
		this.expedienteProcesso = expedienteProcesso;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public String getStatusVencimento() {
		return statusVencimento;
	}

	public void setStatusVencimento(String statusVencimento) {
		this.statusVencimento = statusVencimento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public ContentCosmeticNotificationDetail getContentCosmeticNotificationDetail() {
		return contentCosmeticNotificationDetail;
	}

	public void setContentCosmeticNotificationDetail(ContentCosmeticNotificationDetail contentCosmeticNotificationDetail) {
		this.contentCosmeticNotificationDetail = contentCosmeticNotificationDetail;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result
				+ ((contentCosmeticNotificationDetail == null) ? 0 : contentCosmeticNotificationDetail.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((expedientePeticao == null) ? 0 : expedientePeticao.hashCode());
		result = prime * result + ((expedienteProcesso == null) ? 0 : expedienteProcesso.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((statusVencimento == null) ? 0 : statusVencimento.hashCode());
		result = prime * result + ((transacao == null) ? 0 : transacao.hashCode());
		result = prime * result + ((vencimento == null) ? 0 : vencimento.hashCode());
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
		if (!(obj instanceof ContentCosmeticNotification)) {
			return false;
		}
		ContentCosmeticNotification other = (ContentCosmeticNotification) obj;
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
		if (contentCosmeticNotificationDetail == null) {
			if (other.contentCosmeticNotificationDetail != null)
				return false;
		} else if (!contentCosmeticNotificationDetail.equals(other.contentCosmeticNotificationDetail))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (expedientePeticao == null) {
			if (other.expedientePeticao != null)
				return false;
		} else if (!expedientePeticao.equals(other.expedientePeticao))
			return false;
		if (expedienteProcesso == null) {
			if (other.expedienteProcesso != null)
				return false;
		} else if (!expedienteProcesso.equals(other.expedienteProcesso))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (statusVencimento == null) {
			if (other.statusVencimento != null)
				return false;
		} else if (!statusVencimento.equals(other.statusVencimento))
			return false;
		if (transacao == null) {
			if (other.transacao != null)
				return false;
		} else if (!transacao.equals(other.transacao))
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
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
		if (qtdRegistro!=other.qtdRegistro)
			return false;
		return true;
	}
	
	public void lodaProcess(Process process) {
		
		this.setDataAlteracao(null);
		
		ProcessDetail detail = process.getProcessDetail();
		
		List<ProcessPetition> peticoes = detail.getPeticoes();
		
		for (ProcessPetition processPetition : peticoes) {
			
			if (processPetition.getDataPublicacao()!=null) {
				if (this.getDataAlteracao() != null
						&& processPetition.getDataPublicacao().isAfter(this.getDataAlteracao())) {
					this.setDataAlteracao(processPetition.getDataPublicacao());
				}
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
			System.out.println(" ContentCosmeticNotification CNPJ "+this.getCnpj()+" Processo "+this.getProcesso()+" ERRO DE DATAS");
		}
	}	

}
