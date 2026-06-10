package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service() {
        return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
                        .service()
        );
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse registry() {
        return service().registry();
    }
}
