# v1860 RouteCleanup upkeep assurance extraction

## Scope

v1860 moves the exact ten-file implementation closure behind
OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController into
ops.maintenance.routecleanup. The five service/response pairs are
ArchiveDigestLedger, OperatorReviewPacket, VersionLineage, ReadinessGate, and
UpkeepCloseout. The Spring controller remains the root HTTP adapter.

## Family Design

Abstraction: RouteCleanup upkeep assurance pipeline.

Data boundary: existing response records preserve immutable report payloads.

Behavior boundary: five services compose v1859 upkeep facts in dependency order;
the root controller only maps GET routes.

Test boundary: OpsExtractionTestSupport owns file and walkthrough mechanics;
OpsBoundaryTestSupport owns boundary census and reader lookup. Version guards
provide inventories, policy, routes, and exact values.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one compiler-closed slice | Move exactly 10 production files and 5 owned behavior tests | exact inventory, root absence, and dependency guards | local gate passed |
| Keep the HTTP adapter visible | Retain UpkeepAssuranceController in root and import the package boundary | controller source and focused integration tests | local gate passed |
| Preserve route bytes | Move 5 suffixes to RouteCleanupRoutes without changing values | reflection map and global-owner absence guard | local gate passed |
| Preserve measured readers | Keep the exact 5-source, 18-edge, 10-target production boundary | shared boundary census | local gate passed |
| Limit service visibility | Keep PROFILE private; expose only ArchiveDigestLedger and VersionLineage ENDPOINT for measured readers | reflection and external-reader guards | local gate passed |
| Consolidate repeated guards | Move generic boundary census and external-reader lookup into OpsBoundaryTestSupport while keeping both support files under 80 lines | v1858 size gate plus v1859/v1860 shared-engine use | local gate passed |
| Preserve response contracts | Relocate 10 existing SpotBugs response mirrors without adding waivers | exact old/new FQN counts | local gate passed |
| Tighten the census | Direct root 219 -> 209; movable 115 -> 105; RouteCleanup 111 -> 101 | endgame census and zero-unassigned guard | local gate passed |
| Explain before final verify | Archive exactly 10 Chinese headings and at least 3000 Han characters | walkthrough quality gate | local gate passed |

## Compatibility Boundary

This extraction does not add, remove, or rename an HTTP route. It does not
change response components, catalog items, profile strings, evidence paths,
digest material, fixture bytes, transaction mode, write routing, credential
access, service startup, deployment, rollback, or archive layout. Node and
mini-kv remain read-only evidence subjects and are not started by this version.

## Failure Conditions

- Any of the five route suffixes differs from its v1859 byte value.
- Any moved service imports a root RouteCleanup implementation.
- The root controller or measured readers cannot compile through the narrow
  public package boundary.
- A PROFILE or an unconsumed ENDPOINT becomes public.
- The shared test engine is bypassed by another copied boundary scanner.
- Any historical fixture, response expectation, or evidence byte changes.
- Direct root is not 209, total ops Java exceeds 1352, or unassigned files exist.
- The walkthrough has fewer than 3000 Han characters or differs from the ten
  required headings.
