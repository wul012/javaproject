package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService;

public final class ArchiveDigestTestData {

  private ArchiveDigestTestData() {}

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
      sourceArchiveService() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
            new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService())));
  }

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService(
        sourceArchiveService());
  }

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
      registry() {
    return service().registry();
  }
}
