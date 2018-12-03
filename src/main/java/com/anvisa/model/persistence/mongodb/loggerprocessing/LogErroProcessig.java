package com.anvisa.model.persistence.mongodb.loggerprocessing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;

@Document(collection="logErroProcessig")
public class LogErroProcessig {

	private String cnpj;
	private String process;
	private String erro;
	private String categoria;
	private String classe;
	private String erroFull;
	private LocalDateTime hora;
	
	
	public LogErroProcessig(String cnpj, String process, String erro, String categoria, String classe,
			Exception stackTrace, LocalDateTime hora) {
		StringWriter sw = new StringWriter();
		stackTrace.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		
		this.cnpj = cnpj;
		this.process = process;
		this.erro = erro;
		this.categoria = categoria;
		this.classe = classe;
		this.erroFull = exceptionAsString;
		this.hora = hora;
		
		// TODO Auto-generated constructor stub
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getErro() {
		return erro;
	}
	public void setErro(String erro) {
		this.erro = erro;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getErroFull() {
		return erroFull;
	}
	public void setErroFull(String erroFull) {
		this.erroFull = erroFull;
	}
	public LocalDateTime getHora() {
		return hora;
	}
	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

}
