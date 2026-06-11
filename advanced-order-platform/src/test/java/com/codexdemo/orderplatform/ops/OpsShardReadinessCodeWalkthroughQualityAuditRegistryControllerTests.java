package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityAuditRegistryControllerTests {

    @Test
    void registryRouteExposesQualityAuditEvidence() {
        assertThat(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_AUDIT_REGISTRY)
                .isEqualTo("/code-walkthrough-quality-audit-registry");

        var response = new OpsShardReadinessCodeWalkthroughQualityAuditRegistryController(
                OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport.service())
                .registry();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-code-walkthrough-quality-audit-registry.v1");
        assertThat(response.version()).isEqualTo("Java v1758");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
    }
}
