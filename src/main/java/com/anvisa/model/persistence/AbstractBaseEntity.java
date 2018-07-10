package com.anvisa.model.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public abstract class AbstractBaseEntity {

	@Id
    @GeneratedValue
	@Column(name = "ID", nullable = false)
	public Long id;

	@Column(name = "active", nullable = false)
	@ColumnDefault(value = "true")
	private boolean active;

	@JsonAlias(value = "insertUser")
	@ManyToOne
	private User insertUser;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "insert_date")
	@Temporal(TemporalType.DATE)
	private Date insertDate;

	@JsonAlias(value = "updateUser")
	@ManyToOne
	private User updateUser;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias(value = "updateDate")
	@Column(name = "update_date")
	@Temporal(TemporalType.DATE)
	private Date updateDate;

	@JsonAlias(value = "ownerUser")
	@ManyToOne	
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
