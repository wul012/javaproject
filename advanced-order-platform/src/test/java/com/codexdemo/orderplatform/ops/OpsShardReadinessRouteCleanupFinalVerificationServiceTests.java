package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupFinalVerificationServiceTests {

    @Test
    void buildsFinalVerificationFromBriefPacketAndDigest() {
        OpsShardReadinessRouteCleanupFinalVerificationResponse verification =
                new OpsShardReadinessRouteCleanupFinalVerificationService(
                        transitionBriefService(),
                        reviewerPacketService(),
                        OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService()
                ).verification();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(380);
        assertThat(verification.project()).isEqualTo("advanced-order-platform");
        assertThat(verification.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(verification.readOnly()).isTrue();
        assertThat(verification.executionAllowed()).isFalse();
        assertThat(verification.finalVerificationEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-verification");
        assertThat(verification.finalVerificationProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-final-verification.v1");
        assertThat(verification.verificationCount()).isEqualTo(5);
        assertThat(verification.verifications())
                .extracting(OpsShardReadinessRouteCleanupFinalVerificationResponse.Verification::name)
                .containsExactly(
                        "transition-brief",
                        "reviewer-packet",
                        "final-digest",
                        "versions-continuous",
                        "read-only-boundary"
                );
        assertThat(verification.verifications())
                .allSatisfy(item -> assertThat(item.status()).isEqualTo("passed"));
        assertThat(verification.digestValue()).matches("[0-9a-f]{64}");
        assertThat(verification.decision()).isEqualTo("final-verification-ready-for-archive-plan");
        assertThat(verification.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService() {
        return new OpsShardReadinessRouteCleanupTransitionBriefService(
                reviewerPacketService(),
                operationalSnapshotService(),
                policyGuardService()
        );
    }

    private OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService() {
        return new OpsShardReadinessRouteCleanupReviewerPacketService(
                evidenceRegisterService(),
                acceptanceReceiptService(),
                policyGuardService()
        );
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

    private OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService() {
        return new OpsShardReadinessRouteCleanupOperationalSnapshotService(
                OpsShardReadinessRouteCleanupServiceFixtures.continuityReportService(),
                OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                acceptanceReceiptService()
        );
    }

    private OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService() {
        return new OpsShardReadinessRouteCleanupPolicyGuardService(
                operationalSnapshotService(),
                evidenceRegisterService()
        );
    }
}
