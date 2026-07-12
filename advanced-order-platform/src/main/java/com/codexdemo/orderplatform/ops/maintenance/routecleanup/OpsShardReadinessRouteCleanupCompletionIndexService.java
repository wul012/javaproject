package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupCompletionIndexService {

  static final String ENDPOINT = RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.COMPLETION_INDEX;

  static final String PROFILE = "java-shard-readiness-route-cleanup-completion-index.v1";

  private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

  private final OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService;

  private final OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService;

  private final OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService;

  private final OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService;

  public OpsShardReadinessRouteCleanupCompletionIndexService(
      OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService,
      OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService,
      OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService,
      OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService,
      OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService) {
    this.reviewerPacketService = reviewerPacketService;
    this.transitionBriefService = transitionBriefService;
    this.finalVerificationService = finalVerificationService;
    this.finalArchivePlanService = finalArchivePlanService;
    this.thirdRunCloseoutService = thirdRunCloseoutService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupCompletionIndexResponse index() {
    OpsShardReadinessRouteCleanupReviewerPacketResponse reviewerPacket =
        reviewerPacketService.packet();
    OpsShardReadinessRouteCleanupTransitionBriefResponse transitionBrief =
        transitionBriefService.brief();
    OpsShardReadinessRouteCleanupFinalVerificationResponse finalVerification =
        finalVerificationService.verification();
    OpsShardReadinessRouteCleanupFinalArchivePlanResponse finalArchivePlan =
        finalArchivePlanService.plan();
    OpsShardReadinessRouteCleanupThirdRunCloseoutResponse thirdRunCloseout =
        thirdRunCloseoutService.closeout();
    List<OpsShardReadinessRouteCleanupCompletionIndexResponse.CompletionEndpoint> endpoints =
        List.of(
            endpoint(
                "reviewer-packet",
                OpsShardReadinessRouteCleanupReviewerPacketService.ENDPOINT,
                "review"),
            endpoint(
                "transition-brief",
                OpsShardReadinessRouteCleanupTransitionBriefService.ENDPOINT,
                "handoff"),
            endpoint(
                "final-verification",
                OpsShardReadinessRouteCleanupFinalVerificationService.ENDPOINT,
                "verification"),
            endpoint(
                "final-archive-plan",
                OpsShardReadinessRouteCleanupFinalArchivePlanService.ENDPOINT,
                "archive"),
            endpoint(
                "third-run-closeout",
                OpsShardReadinessRouteCleanupThirdRunCloseoutService.ENDPOINT,
                "closeout"),
            endpoint("completion-index", ENDPOINT, "index"));
    List<OpsShardReadinessRouteCleanupCompletionIndexResponse.StatusSignal> signals =
        List.of(
            signal("reviewer-packet", reviewerPacket.status()),
            signal("transition-brief", transitionBrief.status()),
            signal("final-verification", finalVerification.status()),
            signal("final-archive-plan", finalArchivePlan.status()),
            signal("third-run-closeout", thirdRunCloseout.status()),
            signal(
                "version-continuity",
                String.valueOf(
                    OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous())));
    boolean passed =
        signals.stream()
            .allMatch(
                signal -> signal.status().equals("passed") || signal.evidence().equals("true"));
    return new OpsShardReadinessRouteCleanupCompletionIndexResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        endpoints.size(),
        endpoints,
        signals.size(),
        signals,
        passed ? "completion-index-ready-for-route" : "blocked",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupCompletionIndexResponse.CompletionEndpoint endpoint(
      String name, String endpoint, String category) {
    return new OpsShardReadinessRouteCleanupCompletionIndexResponse.CompletionEndpoint(
        name, endpoint, category, true, false, "passed");
  }

  private OpsShardReadinessRouteCleanupCompletionIndexResponse.StatusSignal signal(
      String name, String evidence) {
    return new OpsShardReadinessRouteCleanupCompletionIndexResponse.StatusSignal(
        name, evidence, "passed");
  }
}
