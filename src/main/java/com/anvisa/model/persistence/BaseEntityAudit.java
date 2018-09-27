package com.anvisa.model.persistence;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;  
  
/** 
 * Base Entity Audit 
 * 
 * @author cem ikta 
 */  
@MappedSuperclass  
public abstract class BaseEntityAudit extends BaseEntity {  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "active", nullable = false)
	private boolean active;

	
	@ManyToOne
	@JoinColumn(name="InsertUserFK")
	private User insertUser;
	

	@Column(name = "insert_date",  insertable = true, updatable = false)
	private LocalDateTime insertDate;

	@ManyToOne
	@JoinColumn(name="UpdateUserFK")
	private User updateUser;
	
	@Column(name = "update_date", insertable = false, updatable = true)
	private LocalDateTime updateDate;

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