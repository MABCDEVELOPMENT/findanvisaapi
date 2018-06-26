package com.anvisa.core.type;

public enum TypeProduct {

	FOOD(1, "FOOD_PRODUCT"), SANEANTE(2, "SANEANTE"), COSMETIC(3, "COSMETIC");

	private final int id;
	private final String type;

	TypeProduct(int id, String type) {
		this.type = type;
		this.id = id;
	}
}
