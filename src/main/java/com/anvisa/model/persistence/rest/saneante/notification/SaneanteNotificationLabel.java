package com.anvisa.model.persistence.rest.saneante.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anvisa.model.persistence.BaseEntity;
import com.anvisa.model.persistence.rest.IStringListGeneric;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "saneante_notification_label")
public class SaneanteNotificationLabel extends BaseEntity implements IStringListGeneric {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "value", length = 300)
	@JsonAlias(value = "valor")
	String valor;
	
	public SaneanteNotificationLabel(String strRotulo) {
		// TODO Auto-generated constructor stub
		this.valor = valor;
	}

	public SaneanteNotificationLabel() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setValor(String valor) {
		// TODO Auto-generated method stub
		this.valor = valor;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return valor;
	}
	

}
