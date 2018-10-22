package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.anvisa.model.persistence.rest.process.Process;

public interface ProcessRepository extends JpaRepository<Process, Long>,JpaSpecificationExecutor<Process> {

	@Query("select n from Process n where n.processo =:processo and n.cnpj=:cnpj")
	public Process findByProcessCnpj(@Param("processo") String process, @Param("cnpj") String cnpj);
	
}
