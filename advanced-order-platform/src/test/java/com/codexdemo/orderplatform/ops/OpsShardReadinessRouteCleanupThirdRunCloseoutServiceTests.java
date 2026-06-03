package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupThirdRunCloseoutServiceTests {

    @Test
    void closesThirdRouteCleanupHandoffRunFromV366() {
        OpsShardReadinessRouteCleanupThirdRunCloseoutResponse closeout =
                new OpsShardReadinessRouteCleanupThirdRunCloseoutService(
                        finalVerificationService(),
                        finalArchivePlanService(),
                        acceptanceReceiptService()
                ).closeout();

        int latestVersion = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();

        assertThat(latestVersion).isGreaterThanOrEqualTo(384);
        assertThat(closeout.project()).isEqualTo("advanced-order-platform");
        assertThat(closeout.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(closeout.readOnly()).isTrue();
        assertThat(closeout.executionAllowed()).isFalse();
        assertThat(closeout.thirdRunCloseoutEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-third-run-closeout");
        assertThat(closeout.thirdRunCloseoutProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-third-run-closeout.v1");
        assertThat(closeout.firstVersion()).isEqualTo(366);
        assertThat(closeout.latestVersion()).isEqualTo(latestVersion);
        assertThat(closeout.versionCount()).isEqualTo(latestVersion - 365);
        assertThat(closeout.versionCount()).isGreaterThanOrEqualTo(19);
        assertThat(closeout.finalVerificationEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-verification");
        assertThat(closeout.finalArchivePlanEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-archive-plan");
        assertThat(closeout.readinessEvidenceCount()).isEqualTo(5);
        assertThat(closeout.readinessEvidence())
                .contains(
                        "final-verification:passed",
                        "archive-plan:passed",
                        "acceptance-receipt:passed",
                        "boundary:passed",
                        "continuity:true"
                );
        assertThat(closeout.decision()).isEqualTo("third-run-closeout-ready");
        assertThat(closeout.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService() {
        return new OpsShardReadinessRouteCleanupFinalVerificationService(
                transitionBriefService(),
                reviewerPacketService(),
                OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService()
        );
    }

    private OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService() {
        return new OpsShardReadinessRouteCleanupFinalArchivePlanService(
                finalVerificationService(),
                OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService()
        );
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
