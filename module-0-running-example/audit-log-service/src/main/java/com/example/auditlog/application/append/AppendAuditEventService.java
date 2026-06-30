package com.example.auditlog.application.append;

import com.example.auditlog.domain.AuditEvent;
import com.example.auditlog.domain.AuditEventAppendRepository;
import java.time.Clock;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppendAuditEventService {

  private final AuditEventAppendRepository repository;
  private final Clock clock;

  public AppendAuditEventService(AuditEventAppendRepository repository, Clock clock) {
    this.repository = repository;
    this.clock = clock;
  }

  @Transactional
  public AuditEvent append(AppendAuditEventCommand command) {
    Objects.requireNonNull(command, "command must not be null");
    var event =
        AuditEvent.create(
            UUID.randomUUID(),
            clock.instant(),
            command.actor(),
            command.action(),
            command.resourceType(),
            command.resourceId(),
            command.outcome(),
            command.reason(),
            command.metadata());
    return repository.append(event);
  }
}

