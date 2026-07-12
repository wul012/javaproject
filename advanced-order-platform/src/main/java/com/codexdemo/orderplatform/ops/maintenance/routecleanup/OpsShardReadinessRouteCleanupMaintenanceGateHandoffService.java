package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceGateHandoffService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_GATE_HANDOFF;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-gate-handoff.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse handoff() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v539",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "focused-tests",
                "ci-reviewer",
                "run-new-service-tests-first",
                OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "grouped-route-tests",
                "operator-handoff-reviewer",
                "run-related-mockmvc-routes-second",
                OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "build-validation",
                "release-reviewer",
                "run-build-after-focused-groups",
                OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "smoke-read-only",
                "runtime-boundary-reviewer",
                "smoke-only-after-service-window-exists",
                OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT)),
        List.of(
            "gate-order-focused-grouped-build-smoke",
            "handoff-does-not-run-tests",
            "handoff-does-not-start-upstreams"));
  }
}
