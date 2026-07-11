package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactVerificationCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactVerificationCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .ArtifactVerification>
      artifactVerifications(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return List.of(
        artifact(
            "source-response-json",
            source.version(),
            source.endpoint(),
            "passed".equals(source.status())),
        artifact(
            "markdown-section-rendering",
            source.version(),
            "markdown-sections=" + source.markdownSections().size(),
            source.markdownSections().size()
                == OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                        .EXPECTED_MARKDOWN_SECTION_COUNT
                    - 1),
        artifact(
            "operator-lane-plan",
            source.version(),
            "operator-lanes=" + source.readyOperatorLaneCount() + "/" + source.operatorLaneCount(),
            source.readyOperatorLaneCount() == source.operatorLaneCount()),
        artifact(
            "ci-batch-plan",
            source.version(),
            "ci-batches=" + source.passedCiBatchCount() + "/" + source.ciBatchCount(),
            source.passedCiBatchCount() == source.ciBatchCount()),
        artifact(
            "boundary-lock-plan",
            source.version(),
            "boundary-locks=" + source.lockedBoundaryCount() + "/" + source.boundaryLockCount(),
            source.lockedBoundaryCount() == source.boundaryLockCount()),
        artifact(
            "source-scorecard-summary",
            source.version(),
            "scorecard=" + source.passedScorecardEntryCount() + "/" + source.scorecardEntryCount(),
            source.passedScorecardEntryCount() == source.scorecardEntryCount()));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .ArtifactVerification
      artifact(String artifact, String producer, String evidence, boolean archived) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
        .ArtifactVerification(
        artifact, producer, evidence, archived, archived ? "passed" : "blocked");
  }
}
