package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anvisa.model.persistence.rest.cosmetic.regularized.ContentCosmeticRegularized;

public interface CosmeticRegularizedRepository extends JpaRepository<ContentCosmeticRegularized, Long>,JpaSpecificationExecutor<ContentCosmeticRegularized> {

	@Query("select f from ContentCosmeticRegularized f where f.processo =:processo")
	public ContentCosmeticRegularized findByProcess(@Param("processo") String process);

}
