package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.anvisa.model.persistence.RegisterCNPJ;
import com.anvisa.model.persistence.User;
import com.anvisa.model.persistence.UserRegisterCNPJ;

public interface UserRegisterCNPJRepository extends JpaRepository<UserRegisterCNPJ, Long> {

	@Query("select u from UserRegisterCNPJ u where u.user=:user and u.cnpj=:cnpj")
	public UserRegisterCNPJ findId(@Param("user") User user,@Param("cnpj") RegisterCNPJ cnpj);
	
	@Modifying
	@Transactional
	@Query("delete From UserRegisterCNPJ u where u.cnpj=:cnpj")
	public void deleteCnpj(@Param("cnpj") RegisterCNPJ cnpj);

	
}
