package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityGateRoutePathsTests {

  @Test
  void delegatesQualityGateRouteThroughSharedRoutePaths() {
    assertThat(
            OpsShardReadinessCodeWalkthroughQualityGateRoutePaths
                .CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY)
        .isEqualTo("/code-walkthrough-quality-gate-registry");
    assertThat(
            OpsShardReadinessCodeWalkthroughQualityGateRoutePaths
                .CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY)
        .isEqualTo(
            OpsShardReadinessCodeWalkthroughQualityGateRoutePaths
                .CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY);
    assertThat(OpsShardReadinessCodeWalkthroughQualityGateRegistryService.ENDPOINT)
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry");
  }
}
