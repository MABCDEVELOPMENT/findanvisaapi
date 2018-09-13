package com.anvisa.rest.detalhe.saneante.product;

import java.time.LocalDate;
import java.util.ArrayList;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SaneanteProductApresentacao {

	private String codigo;
	private String apresentacao;
	private ArrayList<String> formasFarmaceuticas;

	private String numero;
	private String tonalidade;
	private LocalDate dataPublicacao;
	private String validade;
	private String tipoValidade;
	private String registro;
	private ArrayList<String> principiosAtivos;
	private String complemento;
	private EmbalagemPrimaria embalagemPrimaria;
	private String embalagemSecundaria;
	private ArrayList<String> envoltorios;
	private ArrayList<String> acessorios;
	private String acondicionamento;
	private ArrayList<String> marcas;
	private ArrayList<FabricantesNacional> fabricantesNacionais;
	private ArrayList<String> fabricantesInternacionais;
	private ArrayList<String> viasAdministracao;
	private boolean ifaUnico;
	private ArrayList<String> conservacao;
	private ArrayList<String> restricaoPrescricao;
	private ArrayList<String> restricaoUso;
	private ArrayList<String> destinacao;
	private String restricaoHospitais;
	private String tarja;
	private String medicamentoReferencia;
	private String apresentacaoFracionada;
	private LocalDate dataVencimentoRegistro;
	private boolean ativa;
	private boolean inativa;
	private boolean emAnalise;

	
	public void loadApresentacao(JsonNode node) {
		
		this.setCodigo(JsonToObject.getValue(node, "codigo"));
		this.setApresentacao(JsonToObject.getValue(node, "apresentacao"));
		this.setFormasFarmaceuticas(JsonToObject.getArrayStringValue(node, "formasFarmaceuticas") );
		this.setNumero(JsonToObject.getValue(node, "numero"));
		this.setTonalidade(JsonToObject.getValue(node, "tonalidade"));
		this.setDataPublicacao(JsonToObject.getValueDate(node,"dataPublicacao"));
		this.setValidade(JsonToObject.getValue(node, "validade"));
		this.setTipoValidade(JsonToObject.getValue(node, "tipoValidade"));
		this.setRegistro(JsonToObject.getValue(node, "registro"));
		this.setPrincipiosAtivos(JsonToObject.getArrayStringValue(node, "principiosAtivos"));
		this.setComplemento(JsonToObject.getValue(node, "complemento"));
		
		EmbalagemPrimaria embalagemPrimaria = new EmbalagemPrimaria();
		embalagemPrimaria.load(node, "embalagemPrimaria");
		this.setEmbalagemPrimaria(embalagemPrimaria);
		
		this.setEmbalagemSecundaria(JsonToObject.getValue(node, "embalagemSecundaria"));
		this.setEnvoltorios(JsonToObject.getArrayStringValue(node, "envoltorios"));
		this.setAcessorios(JsonToObject.getArrayStringValue(node, "acessorios"));
		this.setAcondicionamento(JsonToObject.getValue(node, "acondicionamento"));
		this.setMarcas(JsonToObject.getArrayStringValue(node,"marcas"));
		this.loadFabricantes(node, "fabricantesNacionais");
		this.setFabricantesInternacionais(JsonToObject.getArrayStringValue(node,"fabricantesInternacionais"));
		this.setViasAdministracao(JsonToObject.getArrayStringValue(node,"viasAdministracao"));
		String ifaUnico = JsonToObject.getValue(node, "ifaUnico");
		this.setIfaUnico(Boolean.parseBoolean(ifaUnico));
		
		this.setConservacao(JsonToObject.getArrayStringValue(node, "conservacao"));
		this.setRestricaoPrescricao(JsonToObject.getArrayStringValue(node, "restricaoPrescricao"));
		this.setRestricaoUso(JsonToObject.getArrayStringValue(node, "restricaoUso"));
		this.setDestinacao(JsonToObject.getArrayStringValue(node, "destinacao"));
		this.setRestricaoHospitais(JsonToObject.getValue(node, "restricaoHospitais"));
		this.setTarja(JsonToObject.getValue(node, "tarja"));
		this.setMedicamentoReferencia(JsonToObject.getValue(node, "medicamentoReferencia"));
		this.setApresentacaoFracionada(JsonToObject.getValue(node, "apresentacaoFracionada"));
		this.setDataVencimentoRegistro(JsonToObject.getValueDate(node,"dataVencimentoRegistro"));
		
		String ativa = JsonToObject.getValue(node, "ativa");
		this.setAtiva(Boolean.parseBoolean(ativa));
		
		String inativa = JsonToObject.getValue(node, "inativa");
		this.setInativa(Boolean.parseBoolean(inativa));
		
		String emAnalise = JsonToObject.getValue(node, "emAnalise");
		this.setEmAnalise(Boolean.parseBoolean(emAnalise));
		
		
	}
	
	public void loadFabricantes(JsonNode node,String attribute) {
		
		this.fabricantesNacionais = new ArrayList<FabricantesNacional>();
		
		ArrayNode element = (ArrayNode)node.findValue(attribute);
		
		if (element!=null) {
				
			for (JsonNode jsonNode : element) {
				
				FabricantesNacional fabricantesNacional = new FabricantesNacional();
				fabricantesNacional.load(jsonNode);
				this.fabricantesNacionais.add(fabricantesNacional);
				
			}
		}
		
	}
	
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getApresentacao() {
		return this.apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public ArrayList<String> getFormasFarmaceuticas() {
		return this.formasFarmaceuticas;
	}

	public void setFormasFarmaceuticas(ArrayList<String> formasFarmaceuticas) {
		this.formasFarmaceuticas = formasFarmaceuticas;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTonalidade() {
		return this.tonalidade;
	}

	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}

	public LocalDate getDataPublicacao() {
		return this.dataPublicacao;
	}

	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getValidade() {
		return this.validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getTipoValidade() {
		return this.tipoValidade;
	}

	public void setTipoValidade(String tipoValidade) {
		this.tipoValidade = tipoValidade;
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public ArrayList<String> getPrincipiosAtivos() {
		return this.principiosAtivos;
	}

	public void setPrincipiosAtivos(ArrayList<String> principiosAtivos) {
		this.principiosAtivos = principiosAtivos;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public EmbalagemPrimaria getEmbalagemPrimaria() {
		return this.embalagemPrimaria;
	}

	public void setEmbalagemPrimaria(EmbalagemPrimaria embalagemPrimaria) {
		this.embalagemPrimaria = embalagemPrimaria;
	}

	public String getEmbalagemSecundaria() {
		return this.embalagemSecundaria;
	}

	public void setEmbalagemSecundaria(String embalagemSecundaria) {
		this.embalagemSecundaria = embalagemSecundaria;
	}

	public ArrayList<String> getEnvoltorios() {
		return this.envoltorios;
	}

	public void setEnvoltorios(ArrayList<String> envoltorios) {
		this.envoltorios = envoltorios;
	}

	public ArrayList<String> getAcessorios() {
		return this.acessorios;
	}

	public void setAcessorios(ArrayList<String> acessorios) {
		this.acessorios = acessorios;
	}

	public String getAcondicionamento() {
		return this.acondicionamento;
	}

	public void setAcondicionamento(String acondicionamento) {
		this.acondicionamento = acondicionamento;
	}

	public ArrayList<String> getMarcas() {
		return this.marcas;
	}

	public void setMarcas(ArrayList<String> marcas) {
		this.marcas = marcas;
	}



	public ArrayList<FabricantesNacional> getFabricantesNacionais() {
		return this.fabricantesNacionais;
	}

	public void setFabricantesNacionais(ArrayList<FabricantesNacional> fabricantesNacionais) {
		this.fabricantesNacionais = fabricantesNacionais;
	}

	public ArrayList<String> getFabricantesInternacionais() {
		return this.fabricantesInternacionais;
	}

	public void setFabricantesInternacionais(ArrayList<String> fabricantesInternacionais) {
		this.fabricantesInternacionais = fabricantesInternacionais;
	}



	public ArrayList<String> getViasAdministracao() {
		return this.viasAdministracao;
	}

	public void setViasAdministracao(ArrayList<String> viasAdministracao) {
		this.viasAdministracao = viasAdministracao;
	}

	public boolean getIfaUnico() {
		return this.ifaUnico;
	}

	public void setIfaUnico(boolean ifaUnico) {
		this.ifaUnico = ifaUnico;
	}

	public ArrayList<String> getConservacao() {
		return this.conservacao;
	}

	public void setConservacao(ArrayList<String> conservacao) {
		this.conservacao = conservacao;
	}

	public ArrayList<String> getRestricaoPrescricao() {
		return this.restricaoPrescricao;
	}

	public void setRestricaoPrescricao(ArrayList<String> restricaoPrescricao) {
		this.restricaoPrescricao = restricaoPrescricao;
	}

	public ArrayList<String> getRestricaoUso() {
		return this.restricaoUso;
	}

	public void setRestricaoUso(ArrayList<String> restricaoUso) {
		this.restricaoUso = restricaoUso;
	}

	public ArrayList<String> getDestinacao() {
		return this.destinacao;
	}

	public void setDestinacao(ArrayList<String> destinacao) {
		this.destinacao = destinacao;
	}

	public String getRestricaoHospitais() {
		return this.restricaoHospitais;
	}

	public void setRestricaoHospitais(String restricaoHospitais) {
		this.restricaoHospitais = restricaoHospitais;
	}

	public String getTarja() {
		return this.tarja;
	}

	public void setTarja(String tarja) {
		this.tarja = tarja;
	}

	public String getMedicamentoReferencia() {
		return this.medicamentoReferencia;
	}

	public void setMedicamentoReferencia(String medicamentoReferencia) {
		this.medicamentoReferencia = medicamentoReferencia;
	}

	public String getApresentacaoFracionada() {
		return this.apresentacaoFracionada;
	}

	public void setApresentacaoFracionada(String apresentacaoFracionada) {
		this.apresentacaoFracionada = apresentacaoFracionada;
	}

	public LocalDate getDataVencimentoRegistro() {
		return this.dataVencimentoRegistro;
	}

	public void setDataVencimentoRegistro(LocalDate dataVencimentoRegistro) {
		this.dataVencimentoRegistro = dataVencimentoRegistro;
	}

	public boolean getAtiva() {
		return this.ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public boolean getInativa() {
		return this.inativa;
	}

	public void setInativa(boolean inativa) {
		this.inativa = inativa;
	}

	public boolean getEmAnalise() {
		return this.emAnalise;
	}

	public void setEmAnalise(boolean emAnalise) {
		this.emAnalise = emAnalise;
	}

}
