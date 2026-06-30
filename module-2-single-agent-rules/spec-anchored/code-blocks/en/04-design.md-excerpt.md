# Query API — design

## API contract
GET /api/v1/audit-events?actor=&resourceType=&resourceId=&from=&to=&limit=&cursor=
200 -> { items: [...], nextCursor: string | null }
400 -> malformed: empty filter set / bad cursor / unparseable timestamps / invalid limit
422 -> semantic: range > 31d / to < from / invalid filter combination

## Sort & determinism
ORDER BY recorded_at DESC, id DESC.
The id tiebreaker is required: without it keyset pagination breaks
when recordedAt values tie (bulk inserts in the same millisecond).

## Pagination
Keyset (cursor) on (recordedAt, id). Offset rejected: O(N) on large windows.
The cursor is opaque for clients and HMAC-signed in implementation.

## Indexes (Flyway V2)
(actor, recorded_at DESC, id DESC)
(resource_type, resource_id, recorded_at DESC, id DESC)

## Integration
api: new QueryAuditEventsController. Write path untouched.
