package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean readsCredentialValue,
        boolean resolvesRawEndpointUrl,
        boolean managedAuditHttpAllowed,
        String endpoint,
        String profile,
        String sourcePlan,
        String requiredArchiveVerificationPlan,
        String operatorHandoffPlan,
        String sourceConsumerPackageVersion,
        String sourceConsumerPackageEndpoint,
        String sourceConsumerPackageState,
        String verificationDossierState,
        int sourcePackageSnapshotCount,
        int provenanceEntryCount,
        int passedProvenanceEntryCount,
        int sectionDigestCount,
        int passedSectionDigestCount,
        int audienceRouteCount,
        int readyAudienceRouteCount,
        int ciLaneCount,
        int readOnlyCiLaneCount,
        int acceptanceGateCount,
        int passedAcceptanceGateCount,
        int boundaryAuditCount,
        int lockedBoundaryAuditCount,
        int releaseChecklistCount,
        int readyReleaseChecklistCount,
        int handoffReceiptCount,
        int readyHandoffReceiptCount,
        int scorecardEntryCount,
        int passedScorecardEntryCount,
        int markdownSectionCount,
        List<SourcePackageSnapshot> sourcePackageSnapshots,
        List<ProvenanceEntry> provenance,
        List<SectionDigest> sectionDigests,
        List<AudienceRoute> audienceRoutes,
        List<CiLane> ciLanes,
        List<AcceptanceGate> acceptanceGates,
        List<BoundaryAudit> boundaryAudits,
        List<ReleaseChecklistItem> releaseChecklist,
        List<HandoffReceipt> handoffReceipts,
        List<ScorecardEntry> scorecard,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourcePackageSnapshot(
            String version,
            String endpoint,
            String profile,
            String consumerPackageState,
            int manifestEntryCount,
            int packageSectionCount,
            int ciMatrixEntryCount,
            int boundaryLockCount,
            int handoffChecklistCount,
            String status
    ) {
    }

    public record ProvenanceEntry(
            String name,
            String value,
            boolean required,
            String status
    ) {
    }

    public record SectionDigest(
            String heading,
            int lineCount,
            boolean required,
            String status
    ) {
    }

    public record AudienceRoute(
            String audience,
            String owner,
            String packet,
            String reviewerLane,
            boolean ready,
            String status
    ) {
    }

    public record CiLane(
            int order,
            String batch,
            String commandFamily,
            boolean readOnly,
            boolean sourcePassed,
            String replayGroup,
            String status
    ) {
    }

    public record AcceptanceGate(
            String code,
            String evidence,
            String verifyingArtifact,
            boolean passed,
            String status
    ) {
    }

    public record BoundaryAudit(
            String code,
            String lockedBehavior,
            boolean locked,
            String auditEvidence,
            String status
    ) {
    }

    public record ReleaseChecklistItem(
            int order,
            String item,
            String owner,
            String releaseEvidence,
            boolean ready,
            String status
    ) {
    }

    public record HandoffReceipt(
            String receiver,
            String sourceEvidence,
            String receiptType,
            boolean ready,
            String status
    ) {
    }

    public record ScorecardEntry(
            String name,
            int expected,
            int actual,
            String status
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
