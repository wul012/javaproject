package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupPostPushCloseoutServiceTests {

  @Test
  void closesOutCompletionCertificateAfterPushEvidence() {
    OpsShardReadinessRouteCleanupPostPushCloseoutResponse closeout =
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.postPushCloseoutService()
            .closeout();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(389);
    assertThat(closeout.project()).isEqualTo("advanced-order-platform");
    assertThat(closeout.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(closeout.readOnly()).isTrue();
    assertThat(closeout.executionAllowed()).isFalse();
    assertThat(closeout.postPushCloseoutEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-post-push-closeout");
    assertThat(closeout.postPushCloseoutProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-post-push-closeout.v1");
    assertThat(closeout.completionCertificateEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-completion-certificate");
    assertThat(closeout.ciEvidenceEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-ci-evidence");
    assertThat(closeout.closeoutSignalCount()).isEqualTo(6);
    assertThat(closeout.closeoutSignals())
        .extracting(OpsShardReadinessRouteCleanupPostPushCloseoutResponse.CloseoutSignal::name)
        .containsExactly(
            "completion-certificate",
            "completion-certificate-id",
            "ci-evidence-profile",
            "ci-validation-steps",
            "boundary-status",
            "execution-disabled");
    assertThat(closeout.closeoutSignals())
        .allSatisfy(signal -> assertThat(signal.status()).isEqualTo("passed"));
    assertThat(closeout.decision()).isEqualTo("post-push-closeout-ready-for-route");
    assertThat(closeout.status()).isEqualTo("passed");
  }
}
