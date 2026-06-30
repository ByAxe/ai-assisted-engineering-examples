# AI-Assisted Engineering Examples

Companion repository for the AI-Assisted Engineering book and education program.

This repository contains the runnable Audit Log Service example and reusable project artifacts referenced by the book: prompts, `AGENTS.md` variants, specs, workflow examples, tests, checklists, and templates.

## Start Here

- [`book-map.md`](book-map.md) maps book modules and chapters to files in this repository.
- [`artifact-inventory.tsv`](artifact-inventory.tsv) lists the included artifacts.
- [`module-0-running-example/audit-log-service`](module-0-running-example/audit-log-service) is the runnable example project used throughout the book.

## Repository Layout

```text
module-0-running-example/
module-1-foundations/
module-2-single-agent-rules/
module-3-spec-driven-development/
module-4-agent-team-scale/
module-5-organization-adoption/
```

Each module folder follows the book route. Files inside are intentionally plain text/source files so they can be copied into a real project.

## Run The Example Project

```bash
cd module-0-running-example/audit-log-service
./gradlew test
```

For a manual local database:

```bash
docker compose up -d
./gradlew bootRun
```

The service exposes:

- `POST /api/v1/audit-events`
- `GET /api/v1/audit-events`
