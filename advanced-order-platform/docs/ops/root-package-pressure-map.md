# Root package pressure map

The old `com.codexdemo.orderplatform.ops` package contains a long line of
historical evidence endpoints. Those classes are kept stable for already
published routes and tests. New readability upkeep work should avoid adding to
that pressure when a narrower package can carry the context.

## Pressure points

| Area | Current pressure | Preferred direction |
| --- | --- | --- |
| Shard readiness evidence | Many historical `OpsShardReadiness*` classes remain in the root ops package. | Keep existing public routes stable; add new maintenance-only work under focused subpackages. |
| Code walkthrough depth | The root package still owns the historical depth registry endpoint. | Use docs/ops maps to make the root entry discoverable before considering any later migration. |
| Readability upkeep | The new `ops.maintenance.readability` package carries the current upkeep work. | Keep route paths, response records, catalogs, renderers, support, services, controllers, and tests in the subpackage. |
| Screenshot and explanation archives | Archive rules are spread across docs and tests. | Keep archive navigation in segmented roots and point from docs/ops instead of dumping new files into broad roots. |

## Migration discipline

Root-package cleanup should be evidence-led. A future move needs a route map,
test map, response compatibility check, docs link, and Chinese walkthrough
before any class is renamed or relocated. Bulk rename work is not a default
maintenance action.

## Boundary

This map is documentation only. It does not change package names, routes,
schemas, database state, ports, credentials, services, containers, or upstream
projects.
