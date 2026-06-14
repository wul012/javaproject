package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughDepthRegistryControllerTests {

  @Test
  void registryRouteExposesDepthEvidence() {
    var response =
        new OpsShardReadinessCodeWalkthroughDepthRegistryController(
                new OpsShardReadinessCodeWalkthroughDepthRegistryService())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-depth-registry");
    assertThat(response.version()).isEqualTo("Java v1778");
    assertThat(response.minimumChineseCharacterCount()).isEqualTo(3000);
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }
}
