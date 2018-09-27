package com.anvisa.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "scheduled_email")
public class ScheduledEmail extends BaseEntityAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "email", nullable = false)
	@JsonAlias(value = "email")
	private String email;

	@Column(name = "name", nullable = false)
	@JsonAlias(value = "name")
	private String name;

	@Column(name = "body", nullable = false)
	@JsonAlias(value = "body")
	private String body;

	@Column(name = "sent")
	@JsonAlias(value = "sent")
	private boolean sent = false;

	@Column(name = "subject", nullable = false)
	@JsonAlias(value = "subject")
	private String subject;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
