package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCompletionCertificateResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCompletionCertificateService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCompletionIndexResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCompletionIndexService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalArchivePlanResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalVerificationResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReviewerPacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReviewerPacketService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupThirdRunCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupThirdRunCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupTransitionBriefResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupTransitionBriefService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupCompletionController {

  private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

  private final OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService;

  private final OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService;

  private final OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService;

  private final OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService;

  private final OpsShardReadinessRouteCleanupCompletionIndexService completionIndexService;

  private final OpsShardReadinessRouteCleanupCompletionCertificateService
      completionCertificateService;

  public OpsShardReadinessRouteCleanupCompletionController(
      OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService,
      OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService,
      OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService,
      OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService,
      OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService,
      OpsShardReadinessRouteCleanupCompletionIndexService completionIndexService,
      OpsShardReadinessRouteCleanupCompletionCertificateService completionCertificateService) {
    this.reviewerPacketService = reviewerPacketService;
    this.transitionBriefService = transitionBriefService;
    this.finalVerificationService = finalVerificationService;
    this.finalArchivePlanService = finalArchivePlanService;
    this.thirdRunCloseoutService = thirdRunCloseoutService;
    this.completionIndexService = completionIndexService;
    this.completionCertificateService = completionCertificateService;
  }

  @GetMapping(RouteCleanupRoutes.REVIEWER_PACKET)
  public OpsShardReadinessRouteCleanupReviewerPacketResponse reviewerPacket() {
    return reviewerPacketService.packet();
  }

  @GetMapping(RouteCleanupRoutes.TRANSITION_BRIEF)
  public OpsShardReadinessRouteCleanupTransitionBriefResponse transitionBrief() {
    return transitionBriefService.brief();
  }

  @GetMapping(RouteCleanupRoutes.FINAL_VERIFICATION)
  public OpsShardReadinessRouteCleanupFinalVerificationResponse finalVerification() {
    return finalVerificationService.verification();
  }

  @GetMapping(RouteCleanupRoutes.FINAL_ARCHIVE_PLAN)
  public OpsShardReadinessRouteCleanupFinalArchivePlanResponse finalArchivePlan() {
    return finalArchivePlanService.plan();
  }

  @GetMapping(RouteCleanupRoutes.THIRD_RUN_CLOSEOUT)
  public OpsShardReadinessRouteCleanupThirdRunCloseoutResponse thirdRunCloseout() {
    return thirdRunCloseoutService.closeout();
  }

  @GetMapping(RouteCleanupRoutes.COMPLETION_INDEX)
  public OpsShardReadinessRouteCleanupCompletionIndexResponse completionIndex() {
    return completionIndexService.index();
  }

  @GetMapping(RouteCleanupRoutes.COMPLETION_CERTIFICATE)
  public OpsShardReadinessRouteCleanupCompletionCertificateResponse completionCertificate() {
    return completionCertificateService.certificate();
  }
}
