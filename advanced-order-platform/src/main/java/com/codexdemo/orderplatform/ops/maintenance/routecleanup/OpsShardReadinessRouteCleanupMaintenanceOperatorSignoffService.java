package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_OPERATOR_SIGNOFF;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-operator-signoff.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse signoff() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v555",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "contract-freeze-reviewed",
                "operator-reviewer",
                "v1 read contracts remain frozen for consumer review",
                OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "consumer-gate-reviewed",
                "operator-reviewer",
                "consumer handoff packet is present and read-only",
                OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "archive-verifier-reviewed",
                "archive-reviewer",
                "archive verifier summary keeps capture work external",
                OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "ci-budget-reviewed",
                "ci-reviewer",
                "focused tests and final suite gate remain separated",
                OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "route-inventory-reviewed",
                "route-catalog-maintainer",
                "route digest uses shared constants and static evidence",
                OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService.ENDPOINT)),
        List.of(
            "operator-signoff-items-5",
            "operator-signoff-is-evidence-only",
            "operator-signoff-does-not-approve-execution"));
  }
}
