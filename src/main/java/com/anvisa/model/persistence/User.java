package com.anvisa.model.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "user")
public class User extends AbstractBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_name", length = 40, nullable = false, unique = true)
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

	@Column(name = "perfil", length = 2, nullable = false)
	@JsonAlias(value = "perfil")
	private String perfil;

	@ManyToMany
	@JoinTable(name = "user_register_cnpj", joinColumns = {
			@JoinColumn(name = "user_id", unique = true) }, inverseJoinColumns = {
					@JoinColumn(name = "cnpj_id", unique = true) })
	private List<RegisterCNPJ> registerCNPJs;

	public List<RegisterCNPJ> getRegisterCNPJs() {
		return this.registerCNPJs;
	}

	public void setRegisterCNPJs(List<RegisterCNPJ> registerCNPJs) {
		this.registerCNPJs = registerCNPJs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
