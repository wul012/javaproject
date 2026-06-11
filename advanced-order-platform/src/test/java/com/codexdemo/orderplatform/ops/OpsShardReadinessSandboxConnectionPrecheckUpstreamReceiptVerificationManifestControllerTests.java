package com.codexdemo.orderplatform.ops;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestControllerTests {

    @Test
    void routeAndControllerExposeManifest() {
        assertThat(OpsShardReadinessSandboxConnectionRoutePaths
                .SANDBOX_CONNECTION_PRECHECK_UPSTREAM_RECEIPT_VERIFICATION_MANIFEST)
                .isEqualTo(OpsShardReadinessRoutePaths
                        .SANDBOX_CONNECTION_PRECHECK_UPSTREAM_RECEIPT_VERIFICATION_MANIFEST);

        var response =
                new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController(
                        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport
                                .service()
                ).manifest();

        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest");
        assertThat(response.checks()).contains(
                "sandbox-connection-precheck-upstream-receipt-verification-manifest-source-plan-Node v2002",
                "sandbox-connection-precheck-upstream-receipt-verification-manifest-java-evidence-Java v99",
                "sandbox-connection-precheck-upstream-receipt-verification-manifest-ready-for-retention"
        );
    }

    @Test
    void rendererListsExpectedSections() {
        var response =
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport.manifest();

        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Source Receipt",
                        "Split Modules",
                        "Evidence References",
                        "Precheck Fields",
                        "Boundary Guards",
                        "Code Health Gates",
                        "Verification Gates",
                        "Handoff Notes"
                );
    }
}
