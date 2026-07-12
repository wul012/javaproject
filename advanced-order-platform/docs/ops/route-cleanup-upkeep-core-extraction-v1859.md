# v1859 RouteCleanup upkeep core extraction

## Scope

v1859 moves the exact twelve-file implementation closure behind
OpsShardReadinessRouteCleanupMaintenanceUpkeepController into the existing
ops.maintenance.routecleanup package. The closure contains five service/response
pairs plus the upkeep catalog and its private seed owner. The Spring controller
stays in the root composition package.

## Family Design

Abstraction: RouteCleanup upkeep core.

Data boundary: UpkeepCatalog and its private seeds own nine immutable maintenance
items; RouteCleanupRoutes owns five immutable route suffixes.

Behavior boundary: five services project the catalog into HTTP response views;
the root controller adapts HTTP and remaining root services consume public types.

Test boundary: OpsExtractionTestSupport owns mechanics; v1859 owns only its file,
route, visibility, reader, mirror, census, and walkthrough expectations.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one compiler-closed slice | Move exactly 12 production files and 5 owned behavior tests | exact inventory, root absence, and dependency guards | local gate passed |
| Keep the HTTP adapter visible | Retain UpkeepController in root and import the package boundary | controller source and focused integration tests | local gate passed |
| Preserve route bytes | Move 5 suffixes to RouteCleanupRoutes without changing values | reflection map and global-owner absence guard | local gate passed |
| Preserve data consumers | Expose only the catalog operations and immutable Item record required by 6 measured root readers | modifier and 13-source/37-edge census | local gate passed |
| Limit service visibility | Keep PROFILE private; expose only FailClosed ENDPOINT for its measured ShardFieldMap reader | reflection and production-reader guards | local gate passed |
| Repay temporary visibility | Return the 9 v1858 ENDPOINT fields to package visibility after Seeds moves beside them | zero-external-reader and modifier guards | local gate passed |
| Preserve response contracts | Relocate 10 existing SpotBugs response mirrors without adding waivers | exact old/new FQN counts | local gate passed |
| Tighten the census | Direct root 231 -> 219; movable 127 -> 115; RouteCleanup 123 -> 111 | endgame census and zero-unassigned guard | local gate passed |
| Explain before final verify | Archive exactly 10 Chinese headings and at least 3000 Han characters | walkthrough quality gate | local gate passed |

## Compatibility Boundary

No HTTP method, route byte, response component, catalog item, version number,
profile, evidence path, fixture byte, digest, transaction mode, write boundary,
credential boundary, service-startup behavior, deployment, rollback, or archive
layout changes. This version does not run Node or mini-kv and does not claim live
cross-project execution.

## Failure Conditions

- The dependency closure differs from the named twelve files.
- Any of the five route suffixes changes value or remains duplicated globally.
- A moved implementation imports a root RouteCleanup implementation.
- A v1858 endpoint remains public without an external production reader.
- FailClosed ENDPOINT becomes private while ShardFieldMap remains a reader.
- Catalog items, historical evidence paths, or response expectations change.
- SpotBugs mirrors are added, lost, or left at an old FQN.
- Root is not 219, total ops Java exceeds 1352, or the census has an unassigned file.
- The walkthrough fails its heading, depth, or Chinese-majority gates.
