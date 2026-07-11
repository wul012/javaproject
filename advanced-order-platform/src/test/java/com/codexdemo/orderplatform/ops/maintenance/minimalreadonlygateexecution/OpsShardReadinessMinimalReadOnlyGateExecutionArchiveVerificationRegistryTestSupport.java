package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

public final
class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport {

  private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport() {}

  public static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry() {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService().registry();
  }

  public static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
        new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService());
  }

  public static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
      registry() {
    return service().registry();
  }
}
