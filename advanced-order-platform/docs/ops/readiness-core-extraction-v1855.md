# v1855 Readiness core extraction

## Scope

This version extracts the complete direct-root shard-readiness core into
`ops.maintenance.readinesscore`. The scope is 20 production files: nine named
service/response pairs, the base readiness service/response pair, and the
two-file ActiveShardPlanHandoff pair. Spring controllers and the global route
aggregator remain in the direct root.

No route string, fixture path, evidence path, response component, receipt byte,
HTTP method, write boundary, credential boundary, deployment behavior,
rollback behavior, or archive layout may change.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one complete dependency closure | Moved all 20 core production files to `ops.maintenance.readinesscore` | v1855 package/root census guard | implemented |
| Keep web adapters visible | Retained three root controllers and imported public service/response boundaries | controller mapping tests and v1855 import guard | implemented |
| Preserve route bytes without a reverse root dependency | Base readiness service owns the base and three evidence suffixes; root `RoutePaths` delegates | route-path tests and literal equality assertions | implemented |
| Move package-local behavior tests | Moved ten service tests; cross-family snapshot and route tests stay in root | package/root test census | implemented |
| Repair existing consumers only | Repointed readonly-evidence, v1contract, runtime-execution, prototype and root adapters | compiler plus explicit 16-file inbound-consumer guard | implemented |
| Preserve static-analysis scope | Relocated the 18 mirrored SpotBugs response FQNs without adding exclusions | old/new FQN guard and SpotBugs | implemented |
| Tighten the endgame ratchet | Direct root 310 -> 290; movable 206 -> 186; both readiness buckets -> 0 | `scripts/ops-root-census.ps1 -Json` | implemented |
| Obey the new elegance gates | Added no production type; all new code identifiers are at or below 40 characters; family design note preceded implementation | v1855 source-name guard and committed walkthrough | implemented |
| Explain before implementation and verify | Archived a 3,000+ Han Chinese walkthrough with exactly ten required headings before implementation | walkthrough quality/depth tests | implemented |
| Close with independent evidence | Focused tests, Spotless and full verify passed; remote CI remains | local logs and GitHub Actions run IDs | local gates complete |

## Boundary

The extracted package publishes only the Spring services, immutable response
records, and endpoint/fixture/evidence constants already consumed across
packages. It does not introduce a second facade or a new route-owner file. The
existing `OpsShardReadinessService` becomes the compact owner of the base route
and the three evidence suffixes, while the retained global route aggregator
forwards to those constants for compatibility.

## Focused verification

`test-compile` passed after the 20 production and ten test moves, proving all
runtime and test consumers resolve the new package. The first 81-test focused
run had three maintenance-only failures: the new stale-import guard combined
an unrelated root Prototype import with a valid new core import, while two
v1828 census assertions still expected 310/206. The import guard now checks
exact moved-type imports, and the live census pins tightened to 290/186 while
the historical 310/206 transition remains in the evidence chain.

A ten-test census rerun then exposed one discoverability omission: the new
document was not yet indexed from `docs/ops/README.md`. Adding the v1855 row
closed that gate. The corrected full focused selection passed all 81 tests.
The maintainability census remains within the existing production
1111/34/1/1 and test 853/8/2/0 budgets; no cap was raised.

The first full verify ran 1,806 tests and reported 12 failures plus one error,
all in maintenance evidence: eight historical live root-count pins still read
310, the v1852 guard read the base readiness service from its old root path,
and four walkthrough checks shared one documentation cause (two required
heading spaces, the workload literals, and a slightly sub-majority Chinese
ratio). The live pins now read 290, the path follows `readinesscore`, and the
walkthrough records this real failure chain without a legacy marker or a lower
quality threshold.

The corrected full verify passed all 1,806 tests in 8m35s. JaCoCo analyzed
2,228 classes and met every configured check; SpotBugs reported zero bugs and
zero errors. Remote implementation and closeout CI remain the only unclosed
evidence at implementation-commit time.

## Failure conditions

- Any changed route, fixture, evidence, response, or receipt byte invalidates
  the extraction.
- A moved service importing the retained root route aggregator invalidates the
  dependency direction.
- Moving a Spring controller or a cross-family route/snapshot test invalidates
  the adapter boundary.
- Adding a new Java identifier or filename over 40 characters invalidates the
  elegance gate.
- Raising a root-count, total-file, source-size, or SpotBugs waiver ratchet
  invalidates the version.
- Editing the walkthrough after final verification requires the final
  verification to run again.

## Verification

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='*ShardReadiness*ServiceTests,OpsExtractionV1855Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,OpsShardReadinessRoutePathsTests,OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests,JavaMaintainabilityBudgetTests' test
.\mvnw.cmd -q spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```
