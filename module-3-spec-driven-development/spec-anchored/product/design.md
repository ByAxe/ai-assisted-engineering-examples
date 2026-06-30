# Product Design

## Architecture

The service uses a layered package structure under `com.example.auditlog`.

- `domain` owns framework-free audit event types and repository ports.
- `application.append` owns the write use case.
- `application.query` owns the read use case.
- `persistence` owns JPA mappings and adapters.
- `api.append` owns HTTP writes.
- `api.query` owns HTTP reads.

## Append Path

`AuditEventAppendController` accepts create requests and calls `AppendAuditEventService`.

The append service validates required fields, generates the server timestamp and UUID, and writes through `AuditEventAppendRepository`.

## Query Path

`AuditEventQueryController` accepts read-only filters and calls `QueryAuditEventService`.

The query service normalizes paging and delegates to `AuditEventReadRepository`. It does not depend on append services or append persistence adapters.

## Database

The `audit_events` table stores immutable events:

- `id uuid primary key`
- `recorded_at timestamptz not null`
- `actor text not null`
- `action text not null`
- `resource_type text`
- `resource_id text`
- `outcome text not null`
- `reason text`
- `metadata jsonb`

Indexes exist for `recorded_at`, `actor`, `action`, and `outcome`.

`V2__append_only_guard.sql` adds a trigger that raises `audit_events is append-only` before every update or delete.

