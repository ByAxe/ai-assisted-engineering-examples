# Query Outcome And Action Filter Design

## Classes Involved

- `src/main/java/com/example/auditlog/api/query/AuditEventQueryController.java`
- `src/main/java/com/example/auditlog/application/query/AuditEventQuery.java`
- `src/main/java/com/example/auditlog/application/query/QueryAuditEventService.java`
- `src/main/java/com/example/auditlog/domain/AuditEventSearch.java`
- `src/main/java/com/example/auditlog/persistence/query/JpaAuditEventReadRepositoryAdapter.java`
- `src/main/java/com/example/auditlog/api/common/ApiExceptionHandler.java`
- `src/test/java/com/example/auditlog/api/QueryAuditEventFilterIT.java`
- `src/test/java/com/example/auditlog/query/QueryCharacterizationIT.java`
- `src/test/java/com/example/auditlog/arch/QueryPathReadOnlyRuleTest.java`
- `.specs/api/query-api.md`

## Classes That Must Not Change

- `src/main/java/com/example/auditlog/api/append/AuditEventAppendController.java`
- `src/main/java/com/example/auditlog/application/append/AppendAuditEventService.java`
- `src/main/java/com/example/auditlog/application/append/AppendAuditEventCommand.java`
- `src/main/java/com/example/auditlog/persistence/append/JpaAuditEventAppendRepositoryAdapter.java`
- `src/main/resources/db/migration/**`

## Data Flow

`AuditEventQueryController` accepts optional `Action` and `Outcome` parameters and includes them in `AuditEventQuery`.

`QueryAuditEventService` passes the filters into `AuditEventSearch`.

`JpaAuditEventReadRepositoryAdapter` adds optional JPA specifications for action and outcome. The new predicates are combined with the existing actor/from/to predicates using `AND`.

## Safety

The characterization test proves absent params keep baseline behavior unchanged.

The ArchUnit rule proves query code does not depend on append/write code and does not call persistence write methods.
