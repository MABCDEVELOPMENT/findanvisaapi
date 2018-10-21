package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.saneante.notification.SaneanteNotification;

public interface SaneanteNotificationRepository extends JpaRepository<SaneanteNotification, Long>,JpaSpecificationExecutor<SaneanteNotification> {

	@Query("select n from SaneanteNotification n where n.processo =:processo and n.cnpj=:cnpj and n.expedienteProcesso =:expedienteProcesso")
	public SaneanteNotification findByProcessCnpj(@Param("processo") String process, @Param("cnpj") String cnpj, @Param("expedienteProcesso") String expedienteProcesso);
	
}
