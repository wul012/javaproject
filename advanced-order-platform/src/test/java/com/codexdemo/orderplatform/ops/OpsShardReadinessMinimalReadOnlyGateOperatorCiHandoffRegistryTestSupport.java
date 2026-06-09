package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
            sourceArchiveService() {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService service() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
                sourceArchiveService()
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse registry() {
        return service().registry();
    }
}
