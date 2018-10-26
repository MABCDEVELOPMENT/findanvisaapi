package com.anvisa.model.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Table(name = "user_token")
public class UserToken extends BaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private User userToken;
	
	@Column(name = "token", length = 300, nullable = false)
	@JsonAlias(value = "token")
	private String token;
	
	@Column(name = "tokenUsed")
	@JsonAlias(value = "tokenUsed")
	private boolean tokenUsed;

	public User getUserToken() {
		return userToken;
	}

	public void setUserToken(User userToken) {
		this.userToken = userToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isTokenUsed() {
		return tokenUsed;
	}

	public void setTokenUsed(boolean tokenUsed) {
		this.tokenUsed = tokenUsed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + (tokenUsed ? 1231 : 1237);
		result = prime * result + ((userToken == null) ? 0 : userToken.hashCode());
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
		if (!(obj instanceof UserToken)) {
			return false;
		}
		UserToken other = (UserToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (tokenUsed != other.tokenUsed)
			return false;
		if (userToken == null) {
			if (other.userToken != null)
				return false;
		} else if (!userToken.equals(other.userToken))
			return false;
		return true;
	}
	
	

}
