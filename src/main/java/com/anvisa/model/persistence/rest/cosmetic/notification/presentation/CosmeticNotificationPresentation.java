package com.anvisa.model.persistence.rest.cosmetic.notification.presentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "cosmetic_notification_presemtaton")
public class CosmeticNotificationPresentation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "presentation", length = 200, nullable = false)
	@JsonAlias(value = "apresentacao")
	String apresentacao;
	
	@Column(name = "tonality", length = 100)
	@JsonAlias(value = "tonalidade")
    String tonalidade;

	@Column(name = "eans", length = 100)
	@JsonAlias(value = "eans")
    String eans;

	@Column(name = "only_exportation")
	@JsonAlias(value = "apenasExportacao")
    boolean apenasExportacao;

	@Column(name = "only_import")
	@JsonAlias(value = "rotuloAprovado")
    boolean rotuloAprovado;
}
