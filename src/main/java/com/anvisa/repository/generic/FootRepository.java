package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvisa.model.persistence.rest.foot.ContentFoot;

public interface FootRepository extends JpaRepository<ContentFoot,Long> {

}
