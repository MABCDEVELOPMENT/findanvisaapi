package com.anvisa.controller.find;

import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.RootObject;

public class FindData {
	
	static int CATEGORY_FOOT = 0;
	
	static int CATEGORY_COSMETIC = 1;
	static int COSMETIC_OPTION_REGISTER = 0;
	
	
	
	public static RootObject find(QueryRecordParameter queryRecordParameter) {
		
		RootObject rootObject = new RootObject();
		
		if (queryRecordParameter.getCategory() == CATEGORY_FOOT) {
			rootObject.getContent().addAll(FindDataFoot.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_REGISTER) {
			rootObject.getContent().addAll(FindDataCosmeticRegister.find(queryRecordParameter));
		}
		
		return rootObject;
	}

}
