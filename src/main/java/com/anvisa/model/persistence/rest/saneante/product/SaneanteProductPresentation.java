package com.anvisa.model.persistence.rest.saneante.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Entity
@Table(name = "saneante_product_presentation")
public class SaneanteProductPresentation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JsonAlias(value = "embalagemPrimaria")
	private SaneanteProductPrimaryPackage embalagemPrimaria;
	
	@Column(name = "second_package", length = 100, nullable = true)
	@JsonAlias(value = "embalagemSecundaria")	
    private String embalagemSecundaria;

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
		
		List<SaneanteStringListGeneric> stringListGenericformas = JsonToObject.getArraySaneanteStringListGeneric(node, "formasFarmaceuticas");
		
		ArrayList<SaneanteProductPharmaceuticalForm> formas = new ArrayList<SaneanteProductPharmaceuticalForm>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericformas) {
			formas.add(new SaneanteProductPharmaceuticalForm(saneanteStringListGeneric.getValor()));
		}
		
		this.setFormasFarmaceuticas(formas);
		
		this.setNumero(JsonToObject.getValue(node, "numero"));
		this.setTonalidade(JsonToObject.getValue(node, "tonalidade"));
		this.setDataPublicacao(JsonToObject.getValueDate(node, "dataPublicacao"));
		this.setValidade(JsonToObject.getValue(node, "validade"));
		this.setTipoValidade(JsonToObject.getValue(node, "tipoValidade"));
		this.setRegistro(JsonToObject.getValue(node, "registro"));
		
		List<SaneanteStringListGeneric> stringListGenericPrincitiosAtivos = JsonToObject.getArraySaneanteStringListGeneric(node, "principiosAtivos");
		
		ArrayList<SaneanteProductActivePrinciple> princitiosAtivos = new ArrayList<SaneanteProductActivePrinciple>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericPrincitiosAtivos) {
			princitiosAtivos.add(new SaneanteProductActivePrinciple(saneanteStringListGeneric.getValor()));
		}
		
		this.setPrincipiosAtivos(princitiosAtivos);
		this.setComplemento(JsonToObject.getValue(node, "complemento"));

		SaneanteProductPrimaryPackage embalagemPrimaria = new SaneanteProductPrimaryPackage();
		embalagemPrimaria.load(node, "embalagemPrimaria");
		this.setEmbalagemPrimaria(embalagemPrimaria);

		this.setEmbalagemSecundaria(JsonToObject.getValue(node, "embalagemSecundaria"));
		
		List<SaneanteStringListGeneric> stringListGenericEnvoltorios = JsonToObject.getArraySaneanteStringListGeneric(node, "envoltorios");
		
		ArrayList<SaneanteProductWrapper> envoltorios = new ArrayList<SaneanteProductWrapper>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericEnvoltorios) {
			envoltorios.add(new SaneanteProductWrapper(saneanteStringListGeneric.getValor()));
		}
		
		this.setEnvoltorios(envoltorios);
		
		List<SaneanteStringListGeneric> stringListGenericAcessorios = JsonToObject.getArraySaneanteStringListGeneric(node, "acessorios");
		
		ArrayList<SaneanteProductAccessory> acessorios = new ArrayList<SaneanteProductAccessory>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericAcessorios) {
			acessorios.add(new SaneanteProductAccessory(saneanteStringListGeneric.getValor()));
		}
		
		this.setAcessorios(acessorios);
		
		
		this.setAcondicionamento(JsonToObject.getValue(node, "acondicionamento"));
		
		List<SaneanteStringListGeneric> stringListGenericMarcas = JsonToObject.getArraySaneanteStringListGeneric(node, "marcas");
		
		ArrayList<SaneanteProductBrand> marcas = new ArrayList<SaneanteProductBrand>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericMarcas) {
			marcas.add(new SaneanteProductBrand(saneanteStringListGeneric.getValor()));
		}
		
		this.setMarcas(marcas);
		
		this.loadFabricantes(node, "fabricantesNacionais");
		
		
		List<SaneanteStringListGeneric> stringListGenericFabricantesInternacionais = JsonToObject.getArraySaneanteStringListGeneric(node, "fabricantesInternacionais");
		
		ArrayList<SaneanteProductManufacturerInternational> fabricantesInternacionais = new ArrayList<SaneanteProductManufacturerInternational>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericFabricantesInternacionais) {
			fabricantesInternacionais.add(new SaneanteProductManufacturerInternational(saneanteStringListGeneric.getValor()));
		}
		
		this.setFabricantesInternacionais(fabricantesInternacionais);

		List<SaneanteStringListGeneric> stringListGenericViasAdministracao = JsonToObject.getArraySaneanteStringListGeneric(node, "viasAdministracao");
		
		ArrayList<SaneanteProductViaAdministration> viasAdministracaos = new ArrayList<SaneanteProductViaAdministration>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericViasAdministracao) {
			viasAdministracaos.add(new SaneanteProductViaAdministration(saneanteStringListGeneric.getValor()));
		}
		
		this.setViasAdministracao(viasAdministracaos);
		
		String ifaUnico = JsonToObject.getValue(node, "ifaUnico");
		this.setIfaUnico(Boolean.parseBoolean(ifaUnico));

		List<SaneanteStringListGeneric> stringListGenericConservacoes = JsonToObject.getArraySaneanteStringListGeneric(node, "conservacao");
		
		ArrayList<SaneanteProductConservation> conservacoes = new ArrayList<SaneanteProductConservation>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericConservacoes) {
			conservacoes.add(new SaneanteProductConservation(saneanteStringListGeneric.getValor()));
		}
		
		this.setConservacao(conservacoes);
		
		List<SaneanteStringListGeneric> stringListGenericrestricaoPrescricoes = JsonToObject.getArraySaneanteStringListGeneric(node, "restricaoPrescricao");
		
		ArrayList<SeneanteProductRestrictionPrescription> restricaoPrescricoes = new ArrayList<SeneanteProductRestrictionPrescription>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericrestricaoPrescricoes) {
			restricaoPrescricoes.add(new SeneanteProductRestrictionPrescription(saneanteStringListGeneric.getValor()));
		}
		
		this.setRestricaoPrescricao(restricaoPrescricoes);

		List<SaneanteStringListGeneric> stringListGenericRestricoesUso = JsonToObject.getArraySaneanteStringListGeneric(node, "restricaoUso");
		
		ArrayList<SeneanteProductUseRestriction> restricoesUso = new ArrayList<SeneanteProductUseRestriction>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericRestricoesUso) {
			restricoesUso.add(new SeneanteProductUseRestriction(saneanteStringListGeneric.getValor()));
		}
		
		this.setRestricaoUso(restricoesUso);
		
		List<SaneanteStringListGeneric> stringListGenericDestinacao = JsonToObject.getArraySaneanteStringListGeneric(node, "destinacao");
		
		ArrayList<SeneanteProductDestination> destinacao = new ArrayList<SeneanteProductDestination>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericDestinacao) {
			destinacao.add(new SeneanteProductDestination(saneanteStringListGeneric.getValor()));
		}
		
		this.setDestinacao(destinacao);
		
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