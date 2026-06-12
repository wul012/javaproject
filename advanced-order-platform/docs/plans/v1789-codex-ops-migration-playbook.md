# v1789 Codex ops migration playbook

This playbook turns the Java ops governance consolidation roadmap into
repeatable Codex work. It is intentionally conservative: preserve contracts and
archives first, then migrate only when the proof is clear.

## Hard rules

1. Do not rename or move archive folders `a/` through `f/`, `e/<version>/`, or
   evidence JSON files.
2. Do not change route paths, response fields, public endpoint behavior, or
   existing test expectations to make a consolidation pass.
3. Do not add new echo, verification, readiness, or closure chains while this
   plan is active.
4. Do not use `git fetch --all --tags`; if fetching is required, use the Java
   canonical remote: `git fetch javaproject --tags --prune`.
5. Keep each batch small enough to review, with a Chinese walkthrough that
   explains real Java project work rather than padding.

## Step 0: verify the baseline

Run the focused gate before any migration batch:

```powershell
mvn -q "-Dtest=ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test
```

The gate must pass before moving on. If the ratchet fails, do not raise the
baseline unless the user explicitly approves a new roadmap. Prefer reducing or
reclassifying work.

## Step 1: inventory before moving code

For the first implementation batch after v1789:

- Group root ops files by route family and evidence family.
- Mark which clusters are referenced by archive files, Node path catalogs, or
  existing integration tests.
- Produce a candidate list of safe consolidation targets.
- Record skipped clusters with a one-line reason.

Do not move classes in the inventory batch. The point is to make the blast
radius visible before touching public names.

## Step 2: contract-preserving batches

For each later batch:

- Pick one narrow cluster.
- Keep public routes and response schemas stable.
- Prefer extracting shared catalog/render/support helpers over renaming public
  endpoint classes.
- Add or update focused tests before relying on a migration.
- Keep archive paths unchanged.
- Record the batch in the walkthrough and any active progress table.

## Step 3: per-batch verification

Run focused tests for the touched area plus the baseline gate. If a batch
touches Spring context or public controller wiring, run the full Java suite
before push.

```powershell
mvn -q "-Dtest=ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test
```

Final closeout batches must also run:

```powershell
mvn -q test
```

## Step 4: closeout

Each version needs:

- A tag with the Java version number and clear scope.
- A Chinese walkthrough with real project-local work.
- A clean worktree.
- Push to `javaproject`.
- GitHub Actions success before declaring the batch closed.

## Final acceptance checklist

- [ ] Ops main-source file counts did not grow beyond the v1789 ratchet.
- [ ] Archive folders and evidence JSON paths are untouched.
- [ ] Public routes and response schemas are stable.
- [ ] All skipped clusters have reasons.
- [ ] Focused tests pass.
- [ ] Full tests pass for final closeout.
- [ ] CI is green on the pushed head.
