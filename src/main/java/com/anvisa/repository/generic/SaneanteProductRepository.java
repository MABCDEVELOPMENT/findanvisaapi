package com.anvisa.repository.generic;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.saneante.product.SaneanteProduct;

public interface SaneanteProductRepository extends JpaRepository<SaneanteProduct, Long>,JpaSpecificationExecutor<SaneanteProduct> {

	@Query("select f from SaneanteProduct f where f.processo =:processo and f.cnpj=:cnpj and f.codigo=:codigo and f.registro=:registro and f.dataVencimento =:dataVencimento")
	public SaneanteProduct findByProcessCnpjRegistroVencimento(@Param("processo") String process, @Param("cnpj") String cnpj,
			@Param("codigo") String codigo, @Param("registro") String registro, @Param("dataVencimento") LocalDate dataVencimento);

}
