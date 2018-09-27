package com.anvisa.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_register_cnpj")
public class UserRegisterCNPJ extends BaseEntityAudit {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private User user;

	@ManyToOne
	private RegisterCNPJ cnpj;
	
	@Column(name = "sendNotification", nullable = false,   columnDefinition = "boolean default false")
	private boolean sendNotification = false;
	
	@Column(name = "foot", nullable = false,   columnDefinition = "boolean default false")
	private boolean foot = false;
	
	@Column(name = "cosmetic", nullable = false, columnDefinition = "boolean default false")
	private boolean cosmetic = false;
	
	@Column(name = "saneante", nullable = false, columnDefinition = "boolean default false")
	private boolean saneante = false ;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RegisterCNPJ getCnpj() {
		return cnpj;
	}

	public void setCnpj(RegisterCNPJ cnpj) {
		this.cnpj = cnpj;
	}
	
	public boolean isSendNotification() {
		return sendNotification;
	}

	public void setSendNotification(boolean sendNotification) {
		this.sendNotification = sendNotification;
	}

	public boolean isFoot() {
		return foot;
	}

	public void setFoot(boolean foot) {
		this.foot = foot;
	}

	public boolean isCosmetic() {
		return cosmetic;
	}

	public void setCosmetic(boolean cosmetic) {
		this.cosmetic = cosmetic;
	}

	public boolean isSaneante() {
		return saneante;
	}

	public void setSaneante(boolean saneante) {
		this.saneante = saneante;
	}


}
