package com.example.auditlog.persistence.query;

import com.example.auditlog.domain.AuditEventPage;
import com.example.auditlog.domain.AuditEventReadRepository;
import com.example.auditlog.domain.AuditEventSearch;
import com.example.auditlog.persistence.JpaAuditEventEntity;
import com.example.auditlog.persistence.JpaAuditEventRepository;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAuditEventReadRepositoryAdapter implements AuditEventReadRepository {

  private final JpaAuditEventRepository repository;

  public JpaAuditEventReadRepositoryAdapter(JpaAuditEventRepository repository) {
    this.repository = repository;
  }

  @Override
  public AuditEventPage find(AuditEventSearch search) {
    var pageable =
        PageRequest.of(search.page(), search.size(), Sort.by(Sort.Direction.DESC, "recordedAt"));
    var page = repository.findAll(specificationFor(search), pageable);
    return new AuditEventPage(
        page.map(JpaAuditEventEntity::toDomain).toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements());
  }

  private Specification<JpaAuditEventEntity> specificationFor(AuditEventSearch search) {
    return actorEquals(search)
        .and(recordedAtGreaterThanOrEqualTo(search))
        .and(recordedAtLessThan(search))
        .and(actionEquals(search))
        .and(outcomeEquals(search));
  }

  private Specification<JpaAuditEventEntity> actorEquals(AuditEventSearch search) {
    return Optional.of(search)
        .flatMap(AuditEventSearch::actor)
        .<Specification<JpaAuditEventEntity>>map(
            actor -> (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actor"), actor))
        .orElse(alwaysTrue());
  }

  private Specification<JpaAuditEventEntity> recordedAtGreaterThanOrEqualTo(
      AuditEventSearch search) {
    return Optional.of(search)
        .flatMap(AuditEventSearch::from)
        .<Specification<JpaAuditEventEntity>>map(
            from ->
                (root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("recordedAt"), from))
        .orElse(alwaysTrue());
  }

  private Specification<JpaAuditEventEntity> recordedAtLessThan(AuditEventSearch search) {
    return Optional.of(search)
        .flatMap(AuditEventSearch::to)
        .<Specification<JpaAuditEventEntity>>map(
            to ->
                (root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("recordedAt"), to))
        .orElse(alwaysTrue());
  }

  private Specification<JpaAuditEventEntity> actionEquals(AuditEventSearch search) {
    return Optional.of(search)
        .flatMap(AuditEventSearch::action)
        .<Specification<JpaAuditEventEntity>>map(
            action ->
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("action"), action))
        .orElse(alwaysTrue());
  }

  private Specification<JpaAuditEventEntity> outcomeEquals(AuditEventSearch search) {
    return Optional.of(search)
        .flatMap(AuditEventSearch::outcome)
        .<Specification<JpaAuditEventEntity>>map(
            outcome ->
                (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("outcome"), outcome))
        .orElse(alwaysTrue());
  }

  private Specification<JpaAuditEventEntity> alwaysTrue() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}
