package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryControllerTests {

  @Test
  void registryRouteExposesScreenshotExplanationArchivePolicy() {
    assertThat(OpsShardReadinessRoutePaths.SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY)
        .isEqualTo("/screenshot-explanation-archive-registry");

    var response =
        new OpsShardReadinessScreenshotExplanationArchiveRegistryController(
                new OpsShardReadinessScreenshotExplanationArchiveRegistryService())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-screenshot-explanation-archive-registry.v1");
    assertThat(response.version()).isEqualTo("Java v1773");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.capturesScreenshot()).isFalse();
  }
}
