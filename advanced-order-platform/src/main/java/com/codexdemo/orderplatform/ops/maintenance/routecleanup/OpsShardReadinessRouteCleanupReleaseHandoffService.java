package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupReleaseHandoffService {

  public static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.RELEASE_HANDOFF;

  static final String PROFILE = "java-shard-readiness-route-cleanup-release-handoff.v1";

  private final OpsShardReadinessRouteCleanupHandoffChecklistService checklistService;

  private final OpsShardReadinessRouteCleanupArchivePlanService archivePlanService;

  private final OpsShardReadinessRouteCleanupDigestService digestService;

  private final OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService;

  public OpsShardReadinessRouteCleanupReleaseHandoffService(
      OpsShardReadinessRouteCleanupHandoffChecklistService checklistService,
      OpsShardReadinessRouteCleanupArchivePlanService archivePlanService,
      OpsShardReadinessRouteCleanupDigestService digestService,
      OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService) {
    this.checklistService = checklistService;
    this.archivePlanService = archivePlanService;
    this.digestService = digestService;
    this.sourcePlanAlignmentService = sourcePlanAlignmentService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupReleaseHandoffResponse handoff() {
    OpsShardReadinessRouteCleanupHandoffChecklistResponse checklist = checklistService.checklist();
    OpsShardReadinessRouteCleanupArchivePlanResponse archivePlan = archivePlanService.plan();
    OpsShardReadinessRouteCleanupDigestResponse digest = digestService.digest();
    OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse sourceAlignment =
        sourcePlanAlignmentService.alignment();
    List<OpsShardReadinessRouteCleanupReleaseHandoffResponse.HandoffItem> items =
        List.of(
            item("checklist", checklist.checklistProfile() + ":" + checklist.status()),
            item("archive-plan", archivePlan.archiveProfile() + ":" + archivePlan.status()),
            item("digest", digest.digestAlgorithm() + ":" + digest.digestValue()),
            item(
                "source-plan-alignment",
                sourceAlignment.sourcePlan() + ":" + sourceAlignment.status()));
    return new OpsShardReadinessRouteCleanupReleaseHandoffResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT,
        OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT,
        OpsShardReadinessRouteCleanupDigestService.ENDPOINT,
        OpsShardReadinessRouteCleanupSourcePlanAlignmentService.ENDPOINT,
        items.size(),
        items,
        items.stream().allMatch(item -> item.status().equals("passed")) ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupReleaseHandoffResponse.HandoffItem item(
      String name, String evidence) {
    return new OpsShardReadinessRouteCleanupReleaseHandoffResponse.HandoffItem(
        name, evidence, "passed");
  }
}
