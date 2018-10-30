package com.anvisa.model.persistence.mongodb.saneante.notification;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.process.ProcessDetail;
import com.anvisa.model.persistence.mongodb.process.ProcessPetition;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;


@Document(collection = "saneanteNotification")
public class SaneanteNotification extends BaseEntityMongoDB {


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

	@JsonAlias(value = "saneanteNotificationDetail")
	SaneanteNotificationDetail saneanteNotificationDetail;
	

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

	public SaneanteNotificationDetail getSaneanteNotificationDetail() {
		return saneanteNotificationDetail;
	}

	public void setSaneanteNotificationDetail(SaneanteNotificationDetail saneanteNotificationDetail) {
		this.saneanteNotificationDetail = saneanteNotificationDetail;
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
			System.out.println(" SaneanteNotification CNPJ "+process.getCnpj()+" Processo "+this.getProcesso()+" ERRO DE DATAS");
		}
	}	

}
