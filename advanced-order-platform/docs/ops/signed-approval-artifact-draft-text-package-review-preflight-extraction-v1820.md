# Signed Approval Artifact Draft Text Package Review Preflight Extraction v1820

## Summary

v1820 is a contract-preserving extraction of the operator-evidence-value-supply
signed-approval artifact-draft-text-package-review-preflight registry family.
The read-only review criteria implementation now lives in
`ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight`; the
two public controllers and root route aggregator stay in root `ops`.

Direct Java files in root `ops` package fall from 977 to 961. The total `ops`
Java files stay at 1,352 because the package-private gate catalog is collocated
with the rejection-control catalog while the new route owner is added.
The exact guard phrase is: total `ops` Java files stay at 1,352.

## Ownership And Dependencies

The new package owns nine services, the response and support types, criteria
catalogs, and the combined rejection-control/gate catalog. Its criteria keep
reading the already-public v1819 `TextPackageIntake` endpoint constants.
Downstream `TextPackageSubmissionPreflight` and
`SignedApprovalDraftTextPackageProfileSection` now import this family's public
immutable endpoint or response types.

## Route Owner

`OpsShardReadinessSignedApprovalArtifactDraftTextPackageReviewPreflightRoutePaths`
owns the nine route suffixes. Root `OpsShardReadinessRoutePaths` delegates to
it, preserving byte-identical HTTP paths.

## Boundary Preserved

The registry reviews criteria only. It does not parse text, accept packages,
read detached signatures, grant approval, import operator values, open write
routing, start Java or mini-kv, deploy, or roll back.

## Archive Rule

Do not rename or move archive roots, historical `e/<version>/` folders,
evidence JSON files, screenshots, or cross-project handoff paths.

## Verification Hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1820Tests` verifies the narrow
package, retained controllers, documentation index, root ceiling 961, and total
`ops` Java ceiling 1,352.

## Next Link

The next natural chain link is `TextPackageSubmissionPreflight`, whose slot
catalogs already consume this family's public catalog endpoint.
