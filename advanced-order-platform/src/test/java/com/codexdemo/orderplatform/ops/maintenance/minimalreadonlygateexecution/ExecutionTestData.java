package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

public final class ExecutionTestData {

  private ExecutionTestData() {}

  public static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service() {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService();
  }

  public static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
    return service().registry();
  }
}
