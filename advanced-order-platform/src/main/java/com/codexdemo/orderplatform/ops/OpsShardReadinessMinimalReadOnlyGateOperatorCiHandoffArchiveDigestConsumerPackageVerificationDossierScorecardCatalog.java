package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierScorecardCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierScorecardCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .ScorecardEntry> scorecard(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            source,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .SourcePackageSnapshot> sourcePackages,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .ProvenanceEntry> provenance,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .SectionDigest> sectionDigests,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .AudienceRoute> audienceRoutes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .CiLane> ciLanes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .AcceptanceGate> acceptanceGates,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .BoundaryAudit> boundaryAudits,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .ReleaseChecklistItem> releaseChecklist,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .HandoffReceipt> handoffReceipts
            ) {
        return List.of(
                score("source-consumer-package-status", 1, "passed".equals(source.status()) ? 1 : 0),
                score("source-package-snapshot",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_SOURCE_PACKAGE_SNAPSHOT_COUNT,
                        sourcePackages.size()),
                score("provenance",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_PROVENANCE_ENTRY_COUNT,
                        passedProvenance(provenance)),
                score("section-digests",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_SECTION_DIGEST_COUNT,
                        passedSectionDigests(sectionDigests)),
                score("audience-routes",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_AUDIENCE_ROUTE_COUNT,
                        readyAudienceRoutes(audienceRoutes)),
                score("ci-lanes",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_CI_LANE_COUNT,
                        readOnlyCiLanes(ciLanes)),
                score("acceptance-gates",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_ACCEPTANCE_GATE_COUNT,
                        passedAcceptanceGates(acceptanceGates)),
                score("boundary-audits",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_BOUNDARY_AUDIT_COUNT,
                        lockedBoundaryAudits(boundaryAudits)),
                score("release-checklist",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_RELEASE_CHECKLIST_COUNT,
                        readyReleaseChecklist(releaseChecklist)),
                score("handoff-receipts",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
                                .EXPECTED_HANDOFF_RECEIPT_COUNT,
                        readyHandoffReceipts(handoffReceipts))
        );
    }

    private static int passedProvenance(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ProvenanceEntry> entries
    ) {
        return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
    }

    private static int passedSectionDigests(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .SectionDigest> entries
    ) {
        return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
    }

    private static int readyAudienceRoutes(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .AudienceRoute> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .AudienceRoute::ready)
                .count();
    }

    private static int readOnlyCiLanes(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .CiLane> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .CiLane::readOnly)
                .count();
    }

    private static int passedAcceptanceGates(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .AcceptanceGate> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .AcceptanceGate::passed)
                .count();
    }

    private static int lockedBoundaryAudits(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .BoundaryAudit> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .BoundaryAudit::locked)
                .count();
    }

    private static int readyReleaseChecklist(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ReleaseChecklistItem> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .ReleaseChecklistItem::ready)
                .count();
    }

    private static int readyHandoffReceipts(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .HandoffReceipt> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .HandoffReceipt::ready)
                .count();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .ScorecardEntry score(String name, int expected, int actual) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                .ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
    }
}
