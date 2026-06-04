package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessRouteCleanupPostCompletionServiceFixtures {

    private OpsShardReadinessRouteCleanupPostCompletionServiceFixtures() {
    }

    static OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService() {
        return new OpsShardReadinessRouteCleanupPostPushCloseoutService(
                OpsShardReadinessRouteCleanupServiceFixtures.completionCertificateService(),
                OpsShardReadinessRouteCleanupServiceFixtures.ciEvidenceService()
        );
    }

    static OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService() {
        return new OpsShardReadinessRouteCleanupCiRunAttestationService(
                postPushCloseoutService(),
                OpsShardReadinessRouteCleanupServiceFixtures.ciEvidenceService()
        );
    }

    static OpsShardReadinessRouteCleanupTagManifestService tagManifestService() {
        return new OpsShardReadinessRouteCleanupTagManifestService(
                ciRunAttestationService()
        );
    }

    static OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService() {
        return new OpsShardReadinessRouteCleanupReleaseEvidenceBundleService(
                OpsShardReadinessRouteCleanupServiceFixtures.completionCertificateService(),
                ciRunAttestationService(),
                tagManifestService()
        );
    }

    static OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService() {
        return new OpsShardReadinessRouteCleanupConsumerSignoffPacketService(
                releaseEvidenceBundleService(),
                OpsShardReadinessRouteCleanupServiceFixtures.policyGuardService(),
                OpsShardReadinessRouteCleanupServiceFixtures.acceptanceReceiptService()
        );
    }
}
