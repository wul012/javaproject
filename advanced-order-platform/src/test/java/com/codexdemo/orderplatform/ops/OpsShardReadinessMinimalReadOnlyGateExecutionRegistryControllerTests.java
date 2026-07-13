package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryControllerTests {

  @Test
  void registryRouteExposesMinimalReadOnlyGateExecutionEvidence() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY)
        .isEqualTo("/minimal-read-only-gate-execution-registry");

    var response =
        new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryController(
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport.service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-minimal-read-only-gate-execution-registry.v1");
    assertThat(response.version()).isEqualTo("Java v1312");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }
}
