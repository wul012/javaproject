# v1853 V1Contract consumer/alignment extraction

## Scope

This version performs one contract-preserving structural change. It moves the
complete 42-file V1Contract consumer/alignment production closure from the
direct `ops` root into `ops.maintenance.v1contract`. The Spring controller stays
in the root package, 98 package-local tests move with the implementation, and
three controller/route structure tests remain beside the controller.

No route string, fixture path, evidence path, response component, receipt byte,
HTTP method, write boundary, credential boundary, deployment behavior, rollback
behavior, or archive layout changes.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move the whole family | 42 production files in `ops.maintenance.v1contract`; only `OpsShardReadinessV1ContractController` remains in direct root | `ReadabilityUpkeepOpsConsolidationExtractionV1853Tests.completeV1ContractClosureMovesWhileControllerStaysRoot` | complete |
| Keep web ownership visible | The root controller imports 11 public service/response pairs from the family package | controller split, mapping, and route inventory tests remain in root | complete |
| Preserve route bytes | Existing 11 `V1_CONTRACT_*` suffixes keep their literal values and become public immutable references on the retained global route owner | route-path tests plus the v1853 public-suffix guard | complete |
| Keep internals narrow | Snapshot, receipt-segment, evidence-path, and catalog helper classes remain package-private | v1853 privacy guard; compiler validates package-local callers | complete |
| Remove reverse type borrowing | `OpsShardReadinessV1ContractEndpointPairs` owns an immutable `EndpointPair`; the root registry maps it into its private pair | v1853 endpoint-pair guard and endpoint integrity tests | complete |
| Preserve historical checks | A test-source V1Contract support forwards only 17 required snapshot methods; the production snapshots remain private | historical endpoint compatibility test and privacy guard | complete |
| Preserve root registry privacy | `OpsShardReadinessEvidenceEndpointsTestSupport` forwards four immutable lists for moved tests; the production registry stays package-private | all moved historical/adjacency tests compile and execute | complete |
| Move SpotBugs baseline, do not relax it | All 22 EI_EXPOSE_REP / EI_EXPOSE_REP2 FQNs point to the new response package | SpotBugs check and v1853 FQN guard | complete |
| Tighten endgame ratchets | Direct root 471 -> 429; movable 366 -> 324; V1Contract bucket 42 -> 0; unassigned remains 0 | `scripts/ops-root-census.ps1 -Json` and all live root-count guards | complete |
| Explain before final verify | Chinese walkthrough is archived under the v1853-v1857 continuation directory | walkthrough quality/depth gates and v1853 doc guard | complete |

The first focused run executed 245 tests and passed 244. The only failure was
the new extraction guard requiring the retained ControllerSplit test to import
the moved package even though that test correctly references only the retained
controller and global route owner. The guard was narrowed to require package
imports only from the two retained tests that use moved service/response types;
the ControllerSplit branch now checks the controller and route suffixes instead.
The corrected focused run passed all 245 tests in 1m34s.

The first full verify executed 1,785 tests and passed 1,784. Its only failure
was the existing named-file maintainability budget: making the route suffixes
public caused formatter wrapping to grow `OpsShardReadinessRoutePaths` from
1,111 to 1,112 lines. The 1,111 budget was not raised. One grouping blank line
between the unchanged V1Contract and Prototype suffix blocks was removed, with
all route literals preserved, before rerunning the targeted budget test and
the full verify.

## Boundary design

The public production surface is limited to what another package genuinely
needs: 11 Spring services, 11 response records, immutable endpoint/fixture/
evidence constants, the small `OpsShardReadinessV1Contract` contract utility,
and the endpoint-pair registry. Snapshot builders and the long post-handoff
receipt catalogue remain implementation details.

The endpoint-pair change is intentionally asymmetric. The family publishes its
own immutable pair type, while the retained root registry converts it into the
root-private pair. This lets dependency direction point from the composition
root toward the extracted family instead of forcing the family to import a
private root implementation type.

## Census

```text
Direct root 471 -> 429
movable 366 -> 324
V1Contract bucket 42 -> 0
retained root 105 -> 105
unassigned 0 -> 0
total ops main Java 1352 -> 1352
```

## Failure conditions

- Any changed route, fixture, evidence, response, or receipt byte invalidates
  the extraction.
- Making a production snapshot public to satisfy a test invalidates the narrow
  boundary; use test-source support instead.
- Raising any root-count or total-file ratchet invalidates the version.
- Moving the controller or its structure tests out of the root invalidates the
  established web-adapter rule.
- A walkthrough changed after final verify requires the full verify to run
  again.

## Verification commands

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='*V1Contract*,ReadabilityUpkeepOpsConsolidationExtractionV1853Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,OpsShardReadinessRoutePathsTests,OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests' test
.\mvnw.cmd spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```
