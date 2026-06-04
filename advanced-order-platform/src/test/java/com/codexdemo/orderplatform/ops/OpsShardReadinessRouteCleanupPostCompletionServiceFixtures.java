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
}
