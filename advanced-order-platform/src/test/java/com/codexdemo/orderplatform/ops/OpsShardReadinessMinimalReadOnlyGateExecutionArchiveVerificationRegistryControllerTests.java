package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryControllerTests {

    @Test
    void registryRouteExposesArchiveVerificationEvidence() {
        assertThat(OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY)
                .isEqualTo("/minimal-read-only-gate-execution-archive-verification-registry");

        var response = new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController(
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport.service())
                .registry();

        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/minimal-read-only-gate-execution-archive-verification-registry");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-minimal-read-only-gate-execution-archive-verification-registry.v1");
        assertThat(response.version()).isEqualTo("Java v1337");
        assertThat(response.sourceRegistryVersion()).isEqualTo("Java v1312");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
    }
}
