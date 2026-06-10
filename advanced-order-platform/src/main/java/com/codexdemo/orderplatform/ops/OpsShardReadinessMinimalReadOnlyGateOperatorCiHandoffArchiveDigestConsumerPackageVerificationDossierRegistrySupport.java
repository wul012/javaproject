package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367";
    static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
    static final String OPERATOR_HANDOFF_PLAN = "Node v369";
    static final String VERIFICATION_DOSSIER_STATE =
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-ready";
    static final int EXPECTED_SOURCE_PACKAGE_SNAPSHOT_COUNT = 1;
    static final int EXPECTED_PROVENANCE_ENTRY_COUNT = 6;
    static final int EXPECTED_SECTION_DIGEST_COUNT = 9;
    static final int EXPECTED_AUDIENCE_ROUTE_COUNT = 4;
    static final int EXPECTED_CI_LANE_COUNT = 5;
    static final int EXPECTED_ACCEPTANCE_GATE_COUNT = 5;
    static final int EXPECTED_BOUNDARY_AUDIT_COUNT = 8;
    static final int EXPECTED_RELEASE_CHECKLIST_COUNT = 5;
    static final int EXPECTED_HANDOFF_RECEIPT_COUNT = 4;
    static final int EXPECTED_SCORECARD_ENTRY_COUNT = 10;
    static final int EXPECTED_MARKDOWN_SECTION_COUNT = 10;

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            response(
                    String version,
                    String endpoint,
                    String profile,
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
                            .HandoffReceipt> handoffReceipts,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .ScorecardEntry> scorecard,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .MarkdownSection> markdownSections
            ) {
        var sourcePackageCopy = List.copyOf(sourcePackages);
        var provenanceCopy = List.copyOf(provenance);
        var sectionDigestCopy = List.copyOf(sectionDigests);
        var audienceRouteCopy = List.copyOf(audienceRoutes);
        var ciLaneCopy = List.copyOf(ciLanes);
        var acceptanceGateCopy = List.copyOf(acceptanceGates);
        var boundaryAuditCopy = List.copyOf(boundaryAudits);
        var releaseChecklistCopy = List.copyOf(releaseChecklist);
        var handoffReceiptCopy = List.copyOf(handoffReceipts);
        var scorecardCopy = List.copyOf(scorecard);
        var markdownSectionCopy = List.copyOf(markdownSections);
        int passedProvenanceCount = countProvenance(provenanceCopy);
        int passedSectionDigestCount = countSectionDigests(sectionDigestCopy);
        int readyAudienceRouteCount = countReadyAudienceRoutes(audienceRouteCopy);
        int readOnlyCiLaneCount = countReadOnlyCiLanes(ciLaneCopy);
        int passedAcceptanceGateCount = countPassedAcceptanceGates(acceptanceGateCopy);
        int lockedBoundaryAuditCount = countLockedBoundaryAudits(boundaryAuditCopy);
        int readyReleaseChecklistCount = countReadyReleaseChecklist(releaseChecklistCopy);
        int readyHandoffReceiptCount = countReadyHandoffReceipts(handoffReceiptCopy);
        int passedScorecardCount = countScorecard(scorecardCopy);

        List<String> checks = new ArrayList<>();
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-plan-"
                + SOURCE_PLAN);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-required-archive-"
                + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-operator-plan-"
                + OPERATOR_HANDOFF_PLAN);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-version-"
                + source.version());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-status-"
                + source.status());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-markdown-count-"
                + source.markdownSectionCount());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-source-package-count-"
                + sourcePackageCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-provenance-count-"
                + provenanceCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-provenance-count-"
                + passedProvenanceCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-section-digest-count-"
                + sectionDigestCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-section-digest-count-"
                + passedSectionDigestCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-audience-route-count-"
                + audienceRouteCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ready-audience-route-count-"
                + readyAudienceRouteCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ci-lane-count-"
                + ciLaneCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-read-only-ci-lane-count-"
                + readOnlyCiLaneCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-acceptance-gate-count-"
                + acceptanceGateCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-acceptance-gate-count-"
                + passedAcceptanceGateCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-boundary-audit-count-"
                + boundaryAuditCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-locked-boundary-audit-count-"
                + lockedBoundaryAuditCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-release-checklist-count-"
                + releaseChecklistCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ready-release-checklist-count-"
                + readyReleaseChecklistCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-handoff-receipt-count-"
                + handoffReceiptCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-ready-handoff-receipt-count-"
                + readyHandoffReceiptCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-scorecard-count-"
                + scorecardCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-passed-scorecard-count-"
                + passedScorecardCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-markdown-section-count-"
                + markdownSectionCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-consumes-consumer-package");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-upstream-autostart");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-write-routing");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-secret-value");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-raw-endpoint-resolution");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-managed-audit-http");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-runtime-execution");
        checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-deployment-rollback");

        String status = "passed".equals(source.status())
                && source.readOnly()
                && !source.executionAllowed()
                && !source.startsJavaService()
                && !source.startsMiniKvService()
                && !source.readsCredentialValue()
                && !source.resolvesRawEndpointUrl()
                && !source.managedAuditHttpAllowed()
                && source.markdownSectionCount() == EXPECTED_SECTION_DIGEST_COUNT
                && sourcePackageCopy.size() == EXPECTED_SOURCE_PACKAGE_SNAPSHOT_COUNT
                && provenanceCopy.size() == EXPECTED_PROVENANCE_ENTRY_COUNT
                && passedProvenanceCount == provenanceCopy.size()
                && sectionDigestCopy.size() == EXPECTED_SECTION_DIGEST_COUNT
                && passedSectionDigestCount == sectionDigestCopy.size()
                && audienceRouteCopy.size() == EXPECTED_AUDIENCE_ROUTE_COUNT
                && readyAudienceRouteCount == audienceRouteCopy.size()
                && ciLaneCopy.size() == EXPECTED_CI_LANE_COUNT
                && readOnlyCiLaneCount == ciLaneCopy.size()
                && acceptanceGateCopy.size() == EXPECTED_ACCEPTANCE_GATE_COUNT
                && passedAcceptanceGateCount == acceptanceGateCopy.size()
                && boundaryAuditCopy.size() == EXPECTED_BOUNDARY_AUDIT_COUNT
                && lockedBoundaryAuditCount == boundaryAuditCopy.size()
                && releaseChecklistCopy.size() == EXPECTED_RELEASE_CHECKLIST_COUNT
                && readyReleaseChecklistCount == releaseChecklistCopy.size()
                && handoffReceiptCopy.size() == EXPECTED_HANDOFF_RECEIPT_COUNT
                && readyHandoffReceiptCount == handoffReceiptCopy.size()
                && scorecardCopy.size() == EXPECTED_SCORECARD_ENTRY_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownSectionCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
                ? "passed"
                : "blocked";

        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse(
                PROJECT,
                version,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                SOURCE_PLAN,
                REQUIRED_ARCHIVE_VERIFICATION_PLAN,
                OPERATOR_HANDOFF_PLAN,
                source.version(),
                source.endpoint(),
                source.consumerPackageState(),
                VERIFICATION_DOSSIER_STATE,
                sourcePackageCopy.size(),
                provenanceCopy.size(),
                passedProvenanceCount,
                sectionDigestCopy.size(),
                passedSectionDigestCount,
                audienceRouteCopy.size(),
                readyAudienceRouteCount,
                ciLaneCopy.size(),
                readOnlyCiLaneCount,
                acceptanceGateCopy.size(),
                passedAcceptanceGateCount,
                boundaryAuditCopy.size(),
                lockedBoundaryAuditCount,
                releaseChecklistCopy.size(),
                readyReleaseChecklistCount,
                handoffReceiptCopy.size(),
                readyHandoffReceiptCount,
                scorecardCopy.size(),
                passedScorecardCount,
                markdownSectionCopy.size(),
                sourcePackageCopy,
                provenanceCopy,
                sectionDigestCopy,
                audienceRouteCopy,
                ciLaneCopy,
                acceptanceGateCopy,
                boundaryAuditCopy,
                releaseChecklistCopy,
                handoffReceiptCopy,
                scorecardCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                status
        );
    }

    private static int countProvenance(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ProvenanceEntry> entries
    ) {
        return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
    }

    private static int countSectionDigests(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .SectionDigest> entries
    ) {
        return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
    }

    private static int countReadyAudienceRoutes(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .AudienceRoute> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .AudienceRoute::ready)
                .count();
    }

    private static int countReadOnlyCiLanes(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .CiLane> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .CiLane::readOnly)
                .count();
    }

    private static int countPassedAcceptanceGates(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .AcceptanceGate> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .AcceptanceGate::passed)
                .count();
    }

    private static int countLockedBoundaryAudits(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .BoundaryAudit> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .BoundaryAudit::locked)
                .count();
    }

    private static int countReadyReleaseChecklist(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ReleaseChecklistItem> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .ReleaseChecklistItem::ready)
                .count();
    }

    private static int countReadyHandoffReceipts(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .HandoffReceipt> entries
    ) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .HandoffReceipt::ready)
                .count();
    }

    private static int countScorecard(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .ScorecardEntry> entries
    ) {
        return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
    }
}
