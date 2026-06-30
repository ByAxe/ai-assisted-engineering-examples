package com.example.auditlog.persistence;

import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.AuditEvent;
import com.example.auditlog.domain.Outcome;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "audit_events")
public class JpaAuditEventEntity {

  @Id private UUID id;

  @Column(name = "recorded_at", nullable = false, updatable = false)
  private Instant recordedAt;

  @Column(nullable = false, updatable = false)
  private String actor;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private Action action;

  @Column(name = "resource_type", updatable = false)
  private String resourceType;

  @Column(name = "resource_id", updatable = false)
  private String resourceId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private Outcome outcome;

  @Column(updatable = false)
  private String reason;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb", updatable = false)
  private Map<String, String> metadata;

  protected JpaAuditEventEntity() {}

  private JpaAuditEventEntity(
      UUID id,
      Instant recordedAt,
      String actor,
      Action action,
      String resourceType,
      String resourceId,
      Outcome outcome,
      String reason,
      Map<String, String> metadata) {
    this.id = id;
    this.recordedAt = recordedAt;
    this.actor = actor;
    this.action = action;
    this.resourceType = resourceType;
    this.resourceId = resourceId;
    this.outcome = outcome;
    this.reason = reason;
    this.metadata = metadata;
  }

  public static JpaAuditEventEntity fromDomain(AuditEvent event) {
    return new JpaAuditEventEntity(
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

  public AuditEvent toDomain() {
    return AuditEvent.create(
        id, recordedAt, actor, action, resourceType, resourceId, outcome, reason, metadata);
  }

  Instant recordedAt() {
    return recordedAt;
  }

  String actor() {
    return actor;
  }

  Action action() {
    return action;
  }

  Outcome outcome() {
    return outcome;
  }
}
