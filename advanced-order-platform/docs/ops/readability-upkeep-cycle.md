# Readability upkeep cycle

This cycle keeps late-stage ops readability work reviewable. It is a local
Java project maintenance rule, not a deployment workflow.

## Cycle

| Step | Output | Guard |
| --- | --- | --- |
| Map | `docs/ops` route, package, archive, and walkthrough maps | Docs stay discoverable from `docs/ops/README.md`. |
| Model | response records and focused catalog classes | Static data is split by topic instead of being embedded in a large service. |
| Expose | read-only service and controller | Route constants, read-only transaction boundaries, and controller tests remain present. |
| Guard | docs tests, boundary tests, renderer tests, and walkthrough compliance | New docs, routes, and Chinese walkthroughs fail fast when they drift. |
| Close | response version, batch README, tag, push, and CI evidence | Final status matches the published version and no high-risk execution surface opens. |

## Batch discipline

New readability upkeep versions should be large enough to justify their
walkthroughs. A version should normally change at least one durable project
artifact: a map, a model, a catalog, a service route, a focused test, a docs
guard, or a closeout state. Do not create versions that only rewrite the
walkthrough.

## Boundary

The cycle excludes write routing, active shard routing, credential value reads,
raw endpoint URL resolution, managed audit HTTP/TCP connections, deployment,
rollback, Java autostart, mini-kv autostart, and changes in other projects.
