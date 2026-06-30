package com.example.auditlog.api.common;

import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.AuditEvent;
import com.example.auditlog.domain.Outcome;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record AuditEventResponse(
    UUID id,
    Instant recordedAt,
    String actor,
    Action action,
    String resourceType,
    String resourceId,
    Outcome outcome,
    String reason,
    Map<String, String> metadata) {

  public static AuditEventResponse fromDomain(AuditEvent event) {
    return new AuditEventResponse(
        event.id(),
        event.recordedAt(),
        event.actor(),
        event.action(),
        event.resourceType(),
        event.resourceId(),
        event.outcome(),
        event.reason(),
        event.metadata());
  }
}

