package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupFinalArchivePlanServiceTests {

    @Test
    void buildsFinalArchivePlanFromFinalVerificationAndManifest() {
        OpsShardReadinessRouteCleanupFinalArchivePlanResponse plan =
                new OpsShardReadinessRouteCleanupFinalArchivePlanService(
                        finalVerificationService(),
                        OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService()
                ).plan();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(382);
        assertThat(plan.project()).isEqualTo("advanced-order-platform");
        assertThat(plan.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(plan.readOnly()).isTrue();
        assertThat(plan.executionAllowed()).isFalse();
        assertThat(plan.finalArchivePlanEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-archive-plan");
        assertThat(plan.finalArchivePlanProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-final-archive-plan.v1");
        assertThat(plan.finalVerificationEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-verification");
        assertThat(plan.sourceEndpointCount()).isGreaterThanOrEqualTo(28);
        assertThat(plan.archiveStepCount()).isEqualTo(6);
        assertThat(plan.archiveSteps())
                .extracting(OpsShardReadinessRouteCleanupFinalArchivePlanResponse.ArchiveStep::name)
                .containsExactly(
                        "capture-final-verification",
                        "freeze-endpoint-manifest",
                        "archive-evidence-catalog",
                        "record-ci-result-after-push",
                        "cleanup-generated-target",
                        "keep-node-workspace-untouched"
                );
        assertThat(plan.archiveSteps())
                .allSatisfy(step -> assertThat(step.status()).isEqualTo("planned"));
        assertThat(plan.decision()).isEqualTo("archive-plan-ready-after-ci");
        assertThat(plan.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService() {
        return new OpsShardReadinessRouteCleanupFinalVerificationService(
                transitionBriefService(),
                reviewerPacketService(),
                OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService()
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
