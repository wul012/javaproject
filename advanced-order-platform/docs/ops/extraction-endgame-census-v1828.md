# Java ops extraction endgame census v1828

This document is the binding Phase 1 census required by the Java final-push
brief. It converts the remaining direct-root `ops` package from an open-ended
"keep extracting" effort into a finite contract.

## Scope

- Repository state: live after v1833, with the original v1828 baseline retained
  in the progress notes below.
- Counted directory:
  `src/main/java/com/codexdemo/orderplatform/ops/*.java`.
- Current direct-root Java files: **775**.
- Target final direct-root Java files: **105**.
- Remaining direct-root non-controller files to move or collapse: **670**.
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
775 current root files - 105 final root files = 670 files still to move
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
| OperatorEvidenceValueSupply base | 0 | Moved in v1831. |
| ComparedEvidenceCandidateBlueprint | 0 | Moved in v1832. |
| ComparedEvidenceCandidateIntakePreflight | 0 | Moved in v1833. |
| ComparedEvidenceEvaluationPreflight | 0 | Moved in v1839. |
| ComparedPackageReview | 0 | Moved in v1838. |
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

The counted buckets sum to **805** and leave zero unassigned files. The original
v1828 baseline was **874**, with **769** files still to move.

## Batch order guidance

The next extraction should still use the established J7+ recipe: public
RoutePaths owner, byte-identical route strings, package-local tests with moved
implementation, SpotBugs FQN relocation when response records move, root-count
ratchet tightened, and Chinese walkthrough written before final verify.

Recommended near-term order:

1. Move the compared-evidence and compared-package buckets while their
   endpoint dependencies are still easy to audit.
2. Move the release-acceptance route-path split track as its own multi-version
   effort.
3. Leave the `RouteCleanup` web until route owner, endpoint reader, and
   aggregation proof are explicit.

Every five extraction batches after this census need a checkpoint review unless
the user explicitly changes the review cadence.

The v1834-v1837 maintainability program temporarily paused the extraction
series after the v1833 checkpoint. The series resumes at v1838 with the
dependency-safe order `ComparedPackageReview` then
`ComparedEvidenceEvaluationPreflight`, followed by the three coherent
`ReleaseAcceptanceRoutePathSplit` layers. The next Claude checkpoint is after
v1842, the fifth resumed extraction batch.

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

## v1831 progress

v1831 moves the `OperatorEvidenceValueSupply` base implementation bucket into
`ops.maintenance.operatorevidencevaluesupply`. The two public ValueSupply
controllers stay in root. The base route suffixes are now owned by
`OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths`; the root aggregator
delegates to that owner so all public endpoint bytes remain unchanged. The
SpotBugs mirror blocks follow the moved base response FQN, and AdapterPreflight
plus ApprovalPreflight endpoint readers now import the base services from the
maintenance package.

The live direct-root count falls from **848 to 833**, while the final root
target remains **105** and the remaining direct-root non-controller backlog
falls from **743 to 728**. Total `ops` Java files stay at **1,352** because this
batch moves implementation ownership without adding a compensating root file.

## v1832 progress

v1832 moves the `ComparedEvidenceCandidateBlueprint` implementation bucket into
`ops.maintenance.comparedevidencecandidateblueprint`. The public blueprint
controller stays in root. The new route owner contains both the five suffix
constants and the five full endpoint constants formerly carried by the old
EndpointRefs helper, so total `ops` Java files stay at **1,352** instead of
growing by one.

The live direct-root count falls from **833 to 819**, while the final root
target remains **105** and the remaining direct-root non-controller backlog
falls from **728 to 714**. The `ComparedEvidenceCandidateBlueprint` bucket is
now zero. The next compared-evidence chain buckets are
`ComparedEvidenceCandidateIntakePreflight`,
`ComparedEvidenceEvaluationPreflight`, and `ComparedPackageReview`.

## v1833 progress

v1833 moves the `ComparedEvidenceCandidateIntakePreflight` implementation bucket
into `ops.maintenance.comparedevidencecandidateintakepreflight`. The public
intake-preflight controller stays in root. A new route owner holds all five
candidate-intake-preflight suffixes and full endpoint constants. The historical
CandidateDocument route owner still exposes the catalog suffix for backward
catalog consumers, but now delegates that suffix to the intake-preflight owner.

The live direct-root count falls from **819 to 805**, while the final root
target remains **105** and the remaining direct-root non-controller backlog
falls from **714 to 700**. Total `ops` Java files stay at **1,352** because the
old standalone GateCatalog is folded into the moved GuardCatalog, offsetting the
new route owner. The `ComparedEvidenceCandidateIntakePreflight` bucket is now
zero. The next compared-evidence chain buckets are
`ComparedEvidenceEvaluationPreflight` and `ComparedPackageReview`.

## v1838 progress

v1838 moves the sixteen non-controller `ComparedPackageReview` implementation
files into `ops.maintenance.comparedpackagereview`; the public Spring
controller remains in root. The former `EndpointRefs` file becomes the single
public family route owner, and the root route aggregator delegates its six
unchanged suffixes to that owner. `ComparedEvidenceEvaluationPreflight` now
imports the public review route owner, establishing the dependency-safe input
for v1839.

The live direct-root count falls from **805 to 789**, while the final root
target remains **105** and the remaining direct-root non-controller backlog
falls from **700 to 684**. Total `ops` Java files remain at **1,352**, the
`ComparedPackageReview` bucket is zero, and the census reports no unassigned
files.

## v1839 progress

v1839 moves the fourteen non-controller
`ComparedEvidenceEvaluationPreflight` implementation files into
`ops.maintenance.comparedevidenceevaluationpreflight`; the public Spring
controller remains in root. The former public `EndpointRefs` becomes the sole
family RoutePaths owner without adding a file. CandidateBlueprint and
ProfileSection readers import the moved endpoint, service, and response
boundaries, while the moved rule catalogs consume the v1838
ComparedPackageReview route owner.

The live direct-root count falls from **789 to 775**, the final target remains
**105**, and the remaining direct-root non-controller backlog falls from
**684 to 670**. Total `ops` Java files remain at **1,352**, both compared-family
buckets are zero, and the census reports no unassigned files.

## Revision rule

The final root target may only move downward. Raising the target above **105**
requires a new entry in `extraction-waivers.md`, a reviewer-checkable reason,
and a follow-up review. Raising the target without a waiver is a checkpoint
failure.
