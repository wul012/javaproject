package com.codexdemo.orderplatform.ops;

public final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport() {}

  public static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service() {
    return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService(
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
            .service());
  }

  public static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse registry() {
    return service().registry();
  }
}
