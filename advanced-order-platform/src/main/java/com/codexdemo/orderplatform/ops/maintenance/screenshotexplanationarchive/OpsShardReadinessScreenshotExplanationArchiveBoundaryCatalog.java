package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveBoundaryCatalog {

  private OpsShardReadinessScreenshotExplanationArchiveBoundaryCatalog() {}

  static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule>
      boundaryRules() {
    return List.of(
        boundary(
            "no-screenshot-capture",
            "runtime screenshot capture",
            "this registry defines archive policy but does not drive a browser or capture images"),
        boundary(
            "no-historical-move",
            "moving historical d records",
            "old d records remain traceable in place"),
        boundary(
            "no-write-routing", "write routing", "archive organization has no order write surface"),
        boundary(
            "no-credential-value",
            "credential value read",
            "archive paths and route constants do not need secret values"),
        boundary(
            "no-raw-endpoint-url",
            "raw endpoint URL resolution",
            "screenshot archive policy uses symbolic route and folder names only"),
        boundary(
            "no-managed-audit-connection",
            "managed audit HTTP or TCP connection",
            "no external audit endpoint is contacted"),
        boundary(
            "no-java-autostart", "Java service autostart", "tests instantiate services directly"),
        boundary(
            "no-minikv-autostart",
            "mini-kv process autostart",
            "mini-kv is not part of screenshot archive organization"));
  }

  private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule
      boundary(String code, String forbiddenAction, String rationale) {
    return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule(
        code, forbiddenAction, false, rationale);
  }
}
