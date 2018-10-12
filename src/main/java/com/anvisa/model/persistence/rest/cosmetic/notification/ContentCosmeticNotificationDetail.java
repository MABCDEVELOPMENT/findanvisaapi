package com.anvisa.model.persistence.rest.cosmetic.notification;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.rest.detalhe.comestico.notificado.ApresentacaoCosmeticoNotificado;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cosmetic_notification_detail")
public class ContentCosmeticNotificationDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "subject_matter", length = 300, nullable = false)
	@JsonAlias(value = "assunto")
	String assunto;
	
	@Column(name = "product", length = 300, nullable = false)
	@JsonAlias(value = "produto")
	String produto;
	
	@Column(name = "company", length = 200, nullable = true)
	@JsonAlias(value = "empresa")
	String empresa;
	
	@Column(name = "process", length = 20, nullable = false)
	@JsonAlias(value = "processo")
	String processo;
	

	@Column(name = "area", length = 20, nullable = false)
	@JsonAlias(value = "area")
	String area;
	
	@Column(name = "situation", length = 300, nullable = false)
	@JsonAlias(value = "situacao")
	String situacao;

	@Column(name = "date_notification", length = 8, nullable = true)
	@JsonAlias(value = "dataNotificacao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	LocalDate dataNotificacao;
	
	
	ArrayList<ApresentacaoCosmeticoNotificado> apresentacoes;

}
