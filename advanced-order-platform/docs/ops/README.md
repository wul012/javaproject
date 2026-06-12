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
