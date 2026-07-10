# Compared evidence evaluation preflight extraction v1839

v1839 closes the two-family compared-evidence chain started by v1838. It moves
fourteen non-controller implementation files into:

```text
com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight
```

The Spring controller remains in the direct-root `ops` package. Route bytes,
response fields, immutable collection behavior, read-only transactions, and
all fail-closed capability flags remain unchanged.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | Status |
| --- | --- | --- | --- |
| Extract EvaluationPreflight | Fourteen implementation files and four package-local tests move; controller stays root | v1839 readability guard checks exact placement and root absence | Complete |
| Keep one route owner | The former public EndpointRefs file becomes `OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths` | route tests and root-delegation source guard | Complete |
| Preserve outbound review evidence | Four moved rule catalogs import the v1838 ComparedPackageReview route owner | compile, catalog tests, and v1839 import guard | Complete |
| Preserve inbound consumers | CandidateBlueprint imports the moved route owner; ProfileSection imports the moved service/response | v1832 historical guard plus v1839 reader guard | Complete |
| Keep analysis configuration aligned | Both SpotBugs response mirror blocks use the moved FQN | v1839 configuration guard | Complete |
| Tighten the endgame contract | direct root 789 -> 775; movable 684 -> 670; total ops <= 1,352 | census script and exact ratchets | Complete |

## Dependency closure

The moved rule catalogs reference the six review endpoints extracted in v1838.
No root helper remains between these packages: EvaluationPreflight imports the
public `OpsShardReadinessComparedPackageReviewRoutePaths` owner directly.
CandidateBlueprint, which consumes EvaluationPreflight endpoints, now imports
the new evaluation route owner. ProfileSection consumes the evaluation catalog
service and response and therefore imports their moved FQNs. The resulting
dependency direction is explicit and acyclic at the package boundary.

## Route ownership

The old EndpointRefs file is renamed rather than copied. The new owner keeps
five byte-identical suffixes and five complete endpoint constants. The root
aggregator statically delegates the suffixes so the retained controller's
annotations stay unchanged. The route-aggregator 1,111-line budget remains
fixed; two non-semantic field-group separators are removed to pay for the new
static import rather than raising the cap.

## Mechanical evidence

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -Dtest=OpsShardReadinessComparedEvidenceEvaluationPreflight*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1839Tests test
.\mvnw.cmd verify
```

The version contract is 775 direct-root files, 105 retained files, 670
remaining direct-root non-controller files, zero unassigned files, and zero
files in both compared-family buckets. No archive or frozen fixture path moves.

## Safety boundary

EvaluationPreflight still describes rules for evaluating absent candidate
evidence. It does not accept candidate evidence, grant approval, build runtime
payloads, mutate sibling state, start Node or mini-kv, open credentials, route
writes, deploy, roll back, or execute SQL. Every service remains a read-only
transaction and every controller mapping remains GET-only.
