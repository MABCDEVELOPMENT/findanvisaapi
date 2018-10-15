package com.anvisa.interceptor.synchronizedata.entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.Content;
import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
import com.anvisa.model.persistence.rest.foot.ContentFootDetail;
import com.anvisa.repository.generic.CosmeticNotificationRepository;
import com.anvisa.repository.generic.FootDetailRepository;
import com.anvisa.repository.generic.FootRepository;
import com.anvisa.rest.model.ContentProdutoNotificado;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SynchronizeCosmeticNotification extends SynchronizeData implements IntSynchronize {

	@Autowired
	private static CosmeticNotificationRepository cosmeticNotificationRepository;

	@Autowired
	public void setService(CosmeticNotificationRepository cosmeticNotificationRepository) {

		this.cosmeticNotificationRepository = cosmeticNotificationRepository;
		

	}

	public SynchronizeCosmeticNotification() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados?count=4000&page=1";

		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/cosmeticos/notificados/";

	}

	@Override
	public BaseEntity parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		ContentCosmeticNotification contentCosmeticNotification = new ContentCosmeticNotification();

		String assunto = JsonToObject.getAssunto(jsonNode);

		contentCosmeticNotification.setAssunto(assunto);

		contentCosmeticNotification.setProcesso(JsonToObject.getValue(jsonNode, "processo"));

		contentCosmeticNotification.setTransacao(JsonToObject.getValue(jsonNode, "transacao"));

		contentCosmeticNotification
				.setExpedienteProcesso(JsonToObject.getValue(jsonNode, "expedienteProcesso"));

		contentCosmeticNotification.setExpedientePeticao(JsonToObject.getValue(jsonNode, "expedientePeticao"));

		contentCosmeticNotification.setProduto(JsonToObject.getValue(jsonNode, "produto"));

		contentCosmeticNotification.setCnpj(JsonToObject.getValue(jsonNode, "empresa", "cnpj"));

		contentCosmeticNotification.setRazaoSocial(JsonToObject.getValue(jsonNode, "empresa", "razaoSocial"));

		contentCosmeticNotification.setSituacao(JsonToObject.getValue(jsonNode, "situacao", "situacao"));

		contentCosmeticNotification.setVencimento(JsonToObject.getValueDate(jsonNode, "vencimento"));

		return  contentCosmeticNotification;
		

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

		for (Iterator<BaseEntity> iterator = itens.iterator(); iterator.hasNext();) {

			ContentFoot BaseEntity = (ContentFoot) iterator.next();

			ContentFoot localFoot = cosmeticNotificationRepository.findByProcessCnpjCodigoRegistro(BaseEntity.getProcesso(),
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
					footRepository.saveAndFlush(BaseEntity);

				}

			} else {

				footRepository.saveAndFlush(BaseEntity);

			}

		}

	}

}
