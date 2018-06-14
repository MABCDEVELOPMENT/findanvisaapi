package com.anvisa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public abstract class AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	@JsonAlias(value = "id")
	public Long id;

	@Column(name = "active", nullable = false)
	@ColumnDefault(value = "true")
	@JsonAlias(value = "active")
	private boolean active;

	@Column(name = "insert_user")
	@JsonAlias(value = "insertUser")
	@JoinColumn(name = "insert_user", referencedColumnName = "id")
	private User insertUser;

	@Column(name = "insert_date")
	@Temporal(TemporalType.DATE)
	@JsonAlias(value = "insertUser")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date insertDate;

	@Column(name = "update_user")
	@JsonAlias(value = "updateUser")
	@JoinColumn(name = "update_user", referencedColumnName = "id")
	private User updateUser;

	@Column(name = "update_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias(value = "updateDate")
	private Date updateDate;

	@Column(name = "owner_user")
	@JsonAlias(value = "ownerUser")
	@JoinColumn(name = "owner_user", referencedColumnName = "id")
	private User ownerUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(User insertUser) {
		this.insertUser = insertUser;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

}
