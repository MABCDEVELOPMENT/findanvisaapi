package com.anvisa.model.persistence.rest.foot;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.anvisa.model.persistence.rest.process.Process;
import com.anvisa.model.persistence.rest.process.ProcessDetail;
import com.anvisa.model.persistence.rest.process.ProcessPetition;

@Entity
@Table(name = "foot")
public class ContentFoot extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "code", length = 20, nullable = false)
	@JsonAlias(value = "codigo")
	int codigo;
	
	@Column(name = "product", length = 600, nullable = false)
	@JsonAlias(value = "product")
	String produto;

	@Column(name = "register", length = 20, nullable = false)
	@JsonAlias(value = "registro")
	String registro;
	
	@Column(name = "process",  length = 20, nullable = false)
	@JsonAlias(value = "processo")	
	String processo;

	@Column(name = "company", nullable = false)
	@JsonAlias(value = "empresa")
	String empresa;

	@Column(name = "cnpj", length = 14, nullable = false)
	@JsonAlias(value = "cnpj")
	String cnpj;
	
	@Column(name = "situation", length = 60, nullable = false)
	@JsonAlias(value = "situacao")
	String situacao;

	@Column(name = "vencimento", length = 8, nullable = true)
	@JsonAlias(value = "vencimento")
	String vencimento;
	
	@Column(name = "statusVencimento", length = 60, nullable = true)
	@JsonAlias(value = "statusVencimento")	
	String statusVencimento;
	
	@Column(name = "dataVencimento", nullable = true)
	@JsonAlias(value = "dataVencimento")	
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataVencimento;
	
	@Column(name = "dateUpdateRegister")
	@JsonAlias(value = "dataAlteracao")	
	@JsonFormat(pattern="dd/MM/yyyy")	
	LocalDate dataAlteracao;
	
	@Column(name = "dateRegister")
	@JsonAlias(value = "dataRegistro")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate dataRegistro;
	
	@Column(name = "qtdRecord")
	@JsonAlias(value = "qtdRegistro")	
	int qtdRegistro;

	@JsonAlias(value = "contentFootDetail")
	@ManyToOne
	@JoinColumn(name="contentFootDetailFK")
	ContentFootDetail contentFootDetail;
	
	@Transient
	Process process;
	
	
	public ContentFoot() {
		// TODO Auto-generated constructor stub
	}
	
	public ContentFoot(Content content) {
		// TODO Auto-generated constructor stub
		
		this.setCodigo(content.getProduto().getCodigo());
		this.setProduto(content.getProduto().getNome());
		this.setRegistro(content.getProduto().getNumeroRegistro());
		this.setProcesso(content.getProcesso().getNumero());
		this.setEmpresa(content.getEmpresa().getRazaoSocial());
		this.setCnpj(content.getEmpresa().getCnpj());
		this.setSituacao(content.getProcesso().getSituacao());
		this.setVencimento(content.getProduto().getMesAnoVencimentoFormatado());
		this.setDataVencimento(content.getProduto().getDataVencimentoRegistro());
		
		
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
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getStatusVencimento() {
		return statusVencimento;
	}

	public void setStatusVencimento(String statusVencimento) {
		this.statusVencimento = statusVencimento;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
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
	public ContentFootDetail getContentFootDetail() {
		return contentFootDetail;
	}

	public void setContentFootDetail(ContentFootDetail contentFootDetail) {
		this.contentFootDetail = contentFootDetail;
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
		result = prime * result + codigo;
		result = prime * result + ((contentFootDetail == null) ? 0 : contentFootDetail.hashCode());
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
		if (!(obj instanceof ContentFoot)) {
			return false;
		}
		
		ContentFoot other = (ContentFoot) obj;
		if (cnpj == null) {
			if (other.cnpj != null) {
				return false;
			}
		} else if (!cnpj.equals(other.cnpj)) {
			return false;
		}
		if (codigo != other.codigo) {
			return false;
		}
/*		if (contentDetalFoot == null) {
			if (other.contentDetalFoot != null) {
				return false;
			}
		} else if (!contentDetalFoot.equals(other.contentDetalFoot)) {
			return false;
		}*/
		if (dataVencimento == null) {
			if (other.dataVencimento != null) {
				return false;
			}
		} else if (!dataVencimento.equals(other.dataVencimento)) {
			return false;
		}
		if (empresa == null) {
			if (other.empresa != null) {
				return false;
			}
		} else if (!empresa.equals(other.empresa)) {
			return false;
		}
		if (processo == null) {
			if (other.processo != null) {
				return false;
			}
		} else if (!processo.equals(other.processo)) {
			return false;
		}
		if (produto == null) {
			if (other.produto != null) {
				return false;
			}
		} else if (!produto.equals(other.produto)) {
			return false;
		}
		if (registro == null) {
			if (other.registro != null) {
				return false;
			}
		} else if (!registro.equals(other.registro)) {
			return false;
		}
		if (situacao == null) {
			if (other.situacao != null) {
				return false;
			}
		} else if (!situacao.equals(other.situacao)) {
			return false;
		}
		if (statusVencimento == null) {
			if (other.statusVencimento != null) {
				return false;
			}
		} else if (!statusVencimento.equals(other.statusVencimento)) {
			return false;
		}
		if (vencimento == null) {
			if (other.vencimento != null) {
				return false;
			}
		} else if (!vencimento.equals(other.vencimento)) {
			return false;
		}
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
			System.out.println(" FOOT CNPJ "+this.getCnpj()+" Processo "+this.getProcesso()+" ERRO DE DATAS");
		}
	}

}

