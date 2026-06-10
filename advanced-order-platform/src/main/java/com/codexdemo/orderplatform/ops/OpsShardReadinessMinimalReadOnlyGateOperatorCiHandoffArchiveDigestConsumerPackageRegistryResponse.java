package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse(
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
        String sourceDigestVersion,
        String sourceDigestEndpoint,
        String sourceDigestState,
        String consumerPackageState,
        int sourceDigestSnapshotCount,
        int manifestEntryCount,
        int passedManifestEntryCount,
        int consumerAudienceCount,
        int readyConsumerAudienceCount,
        int packageSectionCount,
        int readyPackageSectionCount,
        int acceptanceCriterionCount,
        int passedAcceptanceCriterionCount,
        int ciMatrixEntryCount,
        int readOnlyCiMatrixEntryCount,
        int boundaryLockCount,
        int lockedBoundaryLockCount,
        int handoffChecklistCount,
        int readyHandoffChecklistCount,
        int scorecardEntryCount,
        int passedScorecardEntryCount,
        int markdownSectionCount,
        List<SourceDigestSnapshot> sourceDigestSnapshots,
        List<ManifestEntry> manifest,
        List<ConsumerAudience> consumerAudiences,
        List<PackageSection> packageSections,
        List<AcceptanceCriterion> acceptanceCriteria,
        List<CiMatrixEntry> ciMatrix,
        List<BoundaryLock> boundaryLocks,
        List<HandoffChecklistItem> handoffChecklist,
        List<ScorecardEntry> scorecard,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceDigestSnapshot(
            String version,
            String endpoint,
            String profile,
            String sourceArchiveVersion,
            String digestState,
            int digestSectionCount,
            int consumerPacketCount,
            int replayInstructionCount,
            int boundaryLockCount,
            String status
    ) {
    }

    public record ManifestEntry(
            String name,
            String value,
            boolean required,
            String status
    ) {
    }

    public record ConsumerAudience(
            String audience,
            String owner,
            String packet,
            boolean ready,
            String status
    ) {
    }

    public record PackageSection(
            String section,
            String owner,
            String sourceEvidence,
            boolean ready,
            String status
    ) {
    }

    public record AcceptanceCriterion(
            String code,
            String evidence,
            boolean passed,
            String status
    ) {
    }

    public record CiMatrixEntry(
            int order,
            String batch,
            String commandFamily,
            boolean readOnly,
            boolean sourcePassed,
            String status
    ) {
    }

    public record BoundaryLock(
            String code,
            String lockedBehavior,
            boolean locked,
            String reason
    ) {
    }

    public record HandoffChecklistItem(
            int order,
            String item,
            String owner,
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
