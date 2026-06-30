package com.example.auditlog.api.append;

import com.example.auditlog.api.common.AuditEventResponse;
import com.example.auditlog.application.append.AppendAuditEventCommand;
import com.example.auditlog.application.append.AppendAuditEventService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/audit-events")
public class AuditEventAppendController {

  private final AppendAuditEventService service;

  public AuditEventAppendController(AppendAuditEventService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<AuditEventResponse> append(@Valid @RequestBody AppendAuditEventRequest request) {
    var event =
        service.append(
            new AppendAuditEventCommand(
                request.actor(),
                request.action(),
                request.resourceType(),
                request.resourceId(),
                request.outcome(),
                request.reason(),
                request.metadata()));
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(event.id())
            .toUri();
    return ResponseEntity.created(location).body(AuditEventResponse.fromDomain(event));
  }
}

