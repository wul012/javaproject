# Ops readability upkeep index

This directory is the human entry point for late-stage ops readability upkeep.
It does not replace source code, tests, or registry responses. It gives a
maintainer a short route into the main ops themes before they search the Java
package.

## Maps

| Map | Scope | Primary question |
| --- | --- | --- |
| `shard-readiness-map.md` | shard readiness and read-only evidence | Which endpoints prove readiness without opening write routing? |
| `walkthrough-registry-map.md` | code walkthrough quality and depth | Which registries govern code explanation quality? |
| `archive-layout-map.md` | screenshot/explanation and archive layout | Which docs/tests stop archive sprawl? |
| `registry-template.md` | read-only ops registry shape | Which layers and tests must a new ops registry carry? |
| `class-name-trial.md` | new ops readability subpackages | Which repeated prefixes can be dropped only after package context exists? |
| `route-service-test-map.md` | ops readability routes, services, and tests | Which route is backed by which service and which guard tests? |
| `root-package-pressure-map.md` | ops root package pressure | Which work should stay in the new readability subpackage instead of the old root? |
| `readability-upkeep-cycle.md` | late-stage readability upkeep cycle | Which sequence keeps maps, models, read-only routes, docs guards, and closeout aligned? |
| `readability-upkeep-audit-closeout.md` | audit registry closeout evidence | Which Java-only checks close the current readability upkeep audit batch? |
| `ops-consolidation-inventory-v1796.md` | ops consolidation inventory | Which route family clusters, load-bearing archive paths, and reduction candidates should guide the next split? |
| `code-walkthrough-compliance-extraction-v1797.md` | first ops extraction | Which code walkthrough compliance classes moved out of the root package while preserving routes and archives? |
| `quality-gate-registry-extraction-v1798.md` | second ops extraction | Which code walkthrough quality gate registry classes moved out of the root package while preserving routes and archives? |
| `quality-audit-registry-extraction-v1799.md` | third ops extraction | Which code walkthrough quality audit registry classes moved out of the root package while preserving routes and archives? |
| `depth-registry-extraction-v1800.md` | fourth ops extraction | Which code walkthrough depth registry classes moved out of the root package while preserving routes and archives? |
| `screenshot-explanation-archive-extraction-v1801.md` | fifth ops extraction | Which screenshot explanation archive registry classes moved out of the root package while preserving routes and archives? |
| `credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md` | sixth ops extraction | Which credential resolver disabled fake harness evidence archive classes moved out of the root package while preserving routes and archives? |
| `sandbox-connection-extraction-v1803.md` | seventh ops extraction | Which sandbox connection registry classes moved out of the root package while preserving routes and archives? |
| `signed-approval-route-path-consolidation-v1804.md` | eighth ops extraction | Which signed-approval route-path leaves moved into the new signedapproval subpackage while preserving routes and archives? |
| `candidate-document-extraction-v1805.md` | ninth ops extraction | Which candidate document registry classes moved into the new candidatedocument subpackage while preserving routes and archives? |
| `java-extraction-quality-closeout-v1806.md` | quality closeout | Which historical v1798 tag exception should maintainers avoid using as the current green baseline? |
| `operator-evidence-value-draft-extraction-v1807.md` | tenth ops extraction | Which operator-evidence-value-draft registry classes moved into the new operatorevidencevaluedraft subpackage, and how was the cross-family endpoint coupling handled? |
| `operator-evidence-import-preflight-extraction-v1808.md` | eleventh ops extraction | Which operator-evidence-import-preflight registry classes moved into the new operatorevidenceimportpreflight subpackage, and which upstream endpoint constants became public? |
| `manual-evidence-worksheet-extraction-v1809.md` | twelfth ops extraction | Which manual-evidence-worksheet registry classes moved into the new manualevidenceworksheet subpackage, and which upstream RuntimeExecution endpoint constants became public? |
| `signed-approval-capture-artifact-preflight-extraction-v1810.md` | thirteenth ops extraction | Which operator-evidence-value-supply signed-approval capture-artifact-preflight registry classes moved into the new signedapprovalcaptureartifactpreflight subpackage, and which sibling CapturePreflight endpoint constants became public? |
| `signed-approval-capture-preflight-extraction-v1811.md` | fourteenth ops extraction | Which operator-evidence-value-supply signed-approval capture-preflight registry classes moved into the new signedapprovalcapturepreflight subpackage, and which upstream ApprovalPreflight endpoint constants became public? |
| `approval-preflight-extraction-v1812.md` | fifteenth ops extraction | Which operator-evidence-value-supply approval-preflight registry classes moved into the new approvalpreflight subpackage, and which upstream value-supply endpoint constants became public? |
| `signed-approval-artifact-draft-readiness-extraction-v1813.md` | sixteenth ops extraction | Which operator-evidence-value-supply signed-approval artifact-draft-readiness registry classes moved into the new signedapprovalartifactdraftreadiness subpackage, and how were the inbound sibling readers and the already-public CaptureArtifactPreflight endpoints handled? |
| `signed-approval-artifact-draft-preflight-extraction-v1814.md` | seventeenth ops extraction | Which signed-approval artifact-draft-preflight classes moved into the new signedapprovalartifactdraftpreflight subpackage, and how were the ReadinessLane/ProfileSection inbound readers handled? |
| `signed-approval-artifact-draft-readiness-lane-extraction-v1815.md` | eighteenth ops extraction | Which signed-approval artifact-draft-readiness-lane classes moved into the new signedapprovalartifactdraftreadinesslane subpackage, and how were the ReviewPackagePreflight inbound readers handled? |
| `signed-approval-artifact-draft-review-package-preflight-extraction-v1816.md` | nineteenth ops extraction | Which signed-approval artifact-draft-review-package-preflight classes moved into the new signedapprovalartifactdraftreviewpackagepreflight subpackage, and how were the AuthoringReadiness/ProfileSection inbound readers handled? |
| `signed-approval-artifact-draft-authoring-readiness-extraction-v1817.md` | twentieth ops extraction | Which signed-approval artifact-draft-authoring-readiness classes moved into the new ops.maintenance.signedapprovalartifactdraftauthoringreadiness subpackage, and how did root files fall from 1,025 to 1,009 while InstructionPreflight/ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-instruction-preflight-extraction-v1818.md` | twenty-first ops extraction | Which signed-approval artifact-draft-instruction-preflight classes moved into the new ops.maintenance.signedapprovalartifactdraftinstructionpreflight subpackage, and how did root files fall from 1,009 to 993 while TextPackageIntake/ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-text-package-intake-extraction-v1819.md` | twenty-second ops extraction | Which signed-approval artifact-draft-text-package-intake classes moved into the new ops.maintenance.signedapprovalartifactdrafttextpackageintake subpackage, and how did root files fall from 993 to 977 while TextPackageReviewPreflight/ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-text-package-review-preflight-extraction-v1820.md` | twenty-third ops extraction | Which signed-approval artifact-draft-text-package-review-preflight classes moved into the new ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight subpackage, and how did root files fall from 977 to 961 while TextPackageSubmissionPreflight/ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-text-package-submission-preflight-extraction-v1821.md` | twenty-fourth ops extraction | Why did primary SubmissionPreflight and Closeout move together into ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight, and how did root files fall from 961 to 932 while ComparisonPreflight, evidence-intake, and ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-text-package-comparison-preflight-extraction-v1822.md` | twenty-fifth ops extraction | Which comparison-preflight classes moved into ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight, and how did root files fall from 932 to 919 while ComparisonAcceptancePrecheck, ComparedPackageEvidenceIntake, and ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-extraction-v1823.md` | twenty-sixth ops extraction | Which comparison-acceptance-precheck classes moved into ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck, and how did root files fall from 919 to 911 while ComparedPackageEvidenceIntake and ProfileSection readers stayed aligned? |
| `signed-approval-artifact-draft-text-package-compared-package-evidence-intake-extraction-v1824.md` | twenty-seventh ops extraction | Which compared-package-evidence-intake classes moved into ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake, and how did root files fall from 911 to 897 while ComparedPackageReview and ProfileSection readers stayed aligned? |
| `signed-approval-draft-profile-section-extraction-v1825.md` | twenty-eighth ops extraction | How is the 36-file ProfileSection cluster split across three versions, and how did the 11-file base layer move into ops.maintenance.signedapprovaldraftprofilesection while root files fell from 897 to 887? |
| `signed-approval-draft-text-package-profile-section-extraction-v1826.md` | twenty-ninth ops extraction | How did the text-package ProfileSection layer move into ops.maintenance.signedapprovaldrafttextpackageprofilesection while root files fell from 887 to 874 and the ProfileSectionHandoff layer stayed queued? |
| `java-final-push-step0-reconciliation-v1827.md` | Java final-push reconciliation | Which v1826 closeout facts and project-explanation cleanup items were reconciled before the endgame census starts? |
| `extraction-endgame-census-v1828.md` | Java final-push endgame census | Which remaining direct-root ops families make up the 874-file root package, and why is the final root target fixed at 105 with 769 files still to move? |
| `signed-approval-draft-profile-section-handoff-extraction-v1829.md` | thirtieth ops extraction | How did the ProfileSection handoff layer move into ops.maintenance.signedapprovaldraftprofilesectionhandoff while root files fell from 874 to 864 and remaining movable root files fell to 759? |
| `operator-evidence-value-supply-adapter-preflight-extraction-v1830.md` | thirty-first ops extraction | How did AdapterPreflight move into ops.maintenance.operatorevidencevaluesupplyadapterpreflight while root files fell from 864 to 848 and remaining movable root files fell to 743? |
| `operator-evidence-value-supply-base-extraction-v1831.md` | thirty-second ops extraction | How did the ValueSupply base move into ops.maintenance.operatorevidencevaluesupply while root files fell from 848 to 833 and remaining movable root files fell to 728? |
| `compared-evidence-candidate-blueprint-extraction-v1832.md` | thirty-third ops extraction | How did ComparedEvidenceCandidateBlueprint move into ops.maintenance.comparedevidencecandidateblueprint while root files fell from 833 to 819 and remaining movable root files fell to 714? |
| `compared-evidence-candidate-intake-preflight-extraction-v1833.md` | thirty-fourth ops extraction | How did ComparedEvidenceCandidateIntakePreflight move into ops.maintenance.comparedevidencecandidateintakepreflight while root files fell from 819 to 805 and remaining movable root files fell to 700? |
| `compared-package-review-extraction-v1838.md` | thirty-fifth ops extraction | How did ComparedPackageReview move into ops.maintenance.comparedpackagereview while root files fell from 805 to 789, remaining movable root files fell to 684, and EvaluationPreflight gained a public route boundary? |
| `compared-evidence-evaluation-preflight-extraction-v1839.md` | thirty-sixth ops extraction | How did EvaluationPreflight consume the v1838 review boundary, move into ops.maintenance.comparedevidenceevaluationpreflight, and lower root files from 789 to 775 with 670 movable files left? |
| `release-acceptance-route-path-split-base-closeout-extraction-v1840.md` | thirty-seventh ops extraction | How did the 24-file base/closeout layer and shared route owner move while root fell from 775 to 751, movable files fell to 646, and the remaining split bucket fell to 55? |
| `release-acceptance-route-path-split-sustainment-extraction-v1841.md` | thirty-eighth ops extraction | How did the 19-file sustainment layer consume v1840 closeout, move into its own subpackage, lower root from 751 to 732, lower movable files from 646 to 627, and leave 36 split files? |
| `release-acceptance-package-extraction-v1842.md` | thirty-ninth ops extraction | How did the 36-file acceptance-package, closeout-receipt, and archive-index closure move into `ops.maintenance.releaseacceptancepackage`, lower root from 732 to 696, lower movable files from 627 to 591, and finish the split bucket at zero? |
| `extraction-waivers.md` | Java final-push waiver list | Which non-controller root files may remain after extraction, and what reviewer check keeps each waiver honest? |

## Boundary

This upkeep index is read-only documentation. It does not start Java, mini-kv,
Node, Docker, browsers, or managed audit connections. It does not read
credentials, resolve raw endpoint URLs, deploy, roll back, or mutate business
state.

## Maintenance rule

New ops registry work should prefer a narrow subpackage, a route constant, a
response record, catalog data, renderer/support/service/controller layers, and
tests. Long class names should be shortened only when the package name already
contains the missing context.

## Active consolidation plan

The current Java-side follow-up to the Node v2114 governance consolidation is
documented in `../plans/v1789-java-ops-governance-consolidation-roadmap.md`
and `../plans/v1789-codex-ops-migration-playbook.md`. The plan is
contract-preserving and archive-preserving: Do not rename or move `a/` through
`f/`, `e/<version>/`, or evidence JSON files.

The first J6 inventory is `ops-consolidation-inventory-v1796.md`. It records
root-package pressure, route family clusters, load-bearing archive boundaries,
and the first reduction candidates before any class movement begins.

The first J7 extraction is
`code-walkthrough-compliance-extraction-v1797.md`. It moves eleven
implementation files into `ops.maintenance.walkthrough.compliance`, leaves the
root controller and public route aggregation in place, and lowers direct root
`ops` Java files from 1,330 to 1,319.

The second J8 extraction is `quality-gate-registry-extraction-v1798.md`. It
moves ten implementation files into `ops.maintenance.walkthrough.qualitygate`,
leaves the root controller and public route aggregation in place, and lowers
direct root `ops` Java files from 1,319 to 1,309.

The third J9 extraction is `quality-audit-registry-extraction-v1799.md`. It
moves eleven implementation files into `ops.maintenance.walkthrough.qualityaudit`,
leaves the root controller and public route aggregation in place, and lowers
direct root `ops` Java files from 1,309 to 1,298.

The fourth J10 extraction is `depth-registry-extraction-v1800.md`. It moves
eight implementation files into `ops.maintenance.walkthrough.depth`, leaves the
root controller and public route aggregation in place, and lowers direct root
`ops` Java files from 1,298 to 1,290.

The fifth J11 extraction is `screenshot-explanation-archive-extraction-v1801.md`.
It moves ten implementation files into
`ops.maintenance.screenshotexplanationarchive`, leaves the root controller and
public route aggregation in place, and lowers direct root `ops` Java files from
1,290 to 1,280. It is the first extraction outside the CodeWalkthrough family.

The sixth J12 extraction is
`credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md`.
It moves eleven implementation files into `ops.maintenance.credentialresolver`,
leaves the root controller and public route aggregation in place, and lowers
direct root `ops` Java files from 1,280 to 1,269. It is the first dependency-
injected evidence registry extracted, and made the shared
`OpsEvidenceServiceTestFixtures` test helper public so the moved test support
could reuse it.

The seventh J13 extraction is `sandbox-connection-extraction-v1803.md`. It moves
twenty-six implementation files (two sibling registry sub-clusters that share one
route-path class) into `ops.maintenance.sandboxconnection`, leaves both root
controllers and public route aggregation in place, and lowers direct root `ops`
Java files from 1,269 to 1,243. It is the second dependency-injected evidence
registry family extracted and the largest single root-pressure reduction so far.

The eighth J14 extraction is `signed-approval-route-path-consolidation-v1804.md`.
It is the first pure route-path leaf consolidation: it moves three signed-approval
route-path classes into the new `ops.maintenance.signedapproval` subpackage,
leaves their registry services/controllers and public route aggregation in root,
and lowers direct root `ops` Java files from 1,243 to 1,240. It stands up the
signedapproval subpackage so the larger operator-evidence-value-supply
signed-approval registry families can migrate into it later.

The ninth J15 extraction is `candidate-document-extraction-v1805.md`. It is the
largest extraction so far: it moves fifty-seven candidate-document implementation
files plus the family route-path class into the new
`ops.maintenance.candidatedocument` subpackage, leaves the eight public
controllers and route aggregation in root, and lowers direct root `ops` Java
files from 1,240 to 1,183. The family uses intra-family dependency injection and
has effectively no cross-family coupling; the one cross-family route constant it
reads was made an owned, delegated constant.

The J16 closeout is `java-extraction-quality-closeout-v1806.md`. It does not
move code. It records the current v1805 green baseline, the historical v1798 tag
exception, the v1799 remediation boundary, and the rule that historical tags
must not be rewritten or force-moved.

The tenth J17 extraction is `operator-evidence-value-draft-extraction-v1807.md`.
It moves sixteen operator-evidence-value-draft implementation files plus the
family route-path class into the new `ops.maintenance.operatorevidencevaluedraft`
subpackage, leaves the two controllers and route aggregation in root, and lowers
direct root `ops` Java files from 1,183 to 1,167. It is the first extraction to
apply the cross-family endpoint sub-recipe: seven `OperatorEvidenceImportPreflight`
endpoint constants (read outbound) and several value-draft endpoint constants
(read inbound by the value-supply family) were made public, with imports added,
so the coupling crosses the new package boundary without any route change.

The eleventh J18 extraction is
`operator-evidence-import-preflight-extraction-v1808.md`. It moves fifteen
operator-evidence-import-preflight implementation files plus the family
route-path class into the new
`ops.maintenance.operatorevidenceimportpreflight` subpackage, leaves the two
controllers and route aggregation in root, and lowers direct root `ops` Java
files from 1,167 to 1,152. It completes the next step in the operator-evidence
chain by publicizing the immutable upstream `ManualEvidenceWorksheet` and
`RuntimeExecutionLiveReadGate` endpoint constants read by the moved services,
without changing any route string or response shape.

The twelfth J19 extraction is `manual-evidence-worksheet-extraction-v1809.md`.
It moves fifteen manual-evidence-worksheet implementation files plus the family
route-path class into the new `ops.maintenance.manualevidenceworksheet`
subpackage, leaves the two controllers and route aggregation in root, and lowers
direct root `ops` Java files from 1,152 to 1,137. It moves the upstream worksheet
source for `OperatorEvidenceImportPreflight` while keeping every worksheet route
and response shape stable. RuntimeExecution endpoint constants read by the moved
worksheet services are publicized as immutable read-only strings only.

The thirteenth J20 extraction is
`signed-approval-capture-artifact-preflight-extraction-v1810.md`. It moves sixteen
operator-evidence-value-supply signed-approval capture-artifact-preflight
implementation files into the new
`ops.maintenance.signedapprovalcaptureartifactpreflight` subpackage, leaves the two
controllers and route aggregation in root, and lowers direct root `ops` Java files
from 1,137 to 1,121. The moved services repoint to the public
`OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths` owner that
already lived in `ops.maintenance.signedapproval`. The only cross-family coupling
is the family `FragmentCatalog`, which reads ten sibling `CapturePreflight`
endpoint constants; those constants are publicized as immutable read-only strings.

The fourteenth J21 extraction is
`signed-approval-capture-preflight-extraction-v1811.md`. It moves sixteen
operator-evidence-value-supply signed-approval capture-preflight implementation
files into the new `ops.maintenance.signedapprovalcapturepreflight` subpackage,
leaves the two controllers and public route aggregation in root, and lowers
direct root `ops` Java files from 1,121 to 1,105. The moved services repoint to
the public `OpsShardReadinessSignedApprovalCapturePreflightRoutePaths` owner
that already lived in `ops.maintenance.signedapproval`. The family has two
endpoint-only cross-family edges: `InputCatalog` reads eleven upstream
`ApprovalPreflight` service endpoints, and the already-moved v1810
`CaptureArtifactPreflightFragmentCatalog` reads ten sibling `CapturePreflight`
service endpoints. Both edges are handled by public immutable endpoint strings
and imports only; route strings and response shapes stay unchanged.

The fifteenth J22 extraction is `approval-preflight-extraction-v1812.md`. It
moves fifteen physical operator-evidence-value-supply approval-preflight
implementation files into the new `ops.maintenance.approvalpreflight`
subpackage, collocates the package-private policy catalog with the item catalog
to avoid relaxing the total file-count ratchet, leaves the two controllers and
public route aggregation in root, and lowers direct root `ops` Java files from
1,105 to 1,089. The new
`OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths` owner
now holds the approval-preflight suffixes; the root aggregator delegates to it.
The moved item catalog reads seven upstream value-supply or adapter-preflight
endpoint constants as source evidence, and v1812 publicizes only those immutable
endpoint strings.

The sixteenth J23 extraction is
`signed-approval-artifact-draft-readiness-extraction-v1813.md`. It moves sixteen
operator-evidence-value-supply signed-approval artifact-draft-readiness
implementation files into the new
`ops.maintenance.signedapprovalartifactdraftreadiness` subpackage, leaves the two
controllers and route aggregation in root, and lowers direct root `ops` Java files
from 1,089 to 1,073. The moved services repoint to the public
`OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths` owner that
already lived in `ops.maintenance.signedapproval`. Its item catalogs read ten
`CaptureArtifactPreflight` endpoint constants already publicized in v1810, so no
new outbound visibility change was needed; on the inbound side, this family's own
endpoint constants are publicized for three sibling families (`ArtifactDraftPreflight`,
`ArtifactDraftReviewPackagePreflight`, `SignedApprovalDraftProfileSection`) that
read them from root.

The seventeenth J24 extraction is
`signed-approval-artifact-draft-preflight-extraction-v1814.md`. It moves fifteen
physical operator-evidence-value-supply signed-approval artifact-draft-preflight
implementation files into the new
`ops.maintenance.signedapprovalartifactdraftpreflight` subpackage, collocates the
package-private gate catalog with the guard catalog to avoid relaxing the total
file-count ratchet, leaves the two controllers and route aggregation in root,
and lowers direct root `ops` Java files from 1,073 to 1,057. Its moved field
catalogs read the v1813 artifact-draft-readiness endpoint constants from the new
readiness package, while retained-root `ArtifactDraftReadinessLane` and
`SignedApprovalDraftProfileSection` readers import this family's public immutable
endpoint strings.

The eighteenth J25 extraction is
`signed-approval-artifact-draft-readiness-lane-extraction-v1815.md`. It moves
fifteen physical operator-evidence-value-supply signed-approval
artifact-draft-readiness-lane implementation files into the new
`ops.maintenance.signedapprovalartifactdraftreadinesslane` subpackage, collocates
the package-private gate catalog with the blocker catalog to avoid relaxing the
total file-count ratchet, leaves the two controllers and route aggregation in
root, and lowers direct root `ops` Java files from 1,057 to 1,041. Its moved lane
catalogs read the v1814 artifact-draft-preflight endpoint constants from the new
preflight package, while retained-root `ReviewPackagePreflight` slot catalogs
import this family's public immutable endpoint strings.

The nineteenth J26 extraction is
`signed-approval-artifact-draft-review-package-preflight-extraction-v1816.md`.
It moves fifteen physical operator-evidence-value-supply signed-approval
artifact-draft-review-package-preflight implementation files into the new
`ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight` subpackage,
collocates the package-private gate catalog with the guard catalog to avoid
relaxing the total file-count ratchet, leaves the two controllers and route
aggregation in root, and lowers direct root `ops` Java files from 1,041 to 1,025.
Its moved slot catalogs read the v1815 artifact-draft-readiness-lane
endpoint constants from the new readiness-lane package, while retained-root
`AuthoringReadiness` and `SignedApprovalDraftProfileSection` readers import this
family's public immutable endpoint strings.

The twenty-sixth J33 extraction is
`signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-extraction-v1823.md`.
It moves seven comparison-acceptance-precheck implementation files into
`ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck`,
collocates the package-private guard catalog with the checkpoint catalog, and
leaves the public controller plus root route aggregation in root. Direct root
`ops` Java files fall from 919 to 911 while total `ops` Java files stay at
1,352. The new public route owner preserves four byte-identical paths. The
moved checkpoint catalog consumes the five v1822 ComparisonPreflight endpoint
constants; ComparedPackageEvidenceIntake and ProfileSection readers import the
new public boundary.

The twenty-seventh J34 extraction is
`signed-approval-artifact-draft-text-package-compared-package-evidence-intake-extraction-v1824.md`.
It moves thirteen compared-package-evidence-intake implementation and endpoint
reference files into
`ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake`,
collocates the package-private guard catalog with the slot catalog, and leaves
the public controller plus root route aggregation in root. Direct root `ops`
Java files fall from 911 to 897 while total `ops` Java files stay at 1,352. The
new public route owner preserves five byte-identical paths. ComparedPackageReview
and ProfileSection readers import the new public endpoint/response boundary.
