# Ops readability upkeep index

This directory is the human entry point for late-stage ops readability upkeep.
It does not replace source code, tests, or registry responses. It gives a
maintainer a short route into the main ops themes before they search the Java
package.

## Maps

| Map | Scope | Primary question |
| --- | --- | --- |
| `shard-readiness-map.md` | shard readiness and read-only evidence | Which endpoints prove readiness without opening write routing? |
| `walkthrough-registry-map.md` | code walkthrough quality and depth | Which registries govern code explanation quality? |
| `archive-layout-map.md` | screenshot/explanation and archive layout | Which docs/tests stop archive sprawl? |
| `registry-template.md` | read-only ops registry shape | Which layers and tests must a new ops registry carry? |
| `class-name-trial.md` | new ops readability subpackages | Which repeated prefixes can be dropped only after package context exists? |
| `route-service-test-map.md` | ops readability routes, services, and tests | Which route is backed by which service and which guard tests? |
| `root-package-pressure-map.md` | ops root package pressure | Which work should stay in the new readability subpackage instead of the old root? |
| `readability-upkeep-cycle.md` | late-stage readability upkeep cycle | Which sequence keeps maps, models, read-only routes, docs guards, and closeout aligned? |
| `readability-upkeep-audit-closeout.md` | audit registry closeout evidence | Which Java-only checks close the current readability upkeep audit batch? |
| `ops-consolidation-inventory-v1796.md` | ops consolidation inventory | Which route family clusters, load-bearing archive paths, and reduction candidates should guide the next split? |
| `code-walkthrough-compliance-extraction-v1797.md` | first ops extraction | Which code walkthrough compliance classes moved out of the root package while preserving routes and archives? |
| `quality-gate-registry-extraction-v1798.md` | second ops extraction | Which code walkthrough quality gate registry classes moved out of the root package while preserving routes and archives? |
| `quality-audit-registry-extraction-v1799.md` | third ops extraction | Which code walkthrough quality audit registry classes moved out of the root package while preserving routes and archives? |
| `depth-registry-extraction-v1800.md` | fourth ops extraction | Which code walkthrough depth registry classes moved out of the root package while preserving routes and archives? |
| `screenshot-explanation-archive-extraction-v1801.md` | fifth ops extraction | Which screenshot explanation archive registry classes moved out of the root package while preserving routes and archives? |

## Boundary

This upkeep index is read-only documentation. It does not start Java, mini-kv,
Node, Docker, browsers, or managed audit connections. It does not read
credentials, resolve raw endpoint URLs, deploy, roll back, or mutate business
state.

## Maintenance rule

New ops registry work should prefer a narrow subpackage, a route constant, a
response record, catalog data, renderer/support/service/controller layers, and
tests. Long class names should be shortened only when the package name already
contains the missing context.

## Active consolidation plan

The current Java-side follow-up to the Node v2114 governance consolidation is
documented in `../plans/v1789-java-ops-governance-consolidation-roadmap.md`
and `../plans/v1789-codex-ops-migration-playbook.md`. The plan is
contract-preserving and archive-preserving: Do not rename or move `a/` through
`f/`, `e/<version>/`, or evidence JSON files.

The first J6 inventory is `ops-consolidation-inventory-v1796.md`. It records
root-package pressure, route family clusters, load-bearing archive boundaries,
and the first reduction candidates before any class movement begins.

The first J7 extraction is
`code-walkthrough-compliance-extraction-v1797.md`. It moves eleven
implementation files into `ops.maintenance.walkthrough.compliance`, leaves the
root controller and public route aggregation in place, and lowers direct root
`ops` Java files from 1,330 to 1,319.

The second J8 extraction is `quality-gate-registry-extraction-v1798.md`. It
moves ten implementation files into `ops.maintenance.walkthrough.qualitygate`,
leaves the root controller and public route aggregation in place, and lowers
direct root `ops` Java files from 1,319 to 1,309.

The third J9 extraction is `quality-audit-registry-extraction-v1799.md`. It
moves eleven implementation files into `ops.maintenance.walkthrough.qualityaudit`,
leaves the root controller and public route aggregation in place, and lowers
direct root `ops` Java files from 1,309 to 1,298.

The fourth J10 extraction is `depth-registry-extraction-v1800.md`. It moves
eight implementation files into `ops.maintenance.walkthrough.depth`, leaves the
root controller and public route aggregation in place, and lowers direct root
`ops` Java files from 1,298 to 1,290.

The fifth J11 extraction is `screenshot-explanation-archive-extraction-v1801.md`.
It moves ten implementation files into
`ops.maintenance.screenshotexplanationarchive`, leaves the root controller and
public route aggregation in place, and lowers direct root `ops` Java files from
1,290 to 1,280. It is the first extraction outside the CodeWalkthrough family.
