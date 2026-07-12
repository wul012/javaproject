# v1861 RouteCleanup sustainment core extraction

## Scope

v1861 moves the exact ten-file implementation closure behind
OpsShardReadinessRouteCleanupMaintenanceSustainmentController into
ops.maintenance.routecleanup. The five service/response pairs are
ReleaseChecklist, RemediationQueue, FreshnessWindow, OwnershipRegister, and
RiskLedger. Five owned behavior tests move with the implementation; the Spring
controller remains the root HTTP adapter.

## Family Design

Abstraction: RouteCleanup maintenance sustainment core.

Data boundary: five immutable responses preserve existing review projections.

Behavior boundary: checklist, preview queue, freshness, ownership, and risk
services consume v1859/v1860 facts; the root controller only maps GET routes.

Test boundary: existing extraction and boundary supports own mechanics; the
v1861 guard owns only inventory, policy, routes, and exact values.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one compiler-closed slice | Move exactly 10 production files and 5 owned behavior tests | exact inventory, root absence, and dependency guards | verified |
| Keep the HTTP adapter visible | Retain SustainmentController in root and import the package boundary | controller source and focused integration tests | verified |
| Preserve route bytes | Move 5 suffixes to RouteCleanupRoutes without changing values | reflection map and global-owner absence guard | verified |
| Preserve measured readers | Keep the exact 5-source, 19-edge, 10-target production boundary | shared boundary census | verified |
| Limit service visibility | Keep PROFILE private; expose only RiskLedger ENDPOINT for three measured readers | reflection and external-reader guards | verified |
| Preserve upstream contraction | Tighten v1859 to 4/13/11 and v1860 to 3/12/10 as readers move beside dependencies | historical shared-census guards | verified |
| Preserve response contracts | Relocate 10 existing SpotBugs response mirrors without adding waivers | exact old/new FQN counts | verified |
| Tighten the census | Direct root 209 -> 199; movable 105 -> 95; RouteCleanup 101 -> 91 | endgame census and zero-unassigned guard | verified |
| Explain before final verify | Archive exactly 10 Chinese headings and at least 3000 Han characters | walkthrough quality gate | verified |

## Compatibility Boundary

This extraction does not add, remove, or rename an HTTP route. It does not
change response components, catalog items, risk entries, profile strings,
evidence paths, fixture bytes, transaction mode, write routing, credential
access, service startup, deployment, rollback, or archive layout. Node and
mini-kv remain read-only evidence subjects and are not started by this version.

## Failure Conditions

- Any of the five route suffixes differs from its v1860 byte value.
- Any moved service imports a root RouteCleanup implementation.
- The root controller or measured readers cannot compile through the narrow
  public package boundary.
- A PROFILE or an unconsumed ENDPOINT becomes public.
- The v1859, v1860, or v1861 boundary census differs from its exact value.
- Any historical fixture, response expectation, or evidence byte changes.
- Direct root is not 199, total ops Java exceeds 1352, or unassigned files exist.
- The walkthrough has fewer than 3000 Han characters or differs from the ten
  required headings.

## Local Verification

- Focused regression: 40 tests, zero failures, errors, or skips.
- Full `mvnw verify`: 605.9 seconds; 1,856 tests, zero failures, errors, or skips.
- JaCoCo: 2,228 classes analyzed and every configured threshold met.
- SpotBugs: zero bug instances and zero analysis errors.
- Mechanical census: direct root 199, movable 95, RouteCleanup 91,
  unassigned zero.
