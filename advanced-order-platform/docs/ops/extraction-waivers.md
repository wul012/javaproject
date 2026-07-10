# Java ops extraction waivers

This file is the committed waiver list required by the Java final-push brief.
Controllers and the global `OpsShardReadinessRoutePaths.java` aggregator are
policy-retained root files and do not need per-file waivers. Every other
non-controller file that remains in the final direct-root `ops` package must
appear here.

## Active root waivers

| File | Why it may remain in root | Reviewer check |
| --- | --- | --- |
| `ContextHeaderField.java` | Shared header normalization helper used across multiple readiness and sandbox-connection evidence paths. | `rg -l ContextHeaderField src/main/java src/test/java` should show multiple independent consumers before final closeout. |
| `OpsEvidenceResponse.java` | Shared evidence response envelope for the root evidence service and extracted evidence families. | `rg -l OpsEvidenceResponse src/main/java src/test/java` should show use outside a single endpoint family. |
| `OpsEvidenceService.java` | Cross-family evidence assembler consumed by the overview controller and extracted evidence subpackages. | `rg -l OpsEvidenceService src/main/java src/test/java` should remain broad; if it narrows to one family, move it. |
| `OpsShardReadinessEvidenceEndpoints.java` | Shared endpoint-pair catalog used by v1-contract and read-only evidence snapshots. | `rg -l OpsShardReadinessEvidenceEndpoints src/main/java src/test/java` should show v1-contract plus read-only evidence consumers. |

## Explicit non-waivers

`OpsEvidenceStaticReleaseArtifact.java` and
`OpsEvidenceStaticReleaseDispatchTable.java` are deliberately not root waivers
in v1828. They are treated as the two-file `OpsEvidence static release support`
bucket and must move under shared evidence support during Phase 1.

`OpsShardReadinessReleaseAcceptanceRoutePaths.java` is also not a waiver. It
moved with the v1840 release-acceptance route-path split base layer and must not
return to root.
