package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse(
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
        String sourceArchiveVersion,
        String sourceArchiveEndpoint,
        String sourceArchiveState,
        String digestState,
        int sourceArchiveSnapshotCount,
        int digestSectionCount,
        int passedDigestSectionCount,
        int consumerPacketCount,
        int readyConsumerPacketCount,
        int replayInstructionCount,
        int readOnlyReplayInstructionCount,
        int boundaryLockCount,
        int lockedBoundaryCount,
        int scorecardEntryCount,
        int passedScorecardEntryCount,
        int markdownSectionCount,
        List<SourceArchiveSnapshot> sourceArchiveSnapshots,
        List<DigestSection> digestSections,
        List<ConsumerPacket> consumerPackets,
        List<ReplayInstruction> replayInstructions,
        List<BoundaryLock> boundaryLocks,
        List<ScorecardEntry> scorecard,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceArchiveSnapshot(
            String version,
            String endpoint,
            String profile,
            String sourceHandoffVersion,
            String archiveState,
            int artifactVerificationCount,
            int operatorLaneVerificationCount,
            int ciBatchVerificationCount,
            int boundaryVerificationCount,
            String status
    ) {
    }

    public record DigestSection(
            String name,
            int sourceTotal,
            int sourcePassed,
            String evidence,
            String status
    ) {
    }

    public record ConsumerPacket(
            String packet,
            String owner,
            boolean includesDigest,
            boolean includesBoundaryLocks,
            boolean ready,
            String status
    ) {
    }

    public record ReplayInstruction(
            int order,
            String batch,
            String commandFamily,
            boolean sourcePassed,
            boolean readOnly,
            String instruction,
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
