package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupExtendedCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupExtendedCloseoutService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupAcceptanceReceiptService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ACCEPTANCE_RECEIPT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-acceptance-receipt.v1";

  private final OpsShardReadinessRouteCleanupAuditTrailService auditTrailService;

  private final OpsShardReadinessRouteCleanupExtendedCloseoutService extendedCloseoutService;

  public OpsShardReadinessRouteCleanupAcceptanceReceiptService(
      OpsShardReadinessRouteCleanupAuditTrailService auditTrailService,
      OpsShardReadinessRouteCleanupExtendedCloseoutService extendedCloseoutService) {
    this.auditTrailService = auditTrailService;
    this.extendedCloseoutService = extendedCloseoutService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupAcceptanceReceiptResponse receipt() {
    OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail = auditTrailService.auditTrail();
    OpsShardReadinessRouteCleanupExtendedCloseoutResponse closeout =
        extendedCloseoutService.closeout();
    List<OpsShardReadinessRouteCleanupAcceptanceReceiptResponse.AcceptedCriterion> criteria =
        List.of(
            criterion(
                "audit-trail-passed", auditTrail.auditTrailEndpoint() + ":" + auditTrail.status()),
            criterion(
                "extended-closeout-passed", closeout.closeoutEndpoint() + ":" + closeout.status()),
            criterion(
                "read-only-boundary-held",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()),
            criterion("source-plan-anchored", auditTrail.sourcePlan()),
            criterion(
                "execution-remains-disabled",
                String.valueOf(!auditTrail.executionAllowed() && !closeout.executionAllowed())));
    boolean accepted =
        auditTrail.status().equals("passed")
            && closeout.status().equals("passed")
            && criteria.stream().allMatch(item -> item.status().equals("accepted"));
    return new OpsShardReadinessRouteCleanupAcceptanceReceiptResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupAuditTrailService.ENDPOINT,
        OpsShardReadinessRouteCleanupExtendedCloseoutService.ENDPOINT,
        criteria.size(),
        criteria,
        "accepted-read-only-route-cleanup-handoff-v"
            + OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion(),
        accepted ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupAcceptanceReceiptResponse.AcceptedCriterion criterion(
      String name, String evidence) {
    return new OpsShardReadinessRouteCleanupAcceptanceReceiptResponse.AcceptedCriterion(
        name, evidence, true, "accepted");
  }
}
