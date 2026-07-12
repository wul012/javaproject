package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupHandoffChecklistService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.HANDOFF_CHECKLIST;

  static final String PROFILE = "java-shard-readiness-route-cleanup-handoff-checklist.v1";

  private final OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService;

  private final OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService;

  public OpsShardReadinessRouteCleanupHandoffChecklistService(
      OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService,
      OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService) {
    this.phaseSummaryService = phaseSummaryService;
    this.boundaryMatrixService = boundaryMatrixService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupHandoffChecklistResponse checklist() {
    OpsShardReadinessRouteCleanupPhaseSummaryResponse phaseSummary = phaseSummaryService.summary();
    OpsShardReadinessRouteCleanupBoundaryMatrixResponse boundaryMatrix =
        boundaryMatrixService.matrix();
    List<OpsShardReadinessRouteCleanupHandoffChecklistResponse.CheckItem> checks =
        List.of(
            check(
                "catalog-continuity",
                "java",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous(),
                "catalog entries are continuous from v306 through current Java version"),
            check(
                "phase-summary-ready",
                "java",
                phaseSummary.status().equals("passed"),
                "phase summary profile " + phaseSummary.summaryProfile()),
            check(
                "boundary-matrix-fail-closed",
                "java",
                boundaryMatrix.status().equals("passed"),
                "boundary matrix profile " + boundaryMatrix.matrixProfile()),
            check(
                "execution-disabled",
                "java",
                !boundaryMatrix.executionAllowed(),
                "route cleanup suite remains read-only"),
            check(
                "sibling-start-disabled",
                "node/java/mini-kv",
                true,
                "Node v549 states Java and mini-kv do not need to start"));
    return new OpsShardReadinessRouteCleanupHandoffChecklistResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        checks.size(),
        checks,
        checks.stream()
                .allMatch(OpsShardReadinessRouteCleanupHandoffChecklistResponse.CheckItem::passed)
            ? "passed"
            : "blocked");
  }

  private OpsShardReadinessRouteCleanupHandoffChecklistResponse.CheckItem check(
      String name, String owner, boolean passed, String evidence) {
    return new OpsShardReadinessRouteCleanupHandoffChecklistResponse.CheckItem(
        name, owner, passed, evidence, passed ? "passed" : "blocked");
  }
}
