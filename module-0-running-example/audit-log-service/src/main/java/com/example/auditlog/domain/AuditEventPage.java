package com.example.auditlog.domain;

import java.util.List;

public record AuditEventPage(
    List<AuditEvent> content, int page, int size, long totalElements) {

  public AuditEventPage {
    content = List.copyOf(content);
  }
}

