package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupConsumerChecklistServiceTests {

    @Test
    void buildsConsumerChecklistFromPacketAndContinuityReport() {
        OpsShardReadinessRouteCleanupConsumerChecklistResponse checklist =
                new OpsShardReadinessRouteCleanupConsumerChecklistService(
                        consumerPacketService(),
                        new OpsShardReadinessRouteCleanupContinuityReportService(
                                new OpsShardReadinessRouteCleanupEndpointManifestService(),
                                new OpsShardReadinessRouteCleanupPhaseSummaryService()
                        )
                ).checklist();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(360);
        assertThat(checklist.project()).isEqualTo("advanced-order-platform");
        assertThat(checklist.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(checklist.readOnly()).isTrue();
        assertThat(checklist.executionAllowed()).isFalse();
        assertThat(checklist.checklistProfile()).isEqualTo("java-shard-readiness-route-cleanup-consumer-checklist.v1");
        assertThat(checklist.itemCount()).isEqualTo(4);
        assertThat(checklist.items())
                .extracting(OpsShardReadinessRouteCleanupConsumerChecklistResponse.ChecklistItem::name)
                .containsExactly(
                        "packet-passed",
                        "continuity-passed",
                        "read-only-boundary-held",
                        "blocked-operations-present"
                );
        assertThat(checklist.items())
                .allSatisfy(item -> {
                    assertThat(item.passed()).isTrue();
                    assertThat(item.status()).isEqualTo("passed");
                });
        assertThat(checklist.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService() {
        return new OpsShardReadinessRouteCleanupConsumerPacketService(
                new OpsShardReadinessRouteCleanupReadOnlyGateService(
                        releaseHandoffService(),
                        new OpsShardReadinessRouteCleanupOperatorRunbookService()
                ),
                new OpsShardReadinessRouteCleanupArchiveVerificationService(
                        new OpsShardReadinessRouteCleanupArchivePlanService(),
                        suiteCloseoutService()
                )
        );
    }

    private OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService() {
        return new OpsShardReadinessRouteCleanupSuiteCloseoutService(
                releaseHandoffService(),
                new OpsShardReadinessRouteCleanupReadOnlyGateService(
                        releaseHandoffService(),
                        new OpsShardReadinessRouteCleanupOperatorRunbookService()
                ),
                new OpsShardReadinessRouteCleanupDigestService()
        );
    }

    private OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService() {
        return new OpsShardReadinessRouteCleanupReleaseHandoffService(
                new OpsShardReadinessRouteCleanupHandoffChecklistService(
                        new OpsShardReadinessRouteCleanupPhaseSummaryService(),
                        new OpsShardReadinessRouteCleanupBoundaryMatrixService()
                ),
                new OpsShardReadinessRouteCleanupArchivePlanService(),
                new OpsShardReadinessRouteCleanupDigestService(),
                new OpsShardReadinessRouteCleanupSourcePlanAlignmentService()
        );
    }
}
