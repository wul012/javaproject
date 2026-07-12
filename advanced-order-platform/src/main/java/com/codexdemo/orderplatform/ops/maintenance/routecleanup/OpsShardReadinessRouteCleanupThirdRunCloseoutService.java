package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupThirdRunCloseoutService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.THIRD_RUN_CLOSEOUT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-third-run-closeout.v1";

  private static final int FIRST_THIRD_RUN_VERSION = 366;

  private final OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService;

  private final OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService;

  private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

  public OpsShardReadinessRouteCleanupThirdRunCloseoutService(
      OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService,
      OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService,
      OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService) {
    this.finalVerificationService = finalVerificationService;
    this.finalArchivePlanService = finalArchivePlanService;
    this.acceptanceReceiptService = acceptanceReceiptService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupThirdRunCloseoutResponse closeout() {
    OpsShardReadinessRouteCleanupFinalVerificationResponse verification =
        finalVerificationService.verification();
    OpsShardReadinessRouteCleanupFinalArchivePlanResponse archivePlan =
        finalArchivePlanService.plan();
    OpsShardReadinessRouteCleanupAcceptanceReceiptResponse receipt =
        acceptanceReceiptService.receipt();
    int latest = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
    List<String> readinessEvidence =
        List.of(
            "final-verification:" + verification.status(),
            "archive-plan:" + archivePlan.status(),
            "acceptance-receipt:" + receipt.status(),
            "boundary:" + OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus(),
            "continuity:" + OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous());
    boolean passed =
        verification.status().equals("passed")
            && archivePlan.status().equals("passed")
            && receipt.status().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous();
    return new OpsShardReadinessRouteCleanupThirdRunCloseoutResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        FIRST_THIRD_RUN_VERSION,
        latest,
        latest - FIRST_THIRD_RUN_VERSION + 1,
        OpsShardReadinessRouteCleanupFinalVerificationService.ENDPOINT,
        OpsShardReadinessRouteCleanupFinalArchivePlanService.ENDPOINT,
        readinessEvidence.size(),
        readinessEvidence,
        passed ? "third-run-closeout-ready" : "blocked",
        passed ? "passed" : "blocked");
  }
}
