package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive.OpsShardReadinessScreenshotExplanationArchiveRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRoutePathsTests {

  @Test
  void delegatesScreenshotExplanationArchiveRouteThroughSharedRoutePaths() {
    assertThat(
            OpsShardReadinessScreenshotExplanationArchiveRoutePaths
                .SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY)
        .isEqualTo("/screenshot-explanation-archive-registry");
    assertThat(OpsShardReadinessRoutePaths.SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY)
        .isEqualTo(
            OpsShardReadinessScreenshotExplanationArchiveRoutePaths
                .SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY);
    assertThat(OpsShardReadinessScreenshotExplanationArchiveRegistryService.ENDPOINT)
        .isEqualTo("/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry");
  }
}
