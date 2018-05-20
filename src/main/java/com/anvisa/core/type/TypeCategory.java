package com.anvisa.core.type;

public enum TypeCategory {

	ADITIVOS(4200047, "ADITIVOS"), ADOCANTE_DE_MESA(4100116, "ADOÇANTE DE MESA"), ADOCANTE_DIETETICO(4100114,
			"ADOÇANTE DIETÉTICO"), AGUA_MINERAL_NATURAL_E_AGUA_NATURAL(4200020,
					"AGUA MINERAL NATURAL E ÁGUA NATURAL"), AGUAS_ADICIONADAS_DE_SAIS(4300164,
							"AGUAS ADICIONADAS DE SAIS"), ALIMENTOS_ALEGACOES_PROP_FUNC_SAUDE(4300032,
									"ALIMENTOS C/ALEGAÇOES DE PROPRIEDADES FUNCIONAL E OU DE SAUDE"), ALIMENTOS_INFANTIS(
											4300033, "ALIMENTOS INFANTIS"), ALIMENTOS_PARA_ATLETAS(4300085,
													"ALIMENTOS PARA ATLETAS"), ALIMENTOS_PARA_CONTROLE_DE_PESO(4300083,
															"ALIMENTOS PARA CONTROLE DE PESO"), ALIMENTOS_PARA_DIETAS_COM_INGESTAO_CONTROLADA_DE_ACUCARES(
																	4300086,
																	"ALIMENTOS PARA DIETAS COM INGESTAO CONTROLADA DE AÇUCARES"), ALIMENTOS_PARA_DIETAS_COM_RESTRIÇAO_DE_NUTRIENTES(
																			4300078,
																			"ALIMENTOS PARA DIETAS COM RESTRIÇAO DE NUTRIENTES"), ALIMENTOS_PARA_GESTANTES_E_NUTRIZES(
																					4300088,
																					"ALIMENTOS PARA GESTANTES E NUTRIZES"), ALIMENTOS_PARA_IDOSOS(
																							4300087,
																							"ALIMENTOS PARA IDOSOS"), ALIMENTOS_PARA_NUTRICAO_ENTERAL(
																									4200081,
																									"ALIMENTOS PARA NUTRIÇÃO ENTERAL"), COADJUVANTES_DE_TECNOLOGIA(
																											4200055,
																											"COADJUVANTES DE TECNOLOGIA"), EMBALAGENS(
																													4200071,
																													"EMBALAGENS"), EMBALAGENS_NOVAS_TECNOLOGIAS_RECICLADAS(
																															4300031,
																															"EMBALAGENS NOVAS TECNOLOGIAS ( RECICLADAS )"), NOVOS_ALIMENTOS_E_NOVOS_INGREDIENTES(
																																	4300030,
																																	"NOVOS ALIMENTOS E NOVOS INGREDIENTES"), SAL(
																																			4100204,
																																			"SAL"), SAL_HIPOSSODICO_SUCEDANEOS_DO_SAL(
																																					4200101,
																																					"SAL HIPOSSODICO/SUCEDANEOS DO SAL"), SUBSTANCIAS_BIOATIVAS_E_PROBIOTICOS_ISOLADOS_COM_ALEGACAO_DE_PROP_FUNC_SAUDE(
																																							4300090,
																																							"SUBSTANCIAS BIOATIVAS E PROBIOTICOS ISOLADOS COM ALEGAÇÃO DE PROP. FUNC. E/OU DE SAUDE."), SUPLEMENTO_VITAMINICO_MINERAL(
																																									4300041,
																																									"SUPLEMENTO VITAMINICO E OU MINERAL"), VEGETAIS_EM_CONSERVA_PALMITO_4300009(
																																											4300009,
																																											"VEGETAIS EM CONSERVA (PALMITO)"), VEGETAIS_EM_CONSERVA_PALMITO_4000009(
																																													4000009,
																																													"VEGETAIS EM CONSERVA (PALMITO)");

	private int id;
	private String description;

	private TypeCategory(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
