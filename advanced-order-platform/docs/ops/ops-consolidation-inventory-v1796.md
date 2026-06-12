# v1796 ops consolidation inventory

Status: active consolidation baseline for J6. This document is intentionally
read-only. It records the current ops package pressure before any class
movement so later batches can reduce root-package pressure without losing
route, archive, or evidence compatibility.

## Count Baseline

Measured on 2026-06-12, after v1795 and before v1796 class movement:

| Metric | Current value | Maintenance meaning |
| --- | ---: | --- |
| `src/main/java/com/codexdemo/orderplatform/ops` Java files | 1,352 | Hard upper bound already protected by `ReadabilityUpkeepGovernanceConsolidationPlanTests`. |
| Root `ops` package direct Java files | 1,330 | Primary pressure point for future consolidation. |
| Java files with `Readiness` in the name | 1,210 | The dominant governance/evidence naming family. |
| Direct support subpackages | 2 | `maintenance` and `maintenance/readability`; most legacy evidence still lives in the root package. |

The current ratchet is a ceiling, not a target. Future consolidation should
lower the root count or improve local package readability before any new ops
surface is added.

## Route Family Inventory

The root package is not one feature. It is a long-lived archive of route
families that were added to preserve read-only readiness evidence while Node
and mini-kv were evolving. The largest route family clusters currently visible
from class names are:

| Route family | Approximate files | Notes |
| --- | ---: | --- |
| `OperatorEvidenceValueSupply` | 301 | Repeated approval, artifact, text-package, and preflight evidence lanes. |
| `RouteCleanup` | 184 | Cleanup, completion, sustainment, and read-window evidence lanes. |
| `ReleaseAcceptance` | 161 | Acceptance, archive verification, route-path split, and closeout lanes. |
| `MinimalReadOnlyGate` | 130 | Operator CI handoff, archive digest, verification dossier, and release acceptance gates. |
| `CandidateDocument` | 65 | Candidate document intake, material request, profile section, and handoff packets. |
| `CodeWalkthrough` | 44 | Walkthrough depth, quality gate, compliance, and audit registries. |

The broader shape is also visible by suffix:

| Suffix | Count |
| --- | ---: |
| `Service` | 375 |
| `Catalog` | 347 |
| `Response` | 179 |
| `Renderer` | 121 |
| `Controller` | 102 |
| `Support` | 97 |
| `Builder` | 49 |
| `RoutePaths` | 17 |

These counts show where future extraction work should look first. The next
batches should avoid creating new root-package controller/service/catalog
triples unless there is a deliberate contract reason.

This inventory is the first reduction candidate map for J6. It does not
approve movement by itself; it only marks where later evidence-preserving work
should begin.

## Load-Bearing Archive Boundary

Do not rename or move archive folders `a/` through `f/`, especially
`e/<version>/`, and do not rename or move evidence JSON files. Node has
hardcoded absolute references and content digests into Java and mini-kv archive
trees. A Java-local cleanup that changes those paths can silently break a
downstream consumer even when all Java unit tests pass.

Load-bearing means:

- historical archive roots stay in place;
- evidence JSON filenames stay in place;
- read-only route paths stay in place;
- response fields and schema names stay stable unless a separate contract plan
  explicitly approves a migration;
- docs may point to the archive, but may not replace or relocate it.

## Reduction Candidate List

The first safe reductions should be internal and contract-preserving:

1. Move new maintenance-only support code into `ops.maintenance.*` instead of
   the legacy root package.
2. For future class relocation, start with families that already have
   controller/service/catalog/renderer/support repetition and stable route
   path tests.
3. Prefer package extraction for support/catalog/rendering helpers before
   renaming public response classes.
4. When a family is too load-bearing to move, record a skipped-cluster note
   rather than adding another root-package class.
5. Keep `RoutePaths` classes and controller tests as the first contract check
   for any later movement.

Concrete first candidates:

- `CodeWalkthrough*` can become the model for future registry-family
  consolidation because it already has tests and maintenance docs.
- `CandidateDocument*` is smaller than the other major families and may be a
  safer rehearsal for package extraction after this inventory.
- `OperatorEvidenceValueSupply*` is the largest family, but it is too broad for
  a first move. It should be split only after a smaller rehearsal proves the
  migration pattern.

## v1796 Stop Line

No class moves in v1796. This version creates the inventory, documents the
load-bearing archive boundary, updates the local progress record, and adds a
test that keeps the inventory discoverable. The next version may begin a small
contract-preserving extraction only after route paths, response schemas, and
archive references are explicitly checked.
