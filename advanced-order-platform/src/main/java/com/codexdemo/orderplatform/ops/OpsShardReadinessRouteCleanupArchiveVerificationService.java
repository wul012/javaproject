package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchivePlanResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupArchiveVerificationService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_VERIFICATION;

  static final String PROFILE = "java-shard-readiness-route-cleanup-archive-verification.v1";

  private final OpsShardReadinessRouteCleanupArchivePlanService archivePlanService;

  private final OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService;

  public OpsShardReadinessRouteCleanupArchiveVerificationService(
      OpsShardReadinessRouteCleanupArchivePlanService archivePlanService,
      OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService) {
    this.archivePlanService = archivePlanService;
    this.suiteCloseoutService = suiteCloseoutService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupArchiveVerificationResponse verification() {
    OpsShardReadinessRouteCleanupArchivePlanResponse archivePlan = archivePlanService.plan();
    OpsShardReadinessRouteCleanupSuiteCloseoutResponse closeout = suiteCloseoutService.closeout();
    List<OpsShardReadinessRouteCleanupArchiveVerificationResponse.VerificationCheck> checks =
        List.of(
            check(
                "archive-plan-passed",
                archivePlan.status().equals("passed"),
                archivePlan.archiveProfile()),
            check(
                "archive-artifacts-required",
                archivePlan.artifacts().stream()
                    .allMatch(
                        OpsShardReadinessRouteCleanupArchivePlanResponse.ArchiveArtifact::required),
                "required artifacts=" + archivePlan.artifactCount()),
            check(
                "closeout-passed", closeout.status().equals("passed"), closeout.closeoutProfile()),
            check(
                "digest-present",
                closeout.digestValue().matches("[0-9a-f]{64}"),
                closeout.digestValue()));
    boolean passed =
        checks.stream()
            .allMatch(
                OpsShardReadinessRouteCleanupArchiveVerificationResponse.VerificationCheck::passed);
    return new OpsShardReadinessRouteCleanupArchiveVerificationResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT,
        OpsShardReadinessRouteCleanupSuiteCloseoutService.ENDPOINT,
        checks.size(),
        checks,
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupArchiveVerificationResponse.VerificationCheck check(
      String name, boolean passed, String evidence) {
    return new OpsShardReadinessRouteCleanupArchiveVerificationResponse.VerificationCheck(
        name, passed, evidence, passed ? "passed" : "blocked");
  }
}
