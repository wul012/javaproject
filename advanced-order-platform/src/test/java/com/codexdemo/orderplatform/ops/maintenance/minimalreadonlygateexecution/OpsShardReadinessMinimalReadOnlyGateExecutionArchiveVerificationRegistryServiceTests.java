package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryServiceTests {

  @Test
  void buildsArchiveVerificationRegistryFromJavaV1312SourceRegistry() {
    var response = ArchiveTestData.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1337");
    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-archive-verification-registry");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-minimal-read-only-gate-execution-archive-verification-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367");
    assertThat(response.recommendedNextPlan()).isEqualTo("Node v368");
    assertThat(response.sourceRegistryVersion()).isEqualTo("Java v1312");
    assertThat(response.sourceRegistryEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-registry");
    assertThat(response.archiveState())
        .isEqualTo("minimal-read-only-gate-execution-archive-verification-ready");
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void carriesReadOnlyBoundaryFlagsForward() {
    var response = ArchiveTestData.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
    assertThat(response.deniedBoundaryVerificationCount())
        .isEqualTo(response.boundaryVerificationCount());
  }
}
