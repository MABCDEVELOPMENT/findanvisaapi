package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.anvisa.model.persistence.rest.cosmetic.notification.ContentCosmeticNotification;
import com.anvisa.model.persistence.rest.foot.ContentFoot;

public interface CosmeticNotificationRepository extends JpaRepository<ContentCosmeticNotification, Long>,JpaSpecificationExecutor<ContentCosmeticNotification> {

}
