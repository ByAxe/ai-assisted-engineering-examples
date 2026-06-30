package com.example.auditlog.domain;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record AuditEvent(
    UUID id,
    Instant recordedAt,
    String actor,
    Action action,
    String resourceType,
    String resourceId,
    Outcome outcome,
    String reason,
    Map<String, String> metadata) {

  public AuditEvent {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(recordedAt, "recordedAt must not be null");
    actor = requireText(actor, "actor");
    Objects.requireNonNull(action, "action must not be null");
    Objects.requireNonNull(outcome, "outcome must not be null");
    metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
  }

  public static AuditEvent create(
      UUID id,
      Instant recordedAt,
      String actor,
      Action action,
      String resourceType,
      String resourceId,
      Outcome outcome,
      String reason,
      Map<String, String> metadata) {
    return new AuditEvent(
        id, recordedAt, actor, action, resourceType, resourceId, outcome, reason, metadata);
  }

  private static String requireText(String value, String fieldName) {
    Objects.requireNonNull(value, fieldName + " must not be null");
    if (value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return value;
  }
}

