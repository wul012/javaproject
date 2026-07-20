package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.ReleaseAcceptanceTestData;

public final class ArchiveTestData {

  private ArchiveTestData() {}

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryService
      service() {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryService(
        ReleaseAcceptanceTestData.service());
  }

  public static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
      registry() {
    return service().registry();
  }
}
