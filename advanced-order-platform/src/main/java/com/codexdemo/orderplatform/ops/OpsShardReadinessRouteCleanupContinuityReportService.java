package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPhaseSummaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupContinuityReportService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONTINUITY_REPORT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-continuity-report.v1";

  private static final int FIRST_VERSION = 326;

  private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

  private final OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService;

  public OpsShardReadinessRouteCleanupContinuityReportService(
      OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService,
      OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService) {
    this.endpointManifestService = endpointManifestService;
    this.phaseSummaryService = phaseSummaryService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupContinuityReportResponse report() {
    int latest = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
    boolean versionsContinuous =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous();
    boolean boundaryHeld =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.allEntriesKeepReadOnlyBoundary();
    return new OpsShardReadinessRouteCleanupContinuityReportResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        FIRST_VERSION,
        latest,
        latest - FIRST_VERSION + 1,
        endpointManifestService.manifest().endpointCount(),
        phaseSummaryService.summary().phaseCount(),
        versionsContinuous,
        boundaryHeld,
        versionsContinuous && boundaryHeld ? "passed" : "blocked");
  }
}
