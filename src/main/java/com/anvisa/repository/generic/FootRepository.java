package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.foot.ContentFoot;

public interface FootRepository extends JpaRepository<ContentFoot,Long> {
	
	@Query("select f from ContentFoot f where f.process =:process and f.cnpj=:cnpj)")
	public ContentFoot findByProcessCnpj(@Param("process") String process,@Param("cnpj") String cnpj);

}
