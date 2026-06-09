package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367";
    static final String RECOMMENDED_NEXT_PLAN = "Node v368";
    static final String ARCHIVE_STATE =
            "minimal-read-only-gate-execution-archive-verification-ready";
    static final int EXPECTED_SOURCE_REGISTRY_COUNT = 1;
    static final int EXPECTED_ARTIFACT_VERIFICATION_COUNT = 6;
    static final int EXPECTED_READ_TARGET_VERIFICATION_COUNT = 5;
    static final int EXPECTED_GATE_CHECK_VERIFICATION_COUNT = 20;
    static final int EXPECTED_BOUNDARY_VERIFICATION_COUNT = 10;
    static final int EXPECTED_CI_BATCH_VERIFICATION_COUNT = 4;
    static final int EXPECTED_OPERATOR_HANDOFF_VERIFICATION_COUNT = 5;
    static final int EXPECTED_SCORECARD_ENTRY_COUNT = 7;

    private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .SourceRegistrySnapshot> sourceRegistrySnapshots,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .ArtifactVerification> artifactVerifications,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .ReadTargetVerification> readTargetVerifications,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .GateCheckVerification> gateCheckVerifications,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .BoundaryVerification> boundaryVerifications,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .CiBatchVerification> ciBatchVerifications,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .OperatorHandoffVerification> operatorHandoffVerifications,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .ScorecardEntry> scorecard,
            List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .MarkdownSection> markdownSections
    ) {
        var sourceRegistrySnapshotCopy = List.copyOf(sourceRegistrySnapshots);
        var artifactVerificationCopy = List.copyOf(artifactVerifications);
        var readTargetVerificationCopy = List.copyOf(readTargetVerifications);
        var gateCheckVerificationCopy = List.copyOf(gateCheckVerifications);
        var boundaryVerificationCopy = List.copyOf(boundaryVerifications);
        var ciBatchVerificationCopy = List.copyOf(ciBatchVerifications);
        var operatorHandoffVerificationCopy = List.copyOf(operatorHandoffVerifications);
        var scorecardCopy = List.copyOf(scorecard);
        var markdownSectionCopy = List.copyOf(markdownSections);
        int passedArtifactVerificationCount = countStatus(artifactVerificationCopy);
        int passedReadTargetVerificationCount = countStatus(readTargetVerificationCopy);
        int passedGateCheckVerificationCount = countStatus(gateCheckVerificationCopy);
        int deniedBoundaryVerificationCount = (int) boundaryVerificationCopy.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                        .BoundaryVerification::denied)
                .count();
        int passedCiBatchVerificationCount = countStatus(ciBatchVerificationCopy);
        int passedOperatorHandoffVerificationCount = countStatus(operatorHandoffVerificationCopy);
        int passedScorecardCount = countStatus(scorecardCopy);
        List<String> checks = new ArrayList<>();
        checks.add("minimal-read-only-gate-execution-archive-source-plan-" + SOURCE_PLAN);
        checks.add("minimal-read-only-gate-execution-archive-next-plan-" + RECOMMENDED_NEXT_PLAN);
        checks.add("minimal-read-only-gate-execution-archive-source-registry-count-"
                + sourceRegistrySnapshotCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-artifact-count-"
                + artifactVerificationCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-passed-artifact-count-"
                + passedArtifactVerificationCount);
        checks.add("minimal-read-only-gate-execution-archive-read-target-count-"
                + readTargetVerificationCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-passed-read-target-count-"
                + passedReadTargetVerificationCount);
        checks.add("minimal-read-only-gate-execution-archive-gate-check-count-"
                + gateCheckVerificationCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-passed-gate-check-count-"
                + passedGateCheckVerificationCount);
        checks.add("minimal-read-only-gate-execution-archive-boundary-count-"
                + boundaryVerificationCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-denied-boundary-count-"
                + deniedBoundaryVerificationCount);
        checks.add("minimal-read-only-gate-execution-archive-ci-batch-count-"
                + ciBatchVerificationCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-operator-handoff-count-"
                + operatorHandoffVerificationCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-scorecard-count-"
                + scorecardCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-markdown-section-count-"
                + markdownSectionCopy.size());
        checks.add("minimal-read-only-gate-execution-archive-no-upstream-autostart");
        checks.add("minimal-read-only-gate-execution-archive-no-write-routing");
        checks.add("minimal-read-only-gate-execution-archive-no-secret-value");
        checks.add("minimal-read-only-gate-execution-archive-no-raw-endpoint-resolution");
        checks.add("minimal-read-only-gate-execution-archive-no-managed-audit-http");

        String status = passedArtifactVerificationCount == artifactVerificationCopy.size()
                && passedReadTargetVerificationCount == readTargetVerificationCopy.size()
                && passedGateCheckVerificationCount == gateCheckVerificationCopy.size()
                && deniedBoundaryVerificationCount == boundaryVerificationCopy.size()
                && passedCiBatchVerificationCount == ciBatchVerificationCopy.size()
                && passedOperatorHandoffVerificationCount == operatorHandoffVerificationCopy.size()
                && passedScorecardCount == scorecardCopy.size()
                ? "passed"
                : "blocked";

        return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse(
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
                RECOMMENDED_NEXT_PLAN,
                sourceRegistry.version(),
                sourceRegistry.endpoint(),
                ARCHIVE_STATE,
                artifactVerificationCopy.size(),
                passedArtifactVerificationCount,
                readTargetVerificationCopy.size(),
                passedReadTargetVerificationCount,
                gateCheckVerificationCopy.size(),
                passedGateCheckVerificationCount,
                boundaryVerificationCopy.size(),
                deniedBoundaryVerificationCount,
                ciBatchVerificationCopy.size(),
                passedCiBatchVerificationCount,
                operatorHandoffVerificationCopy.size(),
                passedOperatorHandoffVerificationCount,
                scorecardCopy.size(),
                sourceRegistrySnapshotCopy,
                artifactVerificationCopy,
                readTargetVerificationCopy,
                gateCheckVerificationCopy,
                boundaryVerificationCopy,
                ciBatchVerificationCopy,
                operatorHandoffVerificationCopy,
                scorecardCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                status
        );
    }

    private static int countStatus(List<?> entries) {
        return (int) entries.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                        ::isPassed)
                .count();
    }

    private static boolean isPassed(Object entry) {
        if (entry instanceof OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .ArtifactVerification artifact) {
            return "passed".equals(artifact.status());
        }
        if (entry instanceof OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .ReadTargetVerification target) {
            return "passed".equals(target.status());
        }
        if (entry instanceof OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .GateCheckVerification check) {
            return "passed".equals(check.status());
        }
        if (entry instanceof OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .CiBatchVerification batch) {
            return "passed".equals(batch.status());
        }
        if (entry instanceof OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .OperatorHandoffVerification handoff) {
            return "passed".equals(handoff.status());
        }
        if (entry instanceof OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .ScorecardEntry score) {
            return "passed".equals(score.status());
        }
        return false;
    }
}
