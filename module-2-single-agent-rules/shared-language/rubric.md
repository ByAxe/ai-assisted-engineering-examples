# AI Readiness Rubric

| Axis | 0 | 1 | 2 | 3 |
| --- | --- | --- | --- | --- |
| Feedback signals | Manual only | Local tests | CI on every PR + linters | CI + pre-commit hooks + architecture tests + branch protection |
| Context substrate | Nothing | README and loose notes | Living `AGENTS.md` in repo | `AGENTS.md` + skills + task spec habit |
| Blast radius control | Chat only, code by hand | Agent in IDE on isolated branch | Worktrees + PR flow + branch protection | Sandboxed execution + explicit access rules for secrets and infrastructure |
