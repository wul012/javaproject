package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupTransitionBriefService {

  static final String ENDPOINT = RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.TRANSITION_BRIEF;

  static final String PROFILE = "java-shard-readiness-route-cleanup-transition-brief.v1";

  private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

  private final OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService;

  private final OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService;

  public OpsShardReadinessRouteCleanupTransitionBriefService(
      OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService,
      OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService,
      OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService) {
    this.reviewerPacketService = reviewerPacketService;
    this.operationalSnapshotService = operationalSnapshotService;
    this.policyGuardService = policyGuardService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupTransitionBriefResponse brief() {
    OpsShardReadinessRouteCleanupReviewerPacketResponse packet = reviewerPacketService.packet();
    OpsShardReadinessRouteCleanupOperationalSnapshotResponse snapshot =
        operationalSnapshotService.snapshot();
    OpsShardReadinessRouteCleanupPolicyGuardResponse guard = policyGuardService.guard();
    List<OpsShardReadinessRouteCleanupTransitionBriefResponse.ReadinessSignal> signals =
        List.of(
            signal("reviewer-packet", packet.reviewerPacketEndpoint() + ":" + packet.status()),
            signal("operational-snapshot", snapshot.snapshotEndpoint() + ":" + snapshot.status()),
            signal("policy-guard", guard.policyGuardEndpoint() + ":" + guard.decision()),
            signal("source-plan", "Node v549 route archive verification handoff remains current"),
            signal(
                "execution-boundary",
                String.valueOf(
                    !packet.executionAllowed()
                        && !snapshot.executionAllowed()
                        && !guard.executionAllowed())));
    boolean passed =
        packet.status().equals("passed")
            && snapshot.status().equals("passed")
            && guard.status().equals("passed");
    return new OpsShardReadinessRouteCleanupTransitionBriefResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        signals.size(),
        signals,
        "continue read-only verification and archive evidence before any runtime expansion",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupTransitionBriefResponse.ReadinessSignal signal(
      String name, String evidence) {
    return new OpsShardReadinessRouteCleanupTransitionBriefResponse.ReadinessSignal(
        name, evidence, "passed");
  }
}
