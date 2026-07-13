# Ops Root Endgame v1866

Status: implementation CI passed; tagged closeout pending. The implementation
run is `29215460666`; evidence is final only after closeout Actions also pass.

## Design Note

- Abstractions: `overview` owns runtime counters; `evidencecore` owns static release data.
- Data boundary: immutable response records and artifact metadata contain no behavior.
- Behavior boundary: root controllers and `OpsEvidenceService` remain composition adapters.
- Split boundary: `StaticReleaseCatalog` dispatches; `StaticReleaseSections` builds data.
- Compatibility boundary: response components, endpoint bytes, and read-only flags stay exact.
- Size boundary: both new evidence files stay below 500 lines and names stay within 40 characters.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Reach the census target | Move the final four root implementation types | exact root census 108 -> 104 and remaining 4 -> 0 | complete; run 29215460666 |
| Keep overview behavior | Move service/response/test together; retain root controller | focused unit and integration tests | complete; run 29215460666 |
| Split the static hotspot | Replace the 645-line dispatch table with catalog/sections responsibilities | exact file set and <=500-line caps | complete; run 29215460666 |
| Preserve release contracts | Keep all 12 versions/endpoints and response sections byte-identical | existing evidence contract suite plus catalog guard | complete; run 29215460666 |
| Preserve package direction | Root composition imports the extracted packages; extracted code does not import root controllers | compile and source-direction guard | complete; run 29215460666 |
| Preserve SpotBugs policy | Relocate exactly two accepted overview response mirrors | old/new FQN count and full SpotBugs gate | complete; run 29215460666 |
| Close all waivers | Leave exactly 100 controllers plus four policy-retained shared files | waiver-list and exact retained-set checks | complete; run 29215460666 |
| Preserve the root coverage floor | Exercise every retained RouteCleanup controller delegation through one data-driven contract test | exact invocation count plus JaCoCo package floor | complete; run 29215460666 |
| Explain the change | Chinese-majority walkthrough, exactly 10 headings, at least 3,000 Han characters | v1866 walkthrough guard | complete; run 29215460666 |

## Scope

`OpsOverviewService` and `OpsOverviewResponse` move to
`ops.maintenance.overview`; their controller stays at the HTTP boundary. The
two static release support files become `StaticReleaseCatalog` and
`StaticReleaseSections` under `ops.maintenance.evidencecore`. The artifact enum
becomes a public nested type of the catalog, so the split does not increase the
total `ops` production file count.

No route, JSON property, response ordering, write boundary, credential value,
deployment action, rollback action, archive path, or runtime configuration is
changed.

## Pre-Final Evidence

- Main compilation passed with 1,483 production source files; test compilation
  passed with 874 test source files.
- The committed census command reports root/target/retained `104/104/104`,
  remaining `0`, unassigned `0`, and both final family buckets at zero.
- `StaticReleaseCatalog` is 225 lines and `StaticReleaseSections` is 476 lines;
  both filenames and every new identifier stay within the 40-character budget.
- The broad focused run executed 141 tests. 140 passed; the sole failure was the
  historical census guard's stale movable count `4`. Tightening it to zero was
  followed by an 11-test repair run with zero failures.
- The frozen walkthrough contains 4,778 Han characters, exactly ten required
  headings, and a Chinese-majority letter ratio.
- The first full verify executed 1,900 tests and exposed one failure: the new
  walkthrough used custom headings instead of the archive-wide standard. The
  content was remapped to the required ten headings, mini-kv and blocking
  boundaries were made explicit, and the 13-test local/global walkthrough
  repair suite passed.
- The required complete rerun then passed all 1,900 tests but correctly failed
  the unchanged JaCoCo root-`ops` floor: moving 35 fully-covered overview lines
  out of the exact root package exposed 43 previously missed controller
  delegation lines, producing 96.37% against the 97% floor. The floor remains
  unchanged. `RouteCleanupControllerContractTests` now drives every GET method
  on the seven affected controllers and checks one dependency invocation per
  endpoint through a shared reflection engine. Focused and full repair reruns
  were mandatory before acceptance.
- The next full rerun passed all 1,901 tests with zero failures,
  errors, or skips in 13:28. JaCoCo analyzed 2,229 classes, measured 32,893
  covered and 568 missed lines globally (98.30%), and restored the exact root
  `ops` package to 1,187 covered and zero missed lines. Every floor passed.
  SpotBugs reported zero findings, and Spotless remained green. The walkthrough
  contains 4,778 Han characters and exactly ten standard headings. A final
  pre-commit census then found that the walkthrough's 207/451 size claims came
  from the forbidden nonblank-line counter; they were corrected to the exact
  225/476 `StreamReader` counts. Because that correction touched the walkthrough
  after this green run, one final complete verify remained mandatory.
- The corrected walkthrough then preceded a final complete rerun. It passed all
  1,901 tests, every JaCoCo floor, and SpotBugs with zero findings in 10:24.
  This is the accepted local verification result for v1866.
- Implementation commit `1165de44` passed GitHub Actions run `29215460666`:
  Docker-tagged integration tests completed in 2:04 and the headless regression,
  prod-profile smoke, and JaCoCo artifact job completed in 15:59.

## Failure Conditions

- Direct-root `ops` is not exactly 104 files, or any movable file remains.
- Total `ops` production Java files exceed 1,352.
- Either extracted evidence file exceeds 500 lines or introduces a name over 40 characters.
- Any static release artifact version, endpoint, response section, or list ordering changes.
- Any moved implementation imports a root controller.
- The overview SpotBugs mirrors are missing, duplicated, or left at the old FQN.
- The walkthrough is written or expanded after the final verify.

## Verification Plan

1. Compile production and tests after moves, short-name imports, and catalog split.
2. Run overview, evidence, release-approval, census, waiver, and v1865-v1866 guards.
3. Run Spotless, stale-name scans, exact root census, and diff checks.
4. Freeze the Chinese walkthrough and run one complete `mvnw -B verify`.
5. Push implementation, require both CI jobs green, then close ledger/tag/push and
   require closeout CI green before Phase 2 writes begin.
