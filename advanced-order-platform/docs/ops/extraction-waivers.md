# Java ops extraction waivers

This file is the committed waiver list required by the Java final-push brief.
Controllers and the global `OpsShardReadinessRoutePaths.java` aggregator are
policy-retained root files and do not need per-file waivers. Every other
non-controller file that remains in the final direct-root `ops` package must
appear here.

## Active root waivers

| File | Why it may remain in root | Reviewer check |
| --- | --- | --- |
| `OpsEvidenceResponse.java` | Shared evidence response envelope for the root evidence service and extracted evidence families. | `rg -l OpsEvidenceResponse src/main/java src/test/java` should show use outside a single endpoint family. |
| `OpsEvidenceService.java` | Cross-family evidence assembler consumed by the overview controller and extracted evidence subpackages. | `rg -l OpsEvidenceService src/main/java src/test/java` should remain broad; if it narrows to one family, move it. |
| `OpsShardReadinessEvidenceEndpoints.java` | Shared endpoint-pair catalog used by v1-contract and read-only evidence snapshots. | `rg -l OpsShardReadinessEvidenceEndpoints src/main/java src/test/java` should show v1-contract plus read-only evidence consumers. |

## Explicit non-waivers

`StaticReleaseCatalog.java` and `StaticReleaseSections.java` are not root
waivers. v1866 moved the former static-release pair into
`ops.maintenance.evidencecore` and split dispatch from section construction
without increasing the total production file count. Returning either type to
root would reopen the completed Phase 1 census.

`OpsShardReadinessReleaseAcceptanceRoutePaths.java` is also not a waiver. It
moved with the v1840 release-acceptance route-path split base layer and must not
return to root.

`ContextHeaderField.java` ceased to qualify as a shared-root waiver in v1854.
Its runtime consumers had narrowed to the ReleaseApproval rehearsal family, so
it moved as package-private `ReleaseApprovalContextHeaderField.java` with that
family. Returning the generic helper to root or making it a production-public
type would reopen a boundary that the v1854 extraction deliberately closed.
