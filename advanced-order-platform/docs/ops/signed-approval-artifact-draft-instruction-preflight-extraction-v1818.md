# Signed Approval Artifact Draft Instruction Preflight Extraction v1818

## Summary

v1818 is a contract-preserving extraction of the operator-evidence-value-supply
signed-approval artifact-draft-instruction-preflight registry family. The work
moves the read-only instruction preflight implementation into
`ops.maintenance.signedapprovalartifactdraftinstructionpreflight` while
retaining the public controllers and the root route aggregator in the root
`ops` package.

Direct Java files in root `ops` package fall from 1,009 to 993. The total
`ops` Java files stay at 1,352 because the package-private gate catalog is
collocated with the guard catalog while the new route owner is added.
The exact guard phrase is: total `ops` Java files stay at 1,352.

## Moved Ownership

The new package owns the instruction preflight services, response record,
support builder, slot catalogs, guard catalog, and the combined guard/gate
catalog. The two controllers remain in root so the existing Spring route
surface keeps the same shape:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceController`

The moved implementation reads already-public v1817
`ArtifactDraftAuthoringReadiness` endpoint constants. Downstream readers that
now import this package are `TextPackageIntake` and
`SignedApprovalDraftProfileSection`.

## Route Owner

`OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths`
is the new public owner for the instruction-preflight route suffixes. The root
`OpsShardReadinessRoutePaths` class delegates to it, preserving byte-identical
route strings while removing the moved services' dependency on the root
package-private route aggregator.

## Boundary Preserved

This extraction does not add write routing, active shard routing, credential
value handling, raw endpoint usage, managed audit connections, deployment, or
rollback behavior. It is a read-only package ownership change with route
constant delegation and endpoint visibility adjustments only.

## Archive Rule

Do not rename or move archive roots, historical `e/<version>/` folders,
evidence JSON files, screenshots, or cross-project handoff paths. Node and
other downstream evidence can reference those paths by exact string and digest.

## Verification Hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1818Tests` keeps this note
discoverable from the ops index, checks that representative implementation
files live in the narrow package, checks that controllers remain root-owned,
and ratchets the root direct Java file count to 993 while preserving the
1,352 total `ops` Java file ceiling.

## Next Link

The next natural chain link is `TextPackageIntake`: its field catalogs already
read this family's endpoint constants, and v1818 makes those constants public
from the new package.
