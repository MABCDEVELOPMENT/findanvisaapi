package com.anvisa.rest.detalhe.processo;

public class ProcessoDetalhe {

    String numero;
    
    ProcessoPeticaoDetalhe peticao;
    
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public ProcessoPeticaoDetalhe getPeticao() {
		return peticao;
	}
	public void setPeticao(ProcessoPeticaoDetalhe peticao) {
		this.peticao = peticao;
	}

}
