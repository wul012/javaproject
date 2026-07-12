# v1858 RouteCleanup maintenance core extraction

## Scope

v1858 moves the exact eighteen-file implementation closure behind
OpsShardReadinessRouteCleanupMaintenanceController into
ops.maintenance.routecleanup. The nine service/response pairs are SegmentCatalog,
Continuity, LatestSibling, HandoffPairAudit, BoundaryDrift, SourcePlanAlignment,
TestBudgetPlan, ArchiveManifest, and Closeout. The Spring controller and the
maintenance upkeep catalog stay in the root composition package.

The existing RouteCleanupRoutes data owner receives the nine byte-identical
suffixes. Moved services become behavior owners and may expose only immutable
ENDPOINT constants required by measured root readers. PROFILE remains
package-private. No new production type is introduced.

## Family Design

Abstraction: RouteCleanup maintenance core.

Data boundary: RouteCleanupRoutes owns immutable route suffixes; response records
carry the existing read-only payload bytes.

Behavior boundary: nine services transform the already extracted v1857 evidence
boundary into maintenance reports; the root controller only adapts HTTP and the
root upkeep catalog only composes public endpoint references.

Test engine boundary: OpsExtractionTestSupport owns repeated filesystem,
walkthrough, and string-count mechanics. Version tests retain only their
version-specific file inventory, route map, boundary census, and expected counts.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one compiler-closed slice | Move exactly 18 production files and 9 owned behavior tests | v1858 exact inventory guard plus root absence checks | local verify passed |
| Keep HTTP composition visible | Retain the maintenance controller and upkeep catalog in root | import and public-boundary guard | local verify passed |
| Preserve route bytes | Move 9 suffix constants into RouteCleanupRoutes without changing values | reflection map and global-owner absence guard | local verify passed |
| Limit visibility | Publicize only the 9 immutable ENDPOINT fields used by measured readers; keep PROFILE private | reflection modifiers and source scan | local verify passed |
| Preserve response and archive contracts | Relocate existing response FQNs and all 20 SpotBugs mirrors | exact mirror-count guard and behavior tests | local verify passed |
| Tighten the endgame census | Direct root 249 -> 231; movable 145 -> 127; RouteCleanup 141 -> 123 | live census, historical transition, and zero-unassigned guards | local verify passed |
| Buy elegance at generation time | Extract shared version-test mechanics before adding another guard | shared-support use and identifier budget checks | local verify passed |
| Explain before final verify | Archive a Chinese-majority walkthrough with exactly 10 headings and at least 3000 Han characters | v1858 walkthrough gate | local verify passed |

## Compatibility Boundary

This extraction does not add, remove, or rename an HTTP route. It does not
change response components, profile strings, evidence paths, fixture bytes,
digest material, transaction mode, write routing, credential access, service
startup, deployment, rollback, or archive layout. Node and mini-kv remain
read-only evidence subjects; this version neither starts them nor claims live
cross-project execution.

## Failure Conditions

- Any route suffix differs from its v1857 byte value.
- Any moved service imports a root RouteCleanup implementation.
- The root controller cannot compile against the narrow public package boundary.
- PROFILE becomes public without a measured reader.
- A historical fixture, response expectation, or evidence byte is changed.
- The direct-root count is not 231, total ops Java exceeds 1352, or the census
  has an unassigned file.
- The new version guard clones filesystem and walkthrough helpers instead of
  using the shared test engine.
- The walkthrough has fewer than 3000 Han characters or differs from the ten
  required headings.
