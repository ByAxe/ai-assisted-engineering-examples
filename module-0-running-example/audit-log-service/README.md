# Audit Log Service

Audit Log Service is an append-only compliance audit log. Audit events can be inserted, but they must never be updated or deleted. The invariant is enforced in application structure and in PostgreSQL with a mutation-blocking trigger.

## Run

```bash
./gradlew test
```

The test suite uses Testcontainers with PostgreSQL 16.

For a manual local database:

```bash
docker compose up -d
./gradlew bootRun
```

The service exposes:

- `POST /api/v1/audit-events`
- `GET /api/v1/audit-events`

## Append-Only Invariant

- The server generates `id` and `recordedAt`.
- Clients never send `recordedAt`.
- `actor`, `action`, and `outcome` are required.
- No update or delete endpoint exists.
- The database trigger rejects every `UPDATE` and `DELETE` on `audit_events`.
- Query code is kept separate from append code so the read path can be structurally checked.

## Query API

`GET /api/v1/audit-events` supports these filters:

- `action`
- `actor`
- `from`
- `outcome`
- `to`
- `page`
- `size`

Results are sorted by `recordedAt` descending. `size` defaults to `50` and is capped at `200`.

