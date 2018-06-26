package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.RegisterCNPJ;

public interface RegisterCNPJRepository extends JpaRepository<RegisterCNPJ, Long> {

	@Query("select u from RegisterCNPJ u where u.id=:id")
	public RegisterCNPJ findId(@Param("id") Long id);

}
