package com.anvisa.model.persistence.rest.saneante.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "saneante_notification_label")
public class SaneanteNotificationLabel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "value", length = 600)
	@JsonAlias(value = "valor")
	String valor;
	

	public SaneanteNotificationLabel() {
		// TODO Auto-generated constructor stub
	}

	public void setValor(String valor) {
		// TODO Auto-generated method stub
		this.valor = valor;
	}


	public String getValor() {
		// TODO Auto-generated method stub
		return valor;
	}
	

}
