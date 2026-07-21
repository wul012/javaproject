package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.SplitTestData;
import org.junit.jupiter.api.Test;

class SplitControllerTests {

  @Test
  void controllerExposesRoutePathSplitRegistry() {
    var response =
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitController(SplitTestData.service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/release-acceptance-route-path-split-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-release-acceptance-route-path-split-registry.v1");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.routePathSplitEnabled()).isTrue();
  }
}
