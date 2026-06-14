package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryCloseoutTests {

  @Test
  void closesOutSegmentationWithCurrentAndNextArchiveRoots() {
    var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

    assertThat(response.checks())
        .contains(
            "screenshot-explanation-archive-legacy-root-d",
            "screenshot-explanation-archive-next-root-f",
            "screenshot-explanation-archive-canonical-root-f",
            "screenshot-explanation-archive-transition-root-closed-d_runtime_screenshot_archive_next",
            "screenshot-explanation-archive-segment-plan-count-5",
            "screenshot-explanation-archive-verification-step-count-5");
    assertThat(response.segmentPlans())
        .extracting(
            OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan
                ::versionRange)
        .containsExactly("v116-v152", "v1759-v1763", "v1764-v1768", "v1769-v1773", "v1774-v1785");
  }
}
