package com.example.auditlog.api.append;

import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.Outcome;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record AppendAuditEventRequest(
    @NotBlank String actor,
    @NotNull Action action,
    String resourceType,
    String resourceId,
    @NotNull Outcome outcome,
    String reason,
    Map<String, String> metadata) {}

