package com.anvisa.model.persistence.rest.saneante.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.anvisa.core.json.JsonToObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SaneanteProductApresentacao {

	@Column(name = "code", length = 20)
	@JsonAlias(value = "codigo")
	private String codigo;
	
	@Column(name = "presentation", length = 200, nullable = false)
	@JsonAlias(value = "apresentacao")
	String apresentacao;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductPharmaceuticalForm> formasFarmaceuticas;

	@Column(name = "number", length = 20)
	@JsonAlias(value = "numero")
	private String numero;
	
	@Column(name = "tonality", length = 100)
	@JsonAlias(value = "tonalidade")
    String tonalidade;
	
	@Column(name = "publication")
	@JsonAlias(value = "publicacao")
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataPublicacao;
	
	@Column(name = "due_date", length = 4)
	@JsonAlias(value = "prazoValidade")
	private String validade;
	
	@Column(name = "type_validity", length = 20)
	@JsonAlias(value = "tipoValidade")
	private String tipoValidade;
	
	@Column(name = "register", length = 20)
	@JsonAlias(value = "registro")
	private String registro;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductActivePrinciple> principiosAtivos;
	
	@Column(name = "complement", length = 30)
	@JsonAlias(value = "complemento")
	private String complemento;
	
	@JsonAlias(value = "SaneanteProductPrimaryPackage")
	@OneToOne
	@JoinColumn(name="saneanteProductPrimaryPackageFK")
	SaneanteProductPrimaryPackage embalagemPrimaria;
	
	@Column(name = "second_package", length = 100, nullable = true)
	@JsonAlias(value = "embalagemSecundaria")	
    String embalagemSecundaria;

	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductWrapper> envoltorios;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductAccessory> acessorios;
	
	@Column(name = "packaging")
	@JsonAlias(value = "acondicionamento")
	private String acondicionamento;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductBrand> marcas;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductManufactureNational> fabricantesNacionais;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductManufacturerInternational> fabricantesInternacionais;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductViaAdministration> viasAdministracao;
	
	@Column(name = "ifaUnique")
	@JsonAlias(value = "ifaUnico")
	private boolean ifaUnico;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SaneanteProductConservation> conservacao;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SeneanteProductRestrictionPrescription> restricaoPrescricao;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SeneanteProductUseRestriction> restricaoUso;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<SeneanteProductDestination> destinacao;
	
	@Column(name = "restricthospital")
	@JsonAlias(value = "restricaoHospitais")
	private String restricaoHospitais;
	
	@Column(name = "stripe")
	@JsonAlias(value = "restricaoHospitais")
	private String tarja;
	
	@Column(name = "drugReference")
	@JsonAlias(value = "medicamentoReferencia")
	private String medicamentoReferencia;

	@Column(name = "broken_presentation")
	@JsonAlias(value = "apresentacaoFracionada")
	private String apresentacaoFracionada;
	
	@Column(name = "date_due_Registration")
	@JsonAlias(value = "dataVencimentoRegistro")	
	private LocalDate dataVencimentoRegistro;
	
	@Column(name = "ativa")
	@JsonAlias(value = "ativa")
	private boolean ativa;
	
	@Column(name = "inactive")
	@JsonAlias(value = "inativa")
	private boolean inativa;
	
	@Column(name = "in_analysis")
	@JsonAlias(value = "emAnalise")
	private boolean emAnalise;

	public void loadApresentacao(JsonNode node) {

		this.setCodigo(JsonToObject.getValue(node, "codigo"));
		this.setApresentacao(JsonToObject.getValue(node, "apresentacao"));
		this.setFormasFarmaceuticas(JsonToObject.getArraySaneanteStringListGeneric(node, "formasFarmaceuticas",SaneanteProductPharmaceuticalForm.class));
		this.setNumero(JsonToObject.getValue(node, "numero"));
		this.setTonalidade(JsonToObject.getValue(node, "tonalidade"));
		this.setDataPublicacao(JsonToObject.getValueDate(node, "dataPublicacao"));
		this.setValidade(JsonToObject.getValue(node, "validade"));
		this.setTipoValidade(JsonToObject.getValue(node, "tipoValidade"));
		this.setRegistro(JsonToObject.getValue(node, "registro"));
		this.setPrincipiosAtivos(JsonToObject.getArraySaneanteStringListGeneric(node, "principiosAtivos",SaneanteProductActivePrinciple.class));
		this.setComplemento(JsonToObject.getValue(node, "complemento"));

		SaneanteProductPrimaryPackage embalagemPrimaria = new SaneanteProductPrimaryPackage();
		embalagemPrimaria.load(node, "embalagemPrimaria");
		this.setEmbalagemPrimaria(embalagemPrimaria);

		this.setEmbalagemSecundaria(JsonToObject.getValue(node, "embalagemSecundaria"));
		this.setEnvoltorios(JsonToObject.getArraySaneanteStringListGeneric(node, "envoltorios",SaneanteProductWrapper.class));
		this.setAcessorios(JsonToObject.getArraySaneanteStringListGeneric(node, "acessorios",SaneanteProductAccessory.class));
		this.setAcondicionamento(JsonToObject.getValue(node, "acondicionamento"));
		this.setMarcas(JsonToObject.getArraySaneanteStringListGeneric(node, "marcas",SaneanteProductBrand.class));
		this.loadFabricantes(node, "fabricantesNacionais");
		this.setFabricantesInternacionais(JsonToObject.getArraySaneanteStringListGeneric(node, "fabricantesInternacionais",SaneanteProductManufacturerInternational.class));
		this.setViasAdministracao(JsonToObject.getArraySaneanteStringListGeneric(node, "viasAdministracao",SaneanteProductViaAdministration.class));
		String ifaUnico = JsonToObject.getValue(node, "ifaUnico");
		this.setIfaUnico(Boolean.parseBoolean(ifaUnico));

		this.setConservacao(JsonToObject.getArraySaneanteStringListGeneric(node, "conservacao",SaneanteProductConservation.class));
		this.setRestricaoPrescricao(JsonToObject.getArraySaneanteStringListGeneric(node, "restricaoPrescricao",SeneanteProductRestrictionPrescription.class));
		this.setRestricaoUso(JsonToObject.getArraySaneanteStringListGeneric(node, "restricaoUso",SeneanteProductUseRestriction.class));
		this.setDestinacao(JsonToObject.getArraySaneanteStringListGeneric(node, "destinacao",SeneanteProductDestination.class));
		this.setRestricaoHospitais(JsonToObject.getValue(node, "restricaoHospitais"));
		this.setTarja(JsonToObject.getValue(node, "tarja"));
		this.setMedicamentoReferencia(JsonToObject.getValue(node, "medicamentoReferencia"));
		this.setApresentacaoFracionada(JsonToObject.getValue(node, "apresentacaoFracionada"));
		this.setDataVencimentoRegistro(JsonToObject.getValueDate(node, "dataVencimentoRegistro"));

		String ativa = JsonToObject.getValue(node, "ativa");
		this.setAtiva(Boolean.parseBoolean(ativa));

		String inativa = JsonToObject.getValue(node, "inativa");
		this.setInativa(Boolean.parseBoolean(inativa));

		String emAnalise = JsonToObject.getValue(node, "emAnalise");
		this.setEmAnalise(Boolean.parseBoolean(emAnalise));

	}

	public void loadFabricantes(JsonNode node, String attribute) {

		this.fabricantesNacionais = new ArrayList<SaneanteProductManufactureNational>();

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		if (element != null) {

			for (JsonNode jsonNode : element) {

				SaneanteProductManufactureNational fabricantesNacional = new SaneanteProductManufactureNational();
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

	public List<SaneanteProductPharmaceuticalForm> getFormasFarmaceuticas() {
		return this.formasFarmaceuticas;
	}

	public void setFormasFarmaceuticas(ArrayList<SaneanteProductPharmaceuticalForm> formasFarmaceuticas) {
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

	public List<SaneanteProductActivePrinciple> getPrincipiosAtivos() {
		return this.principiosAtivos;
	}

	public void setPrincipiosAtivos(ArrayList<SaneanteProductActivePrinciple> principiosAtivos) {
		this.principiosAtivos = principiosAtivos;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public SaneanteProductPrimaryPackage getEmbalagemPrimaria() {
		return this.embalagemPrimaria;
	}

	public void setEmbalagemPrimaria(SaneanteProductPrimaryPackage embalagemPrimaria) {
		this.embalagemPrimaria = embalagemPrimaria;
	}

	public String getEmbalagemSecundaria() {
		return this.embalagemSecundaria;
	}

	public void setEmbalagemSecundaria(String embalagemSecundaria) {
		this.embalagemSecundaria = embalagemSecundaria;
	}

	public List<SaneanteProductWrapper> getEnvoltorios() {
		return this.envoltorios;
	}

	public void setEnvoltorios(List<SaneanteProductWrapper> envoltorios) {
		this.envoltorios = envoltorios;
	}

	public List<SaneanteProductAccessory> getAcessorios() {
		return this.acessorios;
	}

	public void setAcessorios(List<SaneanteProductAccessory> acessorios) {
		this.acessorios = acessorios;
	}

	public String getAcondicionamento() {
		return this.acondicionamento;
	}

	public void setAcondicionamento(String acondicionamento) {
		this.acondicionamento = acondicionamento;
	}

	public List<SaneanteProductBrand> getMarcas() {
		return this.marcas;
	}

	public void setMarcas(List<SaneanteProductBrand> marcas) {
		this.marcas = marcas;
	}

	public List<SaneanteProductManufactureNational> getFabricantesNacionais() {
		return this.fabricantesNacionais;
	}

	public void setFabricantesNacionais(List<SaneanteProductManufactureNational> fabricantesNacionais) {
		this.fabricantesNacionais = fabricantesNacionais;
	}

	public List<SaneanteProductManufacturerInternational> getFabricantesInternacionais() {
		return this.fabricantesInternacionais;
	}

	public void setFabricantesInternacionais(List<SaneanteProductManufacturerInternational> fabricantesInternacionais) {
		this.fabricantesInternacionais = fabricantesInternacionais;
	}

	public List<SaneanteProductViaAdministration> getViasAdministracao() {
		return this.viasAdministracao;
	}

	public void setViasAdministracao(List<SaneanteProductViaAdministration> viasAdministracao) {
		this.viasAdministracao = viasAdministracao;
	}

	public boolean getIfaUnico() {
		return this.ifaUnico;
	}

	public void setIfaUnico(boolean ifaUnico) {
		this.ifaUnico = ifaUnico;
	}

	public List<SaneanteProductConservation> getConservacao() {
		return this.conservacao;
	}

	public void setConservacao(List<SaneanteProductConservation> conservacao) {
		this.conservacao = conservacao;
	}

	public List<SeneanteProductRestrictionPrescription> getRestricaoPrescricao() {
		return this.restricaoPrescricao;
	}

	public void setRestricaoPrescricao(List<SeneanteProductRestrictionPrescription> restricaoPrescricao) {
		this.restricaoPrescricao = restricaoPrescricao;
	}

	public List<SeneanteProductUseRestriction> getRestricaoUso() {
		return this.restricaoUso;
	}

	public void setRestricaoUso(List<SeneanteProductUseRestriction> restricaoUso) {
		this.restricaoUso = restricaoUso;
	}

	public List<SeneanteProductDestination> getDestinacao() {
		return this.destinacao;
	}

	public void setDestinacao(List<SeneanteProductDestination> destinacao) {
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