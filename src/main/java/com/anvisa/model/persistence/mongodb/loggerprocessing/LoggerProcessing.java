package com.anvisa.model.persistence.mongodb.loggerprocessing;

import java.time.LocalDate;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;

@Document(collection="loggerProcessing")
public class LoggerProcessing extends BaseEntityMongoDB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
    public static String KEY_SEQ = "logger_processing";	
	
	private RegisterCNPJ cnpj;
	private LocalDate dataProcessamento;
	private String descricao;
	private int categoria;
	private int opcao;
	private Long totalAnvisa;
	private Long totalInserido;
	private Long totalAtualizado;
	private Long totalErro;
	private Long total;
	
	public LocalDate getDataProcessamento() {
		return dataProcessamento;
	}
	public void setDataProcessamento(LocalDate dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}
	public RegisterCNPJ getCnpj() {
		return cnpj;
	}
	public void setCnpj(RegisterCNPJ cnpj) {
		this.cnpj = cnpj;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public int getOpcao() {
		return opcao;
	}
	public void setOpcao(int opcao) {
		this.opcao = opcao;
	}
	public Long getTotalAnvisa() {
		return totalAnvisa;
	}
	public void setTotalAnvisa(Long totalAnvisa) {
		this.totalAnvisa = totalAnvisa;
	}
	public Long getTotalInserido() {
		return totalInserido;
	}
	public void setTotalInserido(Long totalInserido) {
		this.totalInserido = totalInserido;
	}
	public Long getTotalAtualizado() {
		return totalAtualizado;
	}
	public void setTotalAtualizado(Long totalAtualizado) {
		this.totalAtualizado = totalAtualizado;
	}
	public Long getTotalErro() {
		return totalErro;
	}
	public void setTotalErro(Long totalErro) {
		this.totalErro = totalErro;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}

	

}
