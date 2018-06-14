package com.anvisa.core.type;

public enum TypeSearchProductCosmetic {

	SEARCH_REGISTERED("registrados"), SEARCH_NOTIFIED("notificados"), SEARCH_REGULARIZED("regularizados");

	private final String type;

	TypeSearchProductCosmetic(String type) {
		this.type = type;
	}

}
