## 1. Problem
We are starting a new project: `audit-log-service`. It will accept audit events from other systems and store them immutably. For this first step, create only the technical scaffold — no business logic yet.

## 2. Context
- Fresh repository, `git init` already done; `AGENTS.md` exists at the root.
- `AGENTS.md` invariants: append-only domain, events immutable after creation, no update/delete; layered architecture `api` / `domain` / `infrastructure`; dependencies never flow back from `domain`.
- Target package root: `com.example.audit`.

## 3. Constraints
- Use: Java 21, Spring Boot 3.x, Gradle Kotlin DSL, PostgreSQL, Flyway, Testcontainers.
- Do not use: H2, Lombok, MapStruct, any code-generation library, any REST endpoint, any `AuditEvent` entity or domain logic in this step.
- If something is unclear, prefer the smallest working scaffold and leave a TODO instead of inventing a large design.

## 4. Minimum expected changes
- Gradle files, `docker-compose.yml`, `.gitignore`, `AuditLogApplication.java`.
- Package skeleton `api` / `domain` / `infrastructure`, `application.yml`, empty `db/migration/`.
- One Testcontainers smoke test verifying the Spring context starts with Postgres.
- Postgres: database `audit_log`, port `5432`, Flyway enabled, Hibernate `ddl-auto: validate`.

## 5. Verification method
- Run `./gradlew build`, `docker compose up -d`, `./gradlew test`, `./gradlew bootRun`.
- Expected: build green, smoke test passes, Postgres starts, app connects, Flyway validation runs, no business logic yet.
- If any command fails, fix it before reporting completion.

## 6. Integration with existing code
- Respect the existing `AGENTS.md`; do not overwrite it.
- Keep `domain` independent, keep persistence out of `api`, leave `infrastructure` for technical details later.
- Keep all packages almost empty except the entry point and the smoke test.

## 7. Principles
- Follow the steering from `AGENTS.md`.
- Optimize for the smallest working increment, a clear feedback loop, no fake progress, no speculative architecture.
- Before changing anything, briefly summarize your plan; after, show files changed, commands run, their results, and any remaining TODOs.
