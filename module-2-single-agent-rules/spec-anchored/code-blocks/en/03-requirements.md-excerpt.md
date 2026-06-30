# Query API — requirements

## Problem
The append-only audit log has no read API; compliance, SRE and security
hit the database directly. We need a read-only HTTP endpoint to search
events by actor, resourceType/resourceId and time range, without breaking immutability.

## User stories
US-1. As a compliance officer, I want events for an actor within a time
range, so that I can confirm or refute an action during an audit.
  AC-1.1: valid actor + from/to -> 200, sorted by recordedAt DESC, id DESC.
  AC-1.2: no matches -> 200, empty items, nextCursor: null.
  AC-1.3: range > 31 days -> 422.
  AC-1.4: empty filter set -> 400.
US-2. As an SRE, I want events for a resourceType/resourceId within a time
range, so that I can reconstruct what happened to a resource during an incident.
  AC-2.1: valid resourceType + resourceId + from/to -> 200, sorted by recordedAt DESC, id DESC.
US-3. As a security analyst, I want a paginated, gap-free and duplicate-free
walk through a large result set.
  AC-3.1: valid cursor -> next page with no gaps and no duplicates.

## Out of scope
- Multi-actor filters -> v2.
- Action / outcome filters -> later brownfield extension.
- Full-text search over context.

## Open questions
- None — cursor format settled in design as opaque signed cursor (HMAC).
