# Product Plan

This service is the clean starting point for the companion repository.

The current implementation keeps these constraints explicit:

- The domain package has no JPA annotations.
- Append and query paths are split into separate packages.
- Seed data is deterministic for query characterization.
- The database trigger rejects every update and delete.

