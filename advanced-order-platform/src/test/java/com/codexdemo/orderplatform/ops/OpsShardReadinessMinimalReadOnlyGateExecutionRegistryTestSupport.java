package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport {

    private OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service() {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService();
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
        return service().registry();
    }
}
