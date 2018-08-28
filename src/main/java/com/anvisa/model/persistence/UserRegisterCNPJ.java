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
public class UserRegisterCNPJ {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public Long id;
	
	@ManyToOne
	private User user;

	@ManyToOne
	private RegisterCNPJ cnpj;
	
	@Column(name = "sendNotification")
	private boolean sendNotification;
	
	@Column(name = "foot")
	private boolean foot;
	
	@Column(name = "cosmetic")
	private boolean cosmetic;
	
	@Column(name = "saneante")
	private boolean saneante;
	

		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
