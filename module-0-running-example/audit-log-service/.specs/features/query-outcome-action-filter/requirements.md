# Query Outcome And Action Filter Requirements

## User Story

As a compliance reviewer, I want to filter audit events by `outcome` and `action` so that I can narrow investigations without changing audit records.

## Feature

Add two optional query parameters to `GET /api/v1/audit-events`:

- `outcome`: one of `ALLOWED` or `DENIED`.
- `action`: one of the known `Action` enum values.

Both filters are additive:

- If `outcome` is absent, outcome must not affect results.
- If `action` is absent, action must not affect results.
- If both are present, they are combined with `AND`.

## Validation

Invalid enum values return `400 Bad Request` with a clear message naming the invalid parameter.

## Constraints

- The query API remains read-only.
- The append/write path must not change.
- Database migrations must not change.
- Existing baseline query behavior must remain identical when the new params are absent.

