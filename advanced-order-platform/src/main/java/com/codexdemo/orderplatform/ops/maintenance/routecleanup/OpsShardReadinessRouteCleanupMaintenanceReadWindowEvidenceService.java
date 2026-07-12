package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_READ_WINDOW_EVIDENCE;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-read-window-evidence.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse evidence() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v543",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "health-read",
                "operator-handoff-reviewer",
                "8080 health is external-window evidence only",
                OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "ops-overview-read",
                "release-reviewer",
                "ops overview is read-only gate input",
                "/api/v1/ops/overview"),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "shard-readiness-read",
                "catalog-maintainer",
                "shard readiness output remains echo/fixture first",
                OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "startup-boundary",
                "runtime-boundary-reviewer",
                "service never starts Java or mini-kv",
                OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.ENDPOINT)),
        List.of(
            "read-window-targets-documented",
            "read-window-evidence-does-not-probe-live-services",
            "read-window-evidence-does-not-start-upstreams"));
  }
}
