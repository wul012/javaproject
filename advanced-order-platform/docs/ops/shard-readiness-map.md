# Shard Readiness Map

## Purpose

Shard readiness endpoints prove that the Java side can describe readiness,
handoff, archive, and consumer evidence without activating write routing or a
live shard router.

## Entry Points

| Layer | Current entry |
| --- | --- |
| Controller | `OpsShardReadiness*Controller` classes under `src/main/java/com/codexdemo/orderplatform/ops` |
| Service | `OpsShardReadiness*Service` classes, usually `@Transactional(readOnly = true)` |
| Response | `OpsShardReadiness*Response` records |
| Catalog | Static `*Catalog` or `*RoutePaths` classes that describe fixtures, endpoints, checks, and boundaries |
| Tests | `OpsShardReadiness*Tests` and `OpsShardReadiness*IntegrationTests` |
| Endpoint root | `/api/v1/ops/shard-readiness` |

## Read-only Boundary

Shard readiness upkeep must keep these actions closed:

- write routing
- active shard router
- credential value reads
- raw endpoint URL resolution
- managed audit HTTP/TCP calls
- deployment and rollback
- Java or mini-kv autostart

## Reading Order

1. Start from a route path or controller.
2. Open the service to see which catalogs are composed.
3. Open the response record to understand the public schema.
4. Open service and boundary tests to confirm read-only guarantees.
5. Use the matching code walkthrough only after the code path is clear.
