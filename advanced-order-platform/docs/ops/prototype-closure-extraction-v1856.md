# v1856 Prototype closure extraction

## Scope

This version extracts the complete 12-file direct-root Prototype
implementation into `ops.maintenance.prototype`. The three Spring controllers
stay in the root. Three service tests move with the implementation, while the
three controller-split tests remain beside the adapters.

The version also removes two structural debts that would otherwise survive the
move: 30 Prototype suffixes leave the 1,111-line global route aggregator for a
single nested `PrototypeRoutes` data owner, and Prototype stops importing the
large root RouteCleanup closeout service/response through a four-field
`CloseoutSource` port implemented by the existing root service.

No route string, response component, digest material, fixture/evidence value,
HTTP method, write boundary, credential boundary, deployment behavior,
rollback behavior, or archive layout may change.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Move the complete family | Moved 12 non-controller Prototype files to `ops.maintenance.prototype` | package/root census guard | focused verified |
| Keep adapters at the composition root | Retained three controllers and three controller-split tests with explicit package imports | controller structure tests and v1856 guard | focused verified |
| Replace repeated route ownership with data | Moved 30 byte-identical suffixes into nested `PrototypeRoutes`, using new identifiers no longer than 40 characters | route inventory equality and old-symbol absence guards | focused verified |
| Remove the RouteCleanup reverse dependency | Added `CloseoutSource` and `CloseoutSnapshot`; the existing root closeout service implements the narrow port | compiler, port-shape test, and prototype service tests | focused verified |
| Preserve legitimate inbound readers | Kept only catalog and field-alignment endpoint constants public for two RouteCleanup readers | explicit two-consumer guard | focused verified |
| Move package behavior tests | Moved three service tests; left three controller tests in root | test package census | focused verified |
| Preserve static analysis | Relocated 12 mirrored SpotBugs response FQNs without adding exclusions | old/new FQN counts and SpotBugs | full verified |
| Tighten root and hotspot budgets | Direct root 290 -> 278; movable 186 -> 174; both Prototype buckets -> 0; RoutePaths 1111 -> 1058 | census plus maintainability guard | focused verified |
| Obey elegance gates | Added no production file; new type/constant identifiers stay <=40; shared route data replaces three repeated constant families | source-name and route-owner guard | focused verified |
| Explain and verify independently | The 4,655-Han Chinese walkthrough preceded implementation; focused, full, implementation-CI and closeout-CI gates follow | walkthrough/archive tests and run IDs | local verified; remote gates pending |

## Implemented boundary

Public package boundaries are limited to the three Spring services, six
immutable response types, the route data needed by retained controllers, the
two endpoint constants needed by RouteCleanup readers, and the narrow closeout
port. Evidence catalogs remain package-private. RouteCleanup remains the port
adapter owner; Prototype sees no RouteCleanup implementation or response FQN.

## Failure conditions

- A changed route suffix, response field, digest input, fixture/evidence value,
  or endpoint order invalidates the extraction.
- Keeping any Prototype implementation file in direct root or moving a
  controller invalidates the ownership boundary.
- Making the full RouteCleanup response public to Prototype invalidates the
  narrow-port design.
- Retaining the 30 old Prototype constants in the global root route aggregator
  invalidates the route-owner cleanup.
- A new identifier or Java filename over 40 characters invalidates the
  elegance gate.
- Raising any root-count, total-file, source-size, or SpotBugs ratchet
  invalidates the version.

## Verification

The first `test-compile` exposed only the package-private root test fixture
needed by the three moved service tests. Its existing closeout factory became a
public test-source method; no production type was widened and no object graph
was copied. The second `test-compile` passed. The focused selection completed
26 Surefire reports with 95 tests, 0 failures, 0 errors, and 0 skipped in
6m37s. Full `mvnw verify` then passed 1,816 tests in 8m43s; JaCoCo analyzed
2,229 classes and met every coverage floor, while SpotBugs reported zero
findings. The production/test source budgets are 1058/34/1/1 and 853/8/2/0.
Implementation and closeout CI remain the two pending remote gates at this
point in the evidence sequence.

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='OpsShardReadinessPrototype*Tests,OpsExtractionV1856Tests,OpsExtractionV1855Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,OpsShardReadinessRoutePathsTests,JavaMaintainabilityBudgetTests' test
.\mvnw.cmd -q spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```
