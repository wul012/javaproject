package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.ConsumerPackageTestData;

public final class DossierTestData {

  private DossierTestData() {}

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService(
        ConsumerPackageTestData.service());
  }

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
      registry() {
    return service().registry();
  }
}
