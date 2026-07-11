package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

public final class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport {

  private OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport() {}

  public static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service() {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService();
  }

  public static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
    return service().registry();
  }
}
