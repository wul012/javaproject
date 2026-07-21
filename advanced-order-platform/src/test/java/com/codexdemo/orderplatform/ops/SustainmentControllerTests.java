package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.SustainmentTestData;
import org.junit.jupiter.api.Test;

class SustainmentControllerTests {

  @Test
  void exposesStableSustainmentRoute() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY)
        .isEqualTo("/release-acceptance-route-path-split-sustainment-registry");

    var response =
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController(
                SustainmentTestData.service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-release-acceptance-route-path-split-sustainment.v1");
    assertThat(response.status()).isEqualTo("passed");
  }
}
