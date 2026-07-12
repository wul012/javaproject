package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_ROUTE_INVENTORY_DIGEST;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-route-inventory-digest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse digest() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v553",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "sustainment-batch-routes",
                "route-catalog-maintainer",
                "first batch controller owns freeze, handoff, field, read-window, runtime routes",
                OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "assurance-batch-routes",
                "route-catalog-maintainer",
                "assurance controller owns consumer, archive, and CI evidence routes",
                OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "path-contract",
                "contract-maintainer",
                "route constants remain the source for service endpoint composition",
                OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "read-only-inventory",
                "runtime-boundary-reviewer",
                "inventory digest does not query runtime handler mappings",
                OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.ENDPOINT)),
        List.of(
            "route-inventory-digest-controller-count-2",
            "route-inventory-digest-does-not-scan-runtime",
            "route-inventory-digest-uses-shared-paths"));
  }
}
