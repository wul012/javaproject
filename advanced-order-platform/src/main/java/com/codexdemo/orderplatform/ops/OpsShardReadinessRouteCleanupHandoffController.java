package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchivePlanResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchiveVerificationResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchiveVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupConsumerChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupConsumerChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupConsumerPacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupConsumerPacketService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupExtendedCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupExtendedCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupHandoffBundleResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupHandoffBundleService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupHandoffChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupHandoffChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReleaseHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReleaseHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupSuiteCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupSuiteCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupHandoffController {

  private final OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService;

  private final OpsShardReadinessRouteCleanupArchivePlanService archivePlanService;

  private final OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService;

  private final OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService;

  private final OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService;

  private final OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService;

  private final OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService;

  private final OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService;

  private final OpsShardReadinessRouteCleanupExtendedCloseoutService extendedCloseoutService;

  public OpsShardReadinessRouteCleanupHandoffController(
      OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService,
      OpsShardReadinessRouteCleanupArchivePlanService archivePlanService,
      OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService,
      OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService,
      OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService,
      OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService,
      OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService,
      OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService,
      OpsShardReadinessRouteCleanupExtendedCloseoutService extendedCloseoutService) {
    this.handoffChecklistService = handoffChecklistService;
    this.archivePlanService = archivePlanService;
    this.releaseHandoffService = releaseHandoffService;
    this.suiteCloseoutService = suiteCloseoutService;
    this.archiveVerificationService = archiveVerificationService;
    this.consumerPacketService = consumerPacketService;
    this.handoffBundleService = handoffBundleService;
    this.consumerChecklistService = consumerChecklistService;
    this.extendedCloseoutService = extendedCloseoutService;
  }

  @GetMapping(RouteCleanupRoutes.HANDOFF_CHECKLIST)
  public OpsShardReadinessRouteCleanupHandoffChecklistResponse handoffChecklist() {
    return handoffChecklistService.checklist();
  }

  @GetMapping(RouteCleanupRoutes.ARCHIVE_PLAN)
  public OpsShardReadinessRouteCleanupArchivePlanResponse archivePlan() {
    return archivePlanService.plan();
  }

  @GetMapping(RouteCleanupRoutes.RELEASE_HANDOFF)
  public OpsShardReadinessRouteCleanupReleaseHandoffResponse releaseHandoff() {
    return releaseHandoffService.handoff();
  }

  @GetMapping(RouteCleanupRoutes.SUITE_CLOSEOUT)
  public OpsShardReadinessRouteCleanupSuiteCloseoutResponse suiteCloseout() {
    return suiteCloseoutService.closeout();
  }

  @GetMapping(RouteCleanupRoutes.ARCHIVE_VERIFICATION)
  public OpsShardReadinessRouteCleanupArchiveVerificationResponse archiveVerification() {
    return archiveVerificationService.verification();
  }

  @GetMapping(RouteCleanupRoutes.CONSUMER_PACKET)
  public OpsShardReadinessRouteCleanupConsumerPacketResponse consumerPacket() {
    return consumerPacketService.packet();
  }

  @GetMapping(RouteCleanupRoutes.HANDOFF_BUNDLE)
  public OpsShardReadinessRouteCleanupHandoffBundleResponse handoffBundle() {
    return handoffBundleService.bundle();
  }

  @GetMapping(RouteCleanupRoutes.CONSUMER_CHECKLIST)
  public OpsShardReadinessRouteCleanupConsumerChecklistResponse consumerChecklist() {
    return consumerChecklistService.checklist();
  }

  @GetMapping(RouteCleanupRoutes.EXTENDED_CLOSEOUT)
  public OpsShardReadinessRouteCleanupExtendedCloseoutResponse extendedCloseout() {
    return extendedCloseoutService.closeout();
  }
}
