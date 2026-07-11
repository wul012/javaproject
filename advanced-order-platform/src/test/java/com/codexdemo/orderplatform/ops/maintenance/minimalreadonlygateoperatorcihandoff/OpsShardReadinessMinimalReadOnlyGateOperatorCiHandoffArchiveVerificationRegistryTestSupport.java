package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService;

public final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport() {}

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService
      sourceHandoffService() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
        new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
            new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()));
  }

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
        sourceHandoffService());
  }

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
      registry() {
    return service().registry();
  }
}
