package com.anvisa.model.persistence.mongodb.saneante.product;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.process.ProcessDetail;
import com.anvisa.model.persistence.mongodb.process.ProcessPetition;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.anvisa.model.persistence.mongodb.process.Process;

@Document(collection = "saneanteProduct")
public class SaneanteProduct extends BaseEntityMongoDB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonAlias(value = "codigo")
	int codigo;

	@JsonAlias(value = "product")
	String produto;

	@JsonAlias(value = "registro")
	String registro;

	@JsonAlias(value = "processo")
	String processo;

	@JsonAlias(value = "empresa")
	String empresa;

	@JsonAlias(value = "cnpj")
	String cnpj;

	@JsonAlias(value = "situacao")
	String situacao;

	@JsonAlias(value = "vencimento")
	String vencimento;

	@JsonAlias(value = "statusVencimento")
	String statusVencimento;

	@JsonAlias(value = "dataVencimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate dataVencimento;

	@JsonAlias(value = "dataAlteracao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate dataAlteracao;

	@JsonAlias(value = "dataRegistro")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate dataRegistro;

	@JsonAlias(value = "qtdRegistro")
	int qtdRegistro;

	@JsonAlias(value = "saneanteProductDetail")
	SaneanteProductDetail saneanteProductDetail;
	

	Process process;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getVencimento() {
		if (this.vencimento.equals("null")) {
			return "";
		}
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	public String getStatusVencimento() {
		return statusVencimento;
	}

	public void setStatusVencimento(String statusVencimento) {
		this.statusVencimento = statusVencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public SaneanteProductDetail getSaneanteProductDetail() {
		return saneanteProductDetail;
	}

	public void setSaneanteProductDetail(SaneanteProductDetail saneanteProductDetail) {
		this.saneanteProductDetail = saneanteProductDetail;
	}

	public LocalDate getDataAlteracao() {

		if (this.dataAlteracao != null && this.dataRegistro != null) {
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


	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		//result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((registro == null) ? 0 : registro.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((statusVencimento == null) ? 0 : statusVencimento.hashCode());
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
		if (!(obj instanceof SaneanteProduct)) {
			return false;
		}

		SaneanteProduct other = (SaneanteProduct) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (codigo != other.codigo)
			return false;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
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
		if (registro == null) {
			if (other.registro != null)
				return false;
		} else if (!registro.equals(other.registro))
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
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
			return false;
		return true;
	}

	public void lodaProcess() {
		
		this.setDataAlteracao(null);
		
		ProcessDetail detail = this.process.getProcessDetail();
		
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
			System.out.println(this.getClass().getName()+" CNPJ "+this.getCnpj()+" Processo "+this.getProcesso()+" ERRO DE DATAS");
		}
	}

}
