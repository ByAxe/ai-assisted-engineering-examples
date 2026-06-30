package com.example.auditlog.domain;

public interface AuditEventReadRepository {

  AuditEventPage find(AuditEventSearch search);
}

