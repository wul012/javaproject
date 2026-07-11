package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService;

public final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport() {}

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
      sourceDigestService() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService(
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
            new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                    new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()))));
  }

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService(
        sourceDigestService());
  }

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
      registry() {
    return service().registry();
  }
}
