package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutControllerTests {

  @Test
  void controllerExposesCloseoutRoute() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_CLOSEOUT_REGISTRY)
        .isEqualTo(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_CLOSEOUT_REGISTRY);

    var response =
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutController(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.service())
            .closeout();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/release-acceptance-route-path-split-closeout-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-release-acceptance-route-path-split-closeout.v1");
  }
}
