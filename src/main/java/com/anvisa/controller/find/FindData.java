package com.anvisa.controller.find;

import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.RootObject;

public class FindData {
	
	static int CATEGORY_FOOT = 0;
	
	static int CATEGORY_COSMETIC = 1;
	static int COSMETIC_OPTION_REGISTER       = 0;
	static int COSMETIC_OPTION_NOTIFICATION   = 1;
	static int COSMETIC_OPTION_REGULARIZED 	  = 2;
	static int CATEGORY_SANEANTE = 2;
	static int SANEANTE_OPTION_PRODUCT = 0;
	
	
	
	
	public static RootObject find(QueryRecordParameter queryRecordParameter) {
		
		RootObject rootObject = new RootObject();
		
		if (queryRecordParameter.getCategory() == CATEGORY_FOOT) {
			rootObject.getContent().addAll(FindDataFoot.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_REGISTER) {
			rootObject.getContent().addAll(FindDataCosmeticRegister.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_NOTIFICATION) {
			rootObject.getContent().addAll(FindDataCosmeticNotification.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_REGULARIZED) {
			rootObject.getContent().addAll(FindDataCosmeticRegularized.find(queryRecordParameter));		
		} else if (queryRecordParameter.getCategory() == CATEGORY_SANEANTE && queryRecordParameter.getOption() == SANEANTE_OPTION_PRODUCT) {
			rootObject.getContent().addAll(FindDataSaneanteProduct.find(queryRecordParameter));		
		}

		
		return rootObject;
	}

}
