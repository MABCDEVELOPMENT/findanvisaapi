package com.anvisa.model.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "user")
public class User extends BaseEntity  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_name", length = 40, nullable = true)
	@JsonAlias(value = "userName")
	private String userName;

	@Column(name = "full_name", length = 60, nullable = false)
	@JsonAlias(value = "fullName")
	private String fullName;

	@Column(name = "email", length = 255, nullable = false, unique = true)
	@JsonAlias(value = "email")
	private String email;

	@Column(name = "cell_phone", length = 14, nullable = true)
	@JsonAlias(value = "cellPhone")
	private String cellPhone;

	@Column(name = "date_brith", nullable = true)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias(value = "dateBrith")
	private Date dateBrith;

	@Column(name = "password", length = 1, nullable = true)
	@JsonAlias(value = "password")
	private String password;

	@Column(name = "profile", length = 2, nullable = true)
	@JsonAlias(value = "profile")
	private Integer profile = 0;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_register_cnpj", joinColumns = {
			@JoinColumn(name = "user_id", unique = true, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "cnpj_id", unique = true, updatable = false) })
	@OrderBy(value = "fullName ASC")
	private List<RegisterCNPJ> registerCNPJs;
	
	@Column(name = "receive_activation", length = 2, nullable = true)
	@JsonAlias(value = "receveActivation")
	private boolean receiveActivation;
	
	public List<RegisterCNPJ> getRegisterCNPJs() {
		if (this.registerCNPJs == null) {
			this.registerCNPJs = new ArrayList<RegisterCNPJ>();
		}
		return this.registerCNPJs;
	}

	public void setRegisterCNPJs(List<RegisterCNPJ> registerCNPJs) {
		this.registerCNPJs = registerCNPJs;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Date getDateBrith() {
		return dateBrith;
	}

	public void setDateBrith(Date dateBrith) {
		this.dateBrith = dateBrith;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getProfile() {
		return profile;
	}

	public void setProfile(Integer profile) {
		this.profile = profile;
	}

	public boolean isReceiveActivation() {
		return receiveActivation;
	}

	public void setReceiveActivation(boolean receiveActivation) {
		this.receiveActivation = receiveActivation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cellPhone == null) ? 0 : cellPhone.hashCode());
		result = prime * result + ((dateBrith == null) ? 0 : dateBrith.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + (receiveActivation ? 1231 : 1237);
		result = prime * result + ((registerCNPJs == null) ? 0 : registerCNPJs.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (cellPhone == null) {
			if (other.cellPhone != null)
				return false;
		} else if (!cellPhone.equals(other.cellPhone))
			return false;
		if (dateBrith == null) {
			if (other.dateBrith != null)
				return false;
		} else if (!dateBrith.equals(other.dateBrith))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (receiveActivation != other.receiveActivation)
			return false;
		if (registerCNPJs == null) {
			if (other.registerCNPJs != null)
				return false;
		} else if (!registerCNPJs.equals(other.registerCNPJs))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	

}
