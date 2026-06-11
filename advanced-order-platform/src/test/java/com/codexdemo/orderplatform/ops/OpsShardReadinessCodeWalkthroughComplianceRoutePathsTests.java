package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughComplianceRoutePathsTests {

    @Test
    void delegatesCodeWalkthroughComplianceRouteThroughSharedRoutePaths() {
        assertThat(OpsShardReadinessCodeWalkthroughComplianceRoutePaths
                .CODE_WALKTHROUGH_COMPLIANCE_REGISTRY)
                .isEqualTo("/code-walkthrough-compliance-registry");
        assertThat(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_COMPLIANCE_REGISTRY)
                .isEqualTo(OpsShardReadinessCodeWalkthroughComplianceRoutePaths
                        .CODE_WALKTHROUGH_COMPLIANCE_REGISTRY);
        assertThat(OpsShardReadinessCodeWalkthroughComplianceRegistryService.ENDPOINT)
                .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry");
    }
}
