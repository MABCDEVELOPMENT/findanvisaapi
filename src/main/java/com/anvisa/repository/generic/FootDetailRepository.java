package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvisa.model.persistence.rest.foot.ContentDetalFoot;

public interface FootDetailRepository extends JpaRepository<ContentDetalFoot,Long> {

}
