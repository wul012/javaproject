package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySourceTests {

  @Test
  void buildsArchiveDigestFromArchiveVerificationRegistry() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
            .registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1402");
    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367");
    assertThat(response.requiredArchiveVerificationPlan()).isEqualTo("Node v368");
    assertThat(response.operatorHandoffPlan()).isEqualTo("Node v369");
    assertThat(response.sourceArchiveVersion()).isEqualTo("Java v1377");
    assertThat(response.sourceArchiveEndpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry");
    assertThat(response.sourceArchiveState())
        .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-verification-ready");
    assertThat(response.digestState())
        .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-digest-ready");
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsArchiveDigestStrictlyReadOnly() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
            .registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
  }
}
