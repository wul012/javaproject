package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryBoundaryScorecardTests {

  @Test
  void archivesBoundaryLocksWithoutOpeningRuntimeActions() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
            .registry();

    assertThat(response.boundaryVerificationCount()).isEqualTo(8);
    assertThat(response.lockedBoundaryVerificationCount()).isEqualTo(8);
    assertThat(response.passedBoundaryVerificationCount()).isEqualTo(8);
    assertThat(response.boundaryVerifications())
        .allSatisfy(
            boundary -> {
              assertThat(boundary.locked()).isTrue();
              assertThat(boundary.archived()).isTrue();
              assertThat(boundary.status()).isEqualTo("passed");
            });
    assertThat(response.boundaryVerifications())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .BoundaryVerification
                ::code)
        .contains(
            "no-java-autostart",
            "no-mini-kv-autostart",
            "no-write-routing",
            "no-credential-value",
            "no-managed-audit-http");
  }

  @Test
  void scorecardRequiresEveryArchiveVerificationToPass() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
            .registry();

    assertThat(response.scorecardEntryCount()).isEqualTo(6);
    assertThat(response.passedScorecardEntryCount()).isEqualTo(6);
    assertThat(response.scorecard())
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
    assertThat(response.scorecard())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .ScorecardEntry
                ::name)
        .containsExactly(
            "source-handoff-status",
            "artifact-verifications",
            "operator-lane-verifications",
            "ci-batch-verifications",
            "boundary-lock-verifications",
            "source-handoff-scorecard");
  }
}
