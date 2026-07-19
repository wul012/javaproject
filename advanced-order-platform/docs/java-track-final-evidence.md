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
| E1 Build & CI | Parent `.github/workflows/maven-ci.yml`; Maven wrapper; headless and optional Docker jobs | `JavaTrackCloseoutTests.workflowUsesCurrentActions`; real Actions runs | implementation `29221687479` and closeout `29222696374` green |
| E2 Static analysis | Spotless and SpotBugs checks; 682 exact pattern/class identities with secure Git-prior ratchet | Maven verify plus `SpotBugsWaiverTests` | v1871 full local verify green; SpotBugs 0/0 |
| E3 Coverage | JaCoCo global and ten package rules; v1867 floors raised in `pom.xml` | `JavaTrackCloseoutTests.coverageFloorsStayRaised`; JaCoCo check | current local verify green; 2230 classes |
| E4 Security & config | Safe prod profile and threat model in `PRODUCTION_READINESS.md` | existing profile/config tests plus `JavaTrackCloseoutTests.securityBoundaryStaysExplicit` | local full verify green |
| E5 Observability | health/info/metrics, tracing, correlated exception logs | `ActuatorHealthIntegrationTests`, `ApiExceptionTraceIntegrationTests`, `ObservabilityConfigurationTests` | existing suite evidence |
| E6 Error handling | graceful shutdown, finite shutdown timeout, typed API errors and guarded replay | prod smoke, exception tests, failed-event approval/readiness tests | existing suite evidence |
| E7 Docs honesty | README and production boundary use the authorized exact maturity label and list non-authorized capabilities | `ProductionReadinessDocumentationTests` and closeout docs gate | local full verify green |
| E8 Release discipline | `CHANGELOG.md`, git-tag policy, progress ledger, implementation/closeout commits | `JavaTrackCloseoutTests.docsAndReleaseStayHonest`; external git/CI check | tag and canonical remote verified; external review pending |
| E9 Code health | root 104/104/0, no Java file above 750 lines, route owner 27 fields/69 lines, exact name baseline plus changed-file gate | extraction, maintainability, elegance, and HTTP-boundary ratchets | v1870 full local verify green |
| E10 Archive retention | exact SHA-256 manifest, count/raw-byte ceilings, frozen archive policy; CRLF is canonicalized to LF for text only | `ArchiveRetentionTests` and `archive-retention-census.ps1` | v1870-authorized exact set: 1681 files / 19864889 raw bytes |

## Final Censuses

- Ops direct root: 104 files; retained: 104; remaining movable: 0; unassigned: 0.
- Ops total production Java files: 1,352; no extraction family remains in root.
- Production Java: 1,483 files; maximum 738 lines; over 500: 32; over 750/1000: 0/0.
- Test Java: 887 files; maximum 699 lines; over 500: 8;
  over 750/1000: 0/0.
- Long-name shrink-only baseline: production stems/uses/unique 1297/21167/2856;
  tests 795/10225/3833. New and touched names remain within 40 characters.
- Exact long-name identities are frozen in `config/java-name-baseline.txt`; v1869 adds
  Git-aware tests that reject new names, dirty-tree blind spots, oversized feature-source
  growth, and undocumented three-file families.
- Root route owner: 15 owned literals plus 12 ReleaseAcceptance compatibility aliases.
  v1867 repointed 735 reads in 160 files to leaf owners and removed 239 pure forwarding
  aliases without changing route bytes or the root-versus-leaf compatibility response.
- SpotBugs exclusions: 682 exact pattern/class identities; only deletion is allowed, and
  every retained class must load from the compiled classpath.
- Archive retention: v1871's user-authorized optimization adds exactly one walkthrough;
  the current exact set is 1,682 files / 19,878,770 raw bytes.

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
