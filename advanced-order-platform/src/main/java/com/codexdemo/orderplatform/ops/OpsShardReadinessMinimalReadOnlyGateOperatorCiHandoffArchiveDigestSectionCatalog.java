package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionCatalog {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .DigestSection>
      digestSections(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              source) {
    return List.of(
        section(
            "source-handoff-snapshot",
            1,
            "passed".equals(source.status()) ? 1 : 0,
            source.sourceHandoffVersion()),
        section(
            "artifact-verifications",
            source.artifactVerificationCount(),
            source.passedArtifactVerificationCount(),
            "artifacts="
                + source.passedArtifactVerificationCount()
                + "/"
                + source.artifactVerificationCount()),
        section(
            "operator-lane-verifications",
            source.operatorLaneVerificationCount(),
            source.passedOperatorLaneVerificationCount(),
            "lanes="
                + source.passedOperatorLaneVerificationCount()
                + "/"
                + source.operatorLaneVerificationCount()),
        section(
            "ci-batch-verifications",
            source.ciBatchVerificationCount(),
            source.passedCiBatchVerificationCount(),
            "ci-batches="
                + source.passedCiBatchVerificationCount()
                + "/"
                + source.ciBatchVerificationCount()),
        section(
            "boundary-lock-verifications",
            source.boundaryVerificationCount(),
            source.passedBoundaryVerificationCount(),
            "boundaries="
                + source.lockedBoundaryVerificationCount()
                + "/"
                + source.boundaryVerificationCount()),
        section(
            "source-archive-scorecard",
            source.scorecardEntryCount(),
            source.passedScorecardEntryCount(),
            "scorecard="
                + source.passedScorecardEntryCount()
                + "/"
                + source.scorecardEntryCount()));
  }

  private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
          .DigestSection
      section(String name, int sourceTotal, int sourcePassed, String evidence) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
        .DigestSection(
        name,
        sourceTotal,
        sourcePassed,
        evidence,
        sourceTotal == sourcePassed ? "passed" : "blocked");
  }
}
