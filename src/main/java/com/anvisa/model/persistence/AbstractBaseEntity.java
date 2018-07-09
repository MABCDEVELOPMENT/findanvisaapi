package com.anvisa.model.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ForeignKey;

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
	private boolean active = false;

	@Column(name = "insert_user")
	@ManyToAny
	@JoinColumn(name = "insert_use", referencedColumnName = "id")
	private User insertUser;

	@JsonAlias(value = "insertUser")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "insert_date")
	@Temporal(TemporalType.DATE)
	private Date insertDate;

	@JsonAlias(value = "updateUser")
	@Column(name = "update_user")
	@JoinColumn(name = "update_user", referencedColumnName = "id")
	private User updateUser;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias(value = "updateDate")
	@Column(name = "update_date")
	@Temporal(TemporalType.DATE)
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
