# Java ops extraction endgame census v1828

This document is the binding Phase 1 census required by the Java final-push
brief. It converts the remaining direct-root `ops` package from an open-ended
"keep extracting" effort into a finite contract.

## Scope

- Repository state: after v1827, before any v1828 extraction.
- Counted directory:
  `src/main/java/com/codexdemo/orderplatform/ops/*.java`.
- Current direct-root Java files: **874**.
- Target final direct-root Java files: **105**.
- Remaining direct-root non-controller files to move or collapse: **769**.
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
874 current root files - 105 final root files = 769 files still to move
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
| OperatorEvidenceValueSupplyAdapterPreflight | 16 | Move before active value-supply router work. |
| OperatorEvidenceValueSupply base | 15 | Move after adapter preflight so outbound reads are explicit. |
| ComparedEvidenceCandidateBlueprint | 14 | Move as compared-evidence candidate blueprint. |
| ComparedEvidenceCandidateIntakePreflight | 14 | Move as compared-evidence candidate intake preflight. |
| ComparedEvidenceEvaluationPreflight | 14 | Move as compared-evidence evaluation preflight. |
| ComparedPackageReview | 16 | Move after compared evidence packages expose endpoint boundaries. |
| SignedApprovalDraftProfileSection | 10 | Finish the remaining ProfileSection cluster. |
| V1Contract consumer/alignment snapshots | 42 | Move into a v1-contract package while preserving endpoint bytes. |
| ReadOnlyEvidence catalog snapshots | 11 | Move after v1-contract endpoint pair ownership is clear. |
| RuntimeExecutionApprovalInputTemplate | 4 | Move before runtime execution residuals. |
| RuntimeExecutionApproval/Input residuals | 14 | Move as runtime-execution approval/input residuals. |
| ActiveShardPlanHandoff | 2 | Move as a small handoff package. |
| OpsOverview mini-family | 2 | Move service/response; controller stays root. |
| PrototypeConsumerGate | 4 | Move as prototype consumer gate. |
| Prototype catalog/evidence/handoff residuals | 8 | Move as prototype residuals. |
| Readiness core simple endpoints | 18 | Move as small readiness-core endpoint packages. |

The counted buckets sum to **874** and leave zero unassigned files.

## Batch order guidance

The next extraction should still use the established J7+ recipe: public
RoutePaths owner, byte-identical route strings, package-local tests with moved
implementation, SpotBugs FQN relocation when response records move, root-count
ratchet tightened, and Chinese walkthrough written before final verify.

Recommended near-term order:

1. Finish the remaining `SignedApprovalDraftProfileSection` family because it
   is the tail of the v1825-v1826 ProfileSection cluster.
2. Move the `OperatorEvidenceValueSupplyAdapterPreflight` and
   `OperatorEvidenceValueSupply` base buckets before any active router work.
3. Move the compared-evidence and compared-package buckets while their
   endpoint dependencies are still easy to audit.
4. Move the release-acceptance route-path split track as its own multi-version
   effort.
5. Leave the `RouteCleanup` web until route owner, endpoint reader, and
   aggregation proof are explicit.

Every five extraction batches after this census need a checkpoint review unless
the user explicitly changes the review cadence.

## Revision rule

The final root target may only move downward. Raising the target above **105**
requires a new entry in `extraction-waivers.md`, a reviewer-checkable reason,
and a follow-up review. Raising the target without a waiver is a checkpoint
failure.
