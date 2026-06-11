package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityGateRegistryControllerTests {

    @Test
    void registryRouteExposesQualityGateEvidence() {
        assertThat(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY)
                .isEqualTo("/code-walkthrough-quality-gate-registry");

        var response = new OpsShardReadinessCodeWalkthroughQualityGateRegistryController(
                OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.service())
                .registry();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-code-walkthrough-quality-gate-registry.v1");
        assertThat(response.version()).isEqualTo("Java v1753");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
    }
}
