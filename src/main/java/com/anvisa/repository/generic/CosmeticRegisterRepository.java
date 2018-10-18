package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.cosmetic.register.ContentCosmeticRegister;

public interface CosmeticRegisterRepository extends JpaRepository<ContentCosmeticRegister, Long>,JpaSpecificationExecutor<ContentCosmeticRegister> {

	@Query("select f from ContentCosmeticRegister f where f.processo =:processo and f.cnpj=:cnpj and f.expedienteProcesso=:expedienteProcesso")
	public ContentCosmeticRegister findByProcessExpedienteProcessoCnpj(@Param("processo") String process,@Param("expedienteProcesso") String expedienteProcesso ,@Param("cnpj") String cnpj);

}