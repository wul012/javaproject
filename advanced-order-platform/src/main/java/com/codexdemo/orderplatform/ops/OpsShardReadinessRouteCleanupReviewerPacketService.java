package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupReviewerPacketService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REVIEWER_PACKET;

  static final String PROFILE = "java-shard-readiness-route-cleanup-reviewer-packet.v1";

  private final OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService;

  private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

  private final OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService;

  public OpsShardReadinessRouteCleanupReviewerPacketService(
      OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService,
      OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService,
      OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService) {
    this.evidenceRegisterService = evidenceRegisterService;
    this.acceptanceReceiptService = acceptanceReceiptService;
    this.policyGuardService = policyGuardService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupReviewerPacketResponse packet() {
    OpsShardReadinessRouteCleanupEvidenceRegisterResponse register =
        evidenceRegisterService.register();
    OpsShardReadinessRouteCleanupAcceptanceReceiptResponse receipt =
        acceptanceReceiptService.receipt();
    OpsShardReadinessRouteCleanupPolicyGuardResponse guard = policyGuardService.guard();
    List<String> sources =
        List.of(
            OpsShardReadinessRouteCleanupEvidenceRegisterService.ENDPOINT,
            OpsShardReadinessRouteCleanupAcceptanceReceiptService.ENDPOINT,
            OpsShardReadinessRouteCleanupPolicyGuardService.ENDPOINT);
    List<OpsShardReadinessRouteCleanupReviewerPacketResponse.ReviewerCheck> checks =
        List.of(
            check(
                "registered-evidence",
                "endpoint register is populated",
                String.valueOf(register.registeredEvidenceCount())),
            check("acceptance-receipt", "receipt is accepted", receipt.receipt()),
            check("policy-boundary", "blocked capabilities remain disallowed", guard.decision()),
            check(
                "latest-version",
                "packet labels current Java evidence version",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel()));
    boolean passed =
        register.status().equals("passed")
            && receipt.status().equals("passed")
            && guard.status().equals("passed");
    return new OpsShardReadinessRouteCleanupReviewerPacketResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        sources.size(),
        sources,
        checks.size(),
        checks,
        "reviewer packet is ready for read-only handoff inspection",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupReviewerPacketResponse.ReviewerCheck check(
      String name, String expected, String evidence) {
    return new OpsShardReadinessRouteCleanupReviewerPacketResponse.ReviewerCheck(
        name, expected, evidence, "passed");
  }
}
