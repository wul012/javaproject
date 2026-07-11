package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryAggregateChecksTests {

  @Test
  void aggregateChecksRemainStableAndBoundaryFocused() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
            .registry();

    assertThat(response.checks()).hasSize(21);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-archive-source-plan-Node v367",
            "minimal-read-only-gate-operator-ci-handoff-archive-required-archive-Node v368",
            "minimal-read-only-gate-operator-ci-handoff-archive-operator-plan-Node v369",
            "minimal-read-only-gate-operator-ci-handoff-archive-source-handoff-version-Java v1352",
            "minimal-read-only-gate-operator-ci-handoff-archive-source-handoff-status-passed",
            "minimal-read-only-gate-operator-ci-handoff-archive-artifact-count-6",
            "minimal-read-only-gate-operator-ci-handoff-archive-lane-count-4",
            "minimal-read-only-gate-operator-ci-handoff-archive-ci-batch-count-5",
            "minimal-read-only-gate-operator-ci-handoff-archive-boundary-count-8",
            "minimal-read-only-gate-operator-ci-handoff-archive-no-upstream-autostart",
            "minimal-read-only-gate-operator-ci-handoff-archive-no-write-routing",
            "minimal-read-only-gate-operator-ci-handoff-archive-no-secret-value");
    assertThat(response.status()).isEqualTo("passed");
  }
}
