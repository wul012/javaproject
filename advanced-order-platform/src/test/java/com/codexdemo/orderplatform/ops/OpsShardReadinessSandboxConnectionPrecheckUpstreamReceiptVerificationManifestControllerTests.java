package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionRoutePaths;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestControllerTests {

  private static
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService service() {
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService(
        OpsEvidenceServiceTestFixtures.readOnlyFixtureService(
            Mockito.mock(FailedEventSummaryService.class),
            Mockito.mock(OutboxRepository.class),
            Mockito.mock(IdempotencyStore.class)));
  }

  private static
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse manifest() {
    return service().manifest();
  }

  @Test
  void routeAndControllerExposeManifest() {
    assertThat(
            OpsShardReadinessSandboxConnectionRoutePaths
                .SANDBOX_CONNECTION_PRECHECK_UPSTREAM_RECEIPT_VERIFICATION_MANIFEST)
        .isEqualTo(
            OpsShardReadinessSandboxConnectionRoutePaths
                .SANDBOX_CONNECTION_PRECHECK_UPSTREAM_RECEIPT_VERIFICATION_MANIFEST);

    var response =
        new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController(
                service())
            .manifest();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest");
    assertThat(response.checks())
        .contains(
            "sandbox-connection-precheck-upstream-receipt-verification-manifest-source-plan-Node v2002",
            "sandbox-connection-precheck-upstream-receipt-verification-manifest-java-evidence-Java v99",
            "sandbox-connection-precheck-upstream-receipt-verification-manifest-ready-for-retention");
  }

  @Test
  void rendererListsExpectedSections() {
    var response = manifest();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Split Modules",
            "Evidence References",
            "Precheck Fields",
            "Boundary Guards",
            "Code Health Gates",
            "Verification Gates",
            "Handoff Notes");
  }
}
