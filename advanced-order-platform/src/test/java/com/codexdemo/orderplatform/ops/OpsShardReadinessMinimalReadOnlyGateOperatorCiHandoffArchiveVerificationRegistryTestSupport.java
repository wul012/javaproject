package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService
      sourceHandoffService() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
        new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
            new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()));
  }

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
        sourceHandoffService());
  }

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
      registry() {
    return service().registry();
  }
}
