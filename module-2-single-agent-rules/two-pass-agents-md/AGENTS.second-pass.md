# Audit Log Service

## Project Map
- `Audit Log Service` stores immutable audit events for downstream business systems.
- The service uses Spring Boot 3, Postgres, Flyway, and a layered package structure under `com.sam.audit`.
- The repo is intentionally small and prepared for a two-pass feedback loop with an agent.
- Local development uses Docker Compose for Postgres and Testcontainers for integration feedback.

## Hard Invariants
- The audit log is append-only. No `update` or `delete` flow is allowed for events.
- An event must stay immutable after the initial write.
- Persistence changes must preserve traceability and ordering of recorded events.
- Schema evolution must happen through Flyway migrations, not through `ddl-auto=create` shortcuts.

## Architectural Rules
- Code is organized into `api`, `domain`, and `infrastructure`.
- `api` depends on `domain`, not on infrastructure implementation details.
- `domain` must not depend on `api`.
- Infrastructure owns Spring Data adapters; the domain owns the repository contract.
- Verify persistence behavior against real Postgres in Testcontainers before trusting an invariant.
