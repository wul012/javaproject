package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupTransitionBriefServiceTests {

  @Test
  void buildsTransitionBriefFromReviewerSnapshotAndPolicyGuard() {
    OpsShardReadinessRouteCleanupTransitionBriefResponse brief =
        new OpsShardReadinessRouteCleanupTransitionBriefService(
                reviewerPacketService(), operationalSnapshotService(), policyGuardService())
            .brief();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(378);
    assertThat(brief.project()).isEqualTo("advanced-order-platform");
    assertThat(brief.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(brief.readOnly()).isTrue();
    assertThat(brief.executionAllowed()).isFalse();
    assertThat(brief.transitionBriefEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-transition-brief");
    assertThat(brief.transitionBriefProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-transition-brief.v1");
    assertThat(brief.readinessSignalCount()).isEqualTo(5);
    assertThat(brief.readinessSignals())
        .extracting(OpsShardReadinessRouteCleanupTransitionBriefResponse.ReadinessSignal::name)
        .containsExactly(
            "reviewer-packet",
            "operational-snapshot",
            "policy-guard",
            "source-plan",
            "execution-boundary");
    assertThat(brief.readinessSignals())
        .allSatisfy(signal -> assertThat(signal.status()).isEqualTo("passed"));
    assertThat(brief.nextAction()).contains("read-only verification");
    assertThat(brief.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService() {
    return new OpsShardReadinessRouteCleanupReviewerPacketService(
        evidenceRegisterService(), acceptanceReceiptService(), policyGuardService());
  }

  private OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService() {
    return new OpsShardReadinessRouteCleanupEvidenceRegisterService(
        OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
        OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService());
  }

  private OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService() {
    return new OpsShardReadinessRouteCleanupAcceptanceReceiptService(
        new OpsShardReadinessRouteCleanupAuditTrailService(),
        OpsShardReadinessRouteCleanupServiceFixtures.extendedCloseoutService());
  }

  private OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService() {
    return new OpsShardReadinessRouteCleanupOperationalSnapshotService(
        OpsShardReadinessRouteCleanupServiceFixtures.continuityReportService(),
        OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
        acceptanceReceiptService());
  }

  private OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService() {
    return new OpsShardReadinessRouteCleanupPolicyGuardService(
        operationalSnapshotService(), evidenceRegisterService());
  }
}
