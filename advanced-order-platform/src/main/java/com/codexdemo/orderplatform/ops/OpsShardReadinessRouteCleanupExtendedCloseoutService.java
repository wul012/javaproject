package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupExtendedCloseoutService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EXTENDED_CLOSEOUT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-extended-closeout.v1";

  private static final int FIRST_EXTENDED_VERSION = 346;

  private final OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService;

  private final OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService;

  private final OpsShardReadinessRouteCleanupFinalDigestService finalDigestService;

  private final OpsShardReadinessRouteCleanupContinuityReportService continuityReportService;

  public OpsShardReadinessRouteCleanupExtendedCloseoutService(
      OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService,
      OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService,
      OpsShardReadinessRouteCleanupFinalDigestService finalDigestService,
      OpsShardReadinessRouteCleanupContinuityReportService continuityReportService) {
    this.handoffBundleService = handoffBundleService;
    this.consumerChecklistService = consumerChecklistService;
    this.finalDigestService = finalDigestService;
    this.continuityReportService = continuityReportService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupExtendedCloseoutResponse closeout() {
    OpsShardReadinessRouteCleanupHandoffBundleResponse bundle = handoffBundleService.bundle();
    OpsShardReadinessRouteCleanupConsumerChecklistResponse checklist =
        consumerChecklistService.checklist();
    OpsShardReadinessRouteCleanupFinalDigestResponse finalDigest = finalDigestService.digest();
    OpsShardReadinessRouteCleanupContinuityReportResponse continuity =
        continuityReportService.report();
    List<String> evidence =
        List.of(
            "bundle:" + bundle.status(),
            "consumer-checklist:" + checklist.status(),
            "final-digest:" + finalDigest.digestValue(),
            "continuity:" + continuity.status());
    int latestVersion = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
    boolean passed =
        bundle.status().equals("passed")
            && checklist.status().equals("passed")
            && finalDigest.status().equals("passed")
            && continuity.status().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed");
    return new OpsShardReadinessRouteCleanupExtendedCloseoutResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        FIRST_EXTENDED_VERSION,
        latestVersion,
        latestVersion - FIRST_EXTENDED_VERSION + 1,
        OpsShardReadinessRouteCleanupHandoffBundleService.ENDPOINT,
        OpsShardReadinessRouteCleanupConsumerChecklistService.ENDPOINT,
        OpsShardReadinessRouteCleanupFinalDigestService.ENDPOINT,
        OpsShardReadinessRouteCleanupContinuityReportService.ENDPOINT,
        evidence.size(),
        evidence,
        passed ? "extended-closeout-ready-for-final-route" : "blocked",
        passed ? "passed" : "blocked");
  }
}
