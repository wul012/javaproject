# v1826 signed approval draft text package profile section extraction

This version is the second of three contract-preserving ProfileSection
extractions. The complete cluster remains scoped as 36 production files:

- 11 base `SignedApprovalDraftProfileSection` files, completed in v1825;
- 14 `SignedApprovalDraftTextPackageProfileSection` files, handled here;
- 11 `SignedApprovalDraftProfileSectionHandoff` files, left for the next
  version.

v1826 moves the text-package profile section registry implementation into
`ops.maintenance.signedapprovaldrafttextpackageprofilesection`. The public
Spring controller remains in the root `ops` package.

Direct Java files in the root `ops` package fall from 887 to 874. total `ops` Java file count stays at 1,352: the new route owner is offset by folding the package-private gate generator into the registry support class.

## Boundary

The registry suffix is now owned by
`OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRoutePaths` under
`ops.maintenance.signedapproval`. Both the root route aggregator and the
historical candidate-document route catalog delegate to this owner, so the
published HTTP path remains byte-identical.

The moved registry consumes nine read-only sources: the six already-extracted
text-package artifact-draft routes and three compared-evidence candidate routes
that still live in the root package. Explicit public imports make that mixed
boundary visible instead of letting package locality hide it.

## Safety

The change does not accept a text package, compare evidence, parse detached
signature data, capture signed approvals, import operator values, create runtime
payloads, open write routing, or mutate sibling registries. It only relocates
registry assembly, source catalogs, section catalogs, renderers, response
records, route-lock catalogs, support code, and package-local tests.

Do not rename or move archive roots, `e/<version>/` folders, evidence JSON, or
cross-project historical fixtures while continuing the remaining
ProfileSectionHandoff version.

## Verification

`ReadabilityUpkeepOpsConsolidationExtractionV1826Tests` verifies that the note
is discoverable, representative implementations moved, the standalone
GateCatalog is absent, the root controller remains, the new route owner exists,
root files stay at or below 874, total `ops` Java files stay at or below 1,352,
and the ProfileSectionHandoff layer remains available for v1827.
