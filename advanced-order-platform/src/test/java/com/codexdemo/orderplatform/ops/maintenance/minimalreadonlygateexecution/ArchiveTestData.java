package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

public final class ArchiveTestData {

  private ArchiveTestData() {}

  public static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry() {
    return ExecutionTestData.registry();
  }

  public static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
        ExecutionTestData.service());
  }

  public static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
      registry() {
    return service().registry();
  }
}
