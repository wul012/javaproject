package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryBoundaryChecklistTests {

  @Test
  void carriesBoundaryLocksAcceptanceAndChecklist() {
    var response = ConsumerPackageTestData.registry();

    assertThat(response.acceptanceCriterionCount()).isEqualTo(5);
    assertThat(response.passedAcceptanceCriterionCount()).isEqualTo(5);
    assertThat(response.boundaryLockCount()).isEqualTo(8);
    assertThat(response.lockedBoundaryLockCount()).isEqualTo(8);
    assertThat(response.handoffChecklistCount()).isEqualTo(5);
    assertThat(response.readyHandoffChecklistCount()).isEqualTo(5);
    assertThat(response.boundaryLocks())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                    .BoundaryLock
                ::code)
        .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
  }

  @Test
  void scorecardRequiresEveryConsumerPackagePartToPass() {
    var response = ConsumerPackageTestData.registry();

    assertThat(response.scorecardEntryCount()).isEqualTo(8);
    assertThat(response.passedScorecardEntryCount()).isEqualTo(8);
    assertThat(response.scorecard())
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
  }
}
