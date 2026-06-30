package com.example.auditlog.api.query;

import com.example.auditlog.application.query.AuditEventQuery;
import com.example.auditlog.application.query.QueryAuditEventService;
import com.example.auditlog.domain.Action;
import com.example.auditlog.domain.Outcome;
import java.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit-events")
public class AuditEventQueryController {

  private final QueryAuditEventService service;

  public AuditEventQueryController(QueryAuditEventService service) {
    this.service = service;
  }

  @GetMapping
  public AuditEventPageResponse find(
      @RequestParam(required = false) String actor,
      @RequestParam(required = false) Instant from,
      @RequestParam(required = false) Instant to,
      @RequestParam(required = false) Action action,
      @RequestParam(required = false) Outcome outcome,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return AuditEventPageResponse.fromDomain(
        service.find(new AuditEventQuery(actor, from, to, action, outcome, page, size)));
  }
}
