package com.anvisa.model.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "GenericParameter")
public class GenericParameter extends AbstractBaseEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	@JsonAlias(value = "id")
	private Long id;

	@Column(name = "version", length = 20, nullable = false, unique = false)
	@JsonAlias(value = "version")
	private String version;

	@Column(name = "systemName", length = 40, nullable = false, unique = false)
	@JsonAlias(value = "systemName")
	private String systemName;

	@Column(name = "socialName", length = 50, nullable = false, unique = false)
	@JsonAlias(value = "socialName")
	private String socialName;

	@Column(name = "cnpj", length = 14, nullable = false, unique = false)
	@JsonAlias(value = "cnpj")
	private Long cnpj;

	@Column(name = "codeZip", length = 8, nullable = false, unique = false)
	@JsonAlias(value = "codeZip")
	private Integer codeZip;

	@Column(name = "address", length = 60, nullable = false, unique = false)
	@JsonAlias(value = "address")
	private String address;

	@Column(name = "number", length = 10, nullable = true, unique = false)
	@JsonAlias(value = "number")
	private String number;

	@Column(name = "neighborhood", length = 30, nullable = true, unique = false)
	@JsonAlias(value = "neighborhood")
	private String neighborhood;

	@Column(name = "city", length = 30, nullable = true, unique = false)
	@JsonAlias(value = "city")
	private String city;

	@Column(name = "state", length = 2, nullable = true, unique = false)
	@JsonAlias(value = "state")
	private String state;

	@Column(name = "emailClient", length = 200, nullable = true, unique = false)
	@JsonAlias(value = "emailClient")
	private String emailClient;

	@Column(name = "responsiblePerson", length = 40, nullable = true, unique = false)
	@JsonAlias(value = "responsiblePerson")
	private String responsiblePerson;

	@Column(name = "emailReponsible", length = 200, nullable = true, unique = false)
	@JsonAlias(value = "emailReponsible")
	private String emailReponsible;

	@Column(name = "emailDefault", length = 200, nullable = true, unique = false)
	@JsonAlias(value = "emailDefault")
	private String emailDefault;
	
	@Column(name = "emailPermission", length = 200, nullable = true, unique = false)
	@JsonAlias(value = "emailPermission")
	private String emailPermission;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getCodeZip() {
		return codeZip;
	}

	public void setCodeZip(Integer codeZip) {
		this.codeZip = codeZip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(String emailClient) {
		this.emailClient = emailClient;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getEmailReponsible() {
		return emailReponsible;
	}

	public void setEmailReponsible(String emailReponsible) {
		this.emailReponsible = emailReponsible;
	}

	public String getEmailDefault() {
		return emailDefault;
	}

	public void setEmailDefault(String emailDefault) {
		this.emailDefault = emailDefault;
	}

	public String getEmailPermission() {
		return emailPermission;
	}

	public void setEmailPermission(String emailPermission) {
		this.emailPermission = emailPermission;
	}
	
}
