package com.anvisa.core.json;

import com.anvisa.model.Empresa;
import com.anvisa.model.Processo;
import com.anvisa.model.Produto;

public class ContentProduto {

	private int ordem;

	public int getOrdem() {
		return this.ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	private Produto produto;

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	private Empresa empresa;

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	private Processo processo;

	public Processo getProcesso() {
		return this.processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
}
