# Signed Approval Artifact Draft Text Package Intake Extraction v1819

## Summary

v1819 is a contract-preserving extraction of the operator-evidence-value-supply
signed-approval artifact-draft-text-package-intake registry family. The work
moves the read-only intake implementation into
`ops.maintenance.signedapprovalartifactdrafttextpackageintake` while retaining
the public controllers and the root route aggregator in the root `ops` package.

Direct Java files in root `ops` package fall from 993 to 977. The total `ops`
Java files stay at 1,352 because the package-private gate catalog is collocated
with the guard catalog while the new route owner is added. The exact guard
phrase is: total `ops` Java files stay at 1,352.

## Moved Ownership

The new package owns the intake services, response record, response support,
field catalogs, and the combined guard/gate catalog. The two controllers remain
in root so the existing Spring route surface keeps the same shape:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceController`

The moved field catalogs continue to read the already-public v1818
`ArtifactDraftInstructionPreflight` endpoint constants. Downstream readers that
now import this package are `TextPackageReviewPreflight` and
`SignedApprovalDraftTextPackageProfileSection`.

## Route Owner

`OpsShardReadinessSignedApprovalArtifactDraftTextPackageIntakeRoutePaths` is the
new public owner for the text-package-intake route suffixes. The root
`OpsShardReadinessRoutePaths` class delegates to it, preserving byte-identical
route strings while removing the moved services' dependency on the root
package-private route aggregator.

## Boundary Preserved

This extraction does not accept text package material, parse source files,
import evidence, grant approval, expose raw values, open write routing, start a
runtime, deploy, or roll back. It is a read-only package ownership change with
route constant delegation and endpoint visibility adjustments only.

## Archive Rule

Do not rename or move archive roots, historical `e/<version>/` folders,
evidence JSON files, screenshots, or cross-project handoff paths. Node and
other downstream evidence can reference those paths by exact string and digest.

## Verification Hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1819Tests` keeps this note
discoverable from the ops index, checks that representative implementation
files live in the narrow package, checks that controllers remain root-owned,
and ratchets the root direct Java file count to 977 while preserving the 1,352
total `ops` Java file ceiling.

## Next Link

The next natural chain link is `TextPackageReviewPreflight`: its criteria
catalogs already read this family's endpoint constants, and v1819 makes those
constants public from the new package.
