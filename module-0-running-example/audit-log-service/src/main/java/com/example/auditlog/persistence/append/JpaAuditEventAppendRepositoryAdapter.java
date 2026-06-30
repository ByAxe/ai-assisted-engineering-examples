package com.example.auditlog.persistence.append;

import com.example.auditlog.domain.AuditEvent;
import com.example.auditlog.domain.AuditEventAppendRepository;
import com.example.auditlog.persistence.JpaAuditEventEntity;
import com.example.auditlog.persistence.JpaAuditEventRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAuditEventAppendRepositoryAdapter implements AuditEventAppendRepository {

  private final JpaAuditEventRepository repository;

  public JpaAuditEventAppendRepositoryAdapter(JpaAuditEventRepository repository) {
    this.repository = repository;
  }

  @Override
  public AuditEvent append(AuditEvent auditEvent) {
    return repository.save(JpaAuditEventEntity.fromDomain(auditEvent)).toDomain();
  }
}

