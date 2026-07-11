package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryRenderer() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .MarkdownSection>
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .SourceArchiveSnapshot>
              sources,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .DigestSection>
              digestSections,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .ConsumerPacket>
              consumerPackets,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .ReplayInstruction>
              replayInstructions,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .BoundaryLock>
              boundaryLocks,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .ScorecardEntry>
              scorecard) {
    return List.of(
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceRenderer.render(
            sources),
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionRenderer.render(
            digestSections),
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketRenderer
            .render(consumerPackets),
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionRenderer
            .render(replayInstructions),
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockRenderer
            .render(boundaryLocks),
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardRenderer.render(
            scorecard));
  }
}
