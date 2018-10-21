package com.anvisa.model.persistence.rest.saneante.notification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Entity
@Table(name = "saneante_notification_detail")
public class SaneanteNotificationDetail extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "subject_matter", length = 300)
	@JsonAlias(value = "assunto")
	String assunto;
	
	
	@Column(name = "product", length = 300)
	@JsonAlias(value = "produto")
	String produto;
	
	@Column(name = "company", length = 200)
	@JsonAlias(value = "empresa")
	String empresa;
	
	@Column(name = "process", length = 20)
	@JsonAlias(value = "processo")
	String processo;

	@Column(name = "area", length = 300)
	@JsonAlias(value = "processo")
	String area;
	
	@Column(name = "situation", length = 300)
	@JsonAlias(value = "situacao")
	String situacao;
	
	@Column(name = "dateNotificacation")
	@JsonAlias(value = "dataNotificacao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate dataNotificacao;
	
	@OneToMany(cascade=CascadeType.ALL)
	List<SaneanteNotificationPresentation> apresentacoes;
	
	@OneToMany(cascade=CascadeType.ALL)
	List<SaneanteNotificadoPetition> peticoes;
	
	@OneToMany(cascade=CascadeType.ALL)
	List<SaneanteNotificationLabel> rotulos;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public LocalDate getDataNotificacao() {
		return dataNotificacao;
	}

	public void setDataNotificacao(LocalDate dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}

	public List<SaneanteNotificationPresentation> getApresentacoes() {
		return apresentacoes;
	}

	public void setApresentacoes(ArrayList<SaneanteNotificationPresentation> apresentacoes) {
		this.apresentacoes = apresentacoes;
	}

	public List<SaneanteNotificadoPetition> getPeticoes() {
		return peticoes;
	}

	public void setPeticoes(ArrayList<SaneanteNotificadoPetition> peticoes) {
		this.peticoes = peticoes;
	}
	
	public List<SaneanteNotificationLabel> getRotulos() {
		return rotulos;
	}

	public void setRotulos(ArrayList<SaneanteNotificationLabel> rotulos) {
		this.rotulos = rotulos;
	}

	public void setApresentacoes(JsonNode node, String attribute) {

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		ArrayList<SaneanteNotificationPresentation> apresentacoes = new ArrayList<SaneanteNotificationPresentation>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				SaneanteNotificationPresentation apresentacaoSaneanteNotificado = new SaneanteNotificationPresentation();

				apresentacaoSaneanteNotificado.setApresentacao(JsonToObject.getValue(nodeIt, "apresentacao"));
				apresentacaoSaneanteNotificado.setTonalidade(JsonToObject.getValue(nodeIt, "versao"));

				apresentacaoSaneanteNotificado.setEans(JsonToObject.getArraySaneanteNotificationEanValue(nodeIt, "eans"));

				apresentacoes.add(apresentacaoSaneanteNotificado);

			}

		}

		this.setApresentacoes(apresentacoes);

	}

	public void setPeticoes(JsonNode node, String attribute) {

		ArrayNode element = (ArrayNode) node.findValue(attribute);

		ArrayList<SaneanteNotificadoPetition> peticoes = new ArrayList<SaneanteNotificadoPetition>();

		if (element != null) {

			for (Iterator<JsonNode> it = element.iterator(); it.hasNext();) {

				JsonNode nodeIt = it.next();

				SaneanteNotificadoPetition peticaoCosmeticoRegistrado = new SaneanteNotificadoPetition();

				peticaoCosmeticoRegistrado.setExpediente(JsonToObject.getValue(nodeIt, "expediente"));
				peticaoCosmeticoRegistrado.setPublicacao(JsonToObject.getValueDate(nodeIt, "publicacao"));
				peticaoCosmeticoRegistrado.setTransacao(JsonToObject.getValue(nodeIt, "transacao"));

				String assunto = JsonToObject.getValue(nodeIt, "assunto", "codigo") + " "
						+ JsonToObject.getValue(nodeIt, "assunto", "descricao");
				peticaoCosmeticoRegistrado.setAssunto(assunto);

				String situacao = JsonToObject.getValue(nodeIt, "situacao", "situacao") + " "
						+ JsonToObject.getValueDateToString(nodeIt, "situacao", "data");
				peticaoCosmeticoRegistrado.setSituacao(situacao);

				peticoes.add(peticaoCosmeticoRegistrado);

			}

		}

		this.setPeticoes(peticoes);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apresentacoes == null) ? 0 : apresentacoes.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((dataNotificacao == null) ? 0 : dataNotificacao.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((peticoes == null) ? 0 : peticoes.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((rotulos == null) ? 0 : rotulos.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SaneanteNotificationDetail)) {
			return false;
		}
		SaneanteNotificationDetail other = (SaneanteNotificationDetail) obj;
		if (apresentacoes == null) {
			if (other.apresentacoes != null)
				return false;
		} else if (!apresentacoes.equals(other.apresentacoes))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (dataNotificacao == null) {
			if (other.dataNotificacao != null)
				return false;
		} else if (!dataNotificacao.equals(other.dataNotificacao))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (peticoes == null) {
			if (other.peticoes != null)
				return false;
		} else if (!peticoes.equals(other.peticoes))
			return false;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (rotulos == null) {
			if (other.rotulos != null)
				return false;
		} else if (!rotulos.equals(other.rotulos))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		return true;
	}
	
	
}
