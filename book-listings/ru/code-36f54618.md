# Query API — tasks

- [ ] T1. Flyway V2: indexes for Query API
  - refs: design.md#indexes
  - DoD: migration applies on empty + non-empty DB; indexes verified.
- [ ] T2. AuditEventQuery value object + validation
  - refs: design.md#validation, requirements.md#AC-1.3
  - DoD: unit test per validation rule.
- [ ] T3. Cursor codec (encode/decode + sign/verify)
  - refs: design.md#pagination
  - DoD: unit tests incl. corrupted / partial cursor and signature check.
- [ ] T4. Repository.search keyset pagination (actor and resourceType/resourceId filters)
  - refs: design.md#pagination, design.md#indexes
  - DoD: integration test, 250 events, no gaps, no duplicates, both filter paths covered.
- [ ] T5. QueryAuditEventsController + error mapping
  - refs: design.md#api-contract, requirements.md#US-1, requirements.md#US-2
  - DoD: integration tests: happy path (actor + resource) + 400 + 422 + empty result.
- [ ] T6. AGENTS.md note: Query API is read-only, append-only preserved
  - refs: requirements.md#problem
  - DoD: invariant line added and reviewed.
