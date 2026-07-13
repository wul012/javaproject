# README Exhibition v1868

Status: implementation candidate. This maintenance version changes discoverability and
evidence presentation only; it does not add runtime capability or production authority.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Make the GitHub landing useful in 30 seconds | Root `README.md` with bilingual hero, badges, evidence table, Mermaid flow, and deep-doc pointers | `ReadmeExhibitionTests.rootPageLinksEveryClaim` | implemented locally |
| Keep every headline number sourced | Link committed census scripts and final evidence; recompute root and source metrics in Java | `ReadmeExhibitionTests.headlineNumbersMatchTree` | implemented locally |
| Preserve the deep technical README | Add badges and four self-verification commands without moving pinned boundary prose | existing docs tests plus `deepDiveListsReproCommands` | implemented locally |
| Reconcile v1867 reality | Record closeout run `29222696374`, remote tag reachability, and the still-required external review | closeout docs gates and git/Actions evidence | implemented locally |
| Preserve frozen archives | Add only the authorized v1868 walkthrough and regenerate the exact manifest | `ArchiveRetentionTests` plus archive census | implemented: 1679 files / 19834662 raw bytes |

## Boundaries

- No controller, service, route, response, fixture, schema, profile, or runtime setting changes.
- The exact maturity label and all no-authority statements remain verbatim.
- Badges describe reproducible floors or measured facts, not production authorization.
- GitHub social-preview upload remains a manual repository-setting follow-up.

## Failure Conditions

- A pinned documentation assertion is weakened or rewritten to accommodate the exhibit.
- A badge or table number lacks a committed source and a failing consistency check.
- The new root README omits either the authority boundary or the deep-project pointer.
- Archive growth exceeds the one walkthrough explicitly authorized by the brief.
- Local full verify or either remote CI job is red.

## Verification

The focused gate passed 24 tests with zero failures/errors/skips: README claims,
production documentation, Java-track closeout, elegance, maintainability, walkthrough,
and archive retention. The walkthrough is frozen at 3,483 Han characters and exactly
ten standard headings. Current censuses report root 104/104/0 with zero unassigned,
production maximum 738 lines with zero files over 750/1000, and an exact archive set of
1,679 files / 19,834,662 raw bytes. Full `mvnw -B verify` passed 1,918 tests with zero
failures/errors/skips in 15:40; JaCoCo analyzed 2,229 classes and met every floor,
SpotBugs reported zero bugs/errors, and the packaged jar was produced. Implementation
commit `36aebe05` passed GitHub Actions run `29227654360`: Docker-tagged integration
tests completed in 2:02 and the headless regression completed in 13:55; Spotless, full
verify, production smoke, and JaCoCo artifact publication were green. GitHub's repository
API resolves the landing page to root `README.md`. The closeout tag and its CI remain
pending.
