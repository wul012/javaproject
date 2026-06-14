package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

final class OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport {

  private OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport() {}

  static OpsShardReadinessScreenshotExplanationArchiveRegistryService service() {
    return new OpsShardReadinessScreenshotExplanationArchiveRegistryService();
  }

  static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse registry() {
    return service().registry();
  }
}
