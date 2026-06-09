package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367";
    static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
    static final String OPERATOR_HANDOFF_PLAN = "Node v369";
    static final String ARCHIVE_STATE =
            "minimal-read-only-gate-operator-ci-handoff-archive-verification-ready";
    static final int EXPECTED_SOURCE_HANDOFF_SNAPSHOT_COUNT = 1;
    static final int EXPECTED_ARTIFACT_VERIFICATION_COUNT = 6;
    static final int EXPECTED_OPERATOR_LANE_VERIFICATION_COUNT = 4;
    static final int EXPECTED_CI_BATCH_VERIFICATION_COUNT = 5;
    static final int EXPECTED_BOUNDARY_VERIFICATION_COUNT = 8;
    static final int EXPECTED_SCORECARD_ENTRY_COUNT = 6;
    static final int EXPECTED_MARKDOWN_SECTION_COUNT = 6;

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            response(
                    String version,
                    String endpoint,
                    String profile,
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse sourceHandoff,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .SourceHandoffSnapshot> sourceHandoffs,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .ArtifactVerification> artifacts,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .OperatorLaneVerification> lanes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .CiBatchVerification> ciBatches,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .BoundaryVerification> boundaries,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .ScorecardEntry> scorecard,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .MarkdownSection> markdownSections
            ) {
        var sourceHandoffCopy = List.copyOf(sourceHandoffs);
        var artifactCopy = List.copyOf(artifacts);
        var laneCopy = List.copyOf(lanes);
        var ciBatchCopy = List.copyOf(ciBatches);
        var boundaryCopy = List.copyOf(boundaries);
        var scorecardCopy = List.copyOf(scorecard);
        var markdownSectionCopy = List.copyOf(markdownSections);
        int passedArtifactCount = countArtifactStatus(artifactCopy);
        int passedLaneCount = countLaneStatus(laneCopy);
        int passedCiBatchCount = countCiBatchStatus(ciBatchCopy);
        int lockedBoundaryCount = (int) boundaryCopy.stream()
                .filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                        .BoundaryVerification::locked)
                .count();
        int passedBoundaryCount = countBoundaryStatus(boundaryCopy);
        int passedScorecardCount = countScorecardStatus(scorecardCopy);

        List<String> checks = new ArrayList<>();
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-source-plan-"
                + SOURCE_PLAN);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-required-archive-"
                + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-operator-plan-"
                + OPERATOR_HANDOFF_PLAN);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-source-handoff-version-"
                + sourceHandoff.version());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-source-handoff-status-"
                + sourceHandoff.status());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-source-handoff-count-"
                + sourceHandoffCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-artifact-count-"
                + artifactCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-passed-artifact-count-"
                + passedArtifactCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-lane-count-"
                + laneCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-passed-lane-count-"
                + passedLaneCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-ci-batch-count-"
                + ciBatchCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-passed-ci-batch-count-"
                + passedCiBatchCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-boundary-count-"
                + boundaryCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-locked-boundary-count-"
                + lockedBoundaryCount);
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-scorecard-count-"
                + scorecardCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-markdown-section-count-"
                + markdownSectionCopy.size());
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-no-upstream-autostart");
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-no-write-routing");
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-no-secret-value");
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-no-raw-endpoint-resolution");
        checks.add("minimal-read-only-gate-operator-ci-handoff-archive-no-managed-audit-http");

        String status = "passed".equals(sourceHandoff.status())
                && sourceHandoffCopy.size() == EXPECTED_SOURCE_HANDOFF_SNAPSHOT_COUNT
                && artifactCopy.size() == EXPECTED_ARTIFACT_VERIFICATION_COUNT
                && passedArtifactCount == artifactCopy.size()
                && laneCopy.size() == EXPECTED_OPERATOR_LANE_VERIFICATION_COUNT
                && passedLaneCount == laneCopy.size()
                && ciBatchCopy.size() == EXPECTED_CI_BATCH_VERIFICATION_COUNT
                && passedCiBatchCount == ciBatchCopy.size()
                && boundaryCopy.size() == EXPECTED_BOUNDARY_VERIFICATION_COUNT
                && lockedBoundaryCount == boundaryCopy.size()
                && passedBoundaryCount == boundaryCopy.size()
                && scorecardCopy.size() == EXPECTED_SCORECARD_ENTRY_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownSectionCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
                ? "passed"
                : "blocked";

        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse(
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
                sourceHandoff.version(),
                sourceHandoff.endpoint(),
                sourceHandoff.handoffState(),
                ARCHIVE_STATE,
                sourceHandoffCopy.size(),
                artifactCopy.size(),
                passedArtifactCount,
                laneCopy.size(),
                passedLaneCount,
                ciBatchCopy.size(),
                passedCiBatchCount,
                boundaryCopy.size(),
                lockedBoundaryCount,
                passedBoundaryCount,
                scorecardCopy.size(),
                passedScorecardCount,
                markdownSectionCopy.size(),
                sourceHandoffCopy,
                artifactCopy,
                laneCopy,
                ciBatchCopy,
                boundaryCopy,
                scorecardCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                status
        );
    }

    private static int countArtifactStatus(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .ArtifactVerification> entries
    ) {
        return (int) entries.stream()
                .filter(entry -> "passed".equals(entry.status()))
                .count();
    }

    private static int countLaneStatus(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .OperatorLaneVerification> entries
    ) {
        return (int) entries.stream()
                .filter(entry -> "passed".equals(entry.status()))
                .count();
    }

    private static int countCiBatchStatus(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .CiBatchVerification> entries
    ) {
        return (int) entries.stream()
                .filter(entry -> "passed".equals(entry.status()))
                .count();
    }

    private static int countBoundaryStatus(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .BoundaryVerification> entries
    ) {
        return (int) entries.stream()
                .filter(entry -> "passed".equals(entry.status()))
                .count();
    }

    private static int countScorecardStatus(
            List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .ScorecardEntry> entries
    ) {
        return (int) entries.stream()
                .filter(entry -> "passed".equals(entry.status()))
                .count();
    }
}
