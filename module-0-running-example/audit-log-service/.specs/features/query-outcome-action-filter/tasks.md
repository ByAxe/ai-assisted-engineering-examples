# Query Outcome And Action Filter Tasks

## SDD Readiness

1. Add a characterization integration test for current query behavior.
2. Assert no-filter ordering and IDs on seeded fixtures.
3. Assert current actor filtering.
4. Assert current from/to window filtering.
5. Add `docs/dependency-map/query-api.md`.
6. Add ArchUnit as a test dependency.
7. Add a rule that query code cannot depend on append code.
8. Add a rule that query code cannot call write-like methods.
9. Include an AI-readable self-correction message.
10. Run `./gradlew test`.

## Implementation

1. Write failing integration tests for `outcome`, `action`, combined filters, and invalid enum values.
2. Add optional filter fields to query controller and query DTO.
3. Add optional filter fields to domain search.
4. Add read-only JPA predicates for `outcome` and `action`.
5. Update `.specs/api/query-api.md`.
6. Run `./gradlew test`.

