# Java ops extraction endgame census v1828

This document is the binding Phase 1 census required by the Java final-push
brief. It converts the remaining direct-root `ops` package from an open-ended
"keep extracting" effort into a finite contract.

## Scope

- Repository state: live after v1856, with the original v1828 baseline retained
  in the progress notes below.
- Counted directory:
  `src/main/java/com/codexdemo/orderplatform/ops/*.java`.
- Current direct-root Java files: **278**.
- Target final direct-root Java files: **104**.
- Remaining direct-root non-controller files to move or collapse: **174**.
- Total `ops` Java files are not loosened by this census. Route strings,
  response bytes, write boundaries, credentials, deployment, rollback, and
  archive paths are unchanged.

## Retained root contract

The final direct-root `ops` package may contain only these categories:

| Retained category | Count | Rule |
| --- | ---: | --- |
| Public Spring controllers | 100 | Controllers stay in root so HTTP entry points remain obvious. |
| Global route aggregator | 1 | `OpsShardReadinessRoutePaths.java` stays as the shared root aggregator. |
| Shared core waivers | 3 | Listed in `extraction-waivers.md`; each requires a reviewer check. |

This gives the v1828 end-state target:

```text
100 controllers + 1 route aggregator + 3 shared-core waivers = 104 final root files
278 current root files - 104 final root files = 174 files still to move
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
| Keep-root shared core and global route aggregator | 4 | Retain only the three waiver files plus `OpsShardReadinessRoutePaths.java`. |
| OpsEvidence static release support | 2 | Move under a shared evidence package; not a root waiver. |
| MinimalReadOnlyGateOperatorCiHandoff | 0 | Core/archive verification moved in v1844, ArchiveDigest in v1845, ConsumerPackage in v1846, VerificationDossier in v1847, ReleaseAcceptance base in v1848, and its Archive in v1849. |
| MinimalReadOnlyGateExecution | 0 | Execution and archive-verification closure moved in v1843. |
| RouteCleanup web | 170 | High-coupling track; split only with route owner and endpoint proof. |
| ReleaseAcceptanceRoutePathSplit | 0 | Base/closeout moved in v1840, sustainment in v1841, and acceptance package/receipt/archive index in v1842. |
| ReleaseAcceptanceArchiveVerificationHandoff | 0 | Source archive boundary moved in v1849; the verification handoff moved in v1850. |
| ReleaseAcceptance root route owner | 0 | Moved with the base layer in v1840. |
| ReleaseApprovalSandboxEndpointCredentialResolver records | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApprovalManagedAuditSandboxEndpointCredentialResolver builders | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApprovalManagedAuditSandboxConnection builders | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApprovalManagedAudit adapter/quality builders | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApprovalSandboxConnection records | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApprovalRehearsal shared hints/request/builders | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApprovalVerification hints | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| ReleaseApproval shared support | 0 | Moved with the complete ReleaseApproval closure in v1854. |
| OperatorEvidenceValueSupplyAdapterPreflight | 0 | Moved in v1830. |
| OperatorEvidenceValueSupply base | 0 | Moved in v1831. |
| ComparedEvidenceCandidateBlueprint | 0 | Moved in v1832. |
| ComparedEvidenceCandidateIntakePreflight | 0 | Moved in v1833. |
| ComparedEvidenceEvaluationPreflight | 0 | Moved in v1839. |
| ComparedPackageReview | 0 | Moved in v1838. |
| SignedApprovalDraftProfileSection | 0 | Finished in v1829. |
| V1Contract consumer/alignment snapshots | 0 | Moved in v1853; snapshots remain package-private behind test support. |
| ReadOnlyEvidence catalog snapshots | 0 | Moved after RuntimeExecution in v1852; snapshots remain package-private behind test support. |
| RuntimeExecutionApprovalInputTemplate | 0 | Moved with the complete runtime-execution evidence chain in v1851. |
| RuntimeExecutionApproval/Input residuals | 0 | Moved as the complete runtime-execution evidence chain in v1851. |
| ActiveShardPlanHandoff | 0 | Moved with the complete readiness core in v1855. |
| OpsOverview mini-family | 2 | Move service/response; controller stays root. |
| PrototypeConsumerGate | 0 | Moved with the complete Prototype closure in v1856. |
| Prototype catalog/evidence/handoff residuals | 0 | Moved with the complete Prototype closure in v1856. |
| Readiness core simple endpoints | 0 | Moved with ActiveShardPlanHandoff as one closed package in v1855. |

The counted buckets sum to **278** and leave zero unassigned files. The original
v1828 baseline was **874**, with **769** files still to move.

## Batch order guidance

The next extraction should still use the established J7+ recipe: public
RoutePaths owner, byte-identical route strings, package-local tests with moved
implementation, SpotBugs FQN relocation when response records move, root-count
ratchet tightened, and Chinese walkthrough written before final verify.

Recommended near-term order:

1. Start the `RouteCleanup` web with a package-owned route table and a closed
   evidence/maintenance dependency slice.
2. Continue the `RouteCleanup` web in dependency-closed slices; never move a
   caller before the boundary it consumes is public and stable.
3. Leave the two static-release and two OpsOverview support files for a later
   coherent composition-support decision; do not create tiny filler versions.

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

## v1852 progress

v1852 moves the eleven-file ReadOnlyEvidence catalog, handoff, verification,
and endpoint-integrity closure into `ops.maintenance.readonlyevidence`. The
Spring controller and its controller-split test remain root-visible, while
seven package-local tests move beside the implementation. A public test-source
support exposes only six immutable historical endpoint lists and four service
factories; all three production snapshot classes stay package-private. The v184
integrity snapshot now owns its small package-private endpoint-pair value type
instead of borrowing the retained root evidence registry's private nested type.

The live direct-root count falls from **482 to 471**, the final target remains
**105**, and the movable backlog falls from **377 to 366**. The ReadOnlyEvidence
bucket falls from **11 to 0**, total `ops` Java files stay at **1,352**, and the
census reports no unassigned files. The forty-two-file V1Contract bucket is now
the next coherent read-only consumer track; its historical snapshots continue
to read the v175/v179/v184 lists through test support without widening the
production snapshot surface.

## v1853 progress

v1853 moves the complete forty-two-file V1Contract consumer and alignment
closure into `ops.maintenance.v1contract`. The Spring controller and three
controller/route structure tests remain root-visible, while ninety-eight
package-local tests follow the implementation. The family keeps eleven
snapshot classes and the post-handoff receipt helpers package-private. A narrow
test-source support forwards only the historical snapshot methods needed by the
cross-era compatibility test; a second shared test support preserves access to
the retained root evidence registry without making that production registry
public.

The retained global route owner exposes the same eleven immutable suffixes with
unchanged bytes. The family endpoint-pair helper now owns its own public
immutable pair and the root registry maps that pair into its private value
type, removing the previous reverse package dependency. The live direct-root
count falls from **471 to 429**, the final target remains **105**, and the
movable backlog falls from **366 to 324**. The V1Contract bucket falls from
**42 to 0**, total `ops` Java files stay at **1,352**, and the census reports no
unassigned files.

## v1854 progress

v1854 moves the complete one-hundred-eighteen-file direct-root ReleaseApproval
closure into `ops.maintenance.releaseapproval`. It also moves the sole remaining
runtime consumer of `ContextHeaderField` as package-private
`ReleaseApprovalContextHeaderField`, so that historical shared-root waiver is
removed instead of being widened into a production API. The Spring-facing
entry remains in `OpsOverviewController`, while `OpsEvidenceService` composes
the public request, response, and response builder from the extracted package.

The extraction replaces 316 root-service constant dependencies with two
family-owned immutable catalogs: 89 Java ReleaseApproval contract fields and
227 Node upstream evidence fields. Their source-size ratchets are 400 and 800
lines respectively. One forwarding-only execution-denied builder is folded
into its matching support as a nested adapter, offsetting the second catalog so
total `ops` Java files stay at **1,352**. The shared rehearsal test support also
moves with the family and remains the narrow fixture boundary for retained root
tests.

The live direct-root count falls from **429 to 310**. The final target tightens
from **105 to 104** because the `ContextHeaderField` waiver is retired, and the
movable backlog falls from **324 to 206**. All eight ReleaseApproval buckets
fall from a combined **118 to 0**, the census reports zero unassigned files,
and no route, response, evidence, fixture, side-effect, deployment, rollback,
or archive byte changes.

## v1855 progress

v1855 moves the ten-pair readiness core into
`ops.maintenance.readinesscore`: the base readiness pair, eight named
readiness endpoint pairs, and the ActiveShardPlanHandoff pair. Ten package
behavior tests follow the implementation. Three Spring controllers, the
global evidence endpoint registry, the global route aggregator, historical
snapshot tests, and cross-family prototype tests remain at the composition
root and import the public immutable boundary.

The base readiness service now owns the base route and three evidence suffix
constants. Evidence index, verification, and handoff compose those constants
without importing the root aggregator; `OpsShardReadinessRoutePaths` delegates
its existing fields back to the family owner. Route and fixture bytes remain
unchanged. The live direct-root count falls from **310 to 290**, the movable
backlog falls from **206 to 186**, both readiness buckets fall to **0**, total
`ops` main Java stays at **1,352**, and the census reports zero unassigned
files.

## v1856 progress

v1856 moves the complete twelve-file Prototype implementation closure into
`ops.maintenance.prototype`. Three service behavior tests follow the
implementation, while three Spring controllers and their controller-split
tests remain at the composition root. The two Prototype census buckets fall
from **4 + 8 to 0** without deleting any production capability.

The family now owns all thirty byte-identical route suffixes in the nested
`PrototypeRoutes` data owner. The global route aggregator no longer repeats
those constants, and retained controllers import the family owner directly.
The root RouteCleanup post-completion closeout service implements the narrow
`CloseoutSource` port and maps its existing response to a four-field immutable
snapshot, so Prototype no longer imports the wide root service or response.
Only catalog and field-alignment endpoints remain public for their two verified
RouteCleanup readers.

The live direct-root count falls from **290 to 278**, the movable backlog falls
from **186 to 174**, total `ops` main Java stays at **1,352**, and the census
reports zero unassigned files. Route strings, response components, digest
materials, fixture/evidence bytes, write and credential boundaries,
deployment, rollback, and archive paths remain unchanged.

## Revision rule

The final root target may only move downward. Raising the target above **104**
requires a new entry in `extraction-waivers.md`, a reviewer-checkable reason,
and a follow-up review. Raising the target without a waiver is a checkpoint
failure.
