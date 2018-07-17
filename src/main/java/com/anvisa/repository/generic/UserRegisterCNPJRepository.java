package com.anvisa.repository.generic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.User;
import com.anvisa.model.persistence.UserRegisterCNPJ;

public interface UserRegisterCNPJRepository extends JpaRepository<UserRegisterCNPJ, Long> {

	@Query("select u from UserRegisterCNPJ u where u.user=:user")
	public List<UserRegisterCNPJ> findId(@Param("user") User user);
	
}
