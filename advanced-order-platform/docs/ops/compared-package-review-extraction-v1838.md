# Compared package review extraction v1838

v1838 resumes the Java final-push extraction series after the v1834-v1837
maintainability program. It moves the sixteen non-controller
`ComparedPackageReview` implementation files from the direct-root `ops`
package into:

```text
com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview
```

The Spring controller remains in the direct-root package as the visible HTTP
entry point. No route suffix, response field, profile, evidence ordering, or
read-only boundary changes.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | Status |
| --- | --- | --- | --- |
| Extract the review family | Sixteen implementation files and four package-local tests move; only the controller and its controller test stay root | `ReadabilityUpkeepOpsConsolidationExtractionV1838Tests` checks exact placement and root absence | Complete |
| Keep route bytes unchanged | `OpsShardReadinessComparedPackageReviewRoutePaths` owns six suffixes and full endpoints; the root aggregator delegates to it | family route-path tests plus v1838 source guard | Complete |
| Preserve upstream evidence | Four slot catalogs still consume the already-extracted ComparedPackageEvidenceIntake endpoint references | compile and focused catalog/service tests | Complete |
| Prepare the next dependency | EvaluationPreflight rule catalogs import the public review route owner | v1838 source guard checks all four readers | Complete |
| Keep response analysis valid | Both SpotBugs mirror blocks use the relocated response FQN | v1838 SpotBugs configuration guard | Complete |
| Tighten the structural contract | direct root 805 -> 789; movable backlog 700 -> 684; total ops <= 1,352 | census script and ratchet tests | Complete |

## Ownership change

The old `EndpointRefs` helper was not copied. It was renamed and expanded into
the public family route owner, so the extraction does not increase total file
count. Its six full endpoint constants remain available to the next
EvaluationPreflight package, while its six suffix constants become the values
delegated by `OpsShardReadinessRoutePaths`. This leaves one source of route
bytes and preserves the root controller annotations.

The moved package contains the catalog, source-evidence, comparison-outcome,
identity-digest, policy-archive, and handoff-closeout services; the response
record; slot, guard, and reviewer catalogs; and shared support. The controller
imports the public services and response but remains mapped under the root
aggregator's `BASE_PATH`.

## Dependency direction

ComparedPackageReview reads only the already-extracted
`ComparedPackageEvidenceIntakeEndpointRefs` boundary. The still-root
ComparedEvidenceEvaluationPreflight family reads review endpoints in the
opposite direction. Moving review first therefore makes the dependency point
from the next root family into an already-extracted public owner. v1839 can
then move EvaluationPreflight without creating a temporary reverse package
dependency.

## Mechanical evidence

Run from the project root:

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -Dtest=OpsShardReadinessComparedPackageReview*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1838Tests test
.\mvnw.cmd verify
```

The census contract for this version is 789 direct-root files, 105 retained
root files, 684 remaining direct-root non-controller files, zero unassigned
files, and a zero-count `ComparedPackageReview` bucket. Archive roots and
frozen fixture bytes are untouched.

The first full verify ran all 1,697 tests and exposed one maintainability
budget failure: the new delegation import made `OpsShardReadinessRoutePaths`
1,113 lines against its shrink-only 1,111-line cap. The cap was not raised.
Static route imports plus removal of two non-semantic field-group separators
restored the file to exactly 1,111 lines before the final full verify.

## Safety boundary

This extraction remains read-only. It does not add write routing, active shard
selection, credential values, raw endpoints, managed-audit connections,
deployment, rollback, SQL execution, Node process control, or mini-kv process
control. The controller returns the same immutable response shape through the
same six GET routes.
