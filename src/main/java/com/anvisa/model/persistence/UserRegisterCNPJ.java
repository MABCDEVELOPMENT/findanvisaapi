package com.anvisa.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "user_register_cnpj")
public class UserRegisterCNPJ extends AbstractBaseEntity  {

	
	@Id
    @GeneratedValue
	@Column(name = "ID", nullable = false)
	public Long id;
	
	@ManyToOne
	private User user;

	@ManyToOne
	private RegisterCNPJ cnpj;
	
	@Column(name = "sendNotification")
	@JsonAlias(value = "sendNotification")
	private boolean sendNotification;

		
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
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
