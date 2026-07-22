package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HandoffRegistryServiceTests {

  @Test
  void buildsOperatorCiHandoffFromArchiveVerificationRegistry() {
    var response = HandoffTestData.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1352");
    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367");
    assertThat(response.requiredArchiveVerificationPlan()).isEqualTo("Node v368");
    assertThat(response.recommendedOperatorPlan()).isEqualTo("Node v369");
    assertThat(response.sourceArchiveVersion()).isEqualTo("Java v1337");
    assertThat(response.sourceArchiveEndpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-archive-verification-registry");
    assertThat(response.handoffState())
        .isEqualTo("minimal-read-only-gate-operator-ci-handoff-ready");
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsOperatorHandoffStrictlyReadOnly() {
    var response = HandoffTestData.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
    assertThat(response.lockedBoundaryCount()).isEqualTo(response.boundaryLockCount());
  }

  @Test
  void carriesFocusedGroupedBuildSmokeOrder() {
    var response = HandoffTestData.registry();

    assertThat(response.sourceArchiveSnapshotCount())
        .isEqualTo(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport
                .EXPECTED_SOURCE_ARCHIVE_SNAPSHOT_COUNT);
    assertThat(response.operatorLaneCount()).isEqualTo(4);
    assertThat(response.readyOperatorLaneCount()).isEqualTo(4);
    assertThat(response.operatorLanes())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.OperatorLane
                ::lane)
        .containsExactly("focused", "grouped", "build", "smoke");
    assertThat(response.ciBatchCount()).isEqualTo(5);
    assertThat(response.passedCiBatchCount()).isEqualTo(5);
    assertThat(response.ciBatches())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.CiBatchPlan
                ::commandFamily)
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }

  @Test
  void verifiesBoundaryLocksAndScorecard() {
    var response = HandoffTestData.registry();

    assertThat(response.boundaryLockCount()).isEqualTo(8);
    assertThat(response.boundaryLocks()).allSatisfy(lock -> assertThat(lock.locked()).isTrue());
    assertThat(response.boundaryLocks())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.BoundaryLock
                ::code)
        .contains(
            "no-java-autostart",
            "no-mini-kv-autostart",
            "no-write-routing",
            "no-credential-value",
            "no-managed-audit-http");
    assertThat(response.scorecardEntryCount()).isEqualTo(5);
    assertThat(response.passedScorecardEntryCount()).isEqualTo(5);
    assertThat(response.scorecard())
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
  }
}
