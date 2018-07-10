package com.anvisa.model.persistence;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_register_cnpj")
public class UserRegisterCNPJ extends AbstractBaseEntity  {

	@ManyToOne
	private User user;

	@ManyToOne
	private RegisterCNPJ cnpj;

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

}
