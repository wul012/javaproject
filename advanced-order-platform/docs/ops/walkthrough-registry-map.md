# Walkthrough Registry Map

## Purpose

Walkthrough registries keep code explanations maintainable. They define which
sections must exist, how large a version should be, when Chinese longform
walkthroughs are required, and which runtime boundaries stay closed.

## Current Registry Family

| Registry | Endpoint | Main role |
| --- | --- | --- |
| Compliance registry | `/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry` | Standard sections and archive ranges |
| Quality gate registry | `/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry` | Avoid shallow micro-versions |
| Quality audit registry | `/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry` | Audit the gate and review evidence |
| Depth registry | `/api/v1/ops/shard-readiness/code-walkthrough-depth-registry` | Chinese longform and no-padding workload proof |

## Required Layers

| Layer | Expected evidence |
| --- | --- |
| Controller | A single read-only GET route |
| Service | `@Transactional(readOnly = true)` and deterministic catalog composition |
| Response | Counts, status, boundary flags, checks, and markdown sections |
| Catalog | Rule data with stable codes |
| Renderer | Human-readable summary generated from the catalog |
| Support | Count and status calculation |
| Tests | Route, service, renderer, boundary, controller, and archive compliance tests |

## Current Writing Gate

From Java v1774 onward, a committed version walkthrough must be Chinese
longform, contain at least 3000 Chinese characters, include `## 实际工作量说明`,
and explain real project-local work instead of padding.
