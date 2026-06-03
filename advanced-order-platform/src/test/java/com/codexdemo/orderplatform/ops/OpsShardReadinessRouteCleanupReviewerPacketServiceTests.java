package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupReviewerPacketServiceTests {

    @Test
    void buildsReviewerPacketFromRegisterReceiptAndPolicyGuard() {
        OpsShardReadinessRouteCleanupReviewerPacketResponse packet =
                new OpsShardReadinessRouteCleanupReviewerPacketService(
                        evidenceRegisterService(),
                        acceptanceReceiptService(),
                        policyGuardService()
                ).packet();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(376);
        assertThat(packet.project()).isEqualTo("advanced-order-platform");
        assertThat(packet.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(packet.readOnly()).isTrue();
        assertThat(packet.executionAllowed()).isFalse();
        assertThat(packet.reviewerPacketEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-reviewer-packet");
        assertThat(packet.reviewerPacketProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-reviewer-packet.v1");
        assertThat(packet.sourceCount()).isEqualTo(3);
        assertThat(packet.sources())
                .contains(
                        "/api/v1/ops/shard-readiness/route-cleanup-evidence-register",
                        "/api/v1/ops/shard-readiness/route-cleanup-acceptance-receipt",
                        "/api/v1/ops/shard-readiness/route-cleanup-policy-guard"
                );
        assertThat(packet.reviewerCheckCount()).isEqualTo(4);
        assertThat(packet.reviewerChecks())
                .extracting(OpsShardReadinessRouteCleanupReviewerPacketResponse.ReviewerCheck::name)
                .containsExactly(
                        "registered-evidence",
                        "acceptance-receipt",
                        "policy-boundary",
                        "latest-version"
                );
        assertThat(packet.reviewerChecks())
                .allSatisfy(check -> assertThat(check.status()).isEqualTo("passed"));
        assertThat(packet.summary()).contains("read-only handoff");
        assertThat(packet.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService() {
        return new OpsShardReadinessRouteCleanupEvidenceRegisterService(
                OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService()
        );
    }

    private OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService() {
        return new OpsShardReadinessRouteCleanupAcceptanceReceiptService(
                new OpsShardReadinessRouteCleanupAuditTrailService(),
                OpsShardReadinessRouteCleanupServiceFixtures.extendedCloseoutService()
        );
    }

    private OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService() {
        return new OpsShardReadinessRouteCleanupPolicyGuardService(
                new OpsShardReadinessRouteCleanupOperationalSnapshotService(
                        OpsShardReadinessRouteCleanupServiceFixtures.continuityReportService(),
                        OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                        acceptanceReceiptService()
                ),
                evidenceRegisterService()
        );
    }
}
