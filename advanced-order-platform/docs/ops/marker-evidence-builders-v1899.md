# v1899 Marker Evidence Builders

## Family design

- Abstraction: `MarkerEvidence` owns immutable verification metadata shared by marker builders.
- Data boundary: short data owners retain domain constants while public response records stay unchanged.
- Behavior boundary: short rule owners build and validate domain records; builders only orchestrate output.
- Compatibility boundary: public route, Request/Response FQNs, JSON, digests and list order stay frozen.
- Safety boundary: credential, connection, write, migration, deployment and auto-start authority stay denied.
- Migration boundary: only three consecutive marker builders adopt the pattern in this version.
- Failure boundary: either response SHA, any short-owner cap or a fail-closed proof drifting rejects the cut.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Freeze the public contract first | default and header-backed response snapshots exclude only two clock fields | sorted-property full JSON SHA-256 oracle | old and new implementations match |
| Remove three giant owners | three package-private builders become short builder/rules pairs | exact retired-file and per-owner line caps | implemented |
| Share only real repetition | `MarkerEvidence` owns four immutable metadata lists and warning formatting | alias-isolation unit test and structure gate | implemented |
| Preserve domain typing | boundary projection and safety predicates remain in typed domain owners | compiler plus existing warning/no-write tests | implemented |
| Pay back the split budget | four terminal receipt wrappers with no owned behavior retire | unchanged oracle plus deleted-owner gate | implemented |
| Tighten maintainability | global maximum and over-500 budgets decrease | reproducible census and exact caps | implemented |
| Tighten naming | three long files/types and long receipt-chain accessors retire | aggregate and exact name baselines | implemented |
| Preserve archives | add one Chinese walkthrough without moving history | exact manifest, count and raw-byte cap | implemented |
| Close the release chain | implementation, closeout, annotated tag and receipt each receive canonical CI | local release gate plus GitHub runs | release chain closed; receipt CI pending |

## Baseline And Scope

The pre-change `releaseapproval` package contained 119 production Java files and 38,879 lines.
The selected package-private monoliths were 738-line decision marker, 726-line disabled precheck
and 590-line endpoint preflight builders. The package exposed the same four verification metadata
getters across 38 files, while the three selected builders had only package-internal type readers.

The cut preserves `OpsOverviewController`, `OpsEvidenceService`,
`ReleaseApprovalRehearsalRequest`, `ReleaseApprovalRehearsalResponse`, all nested public record
FQNs, route constants, transaction boundaries and upstream Node/mini-kv contract values. It does
not rename either public response-record owner, execute a sandbox connection, read a credential,
write an approval ledger or modify another repository.

## Frozen Compatibility Oracle

`RehearsalResponseOracleTests` was introduced against released v1898 with zero digests. The old
implementation produced:

- default request: `48dc64dd2385de0ad0b98f114be157c98b19012abcfde8384ff6e237248b8550`;
- full header-backed request: `c64e2fac8194ab2f70ef5bbd603a9a92dd0ea1a9ae75459f386c7fa6373258cc`.

The snapshot registers Jackson Java Time support and removes only `sampledAt` and
`liveReadinessHint.serverTimestamp`, the two values sourced from the request-time clock. Every
other scalar, nested record, list order, digest, warning, proof claim, action, blocker and
execution boundary remains in the hash. The fixed old implementation passed twice before source
replacement; the refactored implementation passes the same two hashes.

## Architecture

`ReceiptChain` now names the three internal collaborators `EndpointPreflightBuilder`,
`DecisionMarkerBuilder` and `DisabledPrecheckBuilder`. Each builder owns response orchestration,
verification metadata, warning/boundary line exposure and the final typed record constructor.
`EndpointPreflightRules`, `DecisionMarkerRules` and `DisabledPrecheckRules` own static domain data,
source acceptance, typed subrecord construction and fail-closed predicates.

`MarkerEvidence` contains one warning input name plus immutable boundary names, proof claims and
Node actions. `List.copyOf` isolates all list aliases; warning input and warning line formatting
delegate to the existing canonical digest support. The verification-hint contribution catalog
accepts this typed snapshot for the three migrated builders while its supplier overload remains
for untouched builders. Domain boundary lines and safety predicates deliberately stay typed and
are not converted into maps or callback matrices.

Four terminal 58-line receipt builders owned no state, rule or transformation: each method merely
forwarded to its matching Support. They now retire as one coherent contract-tail slice. The
receipt chain calls each typed Support build method directly; contribution, warning-digest and
no-write consumers call the same Support metadata and proof methods directly. No generic
reflection bridge replaces them, and the response oracle covers every resulting receipt.

## Mechanical Result

- Selected monoliths: `738/726/590` lines become builder/rules pairs
  `276+460`, `237+489`, and `291+299`, plus 26-line shared `MarkerEvidence`.
- Every new owner is below 500 lines; three old monoliths and four pass-through wrappers are absent.
- `releaseapproval`: files stay `119`; lines tighten `38,879 -> 38,570`.
- Production Java stays `1,293`; ops Java stays `1,161`; tests: `909 -> 912`.
- Production maximum: `738 -> 658`; files over 500 lines: `32 -> 29`.
- Catalogs stay 243; Services stay 375; Renderers stay `30 / 3,176 lines / 0 long names`.
- Production names: `1044/19346/2603 -> 1037/19155/2589`.
- Test names: `680/9763/3641 -> 680/9755/3633`.
- Exact name baseline: 22 removals, 0 additions.
- Walkthrough: 3,803 Han, exactly 10 headings, 20,211 raw bytes.
- Archive: `1,709 / 20,354,150 -> 1,710 / 20,374,361`.

The explicit behavior/data boundaries are paid for by deleting one-shot wrappers in the same
package, so neither production nor ops file count grows. Package lines fall by 309 while global
hotspot and naming ratchets tighten at the same time; the split cannot become permission for
uncontrolled file growth.

## Test Ownership

`RehearsalResponseOracleTests` owns full public-output compatibility. `MarkerEvidenceTests` owns
defensive copies and canonical warning formatting. `MarkerBuilderArchitectureTests` owns seven
short-file caps, shared-evidence use and permanent retirement of all seven obsolete owners.
`OpsExtractionV1854Tests` replaces its obsolete all-files-share-a-prefix assumption with exact
short production/test owner inventories while keeping the original public-composition checks.

`JavaMaintainabilityBudgetTests`, `JavaEleganceGateTests`, exact name baseline,
`ArchiveRetentionTests`, walkthrough gates and existing release-approval behavior tests remain
independent global controls. The focused oracle, shared-evidence, architecture, history,
warning-digest, maintainability, elegance, walkthrough and archive selections pass locally.

## Validation Status

Compilation, Spotless formatting, the double response oracle and all focused gates are green.
After this design record and Chinese walkthrough existed, `scripts/verify-release.ps1` peeled
`v1898-order-platform-acceptance-package-catalog` to closeout
`78cac4e90557b599692608a40dce183e94c42424` and passed 2,038 tests in 9:21. JaCoCo
analyzed 2,059 classes and met every floor, SpotBugs reported 0 bugs / 0 errors, the production
smoke passed, and the packaged jar is 67,940,843 bytes. Implementation
`f1980b416c9e3258eda033c7da55e9fa05e67525` passes canonical run `30019562919`:
Docker completes in 2:02 with a 1:52 wrapper verify; headless completes in 19:07 with an
18:22 wrapper verify, 0:12 production smoke, and 0:03 JaCoCo upload. Closeout
`7c171c6ccae2b7b037a7224cc98a6a2537aad416` passes run `30021301566`: Docker
completes in 1:56 with a 1:46 wrapper verify; headless completes in 16:16 with a 15:31
wrapper verify, 0:11 production smoke, and 0:03 JaCoCo upload. Annotated tag
`v1899-order-platform-marker-evidence-builders` peels to that closeout locally and on
`javaproject`. This pure-document receipt still requires its own canonical CI.

## Failure Conditions

- Either frozen response SHA changes, even when focused field assertions remain green.
- Public route, record FQN, component order, digest input, warning, proof, action or blocker drifts.
- A retired monolith, terminal pass-through wrapper or any long internal accessor returns.
- Any new owner exceeds its exact cap, production max exceeds 658, or over-500 count exceeds 29.
- `MarkerEvidence` loses defensive copies or begins deciding domain readiness/authority.
- Typed boundary projection is replaced by `Map<String, Object>`, reflection or unbounded callbacks.
- Credential, raw endpoint, network, SQL, ledger, migration, deployment, rollback or auto-start opens.
- Exact name baseline, archive manifest, fixture, oracle digest or test expectation is loosened.
- Local release verification, canonical CI, tag push or peeled-SHA audit fails.
