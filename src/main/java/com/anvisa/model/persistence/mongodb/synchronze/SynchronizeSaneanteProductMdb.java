package com.anvisa.model.persistence.mongodb.synchronze;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.anvisa.model.persistence.mongodb.interceptor.synchronizedata.IntSynchronizeMdb;
import com.anvisa.model.persistence.mongodb.repository.SaneanteProductRepositoryMdb;
import com.anvisa.model.persistence.mongodb.repository.SynchronizeDataMdb;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProduct;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProductDetail;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteProductLabel;
import com.anvisa.model.persistence.mongodb.saneante.product.SaneanteStringListGeneric;
import com.anvisa.model.persistence.mongodb.sequence.SequenceDaoImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class SynchronizeSaneanteProductMdb extends SynchronizeDataMdb implements IntSynchronizeMdb {
	
	@Autowired
	public static SequenceDaoImpl sequence;
	
	@Autowired
	private static SaneanteProductRepositoryMdb seneanteProductRepository;


	@Autowired
	public void setService(SaneanteProductRepositoryMdb seneanteProductRepository, SequenceDaoImpl sequence) {

		this.seneanteProductRepository = seneanteProductRepository;
		
		this.sequence = sequence; 

	}

	public SynchronizeSaneanteProductMdb() {
		
		SEQ_KEY = "saneante_product";

		URL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3?count=10000&page=1&filter[cnpj]=";
		
		URL_DETAIL = "https://consultas.anvisa.gov.br/api/consulta/produtos/3/";

	}


	public SaneanteProduct parseData(JsonNode jsonNode) {
		// TODO Auto-generated method stub
		SaneanteProduct saneanteProduct = new SaneanteProduct();

		String codigo = JsonToObject.getValue(jsonNode,"produto","codigo");
		if (codigo!=null && !"".equals(codigo)) { 
		   saneanteProduct.setCodigo(Integer.parseInt(codigo));
	    }
		saneanteProduct.setProduto(JsonToObject.getValue(jsonNode,"produto","nome"));
		saneanteProduct.setRegistro(JsonToObject.getValue(jsonNode,"produto","numeroRegistro"));
		saneanteProduct.setProcesso(JsonToObject.getValue(jsonNode,"processo","numero"));
		saneanteProduct.setEmpresa(JsonToObject.getValue(jsonNode,"empresa","razaoSocial"));
		saneanteProduct.setCnpj(JsonToObject.getValue(jsonNode,"empresa","cnpj"));
		saneanteProduct.setSituacao(JsonToObject.getValue(jsonNode,"processo","situacao"));
		saneanteProduct.setVencimento(JsonToObject.getValue(jsonNode,"produto","dataVencimento"));
		saneanteProduct.setDataVencimento(JsonToObject.getValueDate(jsonNode,"produto","dataVencimentoRegistro"));
		
		
		saneanteProduct.setDataRegistro(JsonToObject.getValueDate(jsonNode,"produto","dataRegistro"));
		
/*		if (saneanteProduct.getDataVencimento() != null || saneanteProduct.getDataRegistro() != null) {

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
		}*/
		return saneanteProduct;
	}


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
	public ArrayList<BaseEntityMongoDB> loadData(String cnpj) {
		return super.loadData(this, cnpj);
	}

	
    public SaneanteProductDetail loadDetailData(String concat) {
		
    	SaneanteProductDetail rootObject = null;

		OkHttpClient client = new OkHttpClient();

		Request url = null;

		url = new Request.Builder().url(URL_DETAIL + concat).get().addHeader("authorization", "Guest")
				.addHeader("Accept-Encoding", "gzip").build();

		try {

			Response response = client.newCall(url).execute();

			ObjectMapper objectMapper = new ObjectMapper();

			if (response.code() == 500) {
				response.close();
				client = null;
				return null;
			}

			JsonNode rootNode = objectMapper.readTree(this.getGZIPString(response.body().byteStream()));

			if (rootNode != null) {

				rootObject = this.parseDetailData(rootNode);
			}
			response.close();
			client = null;
			return rootObject;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void persist(ArrayList<BaseEntityMongoDB> itens) {
		
		int cont = 0;
		
		for (Iterator<BaseEntityMongoDB> iterator = itens.iterator(); iterator.hasNext();) {

			SaneanteProduct baseEntity = (SaneanteProduct) iterator.next();

			SaneanteProduct localSaneanteProduct = seneanteProductRepository.findByProcesso(baseEntity.getProcesso(),
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
						//saneanteProductDetail.setId(localSaneanteProduct.getSaneanteProductDetail().getId());
						baseEntity.setSaneanteProductDetail(saneanteProductDetail);
					}
				}

				if (!localSaneanteProduct.equals(baseEntity)) {

					baseEntity.setId(localSaneanteProduct.getId());
					seneanteProductRepository.save(baseEntity);
				}

			} else {
				baseEntity.setId(this.sequence.getNextSequenceId(SEQ_KEY));
				baseEntity.setSaneanteProductDetail(saneanteProductDetail);
				seneanteProductRepository.save(baseEntity);
				
			}
			System.out.println(cont++);
		}
	}
}
