package com.anvisa.model.persistence;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.anvisa.model.persistence.mongodb.BaseEntityMongoDB;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "RegisterCNPJ")
public class RegisterCNPJ extends BaseEntity  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "cnpj", length = 14, unique=true, nullable = false)
	@JsonAlias(value = "cnpj")
	private String cnpj;

	@Column(name = "full_name", length = 60, nullable = false)
	@JsonAlias(value = "fullName")
	@OrderBy
	private String fullName;

	@Column(name = "category", length = 1, nullable = false)
	@JsonAlias(value = "category")
	private int category;
	
	@Transient
	private boolean sendNotification;
	
	@Transient
	private boolean foot;
	
	@Transient
	private boolean cosmetic;
	
	@Transient
	private boolean saneante;

	@Transient
	ArrayList<BaseEntityMongoDB> itensImport;
	
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public ArrayList<BaseEntityMongoDB> getItensImport() {
		return itensImport;
	}

	public void setItensImport(ArrayList<BaseEntityMongoDB> itensImport) {
		this.itensImport = itensImport;
	}

	public void loadCategory() {
		
		if (this.category == 0) {
			this.foot = true;
		} else if (this.category == 1) {
			this.cosmetic = true;
		} else if (this.category == 2) {
			this.saneante = true;
		} else if (this.category == 3) {
			this.foot = this.cosmetic = this.saneante = true;
		}
	}
}
