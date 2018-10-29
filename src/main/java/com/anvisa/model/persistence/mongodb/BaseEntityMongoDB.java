package com.anvisa.model.persistence.mongodb;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.anvisa.core.json.JsonToObject;
import com.anvisa.model.persistence.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;


public abstract class BaseEntityMongoDB implements Serializable {  
	  
    private static final long serialVersionUID = 1L;  
  
    @Id  
    private Long id;  
  
 
	private boolean active;

	
	private User insertUser;
	

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime insertDate;

	//@ManyToOne
	//private User updateUser;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime updateDate;

	//@ManyToOne
	//private User ownerUser;

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

//	public User getUpdateUser() {
//		return updateUser;
//	}
//
//	public void setUpdateUser(User updateUser) {
//		this.updateUser = updateUser;
//	}

	public LocalDateTime getUpdateDate() {
		return updateDate==null?insertDate:updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

/*	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}*/
	
	@PrePersist
	void onCreate() {

		this.setInsertDate(LocalDateTime.now());
	}
	
	@PreUpdate
	void onPersist() {
		this.setUpdateDate(LocalDateTime.now());
	}
    

    @Override  
    public int hashCode() {  
        int hash = 0;  
        hash += (this.getId() != null ? this.getId().hashCode() : 0);  
  
        return hash;  
    }  
  
    @Override  
    public boolean equals(Object object) {  
    if (this == object)  
            return true;  
        if (object == null)  
            return false;  
        if (getClass() != object.getClass())  
            return false;  
  
        BaseEntityMongoDB other = (BaseEntityMongoDB) object;  
        if (this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {  
            return false;  
        }  
        return true;  
    }  
  
    @Override  
    public String toString() {  
        return this.getClass().getName() + " [ID=" + id + "]";  
    }  
    
   
}  
