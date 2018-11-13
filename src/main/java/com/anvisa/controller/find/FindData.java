package com.anvisa.controller.find;

import com.anvisa.controller.mongodb.find.FindDataCosmeticNotificationMdb;
import com.anvisa.controller.mongodb.find.FindDataCosmeticRegisterMdb;
import com.anvisa.controller.mongodb.find.FindDataCosmeticRegularizedMdb;
import com.anvisa.controller.mongodb.find.FindDataFootMdb;
import com.anvisa.controller.mongodb.find.FindDataLogMdb;
import com.anvisa.controller.mongodb.find.FindDataProcessMdb;
import com.anvisa.controller.mongodb.find.FindDataSaneanteNotificationMdb;
import com.anvisa.controller.mongodb.find.FindDataSaneanteProductMdb;
import com.anvisa.rest.QueryRecordLogParameter;
import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.QueryRecordProcessParameter;
import com.anvisa.rest.RootObject;

public class FindData {
	
	static int CATEGORY_FOOT = 0;
	
	static int CATEGORY_COSMETIC = 1;
	static int COSMETIC_OPTION_REGISTER       = 0;
	static int COSMETIC_OPTION_NOTIFICATION   = 1;
	static int COSMETIC_OPTION_REGULARIZED 	  = 2;
	static int CATEGORY_SANEANTE = 2;
	static int SANEANTE_OPTION_PRODUCT = 0;
	static int SANEANTE_OPTION_NOTIFICATION = 1;
	
	
	
	
	
	public static RootObject find(QueryRecordParameter queryRecordParameter) {
		
		RootObject rootObject = new RootObject();
		
		if (queryRecordParameter.getCategory() == CATEGORY_FOOT) {
			rootObject.getContent().addAll(FindDataFootMdb.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_REGISTER) {
			rootObject.getContent().addAll(FindDataCosmeticRegisterMdb.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_NOTIFICATION) {
			rootObject.getContent().addAll(FindDataCosmeticNotificationMdb.find(queryRecordParameter));
		} else if (queryRecordParameter.getCategory() == CATEGORY_COSMETIC && queryRecordParameter.getOption() == COSMETIC_OPTION_REGULARIZED) {
			rootObject.getContent().addAll(new FindDataCosmeticRegularizedMdb().find(queryRecordParameter));		
		} else if (queryRecordParameter.getCategory() == CATEGORY_SANEANTE && queryRecordParameter.getOption() == SANEANTE_OPTION_PRODUCT) {
			rootObject.getContent().addAll(FindDataSaneanteProductMdb.find(queryRecordParameter));		
		} else if (queryRecordParameter.getCategory() == CATEGORY_SANEANTE && queryRecordParameter.getOption() == SANEANTE_OPTION_NOTIFICATION) {
			rootObject.getContent().addAll(FindDataSaneanteNotificationMdb.find(queryRecordParameter));		
		}

		
		return rootObject;
	}
	
	public static RootObject findLog(QueryRecordLogParameter queryRecordParameter) {
		RootObject rootObject = new RootObject();
		rootObject.getContent().addAll(FindDataLogMdb.find(queryRecordParameter));
		return rootObject;
	}
	
	public static RootObject findProcess(QueryRecordProcessParameter queryRecordParameter) {
	
		RootObject rootObject = new RootObject();
		rootObject.getContent().addAll(FindDataProcessMdb.find(queryRecordParameter));
		return rootObject;
	}

}
