# v1857 RouteCleanup read-only gate extraction

## Scope

閺堫剛澧楁潻浣盒╂禒?`OpsShardReadinessRouteCleanupReadOnlyGateService` 娑撹桨绗傞悾宀€娈戠€瑰本鏆?娓氭繆绂嗛梻顓炲瘶閵嗗倽鐨熼悽銊ユ禈鐎圭偞绁村妤€鍩?29 娑?direct-root 閻㈢喍楠囬弬鍥︽閿涙艾宕勭紒鍕箛閸?閸濆秴绨查妴?`EvidenceAnalyzer`閵嗕梗EvidenceCatalog`閵嗕梗EvidenceEntryFactory` 閸滃苯鍙氭稉顏勫瀻濞堜絻鐦夐幑?catalog閵嗗倸宕勬稉鈧紒鍕箛閸?閸掑棙鐎介崳銊︾ゴ鐠囨洟娈㈢€圭偟骞囨潻浣稿弳
`ops.maintenance.routecleanup`閿涘苯娲撴稉?Spring controller 娑撳孩鐗寸捄顖滄暠濞村鐦悾娆忔躬
composition root閵?
`EvidenceEntryFactory` 閻ㄥ嫬鏁稉鈧懕宀冪煑閹舵ê鍙?`EvidenceCatalog`閿涘苯鎮撻弮鑸垫煀婢х偟鐓崥?`RouteCleanupRoutes` 娴ｆ粈璐熼崡浣规蒋 suffix 閻ㄥ嫬鏁稉鈧?owner閵嗗倹鏌婇崠鍛矝閺?29 娑擃亞鏁撴禍褎鏋冩禒璁圭礉
閸忋劋绮?`ops` main 閺傚洣娆㈤幀缁樻殶娑撳秴顤冮崝鐘偓鍌欐崲娴?route閵嗕购esponse component閵嗕龚igest
material閵嗕公ixture/evidence byte閵嗕笭TTP method閵嗕簚rite/credential boundary閵?deployment閵嗕购ollback 閹?archive path 闁垝绗夊妤佹暭閸欐ǜ鈧?
No route string or response byte changes in this extraction.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move one compiler-closed slice | Move the exact 29-file ReadOnlyGate dependency closure to `ops.maintenance.routecleanup` | exact package/root inventory and dependency scan | local verify passed |
| Keep HTTP adapters visible | Retain Evidence, Summary, Governance, and Handoff controllers in root | controller import and mapping tests | local verify passed |
| Remove the sole root out-edge | Add `RouteCleanupRoutes` with the ten existing suffix bytes and repoint moved services/controllers | route byte inventory, old constant absence, compiler | local verify passed |
| Avoid file-count growth | Fold `EvidenceEntryFactory.entry(...)` into `EvidenceCatalog` while adding the route owner | factory absence, route-owner presence, total main count | local verify passed |
| Bound the migration API | Import the 22 actually consumed moved types into 47 remaining production consumers; expose analyzer/catalog members only when compilation proves an inbound read | exact consumer/target guard and compiler | local verify passed |
| Move behavior tests with ownership | Move eleven service/analyzer tests; retain controller-oriented tests at root | exact test inventory | local verify passed |
| Preserve static analysis | Relocate the existing 22 SpotBugs FQN mirrors for ten responses without adding exclusions | old/new exact counts and full SpotBugs | local verify passed |
| Tighten endgame ratchets | Direct root 278 -> 249; movable 174 -> 145; RouteCleanup 170 -> 141; unassigned 0 | census script and all live guards | local verify passed |
| Keep files and names maintainable | New identifiers and Java filenames stay <=40; route owner remains data-only; no giant production file | reflection/source budget guard | local verify passed |
| Explain before implementation | Chinese walkthrough >=3000 Han, Chinese-majority, ten exact headings, written before Java edits | walkthrough guard | local verify passed |

## Exact closure

The closure contains the Evidence, PhaseSummary, BoundaryMatrix,
HandoffChecklist, ArchivePlan, Digest, SourcePlanAlignment, ReleaseHandoff,
OperatorRunbook, and ReadOnlyGate service/response pairs; the analyzer and
aggregate catalog; the entry factory; and LatestSibling, ReadinessSeed,
HandoffCore, HandoffAssurance, HandoffGovernance, and PostCompletion catalogs.
It has no dependency on another direct-root RouteCleanup implementation.

The remaining root web has 92 type-reference edges into 22 moved boundary
types from 47 source files. Forty-two sources read `EvidenceAnalyzer`; that
class is therefore an explicit read-only fact boundary, not an accidental
package leak. Existing public service and response classes keep their names.

The first focused run exposed one reflection-only edge that a static type graph cannot see: the root endpoint manifest enumerated only `OpsShardReadinessRoutePaths`. Removing the ten root constants therefore omitted the evidence-catalog endpoint and correctly drove downstream closeout services to `blocked`. The manifest now merges the global and family route owners, normalizes family field names back to their original `ROUTE_CLEANUP_*` response names, and sorts on those normalized names. The second focused run passed all 153 selected tests without changing any expected response or fail-closed rule.

The first full verify then passed all 1,823 tests and every JaCoCo floor before SpotBugs rejected the newly public analyzer `Segment` list boundary with `EI_EXPOSE_REP` and `EI_EXPOSE_REP2`. Those were not hidden by a new exclusion. The record now copies `sourceNodePlans` both on construction and access, making the cross-package read model mechanically immutable while preserving its components, values, and JSON bytes.

## Failure conditions

- A missing closure member or a reverse import from the new package to a root
  RouteCleanup implementation fails the version.
- A changed route suffix, response component, digest input/order,
  fixture/evidence byte, status rule, or collection order fails the version.
- Keeping both the global route constant and the family route constant fails
  single ownership.
- Keeping `EvidenceEntryFactory` while adding `RouteCleanupRoutes`, or raising
  the total-file ratchet, fails the no-growth rule.
- Making all package members public instead of proving exact consumers fails
  the boundary rule.
- Raising any root, source-size, test-size, or SpotBugs budget fails the
  shrink-only program.

## Verification commands

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q "-Dtest=OpsShardReadinessRouteCleanup*Tests,OpsExtractionV1857Tests,OpsExtractionV1856Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,OpsShardReadinessRoutePathsTests,JavaMaintainabilityBudgetTests" test
.\mvnw.cmd -q spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```
