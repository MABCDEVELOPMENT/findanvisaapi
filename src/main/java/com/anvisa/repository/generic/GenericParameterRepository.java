package com.anvisa.repository.generic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anvisa.model.GenericParameter;

public interface GenericParameterRepository extends JpaRepository<GenericParameter, Long> {

	@Query("select g from GenericParameter g")
	public List<GenericParameter> load();

}