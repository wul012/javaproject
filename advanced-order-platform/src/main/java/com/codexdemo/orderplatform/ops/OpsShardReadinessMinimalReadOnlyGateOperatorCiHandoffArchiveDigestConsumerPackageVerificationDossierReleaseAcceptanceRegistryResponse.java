package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse(
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
        String sourceDossierVersion,
        String sourceDossierEndpoint,
        String sourceDossierState,
        String releaseAcceptanceState,
        int sourceDossierSnapshotCount,
        int readinessGateCount,
        int passedReadinessGateCount,
        int evidenceChainEntryCount,
        int passedEvidenceChainEntryCount,
        int signoffLaneCount,
        int readySignoffLaneCount,
        int ciReplayLaneCount,
        int readOnlyCiReplayLaneCount,
        int boundaryControlCount,
        int lockedBoundaryControlCount,
        int retentionPolicyCount,
        int readyRetentionPolicyCount,
        int replayDecisionCount,
        int passedReplayDecisionCount,
        int closeoutCheckpointCount,
        int readyCloseoutCheckpointCount,
        int scorecardEntryCount,
        int passedScorecardEntryCount,
        int markdownSectionCount,
        List<SourceDossierSnapshot> sourceDossierSnapshots,
        List<ReleaseReadinessGate> readinessGates,
        List<EvidenceChainEntry> evidenceChain,
        List<SignoffLane> signoffLanes,
        List<CiReplayLane> ciReplayLanes,
        List<BoundaryControl> boundaryControls,
        List<RetentionPolicy> retentionPolicies,
        List<ReplayDecision> replayDecisions,
        List<CloseoutCheckpoint> closeoutCheckpoints,
        List<ScorecardEntry> scorecard,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceDossierSnapshot(
            String version,
            String endpoint,
            String profile,
            String dossierState,
            int sectionDigestCount,
            int audienceRouteCount,
            int ciLaneCount,
            int boundaryAuditCount,
            int handoffReceiptCount,
            String status
    ) {
    }

    public record ReleaseReadinessGate(
            String code,
            String evidence,
            int expected,
            int actual,
            boolean passed,
            String status
    ) {
    }

    public record EvidenceChainEntry(
            int order,
            String artifact,
            String sourceEvidence,
            String releaseTarget,
            boolean passed,
            String status
    ) {
    }

    public record SignoffLane(
            String receiver,
            String owner,
            String evidence,
            boolean ready,
            String status
    ) {
    }

    public record CiReplayLane(
            int order,
            String batch,
            String commandFamily,
            String replayGroup,
            boolean readOnly,
            boolean sourcePassed,
            String status
    ) {
    }

    public record BoundaryControl(
            String code,
            String lockedBehavior,
            String auditEvidence,
            boolean locked,
            String status
    ) {
    }

    public record RetentionPolicy(
            String name,
            String sourceEvidence,
            String retentionWindow,
            boolean ready,
            String status
    ) {
    }

    public record ReplayDecision(
            String code,
            String decision,
            String evidence,
            boolean passed,
            String status
    ) {
    }

    public record CloseoutCheckpoint(
            int order,
            String item,
            String owner,
            String evidence,
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
