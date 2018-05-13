package com.anvisa.core.type;

public enum TypeSearchProduct {

	SEARCH_REGISTERED("registrados"), SEARCH_NOTIFIED("notificados"), SEARCH_REGULARIZED("regularizados");

	private final String type;

	TypeSearchProduct(String type) {
		this.type = type;
	}

}
