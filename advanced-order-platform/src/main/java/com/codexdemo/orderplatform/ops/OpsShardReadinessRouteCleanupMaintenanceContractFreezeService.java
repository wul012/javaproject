package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceContractFreezeService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTRACT_FREEZE;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-contract-freeze.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse freeze() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v537",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "read-only-integration-v1",
                "release-reviewer",
                "project-version-readOnly-executionAllowed-status-evidencePath",
                OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "shard-readiness-v1",
                "catalog-maintainer",
                "project-version-readOnly-executionAllowed-shard-fields-evidencePath-status",
                OpsShardReadinessPrototypeEvidenceService.CATALOG_ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "runtime-boundary",
                "runtime-boundary-reviewer",
                "no-order-write-routing-or-ledger-change",
                OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "source-plan",
                "roadmap-reviewer",
                "Node v549 recommended parallel",
                OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.ENDPOINT)),
        List.of(
            "read-only-integration-v1-fields-frozen",
            "shard-readiness-v1-fields-frozen",
            "java-remains-read-only-echo"));
  }
}
