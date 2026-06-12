# Archive Layout Map

## Purpose

Archive layout upkeep keeps screenshots, explanations, receipts, and code
walkthroughs discoverable after many version batches.

## Main Locations

| Location | Role |
| --- | --- |
| `代码讲解记录_总索引.md` | Human index for code walkthrough archives |
| `代码讲解记录_写作规范.md` | Current writing and structure rules |
| `代码讲解记录_整改清单.md` | Cleanup history and backlog |
| `代码讲解记录_生产雏形阶段4/` | Current standard walkthrough continuation |
| `f/` | Segmented screenshot/explanation archive root |

## Guard Tests

| Test | Guarded behavior |
| --- | --- |
| `OpsCodeWalkthroughArchiveComplianceTests` | Standard sections, legacy marker rule, Chinese longform depth, workload section |
| `OpsScreenshotExplanationArchiveSegmentationDocsTests` | Screenshot/explanation archive segmentation docs |
| `OpsScreenshotExplanationFArchiveLayoutInventoryTests` | `f/` root version-band layout |

## Read-only Boundary

Archive layout upkeep can create or update docs and static tests. It must not
open runtime probes, execute deployment or rollback, read credentials, resolve
raw endpoints, or start upstream services.
