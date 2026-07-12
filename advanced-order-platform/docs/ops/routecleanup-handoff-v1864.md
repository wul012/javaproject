# v1864 RouteCleanup handoff closure extraction

## Scope

v1864 moves the dependency-closed RouteCleanup handoff graph from the direct
`ops` root into the existing `ops.maintenance.routecleanup` implementation
package. The graph contains eleven service/response pairs and eleven owned
service tests. Root Spring controllers remain HTTP adapters.

## Family Design

- Abstraction: one read-only handoff and closeout evidence graph.
- Data boundary: immutable response records carry evidence projections.
- Behavior boundary: services compose package-owned evidence and digests.
- HTTP boundary: root controllers import public service/response types.
- Route boundary: `RouteCleanupRoutes` owns every moved suffix byte.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one closed graph | Relocate 22 production types and 11 owned tests | exact inventories and root-absence checks | focused gate passed |
| Preserve HTTP contracts | Keep three root controllers and all route bytes | route-owner reflection and controller mapping tests | focused gate passed |
| Preserve response contracts | Move records without changing components | focused behavior suite and full verify | focused gate passed; final verify required |
| Keep dependency direction | Package consumes no root RouteCleanup implementation | no-reverse-edge source guard | focused gate passed |
| Limit visibility | Public types for root adapters; only three measured ENDPOINT fields public | exact external-reader census | focused gate passed |
| Tighten the endgame | Root 174 -> 152; movable 70 -> 48; RouteCleanup 66 -> 44 | census script and shrink-only ratchets | focused gate passed |
| Explain before verify | Ten Chinese sections with at least 3000 Han | walkthrough compliance test | 3047 Han and ten headings passed |

## Compatibility Boundary

This version does not add, remove, or rename an HTTP endpoint; reorder a
response component; change a digest input; rewrite a fixture; enable a write;
read a credential value; open a managed-audit connection; start Java, Node, or
mini-kv; deploy; roll back; or move a frozen archive path.

`OpsShardReadinessRouteCleanupEndpointManifestService` continues to scan both
the retained global route aggregator and `RouteCleanupRoutes`. Moving the eleven
suffixes therefore changes ownership without changing the manifest names,
values, ordering, or endpoint count.

The service now crosses a Java package boundary to inspect the package-private
global fields. It calls `Field.trySetAccessible()` and fails closed when access
cannot be granted; it does not widen all 299 route fields into a public API.

## Measured Boundary

After extraction, ten direct-root source files are expected to hold 38 type
edges into all 22 moved production types. Only these ENDPOINT constants retain
public visibility for one measured root implementation reader each:

- `OpsShardReadinessRouteCleanupCiEvidenceService.ENDPOINT`
- `OpsShardReadinessRouteCleanupEndpointManifestService.ENDPOINT`
- `OpsShardReadinessRouteCleanupExtendedCloseoutService.ENDPOINT`

Every other moved ENDPOINT and every PROFILE remains package-private.

## Failure Conditions

- A listed production type or owned test remains in the direct root.
- A moved implementation imports a root `RouteCleanup` implementation.
- A route suffix changes by one byte or remains duplicated in the global table.
- The endpoint manifest loses, duplicates, or renames a RouteCleanup entry.
- The measured boundary differs from 10 sources, 38 edges, and 22 targets.
- More than the three measured ENDPOINT fields are public.
- Root count is not 152, movable count is not 48, RouteCleanup is not 44, or
  the census reports an unassigned file.
- The walkthrough has fewer than 3000 Han characters or not exactly ten
  required headings before final verification.

## Verification Plan

Run main and test compilation, the relocated behavior suite, route ownership,
v1857-v1863 boundary guards, the v1864 structural gate, Spotless, and full
`mvnw verify`. Push the implementation commit and require both remote jobs to
pass. Then close the ledger, create the descriptive v1864 tag, push both, and
require the closeout run to pass before v1865 writes begin.

Pre-final evidence: main compilation and all 872 test sources compiled. The
first 155-test focused run passed every service, route, manifest, visibility,
and v1864 boundary assertion; its nine failures were seven stale live-census
strings, the expected v1857 external-boundary contraction, and one overbroad
controller source assertion. The corrected 65-test v1857-v1864 repair suite
passed with zero failures, and Spotless reported zero dirty files. The v1857
live boundary tightened from 37/75/21/33 to 26/44/21/22.
