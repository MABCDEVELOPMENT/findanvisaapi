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
import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegister;
import com.anvisa.model.persistence.rest.foot.ContentDetalFoot;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.repository.generic.CosmeticRegisterRepository;
import com.anvisa.repository.generic.FootDetailRepository;
import com.anvisa.repository.generic.FootRepository;
import com.anvisa.rest.model.Assunto;
import com.anvisa.rest.model.ContentProdutoRegistrado;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SynchronizeCosmeticRegister extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static CosmeticRegisterRepository cosmeticRegisterRepository;

	@Autowired
	public void setService(CosmeticRegisterRepository cosmeticRegisterRepository) {

		this.cosmeticRegisterRepository = cosmeticRegisterRepository;

	}

	public SynchronizeCosmeticRegister() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6?count=2000&page=1&filter[cnpj]=";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/6/";

	}

	@Override
	public ContentCosmeticRegister parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticRegister contentCosmeticRegister = new ContentCosmeticRegister();

		Assunto assunto = JsonToObject.getAssunto(jsonNode);

		contentCosmeticRegister.setAssunto(assunto.toString());

		contentCosmeticRegister.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticRegister.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		contentCosmeticRegister.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expediente"));

		// contentProdutoRegistrado.setExpedientePeticao(JsonToObject.getValue(jsonNode,
		// "expedientePeticao"));

		contentCosmeticRegister.setProduto(JsonToObject.getValue(jsonNode, "nomeProduto"));

		contentCosmeticRegister.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		contentCosmeticRegister.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		contentCosmeticRegister.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		contentCosmeticRegister
				.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento", "vencimento"));


		return contentCosmeticRegister;
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

			ContentCosmeticRegister BaseEntity = (ContentCosmeticRegister) iterator.next();

			ContentCosmeticRegister localFoot = cosmeticRegisterRepository.findByProcessCnpjVencimento(BaseEntity.getProcesso(),
					BaseEntity.getCnpj(), BaseEntity.getVencimento());

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
