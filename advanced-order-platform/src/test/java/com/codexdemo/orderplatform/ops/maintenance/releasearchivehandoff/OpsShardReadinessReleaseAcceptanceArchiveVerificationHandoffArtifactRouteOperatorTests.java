package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactRouteOperatorTests {

  @Test
  void carriesArtifactCrossChecksFromArchiveManifest() {
    var response =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

    assertThat(response.artifactCrossCheckCount()).isEqualTo(7);
    assertThat(response.passedArtifactCrossCheckCount()).isEqualTo(7);
    assertThat(response.artifactCrossChecks())
        .extracting(
            OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck
                ::name)
        .containsExactly(
            "source-release-acceptance-version",
            "source-release-acceptance-state",
            "readiness-gates-passed",
            "evidence-chain-passed",
            "signoff-lanes-ready",
            "ci-replay-lanes-read-only",
            "closeout-checkpoints-ready");
  }

  @Test
  void preservesRouteHandoffReceivers() {
    var response =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

    assertThat(response.routeHandoffCount()).isEqualTo(4);
    assertThat(response.readyRouteHandoffCount()).isEqualTo(4);
    assertThat(response.routeHandoffs())
        .extracting(
            OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff
                ::receiver)
        .containsExactly(
            "operator-ci-handoff-owner",
            "node-v368-archive-verifier",
            "node-v369-operator-ci",
            "java-read-only-boundary-owner");
  }

  @Test
  void createsOperatorInstructionsForNodeV368ArchiveVerification() {
    var response =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

    assertThat(response.operatorInstructionCount()).isEqualTo(4);
    assertThat(response.readyOperatorInstructionCount()).isEqualTo(4);
    assertThat(response.operatorInstructions())
        .extracting(
            OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction
                ::order)
        .containsExactly(1, 2, 3, 4);
    assertThat(response.operatorInstructions())
        .allSatisfy(
            instruction -> {
              assertThat(instruction.sourceEvidence()).isNotBlank();
              assertThat(instruction.instruction()).contains("Node v368");
              assertThat(instruction.ready()).isTrue();
              assertThat(instruction.status()).isEqualTo("passed");
            });
  }
}
