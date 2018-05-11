package com.anvisa.core.type;

public enum TypeSearch {

	FOOD_PRODUCT("FOOD_PRODUCT"), SANEANTE_PRODUCT("SANEANTE_PRODUCT"), PROCESS("PROCESS");

	private final String type;

	TypeSearch(String type) {
		this.type = type;
	}
}
