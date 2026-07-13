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
| E1 Build & CI | Parent `.github/workflows/maven-ci.yml`; Maven wrapper; headless and optional Docker jobs | `JavaTrackCloseoutTests.workflowUsesCurrentActions`; real Actions runs | local verify green; remote pending |
| E2 Static analysis | Spotless ratchet and SpotBugs check in `pom.xml`; 686-entry shrink-only exclusion baseline | Maven verify plus `JavaEleganceGateTests.spotbugsBaselineOnlyShrinks` | local verify green; SpotBugs 0/0 |
| E3 Coverage | JaCoCo global and ten package rules; v1867 floors raised in `pom.xml` | `JavaTrackCloseoutTests.coverageFloorsStayRaised`; JaCoCo check | local verify green; 2229 classes |
| E4 Security & config | Safe prod profile and threat model in `PRODUCTION_READINESS.md` | existing profile/config tests plus `JavaTrackCloseoutTests.securityBoundaryStaysExplicit` | local docs gate pending |
| E5 Observability | health/info/metrics, tracing, correlated exception logs | `ActuatorHealthIntegrationTests`, `ApiExceptionTraceIntegrationTests`, `ObservabilityConfigurationTests` | existing suite evidence |
| E6 Error handling | graceful shutdown, finite shutdown timeout, typed API errors and guarded replay | prod smoke, exception tests, failed-event approval/readiness tests | existing suite evidence |
| E7 Docs honesty | README and production boundary use the authorized exact maturity label and list non-authorized capabilities | `ProductionReadinessDocumentationTests` and closeout docs gate | local docs gate pending |
| E8 Release discipline | `CHANGELOG.md`, git-tag policy, progress ledger, implementation/closeout commits | `JavaTrackCloseoutTests.docsAndReleaseStayHonest`; external git/CI check | tag and remote pending |
| E9 Code health | root 104/104/0, no Java file above 750 lines, route owner 27 fields/69 lines, name census | extraction, maintainability, and elegance ratchets | local focused gate green |
| E10 Archive retention | exact SHA-256 manifest, count/byte ceilings, frozen archive policy | `ArchiveRetentionTests` and `archive-retention-census.ps1` | local gate green: 1678 files / 19819092 bytes |

## Final Censuses

- Ops direct root: 104 files; retained: 104; remaining movable: 0; unassigned: 0.
- Ops total production Java files: 1,352; no extraction family remains in root.
- Production Java: 1,483 files; maximum 738 lines; over 500: 32; over 750/1000: 0/0.
- Test Java: 882 files; maximum 699 lines; over 500: 8;
  over 750/1000: 0/0.
- Long-name shrink-only baseline: production stems/uses/unique 1297/21169/2857;
  tests 795/10226/3834. New and touched names remain within 40 characters.
- Root route owner: 15 owned literals plus 12 ReleaseAcceptance compatibility aliases.
  v1867 repointed 735 reads in 160 files to leaf owners and removed 239 pure forwarding
  aliases without changing route bytes or the root-versus-leaf compatibility response.
- SpotBugs exclusions: at most 686 `<Match>` blocks and shrink-only.

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

Final local result: `mvnw -B verify` passed in 584.7 seconds with 1,914 tests,
zero failures/errors/skips, 2,229 JaCoCo classes and every raised floor met.
SpotBugs reported `BugInstance=0` and `Error=0`; the packaged jar was produced.

## External Review Request

The reviewer must independently rerun the three census commands, inspect every waiver,
run a fresh build/verify, confirm both remote workflow runs and tag reachability, and
rerun the Node one-command live capstone against the final Java tag. Until that review
passes, candidate is the strongest permitted status.
