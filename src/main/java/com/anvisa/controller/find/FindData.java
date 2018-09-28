package com.anvisa.controller.find;

import com.anvisa.rest.QueryRecordParameter;
import com.anvisa.rest.RootObject;

public class FindData {
	
	static int CATEGORY = 0;
	
	
	
	public static RootObject find(QueryRecordParameter queryRecordParameter) {
		
		RootObject rootObject = new RootObject();
		
		if (queryRecordParameter.getCategory() == CATEGORY) {
			rootObject.getContent().addAll(FindDataFoot.find(queryRecordParameter));
		}
		
		return rootObject;
	}

}
