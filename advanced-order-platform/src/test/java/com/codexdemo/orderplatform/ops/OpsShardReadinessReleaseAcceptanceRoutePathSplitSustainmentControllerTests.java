package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentControllerTests {

    @Test
    void controllerExposesSustainmentRoute() {
        assertThat(OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY)
                .isEqualTo(OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY);

        var response = new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.service()
        ).registry();

        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-registry");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-release-acceptance-route-path-split-sustainment.v1");
        assertThat(response.status()).isEqualTo("passed");
    }
}
