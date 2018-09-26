package com.anvisa.interceptor.synchronizedata.foot;

import java.util.ArrayList;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.AbstractBaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.anvisa.model.persistence.rest.foot.ContentDetalFoot;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.repository.generic.FootRepository;
import com.fasterxml.jackson.databind.JsonNode;


public class SynchronizeFoot extends SynchronizeData  implements IntSynchronize {
	
	//public static String URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=1000&page=1&cnpj=[cnpj]";

	public FootRepository repository; 

	public SynchronizeFoot(FootRepository repository) {
		// TODO Auto-generated constructor stub
		this.repository = repository;
		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?page=1&filter[cnpj]=";
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6/";
	}

	@Override
	public ContentFoot parseData(JsonNode jsonNode) {
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
	public ContentDetalFoot parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentDetalFoot contentDetalFoot = new ContentDetalFoot();

		contentDetalFoot.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		contentDetalFoot
				.setClassesTerapeuticas(JsonToObject.getArrayValue(jsonNode, "classesTerapeuticas"));
		contentDetalFoot.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
		contentDetalFoot.setMarca(JsonToObject.getArrayValue(jsonNode, "marcas"));
		contentDetalFoot.setNomeComercial(JsonToObject.getValue(jsonNode, "nomeComercial"));
		contentDetalFoot.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
		contentDetalFoot.setRegistro(JsonToObject.getValue(jsonNode, "numeroRegistro"));
		contentDetalFoot.setMesAnoVencimento(JsonToObject.getValue(jsonNode, "mesAnoVencimento"));
		contentDetalFoot.setPrincipioAtivo(JsonToObject.getValue(jsonNode, "principioAtivo"));
		contentDetalFoot
				.setEmbalagemPrimaria(JsonToObject.getValue(jsonNode, "embalagemPrimaria", "tipo"));
		contentDetalFoot
				.setViasAdministrativa(JsonToObject.getArrayValue(jsonNode, "viasAdministracao"));
		String ifaUnico = JsonToObject.getValue(jsonNode, "ifaUnico");
		contentDetalFoot.setIfaUnico(ifaUnico.equals("true") ? "Sim" : "Não");
		contentDetalFoot.setConservacao(JsonToObject.getArrayValue(jsonNode, "conservacao"));

		
		
		return contentDetalFoot;
	}


	@Override
	public ArrayList<AbstractBaseEntity> loadData(String cnpj) {
		// TODO Auto-generated method stub
		 ArrayList<AbstractBaseEntity> list = super.loadData(this, cnpj);
		 ArrayList<AbstractBaseEntity> listReturn = new ArrayList<AbstractBaseEntity>();
		 
		 for (AbstractBaseEntity abstractBaseEntity : list) {
			 
			 ContentFoot foot = (ContentFoot) abstractBaseEntity;
			 
			 ContentDetalFoot detailFoot = (ContentDetalFoot) this.loadDetailData(foot.getProcesso());
			
			 foot.setContentDateil(detailFoot);
			 
			 listReturn.add(foot); 
			 
		 }
		 
		return listReturn;
	}

	@Override
	public AbstractBaseEntity loadDetailData(String concat) {
		// TODO Auto-generated method stub
		return super.loadDetailData(this, concat);
	}


	

	
	
}
