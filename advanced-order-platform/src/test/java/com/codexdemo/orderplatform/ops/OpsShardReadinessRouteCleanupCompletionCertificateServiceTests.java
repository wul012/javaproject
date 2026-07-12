package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupCompletionCertificateServiceTests {

  @Test
  void buildsReadOnlyCompletionCertificateFromCloseoutEvidence() {
    OpsShardReadinessRouteCleanupCompletionCertificateResponse certificate =
        OpsShardReadinessRouteCleanupServiceFixtures.completionCertificateService().certificate();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(388);
    assertThat(certificate.project()).isEqualTo("advanced-order-platform");
    assertThat(certificate.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(certificate.readOnly()).isTrue();
    assertThat(certificate.executionAllowed()).isFalse();
    assertThat(certificate.completionCertificateEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-completion-certificate");
    assertThat(certificate.completionCertificateProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-completion-certificate.v1");
    assertThat(certificate.completionIndexEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-completion-index");
    assertThat(certificate.thirdRunCloseoutEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-third-run-closeout");
    assertThat(certificate.finalArchivePlanEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-archive-plan");
    assertThat(certificate.certificateClaimCount()).isEqualTo(6);
    assertThat(certificate.certificateClaims())
        .extracting(
            OpsShardReadinessRouteCleanupCompletionCertificateResponse.CertificateClaim::name)
        .containsExactly(
            "completion-index",
            "third-run-closeout",
            "final-archive-plan",
            "read-only-boundary",
            "version-continuity",
            "execution-disabled");
    assertThat(certificate.certificateClaims())
        .allSatisfy(claim -> assertThat(claim.status()).isEqualTo("passed"));
    assertThat(certificate.certificateId()).startsWith("java-route-cleanup-completion-v");
    assertThat(certificate.decision()).isEqualTo("completion-certificate-ready");
    assertThat(certificate.status()).isEqualTo("passed");
  }
}
