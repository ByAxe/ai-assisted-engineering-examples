package com.example.auditlog.application.append;

import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.Outcome;
import java.util.Map;

public record AppendAuditEventCommand(
    String actor,
    Action action,
    String resourceType,
    String resourceId,
    Outcome outcome,
    String reason,
    Map<String, String> metadata) {}

