package com.example.auditlog.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaAuditEventRepository
    extends JpaRepository<JpaAuditEventEntity, UUID>,
        JpaSpecificationExecutor<JpaAuditEventEntity> {}
