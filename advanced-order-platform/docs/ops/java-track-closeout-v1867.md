# Java Track Closeout v1867

Status: design fixed before implementation. External review remains required.

## Family Design Note

- `OpsEvidenceContractTestSupport` owns scenario setup; two short tests own assertions.
- `ReleaseApprovalRehearsalTestSupport` owns rehearsal setup; hint cases stay data-only.
- `JavaSourceNames` owns lexical scanning; the elegance test owns shrink-only budgets.
- `archive-retention-manifest.txt` owns immutable data; its test owns hash verification.
- `OpsShardReadinessRoutePaths` keeps used aliases only; leaf route owners keep route bytes.
- Production behavior, HTTP mappings, response values, fixtures, and write boundaries do not move.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Preserve the Phase 1 end state | Keep exact root `104/104`, movable `0`, unassigned `0` | `ops-root-census.ps1` plus existing root ratchets | local gate green |
| Remove route forwarding | Repoint 735 reads in 160 files to leaf owners, delete 239 pure aliases, and retain the 12-field ReleaseAcceptance compatibility proof | exact field/readers gate, v1840 root-versus-leaf guard, and complete compile | local gate green |
| Keep route bytes exact | Leave all leaf route owners and controller mappings unchanged | existing route and HTTP contract suite | focused gate green |
| Remove source hotspots | Reduce production and test maxima below 800 lines | maintainability census and tightened aggregate caps | local gate green |
| Preserve evidence tests | Keep every existing assertion and the named `OpsEvidenceServiceTests` entry | focused old/new contract runs plus manifest command guard | focused gate green |
| Enforce the name budget | Count long file stems and lexical Java identifiers | script census plus shrink-only JUnit baseline | local gate green |
| Close archive retention | Index binary bytes and canonical cross-platform text with SHA-256 | exact manifest, count/raw-byte caps, and hash test | repaired local verify green |
| Refresh CI actions | Use current official action majors and reject the deprecated v4 set | workflow structure test and real Actions run | local gate green; remote pending |
| Tighten coverage floors | Raise floors whose observed baseline exceeds the old floor by over two points | POM assertions and full JaCoCo check | full local verify green |
| Close E1-E10 evidence | Refresh readiness, changelog, coverage, waivers, and final evidence | `JavaTrackCloseoutTests` | full local verify green; remote pending |
| Explain without padding | Chinese-majority walkthrough with ten standard headings and at least 3,000 Han | global walkthrough gate | frozen at 4,289 Han and ten headings |

## Scope

This version is internal governance only. A multiline-proof census showed that
all 266 root route fields had readers, invalidating the initial dead-alias plan.
The implementation instead repoints 735 reads in 160 files to their leaf owners,
then removes 239 pure forwarding aliases. It keeps 15 root-owned literals and 12
ReleaseAcceptance compatibility aliases whose catalog compares the stable root surface
with the leaf owner rather than comparing the leaf owner with itself. It also splits the
two remaining test files above 800 lines around shared setup instead of copying
fixtures.

The maintainability census gains name metrics, and the final archive set gains
an exact SHA-256 manifest. The CI workflow moves from deprecated action majors
to the latest official majors verified on 2026-07-13: checkout 7, setup-java 5,
and upload-artifact 7.

No order, inventory, payment, Outbox, failed-event, evidence, credential,
managed-audit, SQL, deployment, rollback, mini-kv, or Node runtime behavior is
opened or changed.

## Failure Conditions

- Root `ops` is not exactly 104 files or any movable/unassigned file returns.
- A removed root alias still has a qualified main/test reader after migration.
- A remaining root alias has no qualified reader.
- Any controller mapping, leaf route constant, response assertion, or fixture byte changes.
- Production or test Java has a file above 800 lines.
- A new or touched identifier/file stem exceeds 40 characters.
- The long-name, file-size, SpotBugs, coverage, or archive ratchet is loosened.
- Any frozen archive path is missing, added without indexing, or hash-modified.
- The walkthrough is written or expanded after final verify.
- The final evidence calls the track final before external review.

## Verification Order

1. Apply route pruning and test splits; compile production and tests.
2. Run exact route-reader, assertion-preservation, name, size, archive, and docs gates.
3. Run Spotless and regenerate all censuses with committed scripts.
4. Write and freeze the Chinese walkthrough and final evidence candidate.
5. Run one complete `mvnw -B verify` with raised coverage floors.
6. Push implementation and require both CI jobs green.
7. Record the run, tag the closeout, push once, and require closeout CI green.
8. Stop at the Java track final-review checkpoint for Claude.

## Local Verification Result

The final repaired local `mvnw -B verify` passed in 547.5 seconds: 1,915 tests, zero
failures/errors/skips; JaCoCo analyzed 2,229 classes and met every raised floor;
SpotBugs reported zero bugs and zero errors. The root census is 104/104/0 with
zero unassigned files. Archive retention is 1,678 files / 19,819,450 raw bytes.
Remote implementation and closeout CI remain required before external review.

Initial implementation run `29220274738` passed Docker in 2:18 but failed headless
because raw text hashes differed between Windows CRLF and Linux LF checkouts. The repair
canonicalizes CRLF to LF for `.md/.json/.html` hash input only, keeps PNG raw-byte hashes,
and adds a direct cross-platform line-ending test. The full local verify above was rerun
after the walkthrough and manifest were regenerated.
