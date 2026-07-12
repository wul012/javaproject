package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

final class OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds {

  private OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds() {}

  static List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item> items() {
    return List.of(
        item(
            "segment-catalog",
            471,
            472,
            OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService.ENDPOINT,
            "e/471/evidence/java-route-cleanup-maintenance-segment-catalog-v471.json",
            "catalog-maintainer",
            "split-catalog-readiness"),
        item(
            "continuity",
            473,
            474,
            OpsShardReadinessRouteCleanupMaintenanceContinuityService.ENDPOINT,
            "e/473/evidence/java-route-cleanup-maintenance-continuity-v473.json",
            "release-reviewer",
            "version-continuity"),
        item(
            "latest-sibling-report",
            475,
            476,
            OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService.ENDPOINT,
            "e/475/evidence/java-route-cleanup-maintenance-latest-sibling-report-v475.json",
            "node-plan-consumer",
            "latest-sibling-source-plan"),
        item(
            "handoff-pair-audit",
            477,
            478,
            OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService.ENDPOINT,
            "e/477/evidence/java-route-cleanup-maintenance-handoff-pair-audit-v477.json",
            "handoff-reviewer",
            "service-route-pairing"),
        item(
            "boundary-drift",
            479,
            480,
            OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService.ENDPOINT,
            "e/479/evidence/java-route-cleanup-maintenance-boundary-drift-v479.json",
            "runtime-boundary-reviewer",
            "read-only-boundary"),
        item(
            "source-plan-alignment",
            481,
            482,
            OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService.ENDPOINT,
            "e/481/evidence/java-route-cleanup-maintenance-source-plan-alignment-v481.json",
            "roadmap-reviewer",
            "node-v549-alignment"),
        item(
            "test-budget-plan",
            483,
            484,
            OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService.ENDPOINT,
            "e/483/evidence/java-route-cleanup-maintenance-test-budget-plan-v483.json",
            "ci-reviewer",
            "non-docker-regression"),
        item(
            "archive-manifest",
            485,
            486,
            OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService.ENDPOINT,
            "e/485/evidence/java-route-cleanup-maintenance-archive-manifest-v485.json",
            "archive-reviewer",
            "versioned-evidence-path"),
        item(
            "closeout",
            487,
            488,
            OpsShardReadinessRouteCleanupMaintenanceCloseoutService.ENDPOINT,
            "e/487/evidence/java-route-cleanup-maintenance-closeout-v487.json",
            "operator-handoff-reviewer",
            "maintenance-closeout"));
  }

  private static OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item(
      String name,
      int serviceVersion,
      int routeVersion,
      String endpoint,
      String evidencePath,
      String consumer,
      String boundary) {
    return new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item(
        name, serviceVersion, routeVersion, endpoint, evidencePath, consumer, boundary, "passed");
  }
}
