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
