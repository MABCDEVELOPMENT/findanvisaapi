package com.anvisa.interceptor.synchronizedata.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.model.persistence.rest.foot.ContentFootDetail;
import com.anvisa.repository.generic.FootDetailRepository;
import com.anvisa.repository.generic.FootRepository;
import com.fasterxml.jackson.databind.JsonNode;

public class SynchonizeSaneanteProduct {
	
	@Autowired
	private static FootRepository footRepository;

	@Autowired
	private static FootDetailRepository footDetailRepository;

	@Autowired
	public void setService(FootRepository footRepository, FootDetailRepository footDetailRepository) {

		this.footRepository = footRepository;
		this.footDetailRepository = footDetailRepository;

	}

	public SynchronizeFoot() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=2000&page=1&filter[cnpj]=";

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
		
		contentProduto.setDataRegistro(JsonToObject.getValueDate(jsonNode,"dataRegistro"));
		contentProduto.setDataVencimento(JsonToObject.getValueDate(jsonNode,"dataVencimentoRegistro"));
		
		String strAno = contentProduto.getProcesso().substring(contentProduto.getProcesso().length()-2);
		
		int ano = Integer.parseInt(strAno);
		
		if (ano>=19 && ano<=99) {
			ano = ano + 1900;
		} else {
			ano = ano + 2000;
		}
		
		LocalDate dataAlteracao =  LocalDate.of(ano,contentProduto.getDataVencimento().getMonthValue(),contentProduto.getDataVencimento().getDayOfMonth()); 
		contentProduto.setDataAlteracao(dataAlteracao);

		return contentProduto;
	}

	@Override
	public ContentFootDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentFootDetail contentFootDetail = new ContentFootDetail();

		contentFootDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		contentFootDetail.setClassesTerapeuticas(JsonToObject.getArrayValue(jsonNode, "classesTerapeuticas"));
		contentFootDetail.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
		contentFootDetail.setMarca(JsonToObject.getArrayValue(jsonNode, "marcas"));
		contentFootDetail.setNomeComercial(JsonToObject.getValue(jsonNode, "nomeComercial"));
		contentFootDetail.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
		contentFootDetail.setRegistro(JsonToObject.getValue(jsonNode, "numeroRegistro"));
		contentFootDetail.setMesAnoVencimento(JsonToObject.getValue(jsonNode, "mesAnoVencimento"));
		contentFootDetail.setPrincipioAtivo(JsonToObject.getValue(jsonNode, "principioAtivo"));
		contentFootDetail.setEmbalagemPrimaria(JsonToObject.getValue(jsonNode, "embalagemPrimaria", "tipo"));
		contentFootDetail.setViasAdministrativa(JsonToObject.getArrayValue(jsonNode, "viasAdministracao"));
		String ifaUnico = JsonToObject.getValue(jsonNode, "ifaUnico");
		contentFootDetail.setIfaUnico(ifaUnico.equals("true") ? "Sim" : "Não");
		contentFootDetail.setConservacao(JsonToObject.getArrayValue(jsonNode, "conservacao"));

		return contentFootDetail;
	}

	@Override
	public ArrayList<BaseEntity> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	@Override
	public BaseEntity loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}

	@Override
	public void persist(ArrayList<BaseEntity> itens) {
		int cont = 0;
		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			ContentFoot BaseEntity = (ContentFoot) iterator.next();

			ContentFoot localFoot = footRepository.findByProcessCnpjCodigoRegistro(BaseEntity.getProcesso(),
					BaseEntity.getCnpj(), BaseEntity.getCodigo(), BaseEntity.getRegistro(),
					BaseEntity.getDataVencimento());

			boolean newFoot = (localFoot == null);

			ContentFootDetail detail = (ContentFootDetail) this.loadDetailData(BaseEntity.getProcesso());

			if (detail != null) {

				if (!newFoot) {

					if (localFoot.getContentFootDetail() != null && !detail.equals(localFoot.getContentFootDetail())) {
						detail.setId(localFoot.getContentFootDetail().getId());
						footDetailRepository.saveAndFlush(detail);
						BaseEntity.setContentFootDetail(detail);
					} else {
					    detail.setId(localFoot.getContentFootDetail().getId());
					}    
				} else {

					footDetailRepository.saveAndFlush(detail);

					BaseEntity.setContentFootDetail(detail);
				}

			}

			if (localFoot != null) {

				if (!localFoot.equals(BaseEntity)) {

					BaseEntity.setId(localFoot.getId());
					detail.setId(localFoot.getContentFootDetail().getId());
					BaseEntity.setContentFootDetail(detail);
					footRepository.save(BaseEntity);

				}

			} else {

				footRepository.save(BaseEntity);

			}
			
			System.out.println(cont++);
			
		}
		
		
	}

}
