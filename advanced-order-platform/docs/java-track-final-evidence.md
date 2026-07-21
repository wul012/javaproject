# Java Track Final Evidence Candidate

Status: v1867 implementation candidate. External Java-track review is required before
the word final may describe the track. This file records reproducible evidence; it does
not authorize production execution.

## Maturity Boundary

The authorized label is `single-project validation + verified read-only cross-project
integration (env-gated, single machine, no execution authority)`. The Node-owned C1-C4
capstone was independently reproduced before this closeout. Java still forbids real
payment, credential-value access, raw endpoint execution, managed audit connections,
deployment, rollback, rollback SQL, and unauthenticated failed-event replay.

## E1-E10 Evidence Matrix

| Gate | Implementation evidence | Mechanical failure surface | Candidate state |
| --- | --- | --- | --- |
| E1 Build & CI | Parent `.github/workflows/maven-ci.yml`; Maven wrapper; headless and optional Docker jobs | `JavaTrackCloseoutTests.workflowUsesCurrentActions`; real Actions runs | v1883 implementation run 29807996922 green: Docker 2:02, headless 19:46 |
| E2 Static analysis | Spotless and SpotBugs checks; 676 exact pattern/class identities with secure Git-prior ratchet | Maven verify plus `SpotBugsWaiverTests` | v1883 local SpotBugs 0 bugs / 0 errors |
| E3 Coverage | JaCoCo global and ten package rules; v1867 floors raised in `pom.xml` | `JavaTrackCloseoutTests.coverageFloorsStayRaised`; JaCoCo check | v1883 local verify analyzed 2130 classes/all floors |
| E4 Security & config | Safe prod profile and threat model in `PRODUCTION_READINESS.md` | existing profile/config tests plus `JavaTrackCloseoutTests.securityBoundaryStaysExplicit` | local full verify green |
| E5 Observability | health/info/metrics, tracing, correlated exception logs | `ActuatorHealthIntegrationTests`, `ApiExceptionTraceIntegrationTests`, `ObservabilityConfigurationTests` | existing suite evidence |
| E6 Error handling | graceful shutdown, finite shutdown timeout, typed API errors and guarded replay | prod smoke, exception tests, failed-event approval/readiness tests | existing suite evidence |
| E7 Docs honesty | README and production boundary use the authorized exact maturity label and list non-authorized capabilities | `ProductionReadinessDocumentationTests` and closeout docs gate | local full verify green |
| E8 Release discipline | `CHANGELOG.md`, git-tag policy, progress ledger, implementation/closeout commits | `JavaTrackCloseoutTests.docsAndReleaseStayHonest`; external git/CI check | v1883 implementation `b5cae273` and run green; closeout/tag pending |
| E9 Code health | root 104/104/0, no Java file above 750 lines, route owner 27 fields/69 lines, exact name baseline plus staged change gate | extraction, maintainability, elegance, and HTTP-boundary ratchets | v1883 expanded 119/119, renderer-line cap 3448, full local verify, and implementation CI green |
| E10 Archive retention | exact SHA-256 manifest, count/raw-byte ceilings, frozen archive policy; CRLF is canonicalized to LF for text only | `ArchiveRetentionTests` and `archive-retention-census.ps1` | v1883-authorized exact set: 1694 files / 20092216 raw bytes |

## Final Censuses

- Ops direct root: 104 files; retained: 104; remaining movable: 0; unassigned: 0.
- Ops total production Java files: 1,251; no extraction family remains in root.
- Production Java: 1,383 files; maximum 738 lines; over 500: 32; over 750/1000: 0/0.
- Test Java: 900 files; maximum 699 lines; over 500: 8;
  over 750/1000: 0/0.
- Long-name shrink-only baseline: production stems/uses/unique 1169/20376/2728;
  tests 764/9999/3783. New declarations and filenames remain within 40 characters;
  baseline identities and aggregate occurrences only shrink during staged migration.
- Renderer census: 45 files / 3,616 lines / 30 long filenames, down from
  121 / 5,355 / 119 at the v1872 start of the three-point elegance program.
- Exact long-name identities are frozen in `config/java-name-baseline.txt`; v1869 adds
  Git-aware tests that reject new names, dirty-tree blind spots, oversized feature-source
  growth, and undocumented three-file families.
- Root route owner: 15 owned literals plus 12 ReleaseAcceptance compatibility aliases.
  v1867 repointed 735 reads in 160 files to leaf owners and removed 239 pure forwarding
  aliases without changing route bytes or the root-versus-leaf compatibility response.
- SpotBugs exclusions: 676 exact pattern/class identities; only deletion is allowed, and
  every retained class must load from the compiled classpath.
- Archive retention: v1881's user-authorized optimization adds exactly one walkthrough;
  the current exact set is 1,692 files / 20,059,203 raw bytes.

## Active Waivers

The only shared-core root waivers are `OpsEvidenceResponse.java`,
`OpsEvidenceService.java`, and `OpsShardReadinessEvidenceEndpoints.java`.
`OpsShardReadinessRoutePaths.java` is the separately enumerated retained global route
owner. Its 27 fields require readers and its line cap is 69. Controllers are retained
by the binding root policy. No source-size waiver exists because every Java source file
is below 800 lines.

## Verification Commands

Run from `D:\javaproj\advanced-order-platform`:

```powershell
.\scripts\ops-root-census.ps1 -Json
.\scripts\ops-elegance-census.ps1 -Json
.\scripts\java-maintainability-census.ps1 -Json
.\scripts\archive-retention-census.ps1 -Json
.\mvnw.cmd -B spotless:check
.\mvnw.cmd -B verify
```

The implementation push must produce green headless and Docker-tagged Actions jobs.
After those run ids are recorded, the closeout commit and tag
`v1867-order-platform-production-excellence-java-track-phase2-closeout` must also reach
the canonical `javaproject` remote and its closeout workflow must be green.

Final repaired local result: `mvnw -B verify` passed in 547.5 seconds with 1,915 tests,
zero failures/errors/skips, 2,229 JaCoCo classes and every raised floor met.
SpotBugs reported `BugInstance=0` and `Error=0`; the packaged jar was produced.

Current v1870 local result: the repaired `mvnw -B verify` run wrote 829 current
Surefire reports covering 1,926 tests with zero failures, errors, or skips. The
JaCoCo plugin analyzed 2,230 classes and every configured floor passed before the build
produced the executable jar. SpotBugs then reported zero `BugInstance` findings.

Current v1871 local result: `mvnw -B verify` passed 1,929 tests in 12:40 with zero
failures, errors, or skips. JaCoCo analyzed 2,230 classes and met every floor; SpotBugs
reported `BugInstance=0` and `Error=0`, and the executable jar was produced.

Current v1872 local result: `mvnw -B verify` passed 1,931 tests in 11:57 with zero
failures, errors, or skips. JaCoCo analyzed 2,231 classes and met every floor; SpotBugs
reported `BugInstance=0` and `Error=0`, and the executable jar was produced. Implementation
Actions run `29693892214` passed Docker-tagged integration tests in 2:01 and headless
regression in 19:07 for commit `790b8abb`. Closeout run `29694632205` then passed Docker
in 2:12 and headless in 19:26 for commit `45c0215c`.

Current v1873 local result: the archive-digest behavior, controller, structural, engine,
and census selection passed 21/21. The first complete run executed 1,938 tests and found
only the stale v1866 exact-total pin after legitimate deletion; its `<=1346` repair passed
18/18. The full rerun then passed all 1,938 tests in 13:52. After tightening the exact
long-name aggregate to the v1873 census, the final complete rerun passed 1,939 tests with
zero failures, errors, or skips. JaCoCo analyzed 2,225 classes and met every floor; SpotBugs reported
`BugInstance=0` and `Error=0`, and the executable jar was produced. Implementation
commit `ccd1ca8a` then passed canonical Actions run `29723306983`: Docker-tagged tests
in 2:09 and headless regression in 18:35, including the production-profile smoke and
JaCoCo upload.

Current v1874 local result: the exact nine-section Markdown oracle passed once against the
legacy implementation and again after convergence. Family behavior, controller aggregate,
v1846/v1866 structural gates, and the shared engine first passed 25/25; the expanded
elegance/change/archive/walkthrough/closeout selection passed 47/47. The complete
`mvnw -B verify` passed 1,940 current tests in 16:20 with zero failures, errors, or skips.
JaCoCo analyzed 2,215 classes and met every floor; SpotBugs reported `BugInstance=0` and
`Error=0`, and the executable jar was produced. The census records 1,336 ops files,
106 renderers / 5,032 lines / 102 long renderer filenames, and exact long-name aggregates
1278/21063/2837 for production plus 793/10206/3831 for tests. Implementation commit
`912820c1` passed canonical Actions run `29727976943`: Docker-tagged tests in 2:27 and
headless regression in 18:26, including the production-profile smoke and JaCoCo upload.
Closeout commit `713393ba` then passed run `29729195166`: Docker in 1:48 and headless
in 19:18. Annotated tag `v1874-order-platform-consumer-renderer-engine` is canonical.

Current v1875 local result: the exact ten-section / 51-line
Markdown oracle passed once against the legacy implementation and again after convergence.
Dossier behavior, controller aggregate, and the shared `mapped` engine first passed 17/17;
the expanded v1847/v1866 structure, elegance, change, and census selection passed 43/43,
then the wider downstream/archive/docs selection passed 79/79. The final
`mvnw -B verify` passed 1,943 tests in 17:50 with zero failures, errors, or skips.
JaCoCo analyzed 2,204 classes and met every floor; SpotBugs reported `BugInstance=0`
and `Error=0`, and the executable jar was produced.
The census records 1,325 ops files, 96 renderers / 4,809 lines / 91 long renderer filenames,
and exact long-name aggregates 1266/20996/2825 for production plus 792/10189/3830 for
tests.

Implementation commit `93f7d6b8` then passed canonical Actions run `29733600319`:
Docker-tagged tests in 2:13 and headless regression in 19:06, including the production
profile smoke and JaCoCo upload. Closeout commit `2bad2a5e` passed run `29734915814`:
Docker in 2:22 and headless in 18:20. Annotated tag
`v1875-order-platform-dossier-renderer-engine` is canonical.

Current v1876 local result: the exact ten-section / 56-line
Markdown oracle passed once against the legacy implementation and again after convergence.
Release-acceptance behavior, controller, shared engine, and downstream archive checks
passed 29/29; the expanded v1847/v1848/v1866 structure, elegance, change, and census
selection passed 62/62, then the wider archive/walkthrough/docs selection passed 77/77.
The census records 1,314 ops files, 86 renderers / 4,586 lines /
80 long renderer filenames, and exact long-name aggregates 1254/20929/2813 for production
plus 791/10171/3829 for tests. Full Maven verification passed 1,945 tests in 20:39 with
zero failures/errors/skips, 2,193 JaCoCo classes/all floors, SpotBugs 0/0, and a packaged
jar. The implementation Actions evidence follows; only closeout and tag remain pending.

Implementation commit `52e4c7c9` passed canonical Actions run `29739016977`: the
Docker-tagged job completed in 2:06 and the headless job in 18:15, including wrapper
verification, production-profile boot smoke, and JaCoCo upload. The closeout evidence
follows.

Closeout commit `e82edaa7` passed run `29740214540`: Docker in 2:09 and headless in
14:08. Annotated tag `v1876-order-platform-release-acceptance-renderer-engine` fixes
the canonical version boundary.

Current v1877 local result: the exact nine-section / 57-line
Markdown oracle passed once against the legacy implementation and again after convergence.
Archive behavior, both root controllers, shared engine consumers, and downstream handoff
checks join the v1847-v1849/v1866 structure, elegance, change, and census gates in a
65/65 core selection; the wider archive/walkthrough/closeout/docs selection passed 80/80.
The census records 1,304 ops files, 77 renderers / 4,376 lines / 70 long renderer
filenames, and exact long-name aggregates 1243/20851/2802 for production plus
790/10156/3828 for tests. Full Maven verification passed 1,947 tests in 17:10 with
zero failures/errors/skips, 2,183 JaCoCo classes/all floors, SpotBugs 0/0, and a
packaged jar. Implementation Actions is recorded below; closeout remains green-tag gated.

Implementation commit `b4a84326` passed canonical Actions run `29746619649`: the
Docker-tagged job completed in 2:03 and the headless job in 19:06, including Spotless,
wrapper verification, production-profile boot smoke, and JaCoCo upload. Annotated tag
`v1877-order-platform-archive-registry-renderer-engine` is the deterministic completion
boundary: it may point at this closeout evidence update only after that update's own
canonical Actions succeeds.

Current v1878 local result: the exact ten-section / 67-line Markdown oracle passed
once against the legacy implementation and again after convergence without changing its
expectations. Handoff behavior, both controllers, downstream route-path-split, v1847-v1850
and v1866 structure, elegance, change, and census gates pass 68/68. The census records
1,293 ops files, 67 renderers / 4,211 lines / 59 long renderer filenames, with exact
long-name aggregates 1231/20765/2790 for production and 789/10139/3827 for tests. The
4,541-Han walkthrough and exact 1,689-file archive set are complete before final verify;
the wider evidence selection passes 86/86. Full Maven verification passes 1,949 tests
in 9:16 with zero failures/errors/skips. JaCoCo analyzes 2,172 classes and every floor
passes; SpotBugs reports 0 bugs / 0 errors, and the executable jar is packaged. The
closeout remote run is green.

Implementation commit `57ba6fd2` passes canonical Actions run `29753510453`:
Docker-tagged Maven verification completes in 1:59 and headless wrapper verify in
17:42, followed by the production-profile boot smoke and JaCoCo upload. Closeout commit
`fd3c0cc1` passes run `29755253175`: Docker in 2:27 and headless in 17:48. Annotated
tag `v1878-order-platform-archive-handoff-renderer-engine` is canonical.

Current v1879 candidate: the same three exact Markdown oracles passed 3/3 before and
after replacement, freezing the nine-section / 47-line report, seven-line receipt, and
five-section / 22-line archive index. Behavior, controller, v1842/v1847-v1850/v1866
structure, elegance, change, and census gates pass 71/71. The census records 1,283 ops
files, 58 renderers / 3,973 lines / 47 long renderer filenames, and exact long-name
aggregates 1218/20696/2777 for production plus 786/10116/3822 for tests. The 4,430-Han
walkthrough and exact 1,690-file archive set were complete before final verify. Full
Maven verification passes 1,953 tests in 14:02 with zero failures/errors/skips; JaCoCo
analyzes 2,162 classes with every floor met, SpotBugs reports 0/0, and the executable
jar is packaged. Implementation commit `b5366eb1` passes canonical Actions run
`29759922474`: Docker-tagged verification completes in 1:51 and headless regression
in 19:15, including the production-profile smoke and JaCoCo upload. Closeout commit
`5205246d` passes run `29761487591`: Docker in 2:03 and headless in 19:22. Annotated
tag `v1879-order-platform-acceptance-package-renderers` is canonical.

Current v1880 candidate: the exact handoff and archive Markdown oracles passed 2/2
against the old implementation before deletion and again unchanged after convergence,
freezing five sections / 33 lines and six sections / 36 lines. Core behavior, direct
consumer, v1844/v1866 structure, exact-name, change, and census gates pass 87/87. The
census records 1,274 ops files, 51 renderers / 3,816 lines / 38 long renderer filenames,
with production long-name aggregates 1207/20627/2766 and test script aggregates
784/10091/3818. The 3,273-Han walkthrough and exact 1,691-file archive set were complete
before final verify. Full Maven verification passes 1,956 tests in 10:26 with zero
failures/errors/skips; JaCoCo analyzes 2,153 classes with every floor met, SpotBugs
reports 0/0, and the executable jar is packaged. Implementation commit `179e6609`
passes canonical Actions run `29792136907`: Docker-tagged verification completes in
2:09 and headless regression in 19:17, including the production-profile smoke and
JaCoCo upload. Closeout commit `d9fc4c84` passes run `29793217972`: Docker in 2:21
and headless in 18:56. Annotated tag
`v1880-order-platform-operator-ci-handoff-renderers` is canonical.

Current v1881 candidate: the same exact Markdown oracle passes 2/2 before deletion
and 2/2 after convergence, freezing the execution report at six sections / 40 lines
and archive verification at six sections / 41 lines. The new ordered grouping engine
passes encounter-order and immutability checks. The census records 1,266 ops files,
45 renderers / 3,616 lines / 30 long renderer filenames, with production long-name
aggregates 1197/20544/2756 and test script aggregates 782/10063/3812. The 3,418-Han
walkthrough and exact 1,692-file archive set are complete before final verify. The
expanded behavior, downstream, history, elegance, and docs selection passes 179/179.
Full Maven verification passes 1,960 tests in 9:25 with zero failures/errors/skips;
JaCoCo analyzes 2,145 classes with every floor met, SpotBugs reports 0/0, and the
executable jar is packaged. Implementation commit `7ec4f2ba` passes canonical Actions
run `29795818326`: Docker in 2:06 and headless in 19:28, including the production-profile
smoke and JaCoCo upload. Closeout commit `f0db6641` passes run `29796788347`: Docker in
2:27 and headless in 18:26. Annotated tag
`v1881-order-platform-gate-execution-renderers` is canonical.

Current v1882 candidate: one 7-section / 38-line oracle passes 3/3 against the old
sustainment renderers and again unchanged against `ReportRenderer`. The family shrinks
19 to 11 files; ops shrinks to 1,258 files; renderers fall to 38 / 3,521 lines / 22 long
filenames. Production long-name aggregates are 1188/20495/2747; test script aggregates
are 776/10039/3801. Six long test responsibilities become short names, the route test
now pins a real suffix instead of comparing a constant with itself, and the exact name
baseline has 35 removals with no additions. Behavior/downstream/structure gates pass
27/27 and the naming/change/history/oracle selection passes 28/28. The 3,391-Han,
10-heading walkthrough and exact 1,693-file / 20,076,290-byte archive set are complete
before final verify. The expanded history/elegance/change/walkthrough/archive/closeout/
README selection passes 111/111. Full Maven verification passes 1,963 tests in 12:10
with zero failures, errors, or skips. JaCoCo analyzes 2,137 classes with every floor met,
SpotBugs reports 0 bugs / 0 errors, and the executable jar is packaged. Initial commit `4ced994e`
reached successful Docker verification in run `29799487464`, while headless correctly
failed the exact prior-commit Spotless ratchet on mixed line endings and one formatting
fold. Repair commit `d525524b` passes the same ratchet locally; canonical run
`29799705965` then passes Docker in 2:03 and headless in 19:50, including full verify,
production-profile smoke, and JaCoCo upload. Closeout commit `5ebe1c06` passes run
`29800790309`: Docker in 1:54 and headless in 18:12. Annotated tag
`v1882-order-platform-sustainment-renderer` is canonical.

Current v1883 candidate: five public compatibility types remain unchanged while nineteen
long internal shells become twelve short domain owners and seven forwarding files disappear.
The same focused set passes 19/19 against the old and new implementations, freezing the
registry at six sections / 43 lines and closeout at three sections / 15 lines. Ops shrinks
to 1,251 files; renderers fall to 32 / 3,448 lines / 14 long filenames. Production name
aggregates are 1169/20376/2728 and test aggregates are 764/9999/3783; the exact baseline
has 66 removals and no additions. Expanded gates pass 119/119 after one file-list sort
assumption is corrected. The 3,492-Han, 10-heading walkthrough and exact 1,694-file /
20,092,216-byte archive set precede final verify. `scripts/verify-release.ps1` resolves
the v1882 tag to peeled commit `5ebe1c06`, runs exact Spotless, safely preserves native
stderr while judging Maven by exit code, and completes full verification: 1,968 tests in
8:29, JaCoCo 2,130/all floors, SpotBugs 0/0, and jar packaging. The first final run
correctly rejected a non-canonical `Family design` note; the corrected gate passes 3/3
before the successful full rerun. Implementation commit `b5cae273` passes Actions run
`29807996922`: Docker-tagged tests in 2:02 and headless regression in 19:46, including
prod smoke and JaCoCo upload. v1883 closeout Actions and the annotated tag remain binding
gates. A pre-tag audit then catches the renderer-line ratchet still at 3,451 despite the
3,448 census; run `29809261863` is canceled before it can become closeout evidence. The
cap is tightened and the repair release gate passes all 1,968 tests in 8:49, JaCoCo
2,130/all floors, SpotBugs 0/0, and jar packaging before the final closeout run.

Initial implementation Actions run `29220274738` passed Docker in 2:18 and failed
headless at `ArchiveRetentionTests` because raw text hashes differed between Windows
CRLF and Linux LF. The repair canonicalizes line endings for text hash input only and
has a dedicated LF/CRLF equivalence test. Repair Actions run `29221687479` passed:
Docker-tagged integration tests in 2:21 and headless regression in 18:54, including
Spotless, the full wrapper verify, every raised coverage floor, SpotBugs, the production
profile smoke test, and JaCoCo upload. Closeout Actions run `29222696374` then passed
Docker-tagged integration tests in 2:05 and headless regression in 18:38 for commit
`952e4ab9`, which is the peeled target of the canonical v1867 tag. External review
remains the only ungranted Java-track state.

## External Review Request

The reviewer must independently rerun the three census commands, inspect every waiver,
run a fresh build/verify, confirm both remote workflow runs and tag reachability, and
rerun the Node one-command live capstone against the final Java tag. Until that review
passes, candidate is the strongest permitted status.
