# Java ops extraction endgame census v1828

This document is the binding Phase 1 census required by the Java final-push
brief. It converts the remaining direct-root `ops` package from an open-ended
"keep extracting" effort into a finite contract.

## Scope

- Repository state: live after v1851, with the original v1828 baseline retained
  in the progress notes below.
- Counted directory:
  `src/main/java/com/codexdemo/orderplatform/ops/*.java`.
- Current direct-root Java files: **482**.
- Target final direct-root Java files: **105**.
- Remaining direct-root non-controller files to move or collapse: **377**.
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
482 current root files - 105 final root files = 377 files still to move
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
| MinimalReadOnlyGateOperatorCiHandoff | 0 | Core/archive verification moved in v1844, ArchiveDigest in v1845, ConsumerPackage in v1846, VerificationDossier in v1847, ReleaseAcceptance base in v1848, and its Archive in v1849. |
| MinimalReadOnlyGateExecution | 0 | Execution and archive-verification closure moved in v1843. |
| RouteCleanup web | 170 | High-coupling track; split only with route owner and endpoint proof. |
| ReleaseAcceptanceRoutePathSplit | 0 | Base/closeout moved in v1840, sustainment in v1841, and acceptance package/receipt/archive index in v1842. |
| ReleaseAcceptanceArchiveVerificationHandoff | 0 | Source archive boundary moved in v1849; the verification handoff moved in v1850. |
| ReleaseAcceptance root route owner | 0 | Moved with the base layer in v1840. |
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
| RuntimeExecutionApprovalInputTemplate | 0 | Moved with the complete runtime-execution evidence chain in v1851. |
| RuntimeExecutionApproval/Input residuals | 0 | Moved as the complete runtime-execution evidence chain in v1851. |
| ActiveShardPlanHandoff | 2 | Move as a small handoff package. |
| OpsOverview mini-family | 2 | Move service/response; controller stays root. |
| PrototypeConsumerGate | 4 | Move as prototype consumer gate. |
| Prototype catalog/evidence/handoff residuals | 8 | Move as prototype residuals. |
| Readiness core simple endpoints | 18 | Move as small readiness-core endpoint packages. |

The counted buckets sum to **482** and leave zero unassigned files. The original
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

## v1840 progress

v1840 starts the three-version ReleaseAcceptanceRoutePathSplit track by moving
the seventeen base implementation files, six closeout implementation files,
and the shared release-acceptance route owner into
`ops.maintenance.releaseacceptanceroutepathsplit`. The two Spring controllers
stay root-visible. The moved route catalog compares the retained global route
aggregator with the moved split owner through eleven explicitly public immutable
constants; no broad service or write capability is exposed.

The live direct-root count falls from **775 to 751**, the final target remains
**105**, and the movable backlog falls from **670 to 646**. The
ReleaseAcceptanceRoutePathSplit bucket falls from **78 to 55**, its separate
root route-owner bucket reaches zero, total `ops` Java files stay at **1,352**,
and the census reports no unassigned files.

## v1841 progress

v1841 moves the nineteen non-controller sustainment implementation files into
`ops.maintenance.releaseacceptanceroutepathsplit.sustainment`; its Spring
controller remains root-visible. The moved layer consumes only the v1840
closeout service/response and shared route owner. The still-root acceptance
package imports the moved sustainment service/response, preserving a one-way
dependency for v1842.

The live direct-root count falls from **751 to 732**, the final target remains
**105**, and the movable backlog falls from **646 to 627**. The split bucket
falls from **55 to 36**, total `ops` Java files stay at **1,352**, and the
census reports no unassigned files.

## v1842 progress

v1842 completes the three-version ReleaseAcceptanceRoutePathSplit track. It
moves the thirty-six acceptance-package, closeout-receipt, and closeout-archive-
index implementation files into `ops.maintenance.releaseacceptancepackage`.
Three Spring controllers remain root-visible, and eight package-local tests move
with the implementation while three controller-oriented tests remain in root.
The moved closure reads the v1841 public sustainment service/response and the
v1840 route owner; no route string or response shape changes.

The live direct-root count falls from **732 to 696**, the final target remains
**105**, and the movable backlog falls from **627 to 591**. The
ReleaseAcceptanceRoutePathSplit bucket falls from **36 to 0**, total `ops` Java
files stay at **1,352**, and the census reports no unassigned files.

## v1843 progress

v1843 moves the thirty-one MinimalReadOnlyGateExecution base and archive-
verification implementation files into
`ops.maintenance.minimalreadonlygateexecution`. Two Spring controllers remain
root-visible, thirteen package-local tests move with the implementation, and
two controller tests keep using public test factories from root. The moved
services consume the existing public release-acceptance route owner; the
still-root Operator-CI core imports the moved archive-verification service and
response as its single upstream execution boundary.

The live direct-root count falls from **696 to 665**, the final target remains
**105**, and the movable backlog falls from **591 to 560**. The
MinimalReadOnlyGateExecution bucket falls from **31 to 0**, total `ops` Java
files stay at **1,352**, and the census reports no unassigned files.

## v1844 progress

v1844 moves the twenty-seven MinimalReadOnlyGateOperatorCiHandoff base,
archive-rendering, and archive-verification implementation files into
`ops.maintenance.minimalreadonlygateoperatorcihandoff`. Two Spring controllers
and two controller-oriented Markdown tests remain root-visible, while seven
package-local tests move beside the implementation. The package consumes the
v1843 execution archive-verification boundary and the v1840 public route owner.
The still-root ArchiveDigest layer imports only the public archive-verification
service and response, so the dependency direction remains explicit.

The live direct-root count falls from **665 to 638**, the final target remains
**105**, and the movable backlog falls from **560 to 533**. The
MinimalReadOnlyGateOperatorCiHandoff bucket falls from **140 to 113**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1845 progress

v1845 moves the seventeen MinimalReadOnlyGateOperatorCiHandoff ArchiveDigest
implementation files into
`ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest`. The Spring
controller and its controller-oriented Markdown test remain root-visible,
while five package-local tests move beside the implementation. The digest
service consumes only the v1844 public archive-verification service/response
and the v1840 route owner. The still-root ConsumerPackage layer imports only
the public digest service/response, preserving the one-way dependency chain.

The live direct-root count falls from **638 to 621**, the final target remains
**105**, and the movable backlog falls from **533 to 516**. The
MinimalReadOnlyGateOperatorCiHandoff bucket falls from **113 to 96**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1846 progress

v1846 moves the twenty-three ConsumerPackage implementation files into the
path-safe package `ops.maintenance.minimalreadonlygateoperatorciconsumerpackage`.
The literal full-prefix package would produce a 264-character target path on
this Windows workspace; the selected semantic package lowers the measured
maximum to 244 without changing any class, route, or response name. The Spring
controller and controller-oriented Markdown aggregate test remain root-visible,
while four package-local tests move beside the implementation. The package
consumes only the v1845 digest service/response and v1840 route owner; the
still-root VerificationDossier layer imports only the public ConsumerPackage
service/response.

The live direct-root count falls from **621 to 598**, the final target remains
**105**, and the movable backlog falls from **516 to 493**. The
MinimalReadOnlyGateOperatorCiHandoff bucket falls from **96 to 73**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1847 progress

v1847 moves the twenty-five VerificationDossier implementation files into the
path-safe package `ops.maintenance.operatorcidossier`. The literal ancestry
package measured up to 307 characters, a still-descriptive intermediate name
measured 272, and the selected package keeps the longest moved test path at
248. Full class names, endpoint bytes, response fields, and archive names do
not change. The Spring controller and controller Markdown aggregate test remain
root-visible; seven package-local tests move with implementation. The dossier
consumes only the v1846 ConsumerPackage service/response and v1840 route owner;
the still-root ReleaseAcceptance layer imports only the dossier's public
service/response.

The live direct-root count falls from **598 to 573**, the final target remains
**105**, and the movable backlog falls from **493 to 468**. The
MinimalReadOnlyGateOperatorCiHandoff bucket falls from **73 to 48**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1848 progress

v1848 moves the twenty-five ReleaseAcceptance base implementation files into
the path-safe `ops.maintenance.ciaccept` package. The Spring controller and its
controller-oriented Markdown test remain root-visible, while seven package-local
tests follow the implementation. The moved layer consumes only the public v1847
VerificationDossier service/response and the v1840 route owner; the still-root
ReleaseAcceptanceArchive layer imports only the v1848 public service/response.

The live direct-root count falls from **573 to 548**, the final target remains
**105**, and the movable backlog falls from **468 to 443**. The
MinimalReadOnlyGateOperatorCiHandoff bucket falls from **48 to 23**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1849 progress

v1849 moves the twenty-three ReleaseAcceptanceArchive implementation files into
the path-safe `ops.maintenance.ciarc` package. The Spring controller and its
controller-oriented Markdown test remain root-visible, while six package-local
tests follow the implementation. The moved layer consumes only the public v1848
ReleaseAcceptance service/response and the v1840 route owner; the still-root
ArchiveVerificationHandoff imports only the v1849 public service/response.

The live direct-root count falls from **548 to 525**, the final target remains
**105**, and the movable backlog falls from **443 to 420**. The
MinimalReadOnlyGateOperatorCiHandoff bucket falls from **23 to 0**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1850 progress

v1850 moves the twenty-five ReleaseAcceptanceArchiveVerificationHandoff
implementation files into `ops.maintenance.releasearchivehandoff`. The Spring
controller and controller-oriented Markdown test remain root-visible, while six
package-local tests follow the implementation. The moved handoff consumes only
the public v1849 Archive service/response and v1840 route owner; the already
extracted RoutePathSplit layer imports only the public handoff service/response.

The live direct-root count falls from **525 to 500**, the final target remains
**105**, and the movable backlog falls from **420 to 395**. The independent
ReleaseAcceptanceArchiveVerificationHandoff bucket falls from **25 to 0**, total
`ops` Java files stay at **1,352**, and the census reports no unassigned files.

## v1851 progress

v1851 moves the complete eighteen-file RuntimeExecution evidence chain into
`ops.maintenance.runtimeexecution`. The Spring controller remains root-visible,
while nine package-local behavior tests move beside the implementation and a
single public test support becomes the graph-construction owner used by both
the moved tests and the retained root test factory. No route owner is added:
the nine byte-identical endpoint literals, fixture paths, and evidence paths
remain on their existing services and only become public immutable references
for the still-root evidence catalogs.

The live direct-root count falls from **500 to 482**, the final target remains
**105**, and the movable backlog falls from **395 to 377**. The two
RuntimeExecution census buckets fall from **4 + 14 to 0**, total `ops` Java
files stay at **1,352**, and the census reports no unassigned files. This makes
the eleven-file ReadOnlyEvidence chain the next dependency-safe extraction.

## Revision rule

The final root target may only move downward. Raising the target above **105**
requires a new entry in `extraction-waivers.md`, a reviewer-checkable reason,
and a follow-up review. Raising the target without a waiver is a checkpoint
failure.
