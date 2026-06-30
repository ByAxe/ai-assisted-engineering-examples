# Done Condition

The feature is done when every assertion below is checkable and satisfied.

1. Append/write path untouched: no diff under the append controller, append service, append command, append persistence adapter, or `src/main/resources/db/migration`; the append-only database guard still throws on `UPDATE` and `DELETE`.
2. Existing query results are identical on the seeded fixtures when the new params are absent; the characterization test stays green unchanged.
3. New filters return the expected subset: `?outcome=DENIED` returns only denied rows; `?action=EXPORT_DATA` returns only export rows; `?outcome=DENIED&action=EXPORT_DATA` combines filters with `AND`.
4. Invalid enum values return `400 Bad Request` with a clear message.
5. Compile and tests are green with `./gradlew test`.
6. No new cross-layer dependency exists: query path code does not import the append path, and the ArchUnit rule passes.
