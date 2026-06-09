package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport {

    private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry() {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService().registry();
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService service() {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            registry() {
        return service().registry();
    }
}
