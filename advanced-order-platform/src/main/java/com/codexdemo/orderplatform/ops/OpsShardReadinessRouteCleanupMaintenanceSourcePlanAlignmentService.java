package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupSourcePlanAlignmentService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SOURCE_PLAN_ALIGNMENT;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-source-plan-alignment.v1";

  private final OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService;

  public OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService(
      OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService) {
    this.sourcePlanAlignmentService = sourcePlanAlignmentService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse alignment() {
    OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse upstream =
        sourcePlanAlignmentService.alignment();
    List<String> checks =
        List.of(
            "source-plan-" + upstream.sourcePlan() + "-reused",
            "source-plan-path-points-to-plans3-v549",
            "java-and-mini-kv-not-started-by-maintenance-suite",
            "managed-audit-connection-remains-closed",
            "maintenance-segment-count-"
                + OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments().size());
    return new OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse(
        "advanced-order-platform",
        "Java v481",
        true,
        false,
        ENDPOINT,
        PROFILE,
        upstream.sourcePlan(),
        upstream.sourcePlanPath(),
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments().size(),
        upstream.alignmentCount(),
        checks,
        status(upstream));
  }

  private String status(OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse upstream) {
    boolean passed =
        "passed".equals(upstream.status())
            && "Node v549".equals(upstream.sourcePlan())
            && upstream.sourcePlanPath().contains("/docs/plans3/")
            && upstream.sourcePlanPath().contains("v549-post-java-mini-kv-route-catalog-cleanup")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments().size() == 6;
    return passed ? "passed" : "blocked";
  }
}
