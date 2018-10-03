package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.foot.ContentFootDetail;

public interface FootDetailRepository extends JpaRepository<ContentFootDetail,Long> {
	
	@Query("select fd from ContentFootDetail fd where fd.processo =:process and fd.cnpj=:cnpj")
	public ContentFootDetail findByProcessCnpj(@Param("process") String process,@Param("cnpj") String cnpj);

}
