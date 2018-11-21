package com.anvisa.model.persistence.mongodb;

import java.time.LocalDate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import com.anvisa.model.persistence.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntityMongoDB {  
	  
  
    @Id
    private ObjectId id;  
  
    //@Field(value = "active")
	private boolean active;

    //@Field(value = "insertUser")	
	private User insertUser;
	

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	//@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	//@Field(value = "insertDate")
	private LocalDate insertDate;

	//@ManyToOne
	//private User updateUser;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	//@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	//@Field(value = "updateDate")
	private LocalDate updateDate;

	//@ManyToOne
	//private User ownerUser;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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

	public LocalDate getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDate insertDate) {
		this.insertDate = insertDate;
	}

//	public User getUpdateUser() {
//		return updateUser;
//	}
//
//	public void setUpdateUser(User updateUser) {
//		this.updateUser = updateUser;
//	}

	public LocalDate getUpdateDate() {
		return updateDate==null?insertDate:updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
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

		this.setInsertDate(LocalDate.now());
	}
	
	@PreUpdate
	void onPersist() {
		this.setUpdateDate(LocalDate.now());
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
