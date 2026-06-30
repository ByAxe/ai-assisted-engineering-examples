package com.example.auditlog.domain;

public interface AuditEventAppendRepository {

  AuditEvent append(AuditEvent auditEvent);
}

