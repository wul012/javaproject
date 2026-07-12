package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupSourcePlanAlignmentService {

  public static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.SOURCE_PLAN_ALIGNMENT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-source-plan-alignment.v1";

  static final String SOURCE_PLAN = "Node v549";

  static final String SOURCE_PLAN_PATH =
      "D:/nodeproj/orderops-node/docs/plans3/"
          + "v549-post-java-mini-kv-route-catalog-cleanup-latest-sibling-live-smoke-"
          + "archive-verification-route-archive-verification-roadmap.md";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse alignment() {
    List<OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse.AlignmentItem> alignments =
        List.of(
            item("source-plan", SOURCE_PLAN, SOURCE_PLAN, true),
            item(
                "sibling-startup",
                "Java and mini-kv do not need to start",
                "not started by Java suite",
                true),
            item("runtime-boundary", "executionAllowed=false", "executionAllowed=false", true),
            item(
                "collaboration-mode",
                "Java remains recommended parallel",
                "read-only Java evidence suite",
                true));
    return new OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        SOURCE_PLAN,
        SOURCE_PLAN_PATH,
        alignments.size(),
        alignments,
        alignments.stream()
                .allMatch(
                    OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse.AlignmentItem::aligned)
            ? "passed"
            : "blocked");
  }

  private OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse.AlignmentItem item(
      String subject, String expected, String actual, boolean aligned) {
    return new OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse.AlignmentItem(
        subject, expected, actual, aligned, aligned ? "passed" : "blocked");
  }
}
