package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RUNTIME_BOUNDARY_CHECKLIST;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-runtime-boundary-checklist.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse checklist() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v545",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "no-write-routing",
                "runtime-boundary-reviewer",
                "write-routing remains forbidden",
                OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "no-credential-value",
                "security-reviewer",
                "credential value remains unread",
                OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "no-raw-endpoint-parse",
                "security-reviewer",
                "raw endpoint URL remains unparsed",
                OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "no-managed-audit-connection",
                "audit-reviewer",
                "managed audit connection remains disabled",
                OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "no-upstream-autostart",
                "operator-handoff-reviewer",
                "Java and mini-kv are never started by evidence routes",
                OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService.ENDPOINT)),
        List.of(
            "runtime-boundary-items-5",
            "all-boundary-items-are-forbidden-actions",
            "runtime-boundary-checklist-does-not-execute"));
  }
}
