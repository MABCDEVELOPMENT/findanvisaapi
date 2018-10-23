package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	
	@Query("select u from UserToken u where u.token=:token and u.active = true")
	public UserToken findToken(@Param("token") String token);

}
