# Class Name Trial

This trial applies only to new ops readability upkeep code. It does not rename
historical `OpsShardReadiness*` classes.

## Rule

When a package already carries the context, the class name should describe the
local responsibility.

| Context | Long pattern to avoid for new code | Current trial name |
| --- | --- | --- |
| `ops.maintenance.readability` | `OpsShardReadinessReadabilityUpkeepRegistryService` | `ReadabilityUpkeepRegistryService` |
| `ops.maintenance.readability` | `OpsShardReadinessReadabilityUpkeepRegistryController` | `ReadabilityUpkeepRegistryController` |
| `ops.maintenance.readability` | `OpsShardReadinessReadabilityBoundaryCatalog` | `ReadabilityBoundaryCatalog` |

## Boundary

The trial is not a bulk rename. Existing root-package classes remain stable
until a focused refactor can move them with tests. New subpackages may use
shorter names only when the package name preserves the missing context.

## Verification

`ReadabilityUpkeepClassNameTrialTests` checks that the new readability upkeep
subpackage does not reintroduce the `OpsShardReadiness` prefix and that the
trial document remains linked from `docs/ops/README.md`.
