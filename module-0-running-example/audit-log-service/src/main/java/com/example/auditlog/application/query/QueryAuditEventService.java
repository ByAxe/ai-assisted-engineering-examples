package com.example.auditlog.application.query;

import com.example.auditlog.domain.AuditEventPage;
import com.example.auditlog.domain.AuditEventReadRepository;
import com.example.auditlog.domain.AuditEventSearch;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryAuditEventService {

  private static final int DEFAULT_PAGE = 0;
  private static final int DEFAULT_SIZE = 50;
  private static final int MAX_SIZE = 200;

  private final AuditEventReadRepository repository;

  public QueryAuditEventService(AuditEventReadRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public AuditEventPage find(AuditEventQuery query) {
    var page = query.page() == null ? DEFAULT_PAGE : query.page();
    var requestedSize = query.size() == null ? DEFAULT_SIZE : query.size();
    var size = Math.min(requestedSize, MAX_SIZE);
    return repository.find(
        new AuditEventSearch(
            Optional.ofNullable(query.actor()),
            Optional.ofNullable(query.from()),
            Optional.ofNullable(query.to()),
            Optional.ofNullable(query.action()),
            Optional.ofNullable(query.outcome()),
            page,
            size));
  }
}
