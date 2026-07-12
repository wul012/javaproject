package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_CI_BUDGET_LEDGER;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-ci-budget-ledger.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse ledger() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v551",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "focused-service-tests",
                "ci-maintainer",
                "service tests stay first for each evidence slice",
                OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "route-integration-tests",
                "ci-maintainer",
                "MockMvc route checks stay paired with service evidence",
                OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "path-contract-tests",
                "contract-maintainer",
                "shared route constants are checked before route exposure",
                OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "full-suite-final-gate",
                "release-reviewer",
                "full Maven suite remains the push gate after focused runs",
                OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.ENDPOINT)),
        List.of(
            "ci-budget-ledger-focused-first",
            "ci-budget-ledger-does-not-run-ci",
            "ci-budget-ledger-keeps-smoke-last"));
  }
}
