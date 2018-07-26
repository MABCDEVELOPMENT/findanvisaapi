package com.anvisa.model.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "user_register_cnpj")
public class UserRegisterCNPJ {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue
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
