package com.anvisa;

import com.anvisa.interceptor.synchronizedata.SynchronizeDataTask;

public class ServletInitializer  {

/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FindAnvisaApplication.class);
	}
*/{SynchronizeDataTask.synchronizeData();}
	
}
