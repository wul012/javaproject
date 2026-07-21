package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

final class ScreenshotTestData {

  private ScreenshotTestData() {}

  static OpsShardReadinessScreenshotExplanationArchiveRegistryService service() {
    return new OpsShardReadinessScreenshotExplanationArchiveRegistryService();
  }

  static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse registry() {
    return service().registry();
  }
}
