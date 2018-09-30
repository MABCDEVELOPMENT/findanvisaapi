package com.anvisa.interceptor.synchronizedata.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.BaseEntityAudit;
import com.anvisa.model.persistence.rest.Content;
import com.anvisa.model.persistence.rest.foot.ContentDetalFoot;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.repository.generic.FootDetailRepository;
import com.anvisa.repository.generic.FootRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SynchronizeFoot extends SynchronizeData implements IntSynchronize {

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

		return contentProduto;
	}

	@Override
	public ContentDetalFoot parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentDetalFoot contentDetalFoot = new ContentDetalFoot();

		contentDetalFoot.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		contentDetalFoot.setClassesTerapeuticas(JsonToObject.getArrayValue(jsonNode, "classesTerapeuticas"));
		contentDetalFoot.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
		contentDetalFoot.setMarca(JsonToObject.getArrayValue(jsonNode, "marcas"));
		contentDetalFoot.setNomeComercial(JsonToObject.getValue(jsonNode, "nomeComercial"));
		contentDetalFoot.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
		contentDetalFoot.setRegistro(JsonToObject.getValue(jsonNode, "numeroRegistro"));
		contentDetalFoot.setMesAnoVencimento(JsonToObject.getValue(jsonNode, "mesAnoVencimento"));
		contentDetalFoot.setPrincipioAtivo(JsonToObject.getValue(jsonNode, "principioAtivo"));
		contentDetalFoot.setEmbalagemPrimaria(JsonToObject.getValue(jsonNode, "embalagemPrimaria", "tipo"));
		contentDetalFoot.setViasAdministrativa(JsonToObject.getArrayValue(jsonNode, "viasAdministracao"));
		String ifaUnico = JsonToObject.getValue(jsonNode, "ifaUnico");
		contentDetalFoot.setIfaUnico(ifaUnico.equals("true") ? "Sim" : "Não");
		contentDetalFoot.setConservacao(JsonToObject.getArrayValue(jsonNode, "conservacao"));

		return contentDetalFoot;
	}

	@Override
	public ArrayList<BaseEntityAudit> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	@Override
	public BaseEntityAudit loadDetailData(String concat) {
		return super.loadDetailData(this, concat);
	}

	@Override
	public void persist(ArrayList<BaseEntityAudit> itens) {

		for (Iterator<BaseEntityAudit> iterator = itens.iterator(); iterator.hasNext();) {

			ContentFoot BaseEntity = (ContentFoot) iterator.next();

			ContentFoot localFoot = footRepository.findByProcessCnpjCodigoRegistro(BaseEntity.getProcesso(),
					BaseEntity.getCnpj(), BaseEntity.getCodigo(), BaseEntity.getRegistro(),
					BaseEntity.getDataVencimento());

			boolean newFoot = (localFoot == null);

			ContentDetalFoot detail = (ContentDetalFoot) this.loadDetailData(BaseEntity.getProcesso());

			if (detail != null) {

				if (!newFoot) {

					if (localFoot.getContentDetalFoot() != null && !detail.equals(localFoot.getContentDetalFoot())) {
						detail.setId(localFoot.getContentDetalFoot().getId());
						footDetailRepository.saveAndFlush(detail);
						BaseEntity.setContentDetalFoot(detail);
					} else {
					    detail.setId(localFoot.getContentDetalFoot().getId());
					}    
				} else {

					footDetailRepository.saveAndFlush(detail);

					BaseEntity.setContentDetalFoot(detail);
				}

			}

			if (localFoot != null) {

				if (!localFoot.equals(BaseEntity)) {

					BaseEntity.setId(localFoot.getId());
					detail.setId(localFoot.getContentDetalFoot().getId());
					BaseEntity.setContentDetalFoot(detail);
					footRepository.saveAndFlush(BaseEntity);

				}

			} else {

				footRepository.saveAndFlush(BaseEntity);

			}

		}

	}

}
