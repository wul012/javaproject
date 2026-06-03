package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessRouteCleanupServiceFixtures {

    private OpsShardReadinessRouteCleanupServiceFixtures() {
    }

    static OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService() {
        return new OpsShardReadinessRouteCleanupPhaseSummaryService();
    }

    static OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService() {
        return new OpsShardReadinessRouteCleanupBoundaryMatrixService();
    }

    static OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService() {
        return new OpsShardReadinessRouteCleanupHandoffChecklistService(
                phaseSummaryService(),
                boundaryMatrixService()
        );
    }

    static OpsShardReadinessRouteCleanupArchivePlanService archivePlanService() {
        return new OpsShardReadinessRouteCleanupArchivePlanService();
    }

    static OpsShardReadinessRouteCleanupDigestService digestService() {
        return new OpsShardReadinessRouteCleanupDigestService();
    }

    static OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService() {
        return new OpsShardReadinessRouteCleanupSourcePlanAlignmentService();
    }

    static OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService() {
        return new OpsShardReadinessRouteCleanupReleaseHandoffService(
                handoffChecklistService(),
                archivePlanService(),
                digestService(),
                sourcePlanAlignmentService()
        );
    }

    static OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService() {
        return new OpsShardReadinessRouteCleanupOperatorRunbookService();
    }

    static OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService() {
        return new OpsShardReadinessRouteCleanupReadOnlyGateService(
                releaseHandoffService(),
                operatorRunbookService()
        );
    }

    static OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService() {
        return new OpsShardReadinessRouteCleanupSuiteCloseoutService(
                releaseHandoffService(),
                readOnlyGateService(),
                digestService()
        );
    }

    static OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService() {
        return new OpsShardReadinessRouteCleanupArchiveVerificationService(
                archivePlanService(),
                suiteCloseoutService()
        );
    }

    static OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService() {
        return new OpsShardReadinessRouteCleanupConsumerPacketService(
                readOnlyGateService(),
                archiveVerificationService()
        );
    }

    static OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService() {
        return new OpsShardReadinessRouteCleanupCiEvidenceService();
    }

    static OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService() {
        return new OpsShardReadinessRouteCleanupEndpointManifestService();
    }

    static OpsShardReadinessRouteCleanupRegressionGuardService regressionGuardService() {
        return new OpsShardReadinessRouteCleanupRegressionGuardService(
                endpointManifestService(),
                ciEvidenceService()
        );
    }

    static OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService() {
        return new OpsShardReadinessRouteCleanupHandoffBundleService(
                consumerPacketService(),
                ciEvidenceService(),
                regressionGuardService()
        );
    }

    static OpsShardReadinessRouteCleanupContinuityReportService continuityReportService() {
        return new OpsShardReadinessRouteCleanupContinuityReportService(
                endpointManifestService(),
                phaseSummaryService()
        );
    }

    static OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService() {
        return new OpsShardReadinessRouteCleanupConsumerChecklistService(
                consumerPacketService(),
                continuityReportService()
        );
    }

    static OpsShardReadinessRouteCleanupFinalDigestService finalDigestService() {
        return new OpsShardReadinessRouteCleanupFinalDigestService();
    }

    static OpsShardReadinessRouteCleanupExtendedCloseoutService extendedCloseoutService() {
        return new OpsShardReadinessRouteCleanupExtendedCloseoutService(
                handoffBundleService(),
                consumerChecklistService(),
                finalDigestService(),
                continuityReportService()
        );
    }
}
