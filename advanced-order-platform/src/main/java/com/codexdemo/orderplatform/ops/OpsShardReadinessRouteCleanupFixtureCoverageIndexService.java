package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupFixtureCoverageIndexService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FIXTURE_COVERAGE_INDEX;

  static final String PROFILE = "java-shard-readiness-route-cleanup-fixture-coverage-index.v1";

  private final OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
      maintenanceBoundaryReportService;

  private final OpsShardReadinessRouteCleanupReleaseEvidenceBundleService
      releaseEvidenceBundleService;

  public OpsShardReadinessRouteCleanupFixtureCoverageIndexService(
      OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
          maintenanceBoundaryReportService,
      OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService) {
    this.maintenanceBoundaryReportService = maintenanceBoundaryReportService;
    this.releaseEvidenceBundleService = releaseEvidenceBundleService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse index() {
    OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse boundaryReport =
        maintenanceBoundaryReportService.report();
    OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse bundle =
        releaseEvidenceBundleService.bundle();
    List<OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse.CoverageItem> items =
        List.of(
            item(
                "post-completion-fixtures",
                "OpsShardReadinessRouteCleanupPostCompletionServiceFixtures",
                "post-push, ci, tags, bundle, signoff, archive, boundary"),
            item(
                "controller-split-test",
                "OpsShardReadinessRouteCleanupEvidenceControllerSplitTests",
                "post-completion routes stay in dedicated controller"),
            item(
                "route-paths-test",
                "OpsShardReadinessRoutePathsTests",
                "endpoint constants match service endpoints"),
            item(
                "maintenance-boundary-test",
                "OpsShardReadinessRouteCleanupMaintenanceBoundaryReportServiceTests",
                boundaryReport.decision()),
            item(
                "release-bundle-test",
                "OpsShardReadinessRouteCleanupReleaseEvidenceBundleServiceTests",
                bundle.decision()));
    boolean passed = boundaryReport.status().equals("passed") && bundle.status().equals("passed");
    return new OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService.ENDPOINT,
        items.size(),
        items,
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse.CoverageItem item(
      String name, String target, String coverage) {
    return new OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse.CoverageItem(
        name, target, coverage, "covered");
  }
}
