package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule;
import org.junit.jupiter.api.Test;

class SustainmentCatalogTests {

  @Test
  void registryPinsCloseoutAndParallelPlan() {
    var response = SustainmentTestData.registry();

    assertThat(response.version()).isEqualTo("Java v1604");
    assertThat(response.sourcePlan()).isEqualTo("Node v1878");
    assertThat(response.nodeParallelPlan()).isEqualTo("Node v1867-v1878");
    assertThat(response.sourceCloseoutVersion()).isEqualTo("Java v1579");
    assertThat(response.sourceSplitVersion()).isEqualTo("Java v1570");
    assertThat(response.sourceSnapshotCount()).isEqualTo(1);
    assertThat(response.ownershipRuleCount()).isEqualTo(6);
    assertThat(response.driftGuardCount()).isEqualTo(6);
    assertThat(response.boundaryGuardCount()).isEqualTo(7);
    assertThat(response.ciGateCount()).isEqualTo(5);
    assertThat(response.consumerHandoffCount()).isEqualTo(5);
    assertThat(response.scorecardEntryCount()).isEqualTo(8);
    assertThat(response.markdownSectionCount()).isEqualTo(7);
    assertThat(response.status()).isEqualTo("passed");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rulesAndGuardsRemainClosed() {
    var response = SustainmentTestData.registry();

    assertThat(response.ownershipRules()).allSatisfy(rule -> assertThat(rule.enforced()).isTrue());
    assertThat(response.ownershipRules())
        .extracting(OwnershipRule::component)
        .contains(
            "stable-route-delegate", "catalog-ownership", "renderer-ownership", "test-ownership");
    assertThat(response.driftGuards()).allSatisfy(guard -> assertThat(guard.locked()).isTrue());
    assertThat(response.boundaryGuards()).allSatisfy(guard -> assertThat(guard.locked()).isTrue());
    assertThat(response.ciGates()).allSatisfy(gate -> assertThat(gate.required()).isTrue());
    assertThat(response.consumerHandoffs())
        .allSatisfy(handoff -> assertThat(handoff.ready()).isTrue());
    assertThat(response.scorecard()).allSatisfy(entry -> assertThat(entry.passed()).isTrue());
  }
}
