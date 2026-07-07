# Java ops extraction endgame census v1828

This document is the binding Phase 1 census required by the Java final-push
brief. It converts the remaining direct-root `ops` package from an open-ended
"keep extracting" effort into a finite contract.

## Scope

- Repository state: live after v1830, with the original v1828 baseline retained
  in the progress notes below.
- Counted directory:
  `src/main/java/com/codexdemo/orderplatform/ops/*.java`.
- Current direct-root Java files: **848**.
- Target final direct-root Java files: **105**.
- Remaining direct-root non-controller files to move or collapse: **743**.
- Total `ops` Java files are not loosened by this census. Route strings,
  response bytes, write boundaries, credentials, deployment, rollback, and
  archive paths are unchanged.

## Retained root contract

The final direct-root `ops` package may contain only these categories:

| Retained category | Count | Rule |
| --- | ---: | --- |
| Public Spring controllers | 100 | Controllers stay in root so HTTP entry points remain obvious. |
| Global route aggregator | 1 | `OpsShardReadinessRoutePaths.java` stays as the shared root aggregator. |
| Shared core waivers | 4 | Listed in `extraction-waivers.md`; each requires a reviewer check. |

This gives the v1828 end-state target:

```text
100 controllers + 1 route aggregator + 4 shared-core waivers = 105 final root files
848 current root files - 105 final root files = 743 files still to move
```

`OpsShardReadinessReleaseAcceptanceRoutePaths.java` is not a retained root
aggregator. It is the release-acceptance family route owner and must leave root
with the release-acceptance route-path split track.

## Remaining family buckets

The table below is intentionally ordered by classification rule. A file is
assigned to the first matching rule. This keeps the census reproducible even
when names overlap, for example controllers inside a large family prefix.

| Bucket | Direct-root files now | End-state treatment |
| --- | ---: | --- |
| Keep-root controllers | 100 | Retain. |
| Keep-root shared core and global route aggregator | 5 | Retain only the four waiver files plus `OpsShardReadinessRoutePaths.java`. |
| OpsEvidence static release support | 2 | Move under a shared evidence package; not a root waiver. |
| MinimalReadOnlyGateOperatorCiHandoff | 140 | Split into coherent subpackages; keep controllers in root. |
| MinimalReadOnlyGateExecution | 31 | Move after or alongside the operator handoff core. |
| RouteCleanup web | 170 | High-coupling track; split only with route owner and endpoint proof. |
| ReleaseAcceptanceRoutePathSplit | 78 | Move as the release-acceptance route-path split track. |
| ReleaseAcceptanceArchiveVerificationHandoff | 25 | Move after route-path split ownership is stable. |
| ReleaseAcceptance root route owner | 1 | Move with the release-acceptance route owner; no root waiver. |
| ReleaseApprovalSandboxEndpointCredentialResolver records | 59 | Move as credential-resolver records package. |
| ReleaseApprovalManagedAuditSandboxEndpointCredentialResolver builders | 23 | Move with credential-resolver managed-audit builders. |
| ReleaseApprovalManagedAuditSandboxConnection builders | 9 | Move with sandbox-connection managed-audit builders. |
| ReleaseApprovalManagedAudit adapter/quality builders | 7 | Move with release-approval managed-audit support. |
| ReleaseApprovalSandboxConnection records | 2 | Move with sandbox-connection records. |
| ReleaseApprovalRehearsal shared hints/request/builders | 10 | Move with release-approval rehearsal support. |
| ReleaseApprovalVerification hints | 6 | Move with release-approval verification support. |
| ReleaseApproval shared support | 2 | Move into release-approval shared support; not root. |
| OperatorEvidenceValueSupplyAdapterPreflight | 0 | Moved in v1830. |
| OperatorEvidenceValueSupply base | 15 | Move after adapter preflight so outbound reads are explicit. |
| ComparedEvidenceCandidateBlueprint | 14 | Move as compared-evidence candidate blueprint. |
| ComparedEvidenceCandidateIntakePreflight | 14 | Move as compared-evidence candidate intake preflight. |
| ComparedEvidenceEvaluationPreflight | 14 | Move as compared-evidence evaluation preflight. |
| ComparedPackageReview | 16 | Move after compared evidence packages expose endpoint boundaries. |
| SignedApprovalDraftProfileSection | 0 | Finished in v1829. |
| V1Contract consumer/alignment snapshots | 42 | Move into a v1-contract package while preserving endpoint bytes. |
| ReadOnlyEvidence catalog snapshots | 11 | Move after v1-contract endpoint pair ownership is clear. |
| RuntimeExecutionApprovalInputTemplate | 4 | Move before runtime execution residuals. |
| RuntimeExecutionApproval/Input residuals | 14 | Move as runtime-execution approval/input residuals. |
| ActiveShardPlanHandoff | 2 | Move as a small handoff package. |
| OpsOverview mini-family | 2 | Move service/response; controller stays root. |
| PrototypeConsumerGate | 4 | Move as prototype consumer gate. |
| Prototype catalog/evidence/handoff residuals | 8 | Move as prototype residuals. |
| Readiness core simple endpoints | 18 | Move as small readiness-core endpoint packages. |

The counted buckets sum to **848** and leave zero unassigned files. The original
v1828 baseline was **874**, with **769** files still to move.

## Batch order guidance

The next extraction should still use the established J7+ recipe: public
RoutePaths owner, byte-identical route strings, package-local tests with moved
implementation, SpotBugs FQN relocation when response records move, root-count
ratchet tightened, and Chinese walkthrough written before final verify.

Recommended near-term order:

1. Move the `OperatorEvidenceValueSupply` base bucket now that
   `OperatorEvidenceValueSupplyAdapterPreflight` is extracted and its outbound
   reads are explicit.
2. Move the compared-evidence and compared-package buckets while their
   endpoint dependencies are still easy to audit.
3. Move the release-acceptance route-path split track as its own multi-version
   effort.
4. Leave the `RouteCleanup` web until route owner, endpoint reader, and
   aggregation proof are explicit.

Every five extraction batches after this census need a checkpoint review unless
the user explicitly changes the review cadence.

## v1829 progress

v1829 closes the remaining `SignedApprovalDraftProfileSection` handoff layer by
moving ten non-controller files into
`ops.maintenance.signedapprovaldraftprofilesectionhandoff`. The two public
ProfileSection controllers stay in root. The live direct-root count falls from
**874 to 864**, while the final root target remains **105** and the remaining
direct-root non-controller backlog falls from **769 to 759**.

The reviewer census command is now committed as:

```powershell
.\scripts\ops-root-census.ps1
```

Repository path: `scripts/ops-root-census.ps1`.

Use that command from the `advanced-order-platform` project root to reproduce
the same direct-root count, retained-root count, remaining movable count, bucket
table, and unassigned-file check used by the Java final-push extraction series.

## v1830 progress

v1830 moves the `OperatorEvidenceValueSupplyAdapterPreflight` implementation
bucket into `ops.maintenance.operatorevidencevaluesupplyadapterpreflight`. The
two public AdapterPreflight controllers stay in root. The old standalone
`RuleCatalog` is folded into the moved `SlotCatalog`, which offsets the new
public AdapterPreflight route owner and keeps total `ops` Java files at
**1,352**. The live direct-root count falls from **864 to 848**, while the final
root target remains **105** and the remaining direct-root non-controller backlog
falls from **759 to 743**.

The next low-coupling bucket is `OperatorEvidenceValueSupply base` with 15
direct-root files. It should move before any active value-supply router work so
future write-routing discussions do not depend on root-package evidence helpers.

## Revision rule

The final root target may only move downward. Raising the target above **105**
requires a new entry in `extraction-waivers.md`, a reviewer-checkable reason,
and a follow-up review. Raising the target without a waiver is a checkpoint
failure.
