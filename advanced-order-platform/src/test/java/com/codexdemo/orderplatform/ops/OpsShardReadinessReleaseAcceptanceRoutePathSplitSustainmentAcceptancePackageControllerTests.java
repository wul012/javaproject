package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageControllerTests {

  @Test
  void controllerExposesAcceptancePackageRoute() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE)
        .isEqualTo(
            OpsShardReadinessRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE);

    var response =
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageController(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport
                    .service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-acceptance-package");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-release-acceptance-route-path-split-sustainment-acceptance-package.v1");
    assertThat(response.status()).isEqualTo("passed");
  }
}
