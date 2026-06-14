package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditBatchCatalog {

  private OpsShardReadinessCodeWalkthroughQualityAuditBatchCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment>
      batchAssessments() {
    return List.of(
        assessment(
            "quality-gate-foundation-batch",
            "v1748-v1753",
            6,
            "The batch moved from rule definition to auditable medium-granularity governance.",
            true,
            true,
            "passed"),
        assessment(
            "quality-audit-follow-up-batch",
            "v1754-v1758",
            5,
            "The follow-up batch audits the previous governance work and records verification evidence.",
            true,
            true,
            "planned-and-verified"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment
      assessment(
          String batch,
          String versionRange,
          int versionCount,
          String assessment,
          boolean standardWalkthroughs,
          boolean mediumGranularity,
          String status) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment(
        batch,
        versionRange,
        versionCount,
        assessment,
        standardWalkthroughs,
        mediumGranularity,
        status);
  }
}
