package com.anvisa.model.persistence.mongodb.cosmetic.regularized;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Transient;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.process.Process;
import com.anvisa.model.persistence.mongodb.process.ProcessDetail;
import com.anvisa.model.persistence.mongodb.process.ProcessPetition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Document(collection = "cosmeticRegularized")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentCosmeticRegularized extends BaseEntityMongoDB {
	
	
	@Field(value = "processo")
    String processo;
	
	@Field(value = "product")
    String produto;
	
	@Field(value = "tipo")
    String tipo; 

	@Field(value = "situacao")
    String situacao;
	
	@Field(value = "vencimento")
    LocalDate vencimento;
	
	@Field(value = "dataAlteracao")	
	@JsonFormat(pattern="dd/MM/yyyy")	
	LocalDate dataAlteracao;
	
	@Field(value = "dataRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataRegistro;
	
	@Field(value = "qtdRegistro")	
	int qtdRegistro;
	
	@Field(value = "contentCosmeticRegularizedDetail")	
	ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail;

	@Transient
	Process process;
	
    public ContentCosmeticRegularized() {
		// TODO Auto-generated constructor stub
	}
	
	
	@PersistenceConstructor
	public ContentCosmeticRegularized(String processo, String produto, String tipo, String situacao,
			LocalDate vencimento, LocalDate dataAlteracao, LocalDate dataRegistro, int qtdRegistro,
			ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail, Process process) {
		super();
		this.processo = processo;
		this.produto = produto;
		this.tipo = tipo;
		this.situacao = situacao;
		this.vencimento = vencimento;
		this.dataAlteracao = dataAlteracao;
		this.dataRegistro = dataRegistro;
		this.qtdRegistro = qtdRegistro;
		this.contentCosmeticRegularizedDetail = contentCosmeticRegularizedDetail;
		this.process = process;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public ContentCosmeticRegularizedDetail getContentCosmeticRegularizedDetail() {
		return contentCosmeticRegularizedDetail;
	}

	public void setContentCosmeticRegularizedDetail(ContentCosmeticRegularizedDetail contentCosmeticRegularizedDetail) {
		this.contentCosmeticRegularizedDetail = contentCosmeticRegularizedDetail;
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
		result = prime * result
				+ ((contentCosmeticRegularizedDetail == null) ? 0 : contentCosmeticRegularizedDetail.hashCode());
		result = prime * result + ((dataAlteracao == null) ? 0 : dataAlteracao.hashCode());
		result = prime * result + ((dataRegistro == null) ? 0 : dataRegistro.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + qtdRegistro;
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof ContentCosmeticRegularized)) {
			return false;
		}
		ContentCosmeticRegularized other = (ContentCosmeticRegularized) obj;
		if (contentCosmeticRegularizedDetail == null) {
			if (other.contentCosmeticRegularizedDetail != null)
				return false;
		} else if (!contentCosmeticRegularizedDetail.equals(other.contentCosmeticRegularizedDetail))
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
		if (qtdRegistro != other.qtdRegistro)
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
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
				} else {
					if (this.getDataAlteracao() == null)
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
			try {
				System.out.println(this.getClass().getName()+" CNPJ "+this.getContentCosmeticRegularizedDetail().getCnpj()+" Processo "+this.getProcesso()+" ERRO DE DATAS");	
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(this.getClass().getName()+" Processo "+this.getProcesso()+" ERRO DE DATAS");
			}
			
		}
	}	


}
