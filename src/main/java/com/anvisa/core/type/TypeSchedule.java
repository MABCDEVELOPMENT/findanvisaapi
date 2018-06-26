package com.anvisa.core.type;

public enum TypeSchedule {

	DIARIO("Diário", 1), SEMANAL("Semanal", 2), INTEVALO("Intervalo", 3), INTEVALO_DIA("Intervalo",
			4), INTEVALO_HORA("Intervalo", 5);

	private final String type;

	private final int id;

	TypeSchedule(String type, int id) {
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
