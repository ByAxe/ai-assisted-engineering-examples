# Spec self-eval checklist

## requirements.md
- [ ] Each user story has at least one acceptance criterion
- [ ] AC are testable (can be turned into a test without inference)
- [ ] Out of scope section is filled in explicitly
- [ ] Open questions list is non-empty OR explicitly states "no questions"

## design.md
- [ ] API contract covers all status codes, not just 200
- [ ] Pagination strategy is chosen and justified
- [ ] Sort order is deterministic (tiebreaker present)
- [ ] Indexes are specified for all filters
- [ ] Edge cases (empty filter, max range) are described
- [ ] Integration with existing code: explicit by api/domain/infrastructure layers
- [ ] AGENTS.md invariants linkage is explicit

## tasks.md
- [ ] Each task has refs to requirements/design
- [ ] Each task has DoD
- [ ] Task size: one safe commit/PR
- [ ] Order accounts for dependencies (migration before domain, domain before api)
