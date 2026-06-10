package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367";
    static final String ARCHIVE_VERIFICATION_PLAN = "Node v368";
    static final String OPERATOR_HANDOFF_PLAN = "Node v369";
    static final String HANDOFF_STATE = "release-acceptance-archive-verification-handoff-ready";
    static final int EXPECTED_SOURCE_ARCHIVE_SNAPSHOT_COUNT = 1;
    static final int EXPECTED_VERIFICATION_REQUIREMENT_COUNT = 8;
    static final int EXPECTED_ARTIFACT_CROSS_CHECK_COUNT = 7;
    static final int EXPECTED_ROUTE_HANDOFF_COUNT = 4;
    static final int EXPECTED_OPERATOR_INSTRUCTION_COUNT = 4;
    static final int EXPECTED_CI_PROOF_COUNT = 5;
    static final int EXPECTED_BOUNDARY_GUARD_COUNT = 8;
    static final int EXPECTED_RETENTION_GUARD_COUNT = 5;
    static final int EXPECTED_CLOSEOUT_HANDOFF_COUNT = 6;
    static final int EXPECTED_SCORECARD_ENTRY_COUNT = 9;
    static final int EXPECTED_MARKDOWN_SECTION_COUNT = 10;

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse response(
            String version,
            String endpoint,
            String profile,
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    source,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.SourceArchiveSnapshot>
                    sourceArchiveSnapshots,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.VerificationRequirement>
                    verificationRequirements,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck>
                    artifactCrossChecks,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff>
                    routeHandoffs,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction>
                    operatorInstructions,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof>
                    ciProofs,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard>
                    boundaryGuards,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard>
                    retentionGuards,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff>
                    closeoutHandoffs,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry>
                    scorecard,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection>
                    markdownSections
    ) {
        var sourceArchiveCopy = List.copyOf(sourceArchiveSnapshots);
        var requirementCopy = List.copyOf(verificationRequirements);
        var artifactCopy = List.copyOf(artifactCrossChecks);
        var routeCopy = List.copyOf(routeHandoffs);
        var operatorCopy = List.copyOf(operatorInstructions);
        var ciCopy = List.copyOf(ciProofs);
        var boundaryCopy = List.copyOf(boundaryGuards);
        var retentionCopy = List.copyOf(retentionGuards);
        var closeoutCopy = List.copyOf(closeoutHandoffs);
        var scorecardCopy = List.copyOf(scorecard);
        var markdownCopy = List.copyOf(markdownSections);

        int passedRequirementCount = countPassedRequirements(requirementCopy);
        int passedArtifactCount = countMatchedArtifacts(artifactCopy);
        int readyRouteCount = countReadyRoutes(routeCopy);
        int readyOperatorCount = countReadyOperators(operatorCopy);
        int passedCiCount = countPassedCi(ciCopy);
        int lockedBoundaryCount = countLockedBoundary(boundaryCopy);
        int readyRetentionCount = countReadyRetention(retentionCopy);
        int readyCloseoutCount = countReadyCloseout(closeoutCopy);
        int passedScorecardCount = countPassedScorecard(scorecardCopy);

        List<String> checks = new ArrayList<>();
        checks.add("release-acceptance-archive-verification-handoff-source-plan-" + SOURCE_PLAN);
        checks.add("release-acceptance-archive-verification-handoff-archive-verification-plan-"
                + ARCHIVE_VERIFICATION_PLAN);
        checks.add("release-acceptance-archive-verification-handoff-operator-plan-" + OPERATOR_HANDOFF_PLAN);
        checks.add("release-acceptance-archive-verification-handoff-source-archive-version-" + source.version());
        checks.add("release-acceptance-archive-verification-handoff-source-archive-state-"
                + source.archiveRegistryState());
        checks.add("release-acceptance-archive-verification-handoff-source-count-" + sourceArchiveCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-requirement-count-" + requirementCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-passed-requirement-count-"
                + passedRequirementCount);
        checks.add("release-acceptance-archive-verification-handoff-artifact-count-" + artifactCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-passed-artifact-count-" + passedArtifactCount);
        checks.add("release-acceptance-archive-verification-handoff-route-count-" + routeCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-ready-route-count-" + readyRouteCount);
        checks.add("release-acceptance-archive-verification-handoff-operator-count-" + operatorCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-ready-operator-count-" + readyOperatorCount);
        checks.add("release-acceptance-archive-verification-handoff-ci-proof-count-" + ciCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-passed-ci-proof-count-" + passedCiCount);
        checks.add("release-acceptance-archive-verification-handoff-boundary-count-" + boundaryCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-locked-boundary-count-" + lockedBoundaryCount);
        checks.add("release-acceptance-archive-verification-handoff-retention-count-" + retentionCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-ready-retention-count-" + readyRetentionCount);
        checks.add("release-acceptance-archive-verification-handoff-closeout-count-" + closeoutCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-ready-closeout-count-" + readyCloseoutCount);
        checks.add("release-acceptance-archive-verification-handoff-scorecard-count-" + scorecardCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-passed-scorecard-count-"
                + passedScorecardCount);
        checks.add("release-acceptance-archive-verification-handoff-markdown-section-count-" + markdownCopy.size());
        checks.add("release-acceptance-archive-verification-handoff-consumes-archive-registry");
        checks.add("release-acceptance-archive-verification-handoff-no-upstream-autostart");
        checks.add("release-acceptance-archive-verification-handoff-no-write-routing");
        checks.add("release-acceptance-archive-verification-handoff-no-secret-value");
        checks.add("release-acceptance-archive-verification-handoff-no-raw-endpoint-resolution");
        checks.add("release-acceptance-archive-verification-handoff-no-managed-audit-http");
        checks.add("release-acceptance-archive-verification-handoff-no-runtime-execution");
        checks.add("release-acceptance-archive-verification-handoff-no-deployment-rollback");

        String status = "passed".equals(source.status())
                && source.readOnly()
                && !source.executionAllowed()
                && !source.startsJavaService()
                && !source.startsMiniKvService()
                && !source.readsCredentialValue()
                && !source.resolvesRawEndpointUrl()
                && !source.managedAuditHttpAllowed()
                && sourceArchiveCopy.size() == EXPECTED_SOURCE_ARCHIVE_SNAPSHOT_COUNT
                && requirementCopy.size() == EXPECTED_VERIFICATION_REQUIREMENT_COUNT
                && passedRequirementCount == requirementCopy.size()
                && artifactCopy.size() == EXPECTED_ARTIFACT_CROSS_CHECK_COUNT
                && passedArtifactCount == artifactCopy.size()
                && routeCopy.size() == EXPECTED_ROUTE_HANDOFF_COUNT
                && readyRouteCount == routeCopy.size()
                && operatorCopy.size() == EXPECTED_OPERATOR_INSTRUCTION_COUNT
                && readyOperatorCount == operatorCopy.size()
                && ciCopy.size() == EXPECTED_CI_PROOF_COUNT
                && passedCiCount == ciCopy.size()
                && boundaryCopy.size() == EXPECTED_BOUNDARY_GUARD_COUNT
                && lockedBoundaryCount == boundaryCopy.size()
                && retentionCopy.size() == EXPECTED_RETENTION_GUARD_COUNT
                && readyRetentionCount == retentionCopy.size()
                && closeoutCopy.size() == EXPECTED_CLOSEOUT_HANDOFF_COUNT
                && readyCloseoutCount == closeoutCopy.size()
                && scorecardCopy.size() == EXPECTED_SCORECARD_ENTRY_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
                ? "passed"
                : "blocked";

        return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse(
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
                ARCHIVE_VERIFICATION_PLAN,
                OPERATOR_HANDOFF_PLAN,
                source.version(),
                source.endpoint(),
                source.archiveRegistryState(),
                HANDOFF_STATE,
                sourceArchiveCopy.size(),
                requirementCopy.size(),
                passedRequirementCount,
                artifactCopy.size(),
                passedArtifactCount,
                routeCopy.size(),
                readyRouteCount,
                operatorCopy.size(),
                readyOperatorCount,
                ciCopy.size(),
                passedCiCount,
                boundaryCopy.size(),
                lockedBoundaryCount,
                retentionCopy.size(),
                readyRetentionCount,
                closeoutCopy.size(),
                readyCloseoutCount,
                scorecardCopy.size(),
                passedScorecardCount,
                markdownCopy.size(),
                sourceArchiveCopy,
                requirementCopy,
                artifactCopy,
                routeCopy,
                operatorCopy,
                ciCopy,
                boundaryCopy,
                retentionCopy,
                closeoutCopy,
                scorecardCopy,
                markdownCopy,
                List.copyOf(checks),
                status
        );
    }

    private static int countPassedRequirements(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.VerificationRequirement>
                    requirements
    ) {
        return (int) requirements.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.VerificationRequirement
                        ::passed)
                .count();
    }

    private static int countMatchedArtifacts(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck> artifacts
    ) {
        return (int) artifacts.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck
                        ::matched)
                .count();
    }

    private static int countReadyRoutes(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff> routes
    ) {
        return (int) routes.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff::ready)
                .count();
    }

    private static int countReadyOperators(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction> operators
    ) {
        return (int) operators.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction
                        ::ready)
                .count();
    }

    private static int countPassedCi(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof> ciProofs
    ) {
        return (int) ciProofs.stream().filter(ci -> "passed".equals(ci.status())).count();
    }

    private static int countLockedBoundary(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard> guards
    ) {
        return (int) guards.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard::locked)
                .count();
    }

    private static int countReadyRetention(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard> guards
    ) {
        return (int) guards.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard::ready)
                .count();
    }

    private static int countReadyCloseout(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff> handoffs
    ) {
        return (int) handoffs.stream()
                .filter(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff::ready)
                .count();
    }

    private static int countPassedScorecard(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry> scorecard
    ) {
        return (int) scorecard.stream().filter(score -> "passed".equals(score.status())).count();
    }
}
