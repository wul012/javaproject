package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupPostPushCloseoutService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POST_PUSH_CLOSEOUT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-post-push-closeout.v1";

  private final OpsShardReadinessRouteCleanupCompletionCertificateService
      completionCertificateService;

  private final OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService;

  public OpsShardReadinessRouteCleanupPostPushCloseoutService(
      OpsShardReadinessRouteCleanupCompletionCertificateService completionCertificateService,
      OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService) {
    this.completionCertificateService = completionCertificateService;
    this.ciEvidenceService = ciEvidenceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupPostPushCloseoutResponse closeout() {
    OpsShardReadinessRouteCleanupCompletionCertificateResponse certificate =
        completionCertificateService.certificate();
    OpsShardReadinessRouteCleanupCiEvidenceResponse ciEvidence = ciEvidenceService.evidence();
    List<OpsShardReadinessRouteCleanupPostPushCloseoutResponse.CloseoutSignal> signals =
        List.of(
            signal("completion-certificate", certificate.status()),
            signal("completion-certificate-id", certificate.certificateId()),
            signal("ci-evidence-profile", ciEvidence.ciProfile()),
            signal("ci-validation-steps", String.valueOf(ciEvidence.validationStepCount())),
            signal(
                "boundary-status", OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()),
            signal(
                "execution-disabled",
                String.valueOf(!certificate.executionAllowed() && !ciEvidence.executionAllowed())));
    boolean passed =
        certificate.status().equals("passed")
            && ciEvidence.status().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed");
    return new OpsShardReadinessRouteCleanupPostPushCloseoutResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupCompletionCertificateService.ENDPOINT,
        OpsShardReadinessRouteCleanupCiEvidenceService.ENDPOINT,
        signals.size(),
        signals,
        passed ? "post-push-closeout-ready-for-route" : "blocked",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupPostPushCloseoutResponse.CloseoutSignal signal(
      String name, String evidence) {
    return new OpsShardReadinessRouteCleanupPostPushCloseoutResponse.CloseoutSignal(
        name, evidence, "passed");
  }
}
