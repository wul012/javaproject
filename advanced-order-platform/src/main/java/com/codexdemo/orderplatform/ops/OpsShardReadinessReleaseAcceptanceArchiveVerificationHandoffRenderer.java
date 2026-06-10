package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRenderer {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRenderer() {
    }

    static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection> render(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.SourceArchiveSnapshot>
                    sourceArchiveSnapshots,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.VerificationRequirement>
                    verificationRequirements,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck>
                    artifactCrossChecks,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff> routeHandoffs,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction>
                    operatorInstructions,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof> ciProofs,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard> boundaryGuards,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard> retentionGuards,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff>
                    closeoutHandoffs,
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry> scorecard
    ) {
        return List.of(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceRenderer.render(
                        sourceArchiveSnapshots),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRequirementRenderer.render(
                        verificationRequirements),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactRenderer.render(
                        artifactCrossChecks),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRouteRenderer.render(routeHandoffs),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffOperatorRenderer.render(
                        operatorInstructions),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiRenderer.render(ciProofs),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffBoundaryRenderer.render(boundaryGuards),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionRenderer.render(retentionGuards),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCloseoutRenderer.render(closeoutHandoffs),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffScorecardRenderer.render(scorecard)
        );
    }
}
