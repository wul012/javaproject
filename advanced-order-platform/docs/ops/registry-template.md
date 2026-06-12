# Ops Registry Template

Use this template when adding a new read-only ops registry.

## Required Java Layers

| Layer | Required evidence |
| --- | --- |
| Route paths | `BASE_PATH`, endpoint suffix, and endpoint assembly test |
| Response | a `record` with flags, counts, checks, markdown sections, and `status` |
| Catalog | stable coded entries, not ad hoc strings in the service |
| Renderer | markdown sections generated from catalog entries |
| Support | count and status calculation |
| Service | `RESPONSE_VERSION`, `ENDPOINT`, `PROFILE`, and `@Transactional(readOnly = true)` |
| Controller | one GET route with no mutation and no runtime command |
| Tests | route path test, service test, renderer test, boundary test, controller test |

## Required Boundary Rules

Every read-only registry must explicitly deny:

- write routing
- active shard router
- credential value reads
- raw endpoint URL resolution
- managed audit HTTP/TCP connection
- deployment or rollback
- Java autostart
- mini-kv autostart

## Required Response Fields

The response should include:

- `project`
- `version`
- `readOnly`
- `executionAllowed`
- `endpoint`
- `profile`
- counts for each catalog type
- denied boundary count
- `checks`
- `status`

## Naming Rule

For new subpackages, let the package carry repeated context. Class names should
describe the local responsibility, for example `ReadabilityUpkeepRegistryService`
instead of repeating every historical `OpsShardReadiness` prefix.

## Verification

At minimum, run the registry's targeted tests and
`OpsCodeWalkthroughArchiveComplianceTests`. Before push, run full Maven tests and
wait for CI.
