package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotification;

public interface CosmeticNotificationRepository extends JpaRepository<ContentCosmeticNotification, Long>,JpaSpecificationExecutor<ContentCosmeticNotification> {

	@Query("select n from ContentCosmeticNotification n where n.processo =:processo and n.cnpj=:cnpj and n.expedienteProcesso =:expedienteProcesso")
	public ContentCosmeticNotification findByProcessCnpj(@Param("processo") String process, @Param("cnpj") String cnpj, @Param("expedienteProcesso") String expedienteProcesso);
	
}
