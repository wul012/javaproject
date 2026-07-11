package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardCatalog {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .ScorecardEntry>
      scorecard(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              source,
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
              boundaryLocks) {
    return List.of(
        score("source-archive-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score(
            "digest-sections",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport
                .EXPECTED_DIGEST_SECTION_COUNT,
            passedDigestSections(digestSections)),
        score(
            "consumer-packets",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport
                .EXPECTED_CONSUMER_PACKET_COUNT,
            readyConsumerPackets(consumerPackets)),
        score(
            "read-only-replay-instructions",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport
                .EXPECTED_REPLAY_INSTRUCTION_COUNT,
            readOnlyReplayInstructions(replayInstructions)),
        score(
            "boundary-locks",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport
                .EXPECTED_BOUNDARY_LOCK_COUNT,
            lockedBoundaryCount(boundaryLocks)),
        score(
            "source-archive-scorecard",
            source.scorecardEntryCount(),
            source.passedScorecardEntryCount()));
  }

  private static int passedDigestSections(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .DigestSection>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int readyConsumerPackets(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .ConsumerPacket>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .ConsumerPacket
                    ::ready)
            .count();
  }

  private static int readOnlyReplayInstructions(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .ReplayInstruction>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .ReplayInstruction
                    ::readOnly)
            .count();
  }

  private static int lockedBoundaryCount(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .BoundaryLock>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .BoundaryLock
                    ::locked)
            .count();
  }

  private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
          .ScorecardEntry
      score(String name, int expected, int actual) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
        .ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }
}
