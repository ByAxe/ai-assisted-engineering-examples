package com.example.auditlog.domain;

import java.time.Instant;
import java.util.Optional;

public record AuditEventSearch(
    Optional<String> actor,
    Optional<Instant> from,
    Optional<Instant> to,
    Optional<Action> action,
    Optional<Outcome> outcome,
    int page,
    int size) {

  public AuditEventSearch {
    actor = actor == null ? Optional.empty() : actor.filter(value -> !value.isBlank());
    from = from == null ? Optional.empty() : from;
    to = to == null ? Optional.empty() : to;
    action = action == null ? Optional.empty() : action;
    outcome = outcome == null ? Optional.empty() : outcome;
    if (page < 0) {
      throw new IllegalArgumentException("page must be greater than or equal to 0");
    }
    if (size < 1 || size > 200) {
      throw new IllegalArgumentException("size must be between 1 and 200");
    }
  }
}
