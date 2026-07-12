package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalDigestService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupFinalVerificationService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_VERIFICATION;

  static final String PROFILE = "java-shard-readiness-route-cleanup-final-verification.v1";

  private final OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService;

  private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

  private final OpsShardReadinessRouteCleanupFinalDigestService finalDigestService;

  public OpsShardReadinessRouteCleanupFinalVerificationService(
      OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService,
      OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService,
      OpsShardReadinessRouteCleanupFinalDigestService finalDigestService) {
    this.transitionBriefService = transitionBriefService;
    this.reviewerPacketService = reviewerPacketService;
    this.finalDigestService = finalDigestService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupFinalVerificationResponse verification() {
    OpsShardReadinessRouteCleanupTransitionBriefResponse brief = transitionBriefService.brief();
    OpsShardReadinessRouteCleanupReviewerPacketResponse packet = reviewerPacketService.packet();
    OpsShardReadinessRouteCleanupFinalDigestResponse digest = finalDigestService.digest();
    List<OpsShardReadinessRouteCleanupFinalVerificationResponse.Verification> verifications =
        List.of(
            verification(
                "transition-brief", brief.transitionBriefEndpoint() + ":" + brief.status()),
            verification(
                "reviewer-packet", packet.reviewerPacketEndpoint() + ":" + packet.status()),
            verification("final-digest", digest.digestEndpoint() + ":" + digest.digestValue()),
            verification(
                "versions-continuous",
                String.valueOf(
                    OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous())),
            verification(
                "read-only-boundary",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()));
    boolean passed =
        brief.status().equals("passed")
            && packet.status().equals("passed")
            && digest.status().equals("passed")
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous()
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed");
    return new OpsShardReadinessRouteCleanupFinalVerificationResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        verifications.size(),
        verifications,
        digest.digestValue(),
        passed ? "final-verification-ready-for-archive-plan" : "blocked",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupFinalVerificationResponse.Verification verification(
      String name, String evidence) {
    return new OpsShardReadinessRouteCleanupFinalVerificationResponse.Verification(
        name, evidence, "passed");
  }
}
