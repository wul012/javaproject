package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughDepthRoutePathsTests {

  @Test
  void delegatesDepthRouteThroughSharedRoutePaths() {
    assertThat(OpsShardReadinessCodeWalkthroughDepthRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY)
        .isEqualTo("/code-walkthrough-depth-registry");
    assertThat(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY)
        .isEqualTo(OpsShardReadinessCodeWalkthroughDepthRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY);
    assertThat(OpsShardReadinessCodeWalkthroughDepthRegistryService.ENDPOINT)
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-depth-registry");
  }
}
