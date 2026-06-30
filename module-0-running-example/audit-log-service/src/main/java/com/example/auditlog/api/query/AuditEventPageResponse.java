package com.example.auditlog.api.query;

import com.example.auditlog.api.common.AuditEventResponse;
import com.example.auditlog.domain.AuditEventPage;
import java.util.List;

public record AuditEventPageResponse(
    List<AuditEventResponse> content, int page, int size, long totalElements) {

  public static AuditEventPageResponse fromDomain(AuditEventPage page) {
    return new AuditEventPageResponse(
        page.content().stream().map(AuditEventResponse::fromDomain).toList(),
        page.page(),
        page.size(),
        page.totalElements());
  }
}

