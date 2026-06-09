package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse(
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
        String recommendedNextPlan,
        String sourceRegistryVersion,
        String sourceRegistryEndpoint,
        String archiveState,
        int artifactVerificationCount,
        int passedArtifactVerificationCount,
        int readTargetVerificationCount,
        int passedReadTargetVerificationCount,
        int gateCheckVerificationCount,
        int passedGateCheckVerificationCount,
        int boundaryVerificationCount,
        int deniedBoundaryVerificationCount,
        int ciBatchVerificationCount,
        int passedCiBatchVerificationCount,
        int operatorHandoffVerificationCount,
        int passedOperatorHandoffVerificationCount,
        int scorecardEntryCount,
        List<SourceRegistrySnapshot> sourceRegistrySnapshots,
        List<ArtifactVerification> artifactVerifications,
        List<ReadTargetVerification> readTargetVerifications,
        List<GateCheckVerification> gateCheckVerifications,
        List<BoundaryVerification> boundaryVerifications,
        List<CiBatchVerification> ciBatchVerifications,
        List<OperatorHandoffVerification> operatorHandoffVerifications,
        List<ScorecardEntry> scorecard,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceRegistrySnapshot(
            String version,
            String endpoint,
            String profile,
            String sourcePlan,
            int readTargetCount,
            int gateCheckCount,
            int boundaryRuleCount,
            String status
    ) {
    }

    public record ArtifactVerification(
            String artifact,
            String producer,
            String evidence,
            boolean archived,
            String status
    ) {
    }

    public record ReadTargetVerification(
            String target,
            String commandOrRoute,
            String sourceStatus,
            boolean archived,
            String status
    ) {
    }

    public record GateCheckVerification(
            String code,
            String group,
            boolean sourcePassed,
            boolean archived,
            String status
    ) {
    }

    public record BoundaryVerification(
            String code,
            String forbiddenAction,
            boolean allowed,
            boolean denied,
            String status
    ) {
    }

    public record CiBatchVerification(
            String name,
            int order,
            String commandFamily,
            boolean archived,
            String status
    ) {
    }

    public record OperatorHandoffVerification(
            String step,
            String owner,
            boolean manual,
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
