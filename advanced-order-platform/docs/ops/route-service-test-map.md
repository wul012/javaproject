# Route service test map

This map gives maintainers a narrow reading path for ops readability routes.
It does not start the application, call upstream systems, or replace the
source tests. It records the current read-only contract before more registry
work is added.

## Current routes

| Route | Controller | Service | Response | Primary tests |
| --- | --- | --- | --- | --- |
| `/api/v1/ops/readability/upkeep-registry` | `ReadabilityUpkeepRegistryController` | `ReadabilityUpkeepRegistryService` | `ReadabilityUpkeepRegistryResponse` | `ReadabilityUpkeepRegistryServiceTests`, `ReadabilityUpkeepRegistryControllerTests`, `ReadabilityUpkeepBoundaryTests`, `ReadabilityUpkeepRegistryRendererTests` |
| `/api/v1/ops/readability/upkeep-audit` | `ReadabilityUpkeepAuditController` | `ReadabilityUpkeepAuditService` | `ReadabilityUpkeepAuditResponse` | `ReadabilityUpkeepAuditCatalogTests`, `ReadabilityUpkeepAuditServiceTests`, `ReadabilityUpkeepAuditControllerTests`, `ReadabilityUpkeepAuditBoundaryTests`, `ReadabilityUpkeepAuditRendererTests` |

## Next readability upkeep route rule

Future readability route work should use the same small route pattern instead
of adding another broad root-package class:

- Route constant in `ReadabilityUpkeepRoutePaths`.
- Response record in `ops.maintenance.readability`.
- Catalog classes for static route, package, boundary, and verification facts.
- Renderer/support/service/controller layers with one responsibility each.
- Unit tests for path constants, service counts, renderer sections, boundary
  denials, controller pass-through, docs presence, and Chinese walkthrough
  archive compliance.

## Boundary

Every route on this map is read-only. The map explicitly excludes write
routing, active shard routers, credential value reads, raw endpoint URL
resolution, managed audit connections, deployment, rollback, Java autostart,
and mini-kv autostart.
