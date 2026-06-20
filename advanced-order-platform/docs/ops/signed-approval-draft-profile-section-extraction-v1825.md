# v1825 signed approval draft profile section extraction

This version is the first of three contract-preserving ProfileSection
extractions. The complete cluster contains 36 production files:

- 11 base `SignedApprovalDraftProfileSection` files;
- 14 `SignedApprovalDraftTextPackageProfileSection` files;
- 11 `SignedApprovalDraftProfileSectionHandoff` files.

v1825 moves the base registry implementation into
`ops.maintenance.signedapprovaldraftprofilesection`. The public Spring
controller remains in the root `ops` package.

Direct Java files in the root `ops` package fall from 897 to 887. The total `ops` Java file count stays at 1,352: the new route owner is offset by folding
the package-private gate generator into the registry support class.

## Boundary

The registry suffix is now owned by
`OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths` under
`ops.maintenance.signedapproval`. Both the root route aggregator and the
historical candidate-document route catalog delegate to this owner, so the
published HTTP path remains byte-identical.

The moved registry consumes five already-extracted read-only sources:
ArtifactDraftPreflight, ArtifactDraftReadiness, ReviewPackagePreflight,
AuthoringReadiness, and InstructionPreflight. Its public service and response
form the read-only boundary consumed by the retained-root controller and the
next ProfileSectionHandoff extraction.

## Safety

The change does not materialize draft artifacts, capture signatures, grant
approval, import operator values, create runtime payloads, open write routing,
or mutate sibling registries. It only relocates the registry assembly,
catalogs, renderer, response, route locks, and tests.

Do not rename or move archive roots, `e/<version>/` folders, evidence JSON, or
cross-project historical fixtures while continuing the remaining two
ProfileSection versions.

## Verification

`ReadabilityUpkeepOpsConsolidationExtractionV1825Tests` verifies that the note
is discoverable, representative implementations moved, the standalone
GateCatalog is absent, the root controller remains, root files stay at or below
887, total `ops` Java files stay at or below 1,352, and the two upper
ProfileSection layers remain available for v1826 and v1827.
