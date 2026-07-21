package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

public final class ArchiveTestData {

  private ArchiveTestData() {}

  public static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService
      sourceHandoffService() {
    return HandoffTestData.service();
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
