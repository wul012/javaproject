package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditReviewFindingCatalog {

  private OpsShardReadinessCodeWalkthroughQualityAuditReviewFindingCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding>
      reviewFindings() {
    return List.of(
        finding(
            "no-shallow-version-found",
            "info",
            "v1748-v1753 each bundles enough implementation and verification surface to explain.",
            "keep using medium-granularity batches when a version would otherwise be only a receipt"),
        finding(
            "route-model-test-story-present",
            "info",
            "the quality gate batch names endpoint, response model, service flow, and tests.",
            "preserve this shape for the next Java read-only evidence line"),
        finding(
            "archive-discoverability-present",
            "info",
            "phase4/v1748-v1753 is indexed and has a range README.",
            "continue range folders when walkthrough volume grows"),
        finding(
            "runtime-boundary-preserved",
            "info",
            "write routing, credential value, raw endpoint, managed audit, deployment, and autostart remain closed.",
            "only revisit with an explicit runtime plan"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding finding(
      String code, String severity, String finding, String action) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding(
        code, severity, finding, action, false);
  }
}
