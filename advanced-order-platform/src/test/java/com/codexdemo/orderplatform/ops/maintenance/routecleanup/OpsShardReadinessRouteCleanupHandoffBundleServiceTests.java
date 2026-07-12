package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupHandoffBundleServiceTests {

  @Test
  void buildsBundleFromConsumerPacketCiEvidenceAndRegressionGuard() {
    OpsShardReadinessRouteCleanupHandoffBundleResponse bundle =
        new OpsShardReadinessRouteCleanupHandoffBundleService(
                new OpsShardReadinessRouteCleanupConsumerPacketService(
                    new OpsShardReadinessRouteCleanupReadOnlyGateService(
                        releaseHandoffService(),
                        new OpsShardReadinessRouteCleanupOperatorRunbookService()),
                    new OpsShardReadinessRouteCleanupArchiveVerificationService(
                        new OpsShardReadinessRouteCleanupArchivePlanService(),
                        suiteCloseoutService())),
                new OpsShardReadinessRouteCleanupCiEvidenceService(),
                new OpsShardReadinessRouteCleanupRegressionGuardService(
                    new OpsShardReadinessRouteCleanupEndpointManifestService(),
                    new OpsShardReadinessRouteCleanupCiEvidenceService()))
            .bundle();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(356);
    assertThat(bundle.project()).isEqualTo("advanced-order-platform");
    assertThat(bundle.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(bundle.readOnly()).isTrue();
    assertThat(bundle.executionAllowed()).isFalse();
    assertThat(bundle.bundleEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-handoff-bundle");
    assertThat(bundle.bundleProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-handoff-bundle.v1");
    assertThat(bundle.componentCount()).isEqualTo(3);
    assertThat(bundle.components())
        .extracting(OpsShardReadinessRouteCleanupHandoffBundleResponse.BundleComponent::name)
        .containsExactly("consumer-packet", "ci-evidence", "regression-guard");
    assertThat(bundle.components())
        .allSatisfy(component -> assertThat(component.status()).isEqualTo("passed"));
    assertThat(bundle.decision()).isEqualTo("bundle-ready-for-read-only-consumer");
    assertThat(bundle.status()).isEqualTo("passed");
  }

  private OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService() {
    return new OpsShardReadinessRouteCleanupSuiteCloseoutService(
        releaseHandoffService(),
        new OpsShardReadinessRouteCleanupReadOnlyGateService(
            releaseHandoffService(), new OpsShardReadinessRouteCleanupOperatorRunbookService()),
        new OpsShardReadinessRouteCleanupDigestService());
  }

  private OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService() {
    return new OpsShardReadinessRouteCleanupReleaseHandoffService(
        new OpsShardReadinessRouteCleanupHandoffChecklistService(
            new OpsShardReadinessRouteCleanupPhaseSummaryService(),
            new OpsShardReadinessRouteCleanupBoundaryMatrixService()),
        new OpsShardReadinessRouteCleanupArchivePlanService(),
        new OpsShardReadinessRouteCleanupDigestService(),
        new OpsShardReadinessRouteCleanupSourcePlanAlignmentService());
  }
}
