package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveMarkdownBoundaryTests {

  @Test
  void aggregateArchiveChecksRemainStableAndBoundaryFocused() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport
            .registry();

    assertThat(response.checks()).hasSize(20);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-execution-archive-source-plan-Node v367",
            "minimal-read-only-gate-execution-archive-next-plan-Node v368",
            "minimal-read-only-gate-execution-archive-artifact-count-6",
            "minimal-read-only-gate-execution-archive-passed-artifact-count-6",
            "minimal-read-only-gate-execution-archive-read-target-count-5",
            "minimal-read-only-gate-execution-archive-passed-read-target-count-5",
            "minimal-read-only-gate-execution-archive-gate-check-count-20",
            "minimal-read-only-gate-execution-archive-passed-gate-check-count-20",
            "minimal-read-only-gate-execution-archive-no-upstream-autostart",
            "minimal-read-only-gate-execution-archive-no-managed-audit-http");
    assertThat(response.artifactVerificationCount())
        .isEqualTo(response.passedArtifactVerificationCount());
    assertThat(response.readTargetVerificationCount())
        .isEqualTo(response.passedReadTargetVerificationCount());
    assertThat(response.gateCheckVerificationCount())
        .isEqualTo(response.passedGateCheckVerificationCount());
    assertThat(response.status()).isEqualTo("passed");
  }
}
