package com.anvisa.repository.generic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.anvisa.model.persistence.BaseEntity;

public class BaseEntityRepositoryCustomImpl implements BaseEntityRepositoryCustom {

	 @PersistenceContext
     @Autowired
	 private EntityManager manager;

	
	@Override
	public void save(BaseEntity t) {
		// TODO Auto-generated method stub
		
	}

}
