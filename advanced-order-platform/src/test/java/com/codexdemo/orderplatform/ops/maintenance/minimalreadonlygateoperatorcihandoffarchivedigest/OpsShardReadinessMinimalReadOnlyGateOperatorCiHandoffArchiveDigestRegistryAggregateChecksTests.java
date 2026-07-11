package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryAggregateChecksTests {

  @Test
  void aggregateChecksRemainStableAndBoundaryFocused() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
            .registry();

    assertThat(response.checks()).hasSize(22);
    assertThat(response.boundaryLockCount()).isEqualTo(8);
    assertThat(response.lockedBoundaryCount()).isEqualTo(8);
    assertThat(response.scorecardEntryCount()).isEqualTo(6);
    assertThat(response.passedScorecardEntryCount()).isEqualTo(6);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-plan-Node v367",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-required-archive-Node v368",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-operator-plan-Node v369",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-archive-version-Java v1377",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-archive-status-passed",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-section-count-6",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-replay-instruction-count-5",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-boundary-lock-count-8",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-no-upstream-autostart",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-no-write-routing",
            "minimal-read-only-gate-operator-ci-handoff-archive-digest-no-secret-value");
    assertThat(response.status()).isEqualTo("passed");
  }
}
