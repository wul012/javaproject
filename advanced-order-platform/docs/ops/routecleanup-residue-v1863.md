# v1863 RouteCleanup maintenance residue extraction

## Scope

v1863 closes the pure maintenance review implementation residue in the direct
`ops` root. Thirteen production types and four package-local tests move into the
existing `ops.maintenance.routecleanup` package. The two Spring batch controllers
remain root HTTP adapters, and every route byte and response component stays
unchanged.

`MaintenanceBoundaryReportResponse` and its service deliberately stay in root
for the next general RouteCleanup closure: they consume root ArchiveHandoff and
PolicyGuard implementations, so moving them now would reverse the package
dependency that the v1857 architecture guard forbids.

## Family Design

- Abstraction: one RouteCleanup maintenance review engine boundary.
- Data boundary: one immutable review response carries every batch projection.
- Behavior boundary: support builds items; services compose read-only evidence.
- HTTP boundary: controllers adapt requests and import the implementation package.
- Test boundary: behavior tests live beside the package-private support engine.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State |
| --- | --- | --- | --- |
| Close one coherent residue | Move the exact 13-file maintenance review closure | exact inventory and root-absence guard | mechanically enforced |
| Preserve HTTP compatibility | Keep all maintenance controllers in root | controller imports and focused route tests | mechanically enforced |
| Preserve read-only behavior | Change packages only; retain response and service bodies | behavior suite and full verify | mechanically enforced |
| Keep visibility narrow | Package-private support; repay nine temporary endpoints | 2-source, 13-edge, 12-target census | mechanically enforced |
| Move owned tests | Relocate the four remaining maintenance tests | exact test inventory guard | mechanically enforced |
| Tighten the endgame | Direct root 187 -> 174; movable 83 -> 70 | census script and ratchet tests | mechanically enforced |
| Explain before verify | Archive ten Chinese sections with at least 3000 Han | walkthrough gate | mechanically enforced |

## Compatibility Boundary

This version does not add or rename an endpoint, alter a response record,
change a fixture byte, touch write routing, approve execution, read credential
values, start Node or mini-kv, or change deployment and rollback behavior.

## Failure Conditions

- Any of the thirteen production files remains in the direct root or lands outside
  `ops.maintenance.routecleanup`.
- Any maintenance controller moves out of the root adapter package.
- The package-private review support becomes public or gains an external reader.
- Any route string, response component, evidence item, version label, or status changes.
- Direct root is not 174, movable root is not 70, RouteCleanup is not 66, or an
  unassigned root file appears.
- The final walkthrough is shorter than 3000 Han characters or does not contain
  exactly the ten required headings.

## Verification Plan

Run the v1863 structural guard, all relocated maintenance tests, historical
extraction guards affected by the package contraction, Spotless check, and the
full Maven verify gate. Push the implementation commit, require green Actions,
then close the ledger, tag, and push the closeout commit before v1864 starts.

The first full verify ran 1,873 tests and correctly exposed one stale live
ratchet in the v1806 closeout guard: expected direct root 187 versus measured
174. The historical narrative remains unchanged; the live constant tightens to
174, its focused repair suite must pass, and the complete verify must rerun.
