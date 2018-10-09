package com.anvisa.repository.generic;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.anvisa.model.persistence.rest.cosmetic.register.presentation.ContentCosmeticRegisterDetail;
import com.anvisa.model.persistence.rest.foot.ContentFoot;
@Transactional
public interface CosmeticRegisterDetailRepository extends JpaRepository<ContentCosmeticRegisterDetail, Long>,JpaSpecificationExecutor<ContentFoot> {

	@Query("select f from ContentFoot f where f.processo =:processo and f.cnpj=:cnpj and f.codigo=:codigo and f.registro=:registro and f.dataVencimento =:dataVencimento")
	public ContentCosmeticRegisterDetail findByProcessCnpjVencimento(@Param("processo") String process, @Param("cnpj") String cnpj,
			 @Param("dataVencimento") LocalDate dataVencimento);

}