package com.anvisa.repository.generic;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.foot.ContentFoot;

public interface FootRepository extends JpaRepository<ContentFoot, Long>,JpaSpecificationExecutor<ContentFoot> {

	@Query("select f from ContentFoot f where f.processo =:processo and f.cnpj=:cnpj and f.codigo=:codigo and f.registro=:registro and f.dataVencimento =:dataVencimento")
	public ContentFoot findByProcessCnpjCodigoRegistro(@Param("processo") String process, @Param("cnpj") String cnpj,
			@Param("codigo") int codigo, @Param("registro") String registro, @Param("dataVencimento") LocalDate dataVencimento);
	
}
