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


}
