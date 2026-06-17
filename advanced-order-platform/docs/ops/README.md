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
