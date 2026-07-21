package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryServiceTests {

  @Test
  void buildsNodeV367ReadOnlyGateExecutionRegistry() {
    var response = ExecutionTestData.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1312");
    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-minimal-read-only-gate-execution-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367");
    assertThat(response.previousSmokeLane()).isEqualTo("Node v349");
    assertThat(response.registryState())
        .isEqualTo("minimal-read-only-gate-execution-archived-with-no-new-runtime");
    assertThat(response.sourcePlanCount()).isEqualTo(5);
    assertThat(response.readTargetCount()).isEqualTo(5);
    assertThat(response.passedReadTargetCount()).isEqualTo(5);
    assertThat(response.gateCheckCount()).isEqualTo(20);
    assertThat(response.passedGateCheckCount()).isEqualTo(20);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsExecutionAndRuntimeBoundariesClosed() {
    var response = ExecutionTestData.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
    assertThat(response.deniedBoundaryRuleCount()).isEqualTo(response.boundaryRuleCount());
  }
}
