package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService
            sourceHandoffService() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                        new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()
                )
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
            service() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
                sourceHandoffService()
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            registry() {
        return service().registry();
    }
}
