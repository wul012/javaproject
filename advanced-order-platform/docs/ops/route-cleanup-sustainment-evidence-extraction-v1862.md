# v1862 RouteCleanup sustainment evidence extraction

## Scope

v1862 moves the exact twelve-file evidence closure behind
OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceController into
ops.maintenance.routecleanup. The six service/response pairs are
HandoffAcceptanceDigest, DependencyBoundaryMap, ArchiveRetentionCalendar,
TestEvidenceRollup, OperationsScorecard, and SustainmentCloseout. Six owned
behavior tests and their shared package-private fixture move with the closure;
the Spring controller remains the root HTTP adapter.

## Family Design

Abstraction: RouteCleanup maintenance sustainment evidence.

Data boundary: six immutable responses form a one-way evidence pipeline.

Behavior boundary: leaf projections feed scorecard and closeout aggregation.

Visibility boundary: only measured endpoint readers cross the package.

Test boundary: one package-private fixture serves two composition tests.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one compiler-closed slice | Move exactly 12 production files, 6 tests, and their fixture | exact inventory, root absence, and dependency guards | verified |
| Keep the HTTP adapter visible | Retain SustainmentEvidenceController in root and import the package boundary | controller source and focused integration tests | verified |
| Preserve route bytes | Move 6 suffixes to RouteCleanupRoutes without changing values | reflection map and global-owner absence guard | verified |
| Preserve measured readers | Keep the exact 6-source, 20-edge, 12-target production boundary | shared boundary census | verified |
| Limit service visibility | Keep all PROFILE private; expose exactly five consumed ENDPOINT fields | reflection and external-reader guards | verified |
| Repay catalog visibility | Narrow UpkeepCatalog, its queries, and Item to package visibility | reflection and zero-external-reader guards | verified |
| Preserve upstream contraction | Tighten v1857 to 37/75/21/33, v1859 to 2/11/10, v1860 to 3/12/10, and v1861 to 4/13/10 | historical census guards | verified |
| Preserve response contracts | Relocate 12 existing SpotBugs response mirrors without adding waivers | exact old/new FQN counts | verified |
| Tighten the census | Direct root 199 -> 187; movable 95 -> 83; RouteCleanup 91 -> 79 | endgame census and zero-unassigned guard | verified |
| Explain before final verify | Archive exactly 10 Chinese headings and at least 3000 Han characters | walkthrough quality gate | verified |

## Compatibility Boundary

This extraction does not add, remove, or rename an HTTP route. It does not
change response components, score weights, catalog items, archive intervals,
profile strings, source-plan labels, evidence paths, fixture bytes, transaction
mode, write routing, credentials, service startup, deployment, rollback, or
archive layout. Node and mini-kv remain read-only evidence subjects and are not
started by this version.

## Failure Conditions

- Any of the six route suffixes differs from its v1861 byte value.
- Any moved service imports a root RouteCleanup implementation.
- The root controller or measured readers cannot compile through the narrow
  public package boundary.
- HandoffAcceptanceDigest ENDPOINT, any PROFILE, or any unconsumed symbol is
  public.
- UpkeepCatalog still exposes a public API after its last external reader moves.
- The v1857, v1859, v1860, v1861, or v1862 boundary census differs from its exact value.
- Any historical fixture, response expectation, score weight, or evidence byte
  changes.
- Direct root is not 187, total ops Java exceeds 1352, or unassigned files exist.
- The walkthrough has fewer than 3000 Han characters or differs from the ten
  required headings.

## Local Verification

- Focused regression: 51 tests, zero failures, errors, or skips.
- First full verify: correctly failed because the v1857 external analyzer
  boundary still expected 38/76/21/34 after DependencyBoundaryMap moved beside
  the analyzer.
- Historical repair: v1857 tightened to 37/75/21/33; focused repair suite 16/16
  passed and Spotless was re-applied without loosening a gate.
- Final full `mvnw verify`: 451.5 seconds; 1,865 tests, zero failures, errors, or
  skips.
- JaCoCo: 2,228 classes analyzed and every configured threshold met.
- SpotBugs: zero bug instances and zero analysis errors.
- Mechanical census: direct root 187, movable 83, RouteCleanup 79,
  unassigned zero.
