package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySourceTests {

    @Test
    void buildsArchiveVerificationFromOperatorCiHandoffRegistry() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
                        .registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1377");
        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-verification-registry.v1");
        assertThat(response.sourcePlan()).isEqualTo("Node v367");
        assertThat(response.requiredArchiveVerificationPlan()).isEqualTo("Node v368");
        assertThat(response.operatorHandoffPlan()).isEqualTo("Node v369");
        assertThat(response.sourceHandoffVersion()).isEqualTo("Java v1352");
        assertThat(response.sourceHandoffEndpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-registry");
        assertThat(response.sourceHandoffState())
                .isEqualTo("minimal-read-only-gate-operator-ci-handoff-ready");
        assertThat(response.archiveState())
                .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-verification-ready");
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsArchiveVerificationStrictlyReadOnly() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
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
