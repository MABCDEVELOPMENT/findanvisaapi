package com.anvisa.core.type;

public enum TypeArea {
	ALIMENTO("Alimentos", 6), AUTORIZAÇÕES("Autorizações", 7), COSMETICO("Cosmeticos", 2), DERIVADO_DO_TABACO(
			"Derivado de Tabaco", 10), MEDICAMENTO("Medicamentos", 1), PORTOS_AREOPORTOS_FRONTEIRAS(
					"Portos,Aeroportos e Fronteitas", 11), PRODUTOS_SAUDE("Produtos para saúde(Correlatos)",
							8), PRODUTOS_IN_VITRO("Produtos para diognóstico para uso in vitro",
									5), SANEANTES("Saneantes", 12), TOXICOLOGIA("Toxicologia", 9);

	private final String type;

	private final int id;

	TypeArea(String type, int id) {
		this.type = type;
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}
}
