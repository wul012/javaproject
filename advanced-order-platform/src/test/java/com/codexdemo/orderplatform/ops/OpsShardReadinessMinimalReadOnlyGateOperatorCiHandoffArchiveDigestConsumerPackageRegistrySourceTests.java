package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySourceTests {

    @Test
    void buildsConsumerPackageFromArchiveDigestRegistry() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport
                        .registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1432");
        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1");
        assertThat(response.sourcePlan()).isEqualTo("Node v367");
        assertThat(response.requiredArchiveVerificationPlan()).isEqualTo("Node v368");
        assertThat(response.operatorHandoffPlan()).isEqualTo("Node v369");
        assertThat(response.sourceDigestVersion()).isEqualTo("Java v1402");
        assertThat(response.sourceDigestState())
                .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-digest-ready");
        assertThat(response.consumerPackageState())
                .isEqualTo("minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready");
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsConsumerPackageStrictlyReadOnly() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport
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
