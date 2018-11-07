package com.anvisa.repository.generic;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.RegisterCNPJ;

public interface RegisterCNPJRepository extends JpaRepository<RegisterCNPJ, Long> {

	@Query("select u from RegisterCNPJ u where u.id=:id")
	public RegisterCNPJ findId(@Param("id") Long id);
	
	@Query("select u from RegisterCNPJ u where u.active=true and (u.category =:category or u.category =3)")
	public ArrayList<RegisterCNPJ> findAll(@Param("category") int category);
	
	@Query("select u from RegisterCNPJ u where u.active=true and u.cnpj =:cnpj")
	public ArrayList<RegisterCNPJ> findCnpj(@Param("cnpj") String cnpj);
}
