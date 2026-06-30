# Query API Dependency Map

This map captures the read path for audit-event queries, including optional `actor`, time, `action`, and `outcome` filters.

## Query Path

```mermaid
flowchart LR
    Client["HTTP client"]
    Controller["api.query.AuditEventQueryController"]
    QueryDto["application.query.AuditEventQuery"]
    Service["application.query.QueryAuditEventService"]
    Search["domain.AuditEventSearch"]
    Port["domain.AuditEventReadRepository"]
    Adapter["persistence.query.JpaAuditEventReadRepositoryAdapter"]
    SpringData["persistence.JpaAuditEventRepository"]
    Entity["persistence.JpaAuditEventEntity"]
    Db[("PostgreSQL audit_events")]

    Client --> Controller
    Controller --> QueryDto
    Controller --> Service
    Service --> Search
    Service --> Port
    Port --> Adapter
    Adapter --> SpringData
    SpringData --> Entity
    SpringData --> Db
```

## Inbound Edges

`AuditEventQueryController` is entered only by `GET /api/v1/audit-events`.

`QueryAuditEventService` is called by `AuditEventQueryController`.

## Outbound Edges

`AuditEventQueryController` creates `AuditEventQuery` and delegates to `QueryAuditEventService`.

`QueryAuditEventService` creates `AuditEventSearch` and delegates to `AuditEventReadRepository`.

`JpaAuditEventReadRepositoryAdapter` builds read-only JPA specifications and calls `findAll`.

## Boundary To Append Path

The query path must not import or call:

- `api.append.AuditEventAppendController`
- `application.append.AppendAuditEventService`
- `application.append.AppendAuditEventCommand`
- `persistence.append.JpaAuditEventAppendRepositoryAdapter`
- `save`, `persist`, `merge`, `remove`, or `delete` methods

The append boundary is intentionally explicit so query changes stay inside the read path.
