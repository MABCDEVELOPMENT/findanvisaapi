package com.anvisa.model.persistence;

import java.time.LocalDateTime;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

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
	@JoinColumn(name="InsertUserFK")
	private User insertUser;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "insert_date",  insertable = true, updatable = false)
	private LocalDateTime insertDate;

	@JsonAlias(value = "updateUser")
	@ManyToOne
	@JoinColumn(name="UpdateUserFK")
	private User updateUser;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias(value = "updateDate")
	@Column(name = "update_date", insertable = false, updatable = true)
	private LocalDateTime updateDate;

	@JsonAlias(value = "ownerUser")
	@ManyToOne	
	@JoinColumn(name="OwnerUserFK")
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

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
	
	@PrePersist
	void onCreate() {
		this.setInsertDate(LocalDateTime.now());
	}
	
	@PreUpdate
	void onPersist() {
		this.setUpdateDate(LocalDateTime.now());
	}

}
