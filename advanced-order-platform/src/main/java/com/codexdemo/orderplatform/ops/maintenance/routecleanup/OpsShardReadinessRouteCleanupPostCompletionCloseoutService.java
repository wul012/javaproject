package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.CloseoutSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.CloseoutSource;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupPostCompletionCloseoutService implements CloseoutSource {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.POST_COMPLETION_CLOSEOUT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-post-completion-closeout.v1";

  private static final int FIRST_POST_COMPLETION_VERSION = 389;

  private final OpsShardReadinessRouteCleanupCompletionAuditDigestService
      completionAuditDigestService;

  private final OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
      maintenanceBoundaryReportService;

  private final OpsShardReadinessRouteCleanupArchiveHandoffReceiptService
      archiveHandoffReceiptService;

  private final OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService;

  public OpsShardReadinessRouteCleanupPostCompletionCloseoutService(
      OpsShardReadinessRouteCleanupCompletionAuditDigestService completionAuditDigestService,
      OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
          maintenanceBoundaryReportService,
      OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService,
      OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService) {
    this.completionAuditDigestService = completionAuditDigestService;
    this.maintenanceBoundaryReportService = maintenanceBoundaryReportService;
    this.archiveHandoffReceiptService = archiveHandoffReceiptService;
    this.ciRunAttestationService = ciRunAttestationService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse closeout() {
    OpsShardReadinessRouteCleanupCompletionAuditDigestResponse digest =
        completionAuditDigestService.digest();
    OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse boundary =
        maintenanceBoundaryReportService.report();
    OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse archiveReceipt =
        archiveHandoffReceiptService.receipt();
    OpsShardReadinessRouteCleanupCiRunAttestationResponse ciRunAttestation =
        ciRunAttestationService.attestation();
    int latest = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
    List<String> evidence =
        List.of(
            "completion-audit-digest:" + digest.digestValue(),
            "maintenance-boundary:" + boundary.decision(),
            "archive-handoff-receipt:" + archiveReceipt.receiptId(),
            "ci-run-attestation:" + ciRunAttestation.status(),
            "boundary:" + OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus(),
            "continuity:" + OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous());
    boolean passed =
        digest.status().equals("passed")
            && boundary.status().equals("passed")
            && archiveReceipt.status().equals("passed")
            && ciRunAttestation.status().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous();
    return new OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        FIRST_POST_COMPLETION_VERSION,
        latest,
        latest - FIRST_POST_COMPLETION_VERSION + 1,
        OpsShardReadinessRouteCleanupCompletionAuditDigestService.ENDPOINT,
        OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService.ENDPOINT,
        evidence.size(),
        evidence,
        passed ? "post-completion-closeout-ready-for-route" : "blocked",
        passed ? "passed" : "blocked");
  }

  @Override
  @Transactional(readOnly = true)
  public CloseoutSnapshot snapshot() {
    OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse closeout = closeout();
    return new CloseoutSnapshot(
        closeout.version(),
        closeout.executionAllowed(),
        closeout.postCompletionCloseoutEndpoint(),
        closeout.status());
  }
}
