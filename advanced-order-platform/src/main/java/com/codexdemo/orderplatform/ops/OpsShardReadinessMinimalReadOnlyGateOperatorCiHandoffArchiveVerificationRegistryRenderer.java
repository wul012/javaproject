package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryRenderer() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            .MarkdownSection> render(
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
                            .ScorecardEntry> scorecard
            ) {
        return List.of(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceRenderer.render(
                        sourceHandoffs),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactRenderer.render(
                        artifacts),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneRenderer.render(
                        lanes),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchRenderer.render(
                        ciBatches),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryRenderer.render(
                        boundaries),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardRenderer.render(
                        scorecard)
        );
    }
}
