package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardCatalog {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .ScorecardEntry>
      scorecard(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .ArtifactVerification>
              artifacts,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .OperatorLaneVerification>
              lanes,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .CiBatchVerification>
              ciBatches,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .BoundaryVerification>
              boundaries) {
    return List.of(
        score("source-handoff-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score(
            "artifact-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_ARTIFACT_VERIFICATION_COUNT,
            passedArtifacts(artifacts)),
        score(
            "operator-lane-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_OPERATOR_LANE_VERIFICATION_COUNT,
            passedLanes(lanes)),
        score(
            "ci-batch-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_CI_BATCH_VERIFICATION_COUNT,
            passedCiBatches(ciBatches)),
        score(
            "boundary-lock-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_BOUNDARY_VERIFICATION_COUNT,
            lockedBoundaries(boundaries)),
        score(
            "source-handoff-scorecard",
            source.scorecardEntryCount(),
            source.passedScorecardEntryCount()));
  }

  private static int passedArtifacts(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                  .ArtifactVerification>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int passedLanes(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                  .OperatorLaneVerification>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int passedCiBatches(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                  .CiBatchVerification>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int lockedBoundaries(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                  .BoundaryVerification>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                        .BoundaryVerification
                    ::locked)
            .count();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .ScorecardEntry
      score(String name, int expected, int actual) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
        .ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }
}
