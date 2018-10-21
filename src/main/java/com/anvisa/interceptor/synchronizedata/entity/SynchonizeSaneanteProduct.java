package com.anvisa.interceptor.synchronizedata.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.interceptor.synchronizedata.IntSynchronize;
import com.anvisa.interceptor.synchronizedata.SynchronizeData;
import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.saneante.product.SaneanteProduct;
import com.anvisa.model.persistence.rest.saneante.product.SaneanteProductDetail;
import com.anvisa.model.persistence.rest.saneante.product.SaneanteProductLabel;
import com.anvisa.model.persistence.rest.saneante.product.SaneanteStringListGeneric;
import com.anvisa.repository.generic.SaneanteProductRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SynchonizeSaneanteProduct extends SynchronizeData implements IntSynchronize {
	
	@Autowired
	private static SaneanteProductRepository seneanteProductRepository;


	@Autowired
	public void setService(SaneanteProductRepository seneanteProductRepository) {

		this.seneanteProductRepository = seneanteProductRepository;

	}

	public SynchonizeSaneanteProduct() {

		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3?count=10&page=1&filter[cnpj]=";
		
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3/";

	}

	@Override
	public SaneanteProduct parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteProduct saneanteProduct = new SaneanteProduct();

		saneanteProduct.setCodigo(JsonToObject.getValue(jsonNode,"produto","codigo"));
		saneanteProduct.setProduto(JsonToObject.getValue(jsonNode,"produto","nome"));
		saneanteProduct.setRegistro(JsonToObject.getValue(jsonNode,"produto","numeroRegistro"));
		saneanteProduct.setProcesso(JsonToObject.getValue(jsonNode,"processo","numero"));
		saneanteProduct.setEmpresa(JsonToObject.getValue(jsonNode,"empresa","razaoSocial"));
		saneanteProduct.setCnpj(JsonToObject.getValue(jsonNode,"empresa","cnpj"));
		saneanteProduct.setSituacao(JsonToObject.getValue(jsonNode,"processo","situacao"));
		saneanteProduct.setVencimento(JsonToObject.getValue(jsonNode,"produto","dataVencimento"));
		saneanteProduct.setDataVencimento(JsonToObject.getValueDate(jsonNode,"produto","dataVencimentoRegistro"));
		
		
		saneanteProduct.setDataRegistro(JsonToObject.getValueDate(jsonNode,"produto","dataRegistro"));
		
		if (saneanteProduct.getDataVencimento() != null || saneanteProduct.getDataRegistro() != null) {

			String strAno = saneanteProduct.getProcesso().substring(saneanteProduct.getProcesso().length() - 2);

			int ano = Integer.parseInt(strAno);

			if (ano >= 19 && ano <= 99) {
				ano = ano + 1900;
			} else {
				ano = ano + 2000;
			}
			
			LocalDate data =  saneanteProduct.getDataVencimento()==null?saneanteProduct.getDataRegistro():saneanteProduct.getDataVencimento();
			if (data!=null) {
				LocalDate dataAlteracao = LocalDate.of(ano, data.getMonthValue(),
						data.getDayOfMonth());
				saneanteProduct.setDataAlteracao(dataAlteracao);
			}	
		}
		return saneanteProduct;
	}

	@Override
	public SaneanteProductDetail parseDetailData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteProductDetail saneanteProductDetail = new SaneanteProductDetail();

		saneanteProductDetail.setProcesso(JsonToObject.getValue(jsonNode, "processo", "numero"));
		saneanteProductDetail
				.setClassesTerapeuticas(JsonToObject.getArrayValue(jsonNode, "classesTerapeuticas"));
		saneanteProductDetail.setCnpj(JsonToObject.getValue(jsonNode, "cnpj"));
		saneanteProductDetail.setNumeroAutorizacao(JsonToObject.getValue(jsonNode, "numeroAutorizacao"));
		saneanteProductDetail.setNomeComercial(JsonToObject.getValue(jsonNode, "nomeComercial"));
		saneanteProductDetail.setRazaoSocial(JsonToObject.getValue(jsonNode, "razaoSocial"));
		saneanteProductDetail.setRegistro(JsonToObject.getValue(jsonNode, "numeroRegistro"));
		saneanteProductDetail.setMesAnoVencimento(JsonToObject.getValue(jsonNode, "mesAnoVencimento"));
		saneanteProductDetail.loadApresentaçoes(jsonNode,"apresentacoes");
		
		List<SaneanteStringListGeneric> stringListGenericrotulos = JsonToObject.getArraySaneanteStringListGeneric(jsonNode, "rotulos");
		
		ArrayList<SaneanteProductLabel> rotulos = new ArrayList<SaneanteProductLabel>();
		
		for (SaneanteStringListGeneric saneanteStringListGeneric : stringListGenericrotulos) {
			rotulos.add(new SaneanteProductLabel(saneanteStringListGeneric.getValor()));
		}
		
		saneanteProductDetail.setRotulos(rotulos);
		
		/*		ArrayList<String> rotulos = contentDetalheSaneanteProduct.getRotulos();
		
		for (String rotulo : rotulos) {
			downloadLabel(contentDetalheSaneanteProduct.getProcesso(), rotulo);
		}*/
		

		return saneanteProductDetail;
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

			SaneanteProduct baseEntity = (SaneanteProduct) iterator.next();

			SaneanteProduct localSaneanteProduct = seneanteProductRepository.findByProcessCnpjRegistroVencimento(baseEntity.getProcesso(),
					baseEntity.getCnpj(), baseEntity.getCodigo(), baseEntity.getRegistro(),
					baseEntity.getDataVencimento());


			boolean newRegularized = (localSaneanteProduct == null);
			
			SaneanteProductDetail saneanteProductDetail = (SaneanteProductDetail) this.loadDetailData(baseEntity.getProcesso());
			
/*			if (saneanteProductDetail != null && (saneanteProductDetail.get!=null || baseEntity.getVencimento()!=null)) {
				
				String strAno = baseEntity.getProcesso().substring(baseEntity.getProcesso().length() - 2);

				int ano = Integer.parseInt(strAno);

				if (ano >= 19 && ano <= 99) {
					ano = ano + 1900;
				} else {
					ano = ano + 2000;
				}
				
				LocalDate data = baseEntity.getVencimento()==null?contentCosmeticRegularizedDetail.getData():baseEntity.getVencimento();
				
				LocalDate dataAlteracao = LocalDate.of(ano, data.getMonthValue(),
						data.getDayOfMonth());

				baseEntity.setDataAlteracao(dataAlteracao);

				baseEntity.setDataRegistro(contentCosmeticRegularizedDetail.getData());
				
			}*/


			if (!newRegularized) {
				
				if (localSaneanteProduct.getSaneanteProductDetail()!=null) {
					if (!saneanteProductDetail.equals(localSaneanteProduct.getSaneanteProductDetail())){
						saneanteProductDetail.setId(localSaneanteProduct.getSaneanteProductDetail().getId());
						baseEntity.setSaneanteProductDetail(saneanteProductDetail);
					}
				}

				if (!localSaneanteProduct.equals(baseEntity)) {

					baseEntity.setId(localSaneanteProduct.getId());
					seneanteProductRepository.saveAndFlush(baseEntity);
				}

			} else {
				baseEntity.setSaneanteProductDetail(saneanteProductDetail);
				seneanteProductRepository.saveAndFlush(baseEntity);
				
			}
			System.out.println(cont++);
		}
	}
}
