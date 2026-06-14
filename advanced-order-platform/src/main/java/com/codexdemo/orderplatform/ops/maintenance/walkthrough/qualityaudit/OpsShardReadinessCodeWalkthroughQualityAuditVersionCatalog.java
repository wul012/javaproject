package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditVersionCatalog {

  private OpsShardReadinessCodeWalkthroughQualityAuditVersionCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit>
      versionAudits() {
    return List.of(
        audit(
            "Java v1748",
            "v1748-order-platform-code-walkthrough-quality-gate-foundation",
            "route, response, and version granularity rule foundation",
            4,
            12,
            2),
        audit(
            "Java v1749",
            "v1749-order-platform-code-walkthrough-quality-rubric-evidence-boundary",
            "explanation rubric, evidence anchors, review checklist, and boundary catalogs",
            4,
            14,
            3),
        audit(
            "Java v1750",
            "v1750-order-platform-code-walkthrough-quality-registry-render-support-service",
            "renderer, support aggregation, service, checks, and status computation",
            3,
            13,
            3),
        audit(
            "Java v1751",
            "v1751-order-platform-code-walkthrough-quality-registry-controller-tests",
            "controller plus route, service, and controller tests",
            5,
            12,
            5),
        audit(
            "Java v1752",
            "v1752-order-platform-code-walkthrough-quality-boundary-renderer-immutability-docs",
            "boundary, renderer, immutability tests, and archive standard updates",
            6,
            15,
            4),
        audit(
            "Java v1753",
            "v1753-order-platform-code-walkthrough-quality-gate-closeout",
            "verification closeout and tag handoff",
            3,
            11,
            3));
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit audit(
      String javaVersion,
      String tag,
      String scope,
      int implementationSurfaceCount,
      int explanationEvidencePoints,
      int namedTestCount) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit(
        javaVersion,
        tag,
        scope,
        implementationSurfaceCount,
        explanationEvidencePoints,
        namedTestCount,
        true,
        "passed");
  }
}
