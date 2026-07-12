package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupCiRunAttestationServiceTests {

  @Test
  void buildsReadOnlyCiRunAttestationFromPostPushCloseout() {
    OpsShardReadinessRouteCleanupCiRunAttestationResponse attestation =
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.ciRunAttestationService()
            .attestation();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(391);
    assertThat(attestation.project()).isEqualTo("advanced-order-platform");
    assertThat(attestation.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(attestation.readOnly()).isTrue();
    assertThat(attestation.executionAllowed()).isFalse();
    assertThat(attestation.ciRunAttestationEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-ci-run-attestation");
    assertThat(attestation.ciRunAttestationProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-ci-run-attestation.v1");
    assertThat(attestation.postPushCloseoutEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-post-push-closeout");
    assertThat(attestation.attestationItemCount()).isEqualTo(6);
    assertThat(attestation.attestationItems())
        .extracting(OpsShardReadinessRouteCleanupCiRunAttestationResponse.AttestationItem::name)
        .containsExactly(
            "post-push-closeout",
            "focused-tests-required",
            "full-suite-required",
            "github-actions-required",
            "cleanup-gate-required",
            "validation-step-count");
    assertThat(attestation.attestationItems())
        .allSatisfy(
            item -> {
              assertThat(item.required()).isTrue();
              assertThat(item.status()).isEqualTo("required");
            });
    assertThat(attestation.requirement()).contains("GitHub Actions");
    assertThat(attestation.status()).isEqualTo("passed");
  }
}
