**Context**
- Repository: freshly initialised `audit-log-service/` (empty, `git init` done).
- `AGENTS.md` at the repo root states: append-only domain, no `update`/`delete`, events immutable after write; layers `api` → `domain` → `infrastructure` with no reverse dependencies.
- Target package root: `com.example.audit`. No source files yet.

**Constraints**
- Java 21, Spring Boot 3.x, Gradle Kotlin DSL.
- Respect the layer boundaries from `AGENTS.md`; do not cross them.
- No business logic in this step: no REST endpoints, no `AuditEvent` entity, no service/repository interfaces.
- Postgres only, never H2. Integration tests must use Testcontainers.
- No Lombok, no MapStruct, no other code-generation libraries.
- Do not add dependencies I did not list. If something seems missing, leave a TODO instead of guessing.

**Minimum expected changes**
- `settings.gradle.kts` + `build.gradle.kts` (Spring Boot, JPA, validation, Flyway, Postgres driver, Testcontainers).
- Package skeleton `com.example.audit/{api,domain,infrastructure}` with placeholder `package-info.java`.
- `AuditLogApplication.java`, `application.yml` (Postgres, Flyway on, `ddl-auto: validate`), empty `db/migration/`.
- `docker-compose.yml` with Postgres, credentials matching `application.yml`.
- One Testcontainers smoke test asserting the Spring context boots against Postgres.

**Verification**
- `./gradlew build` — green.
- `docker compose up -d` — Postgres container healthy.
- `./gradlew bootRun` — app starts and connects to Postgres.
- `./gradlew test` — smoke test passes against a Testcontainers Postgres.
- No domain logic under `com.example.audit/` yet.
