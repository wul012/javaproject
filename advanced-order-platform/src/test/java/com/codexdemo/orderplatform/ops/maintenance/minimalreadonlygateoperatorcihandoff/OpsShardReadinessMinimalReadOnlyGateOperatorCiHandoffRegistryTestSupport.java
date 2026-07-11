package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService;

public final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport() {}

  public static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
      sourceArchiveService() {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
        new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService());
  }

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
        sourceArchiveService());
  }

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse registry() {
    return service().registry();
  }
}
