package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.foot.ContentDetalFoot;

public interface FootDetailRepository extends JpaRepository<ContentDetalFoot,Long> {
	
	@Query("select fd from ContentDetalFoot fd where fd.processo =:process and fd.cnpj=:cnpj")
	public ContentDetalFoot findByProcessCnpj(@Param("process") String process,@Param("cnpj") String cnpj);

}
