# v1824 signed approval artifact draft text package compared package evidence intake extraction

This version is a contract-preserving ops-package extraction. It moves the
signed-approval artifact-draft-text-package compared-package-evidence-intake
implementation files into
`ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake`
while the public Spring controller and the root route aggregator stay in the
root `ops` package.

Direct Java files in root `ops` package fall from 911 to 897. The total `ops`
Java files stay at 1,352: the new public route owner is offset by folding the
package-private guard catalog into the slot catalog, where the evidence slots
and fail-closed guards are reviewed together.
In short, total `ops` Java files stay at 1,352 while root pressure drops.

## Boundary

The five route suffixes are now owned by
`OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths`
under `ops.maintenance.signedapproval`. `OpsShardReadinessRoutePaths` delegates
to that owner, so every HTTP path remains byte-identical.

The root controller imports the moved services and response type. The moved
services expose public immutable `ENDPOINT` constants because retained-root
readers need them as read-only evidence sources. Those readers are
`ComparedPackageReview`, `SignedApprovalDraftTextPackageProfileSection`, and the
root controller/route tests.

## Safety

This extraction does not accept compared packages, parse signed draft text, parse
detached signatures, emit approval grants, import values, open write routing, or
start runtime payload execution. It only preserves the read-only evidence-intake
catalog and the five existing HTTP views.

Do not rename or move archive roots, `e/<version>/` folders, evidence JSON, or
cross-project historical fixtures while continuing this Java-only cleanup.

## Verification

The structure guard is
`ReadabilityUpkeepOpsConsolidationExtractionV1824Tests`. It verifies the note is
discoverable, representative implementation files moved into the narrow package,
the old guard catalog file is gone, the root controller and route aggregator are
still root-owned, root files stay at or below 897, and total `ops` Java files
stay at or below 1,352.
