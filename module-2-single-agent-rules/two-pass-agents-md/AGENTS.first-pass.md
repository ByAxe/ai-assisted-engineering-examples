# Audit Log Service

## Project Map
- `Audit Log Service` stores compliance-friendly audit events for other systems.
- The service persists immutable events in Postgres and exposes application code through a layered Spring Boot structure.
- The main code areas are `api`, `domain`, and `infrastructure` under `com.sam.audit`.
- Flyway owns schema evolution, and Docker Compose provides the local Postgres runtime.

## Hard Invariants
- The audit log is append-only. No application flow should update or delete an event.
- An event becomes immutable immediately after it is written.
- Changes to storage or code must preserve traceability and chronological integrity of recorded events.

## Architectural Rules
- Code is split into `api`, `domain`, and `infrastructure` packages.
- `api` may depend on `domain` contracts, but not directly on infrastructure internals.
- `domain` must not depend on `api`.
- Persistence and external concerns belong in `infrastructure`; steering rules belong here, not in task prompts.
