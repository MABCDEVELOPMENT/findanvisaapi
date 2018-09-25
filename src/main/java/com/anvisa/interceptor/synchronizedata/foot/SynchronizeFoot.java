package com.anvisa.interceptor.synchronizedata.foot;

import java.util.ArrayList;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.AbstractBaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.fasterxml.jackson.databind.JsonNode;


public class SynchronizeFoot extends SynchronizeData  implements IntSynchronize {
	
	public static String URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?cnpj=[cnpj]";

	

	@Override
	public AbstractBaseEntity parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		Content content = new Content();

		content.setOrdem(JsonToObject.getOrdem(jsonNode));

		content.setEmpresa(JsonToObject.getEmpresa(jsonNode));

		content.setProcesso(JsonToObject.getProcessoProduto(jsonNode));

		content.setProduto(JsonToObject.getProduto(jsonNode));

		ContentFoot contentProduto = new ContentFoot(content);

		
		return contentProduto;
	}



	@Override
	public ArrayList<AbstractBaseEntity> loadData(String cnpj) {
		// TODO Auto-generated method stub
		return super.loadData(this, cnpj);
	}


	

	
	
}
