# v1789 Java ops governance consolidation roadmap

Status: active planning baseline. This plan is the Java-side answer to the
Node v2114 governance consolidation work. It is safe to run in parallel with
Node because it changes no Node contract and consumes no fresh upstream
evidence.

Planner / executor: Codex in the Java workspace.
Source signal: `D:\nodeproj\orderops-node\docs\plans2\v2114-governance-consolidation-pointer.md`.
Measured on 2026-06-12, `HEAD = d72c6cf7`.

## Problem statement

- `src/main/java/com/codexdemo/orderplatform/ops`: 1,352 Java files.
- Root `ops` package direct Java files: 1,330.
- Files with `Readiness` in the name: 1,210.
- Longest main-source class basename measured locally: 147 characters.
- The business core outside the governance evidence surface is much smaller
  than the ops evidence surface.

The ops package is still useful: it carries a long archive of read-only
readiness, approval, evidence, handoff, and boundary work. The problem is not
that the evidence is invalid. The problem is that new maintenance work can no
longer safely continue by adding more broad root-package readiness classes.

## Non-negotiable archive rule

Do not rename or move archive folders `a/` through `f/`, especially
`e/<version>/`, and do not rename or move evidence JSON files. Node has
hardcoded absolute references and content digests into Java and mini-kv archive
trees. Java consolidation must preserve those paths until a separate
cross-project migration proves otherwise.

## Necessity proof

- Blocker resolved: root ops growth is now measurable and can be stopped with a
  ratchet before any class migration begins.
- Consumer: future Java maintainers and any Node-side consumer that still
  reads Java evidence paths.
- Why existing code cannot be reused as-is: prior readability upkeep added
  maps and an audit endpoint, but no Java-side consolidation roadmap or
  file-count ratchet existed.
- Stop condition: new ops main-source file growth stops; consolidation batches
  only preserve contracts, improve package readability, or reduce duplication.

## Scope

In scope:

1. Documentation baseline.
   - This roadmap.
   - `docs/plans/v1789-codex-ops-migration-playbook.md`.
   - A `docs/ops` pointer to the active consolidation plan.
2. Ratchet enforcement.
   - Main ops Java files must remain at or below 1,352.
   - Root ops Java files must remain at or below 1,330.
   - Readiness-named main ops Java files must remain at or below 1,210.
3. Contract-preserving consolidation batches.
   - Prefer focused subpackages for new support code.
   - Keep route paths, response schema, public class names, archived evidence
     paths, and existing test expectations stable unless a later plan explicitly
     approves a migration.
4. Curated reading and consolidation notes.
   - Document which old areas are load-bearing.
   - Record skipped classes when a batch cannot safely migrate them.

Out of scope:

- Moving `a/` through `f/`, `e/<version>/`, or evidence JSON files.
- Renaming public routes or response fields.
- Adding new echo, verification, readiness, or closure chains while this
  consolidation plan is active.
- Node, mini-kv, aiproj, or `drg-ui-prototype` edits.
- Live cross-project integration or production execution.

## Version slicing

- **v1789**: planning baseline, Codex playbook, docs pointer, ops file-count
  ratchet, Chinese walkthrough.
- **v1790**: root ops inventory by route family, archive load-bearing list, and
  first reduction candidate list. No class moves yet.
- **v1791+**: small contract-preserving consolidation batches. Each batch must
  either lower root-package pressure, remove duplication, or document an
  intentionally skipped load-bearing cluster.
- **Final version**: full verification, CI evidence, route/archive stability
  report, and a decision on whether deeper class relocation is safe.

## Cross-project parallel statement

Java may proceed in parallel with Node v2114 because this roadmap is
Java-internal and contract-preserving. It must not request a new Node-side
echo/verification/readiness chain while Node consolidation is active. If Java
needs a future Node consumer change, record it as a deferred request.

## Enforcement

`ReadabilityUpkeepGovernanceConsolidationPlanTests` protects the plan docs,
archive-preservation rule, and ops file-count ratchet. The fix for a ratchet
failure is consolidation or an explicit new roadmap, not silently raising the
baseline.
