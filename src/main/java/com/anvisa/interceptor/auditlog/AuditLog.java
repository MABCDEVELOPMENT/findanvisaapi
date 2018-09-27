package com.anvisa.interceptor.auditlog;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "auditlog")
public class AuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long auditLogId;
	private String action;
	private String detail;
	private LocalDateTime createdDate;
	private long entityId;
	private String entityName;
	
	


	public AuditLog(String action, LocalDateTime createdDate, Long id, String entityName, String logDeatil) {
		super();
		this.action = action;
		this.detail = logDeatil;
		this.createdDate = createdDate;
		this.entityId = id;
		this.entityName = entityName;
	}

	public Long getAuditLogId() {
		return auditLogId;
	}
	public void setAuditLogId(Long auditLogId) {
		this.auditLogId = auditLogId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}