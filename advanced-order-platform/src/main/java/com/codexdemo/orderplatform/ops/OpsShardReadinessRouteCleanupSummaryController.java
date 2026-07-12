package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupContinuityReportResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupContinuityReportService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupDigestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEndpointManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEndpointManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalDigestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPhaseSummaryResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPhaseSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupSourcePlanAlignmentService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupSummaryController {

  private final OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService;

  private final OpsShardReadinessRouteCleanupDigestService digestService;

  private final OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService;

  private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

  private final OpsShardReadinessRouteCleanupContinuityReportService continuityReportService;

  private final OpsShardReadinessRouteCleanupFinalDigestService finalDigestService;

  public OpsShardReadinessRouteCleanupSummaryController(
      OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService,
      OpsShardReadinessRouteCleanupDigestService digestService,
      OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService,
      OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService,
      OpsShardReadinessRouteCleanupContinuityReportService continuityReportService,
      OpsShardReadinessRouteCleanupFinalDigestService finalDigestService) {
    this.phaseSummaryService = phaseSummaryService;
    this.digestService = digestService;
    this.sourcePlanAlignmentService = sourcePlanAlignmentService;
    this.endpointManifestService = endpointManifestService;
    this.continuityReportService = continuityReportService;
    this.finalDigestService = finalDigestService;
  }

  @GetMapping(RouteCleanupRoutes.PHASE_SUMMARY)
  public OpsShardReadinessRouteCleanupPhaseSummaryResponse phaseSummary() {
    return phaseSummaryService.summary();
  }

  @GetMapping(RouteCleanupRoutes.DIGEST)
  public OpsShardReadinessRouteCleanupDigestResponse digest() {
    return digestService.digest();
  }

  @GetMapping(RouteCleanupRoutes.SOURCE_PLAN_ALIGNMENT)
  public OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse sourcePlanAlignment() {
    return sourcePlanAlignmentService.alignment();
  }

  @GetMapping(RouteCleanupRoutes.ENDPOINT_MANIFEST)
  public OpsShardReadinessRouteCleanupEndpointManifestResponse endpointManifest() {
    return endpointManifestService.manifest();
  }

  @GetMapping(RouteCleanupRoutes.CONTINUITY_REPORT)
  public OpsShardReadinessRouteCleanupContinuityReportResponse continuityReport() {
    return continuityReportService.report();
  }

  @GetMapping(RouteCleanupRoutes.FINAL_DIGEST)
  public OpsShardReadinessRouteCleanupFinalDigestResponse finalDigest() {
    return finalDigestService.digest();
  }
}
