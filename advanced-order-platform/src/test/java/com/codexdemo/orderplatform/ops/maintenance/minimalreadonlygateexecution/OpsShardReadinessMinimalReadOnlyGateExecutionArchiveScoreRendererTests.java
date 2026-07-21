package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveScoreRendererTests {

  @Test
  void verifiesCiHandoffAndScorecardCounts() {
    var response = ArchiveTestData.registry();

    assertThat(response.ciBatchVerificationCount())
        .isEqualTo(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_CI_BATCH_VERIFICATION_COUNT);
    assertThat(response.passedCiBatchVerificationCount())
        .isEqualTo(response.ciBatchVerificationCount());
    assertThat(response.operatorHandoffVerificationCount())
        .isEqualTo(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_OPERATOR_HANDOFF_VERIFICATION_COUNT);
    assertThat(response.passedOperatorHandoffVerificationCount())
        .isEqualTo(response.operatorHandoffVerificationCount());
    assertThat(response.scorecardEntryCount())
        .isEqualTo(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_SCORECARD_ENTRY_COUNT);
    assertThat(response.scorecard())
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
  }

  @Test
  void rendersStableArchiveVerificationMarkdownSections() {
    var response = ArchiveTestData.registry();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Registry",
            "Archive Artifacts",
            "Read Target Verification",
            "Gate Check Verification",
            "Boundary Verification",
            "CI Handoff Scorecard");
    assertThat(response.markdownSections().get(1).lines().get(0))
        .isEqualTo("artifact-verification-count=6");
    assertThat(response.markdownSections().get(3).lines().get(0))
        .isEqualTo("gate-check-verification-count=20");
    assertThat(response.markdownSections().get(4).lines())
        .anySatisfy(line -> assertThat(line).contains("no-write-routing", "denied=true"));
  }
}
