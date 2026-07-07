# Operator evidence value supply adapter preflight extraction v1830

## Summary

v1830 moves the `OperatorEvidenceValueSupplyAdapterPreflight` implementation
out of the direct root `ops` package and into
`ops.maintenance.operatorevidencevaluesupplyadapterpreflight`. The two Spring
controllers stay in root so the HTTP entry points remain visible beside the
global ops route aggregator.

Direct Java files in the root `ops` package fall from 864 to 848. The remaining
direct-root non-controller backlog falls from 759 to 743. The total `ops` Java
files stay at 1,352 because the old package-private `RuleCatalog` is folded into
the moved `SlotCatalog` while the new route owner is added.

## Route ownership

The new public
`OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths` owner
holds the adapter-preflight `BASE_PATH` and the twelve endpoint suffix
constants. `OpsShardReadinessRoutePaths` delegates to that owner, preserving the
byte-identical HTTP route strings consumed by the retained-root controllers.

The moved services now compute their immutable `ENDPOINT` constants from the
new route owner. The retained-root controller tests still compare those public
service endpoint constants against the root route aggregator.

## Endpoint readers

The moved slot catalog reads the existing value-supply base evidence endpoints.
Only the endpoint constants already needed across the new package boundary were
publicized:

- `OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.ENDPOINT`
- `OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.ENDPOINT`
- `OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.ENDPOINT`
- `OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService.ENDPOINT`

Downstream `ApprovalPreflight` still reads the adapter preflight closeout
endpoint, but now imports it from the new package. No response shape, route
string, write boundary, credential handling, runtime execution, deployment,
rollback, or archive layout changed.

## File-growth guard

This batch intentionally avoids adding net `ops` Java files. The standalone
`OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.java`
file is removed, and its `RULE_COUNT`, `allRules`, `rules`, and `rule` helpers
are collocated in `OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog`.
That keeps the rule data package-local while making the route owner addition
file-count neutral.

## Evidence

- `scripts/ops-root-census.ps1 -Json` reports 848 direct-root Java files, 105
  retained-root files, 743 remaining direct-root non-controller files, 0
  unassigned files, and a 0-count
  `OperatorEvidenceValueSupplyAdapterPreflight` bucket.
- `ReadabilityUpkeepOpsConsolidationExtractionV1830Tests` guards the moved
  files, route delegation, folded rule catalog, downstream import, SpotBugs FQN,
  public base endpoint constants, root-count ratchet, total-file ratchet, and
  Chinese walkthrough archive.
- `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` now binds the live
  endgame census to 848 root files and 743 remaining movable files.

Do not rename or move archive roots. Node and mini-kv may hold exact path or
digest references to historical Java evidence, so this extraction only changes
Java source/package ownership and Java documentation.
