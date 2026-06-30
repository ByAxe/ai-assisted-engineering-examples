package com.example.auditlog.application.query;

import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.Outcome;
import java.time.Instant;

public record AuditEventQuery(
    String actor,
    Instant from,
    Instant to,
    Action action,
    Outcome outcome,
    Integer page,
    Integer size) {}
