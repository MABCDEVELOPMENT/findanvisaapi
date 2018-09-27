package com.anvisa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvisa.interceptor.auditlog.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {

}
