package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
            sourceArchiveService() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
                        new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                                new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()
                        )
                )
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
            service() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService(
                sourceArchiveService()
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            registry() {
        return service().registry();
    }
}
