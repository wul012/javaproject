# RouteCleanup Post-Completion Closeout v1865

Status: local verification passed; evidence is accepted only after remote CI
also passes.

## Design Note

- Abstraction: one `routecleanup` package owns the complete read-only RouteCleanup graph.
- Data boundary: response records remain immutable transport data; services retain behavior.
- Behavior boundary: four root controllers remain HTTP adapters and depend inward on the package.
- Route boundary: all 84 suffixes live in `RouteCleanupRoutes`; bytes do not change.
- Test boundary: service tests and their fixtures follow the implementation package.
- Visibility boundary: service types stay public for Spring wiring; all `ENDPOINT` fields become package local.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Close the remaining family | Move 22 service/response pairs and 22 owned service tests into `ops.maintenance.routecleanup` | exact file-set guard and production/test compilation | local passed; remote CI pending |
| Preserve HTTP behavior | Keep Assurance, Completion, Governance, and PostCompletion controllers in root | exact 4-source / 44-edge / 44-target boundary and integration regression | local passed; remote CI pending |
| Preserve route bytes | Move the final 22 suffixes from the global route table to `RouteCleanupRoutes` | exact route map, global absence, and 84-entry manifest | local passed; remote CI pending |
| Remove reverse ownership | Make EndpointManifest read only the family route owner | source-direction guard and compilation | local passed; remote CI pending |
| Repay visibility debt | Return every RouteCleanup `ENDPOINT` and `PROFILE` field to package visibility | reflection plus external-reader census | local passed; remote CI pending |
| Preserve static-analysis policy | Relocate all 44 SpotBugs response mirrors without adding exclusions | exact old/new FQN counts and SpotBugs zero-findings gate | local passed; remote CI pending |
| Tighten the endgame | Root 152 -> 108; movable 48 -> 4; RouteCleanup 44 -> 0 | committed census script and shrink-only ratchets | local passed; remote CI pending |
| Explain the change | Chinese-majority walkthrough with exactly 10 headings and at least 3,000 Han characters | v1865 walkthrough guard | local passed; remote CI pending |

## Scope

The move contains the complete post-completion chain from `AuditTrail` through
`PostCompletionCloseout`. The 44 production types are a dependency-closed set:
after they move, no RouteCleanup implementation remains in the direct-root
package. The four controllers stay at the HTTP boundary and import the public
service/response types. No business command, credential value, database write,
deployment action, rollback action, archive path, response component, or route
string is changed.

The 22 service tests move with their implementations. The shared RouteCleanup
fixture becomes package local beside those tests. The post-completion fixture
also moves, but keeps its one public factory because three prototype tests in a
sibling package intentionally reuse that immutable test graph.

## Pre-Final Evidence

- `mvnw -B spotless:apply` completed successfully and changed no already-clean file.
- Production and test compilation passed with 1,483 main and 873 test source files.
- The broad focused selection executed 133 tests twice. In each run, 132 tests
  passed; the remaining v1857 assertion exposed one now-stale visibility promise.
- The two historical promises were tightened from public to package visibility:
  the analyzer's nested record and every RouteCleanup `ENDPOINT`. The repaired
  v1857 guard then passed all seven tests.
- The v1865 guard passed all eight checks: exact files, direction, route manifest,
  4/44/44 boundary, visibility, SpotBugs mirrors, census, and walkthrough depth.
- The walkthrough already contains 3,072 Han characters and exactly ten required
  headings. It will not be edited after the final verify begins.
- Final local `mvnw -B verify` passed in 10:02 with 1,892 tests, zero failures,
  zero errors, and zero skips. JaCoCo analyzed 2,228 classes and measured 32,852
  covered versus 611 missed lines (98.17%); SpotBugs analyzed 2,316 classes with
  zero findings.

## Failure Conditions

- Any route suffix or full endpoint differs byte-for-byte from v1864.
- Any moved production type remains in direct root, or any named file is missing.
- Any implementation under `ops.maintenance.routecleanup` imports a direct-root
  `OpsShardReadinessRouteCleanup*` implementation.
- Any RouteCleanup `ENDPOINT` or `PROFILE` field remains public without a measured
  external production reader.
- The endpoint manifest contains anything other than the same 84 entries.
- Direct root is not 108, movable root is not 4, RouteCleanup is not 0, or the
  unassigned bucket is non-zero.
- A SpotBugs mirror is missing, duplicated, left at the old FQN, or a new waiver
  is introduced.
- The walkthrough is written or expanded after the final verify.

## Verification Plan

1. Compile production and test source after package and route ownership changes.
2. Run all 22 moved service tests, route tests, endpoint-manifest tests, v1857-v1865
   structural guards, and the endgame census guards.
3. Run Spotless and scan old imports, old route fields, reverse dependencies,
   endpoint visibility, SpotBugs mirrors, and the working-tree diff.
4. Finalize the walkthrough, then run one complete `mvnw verify`.
5. Push the implementation, require both GitHub Actions jobs green, close the J75
   ledger row, create the annotated v1865 tag, and require closeout CI green.
