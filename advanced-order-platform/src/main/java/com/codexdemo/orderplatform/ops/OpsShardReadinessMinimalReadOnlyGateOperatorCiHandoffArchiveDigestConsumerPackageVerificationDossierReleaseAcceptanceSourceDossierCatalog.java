package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSourceDossierCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSourceDossierCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
            .SourceDossierSnapshot> snapshots(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            source
            ) {
        return List.of(new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                .SourceDossierSnapshot(
                        source.version(),
                        source.endpoint(),
                        source.profile(),
                        source.verificationDossierState(),
                        source.sectionDigestCount(),
                        source.audienceRouteCount(),
                        source.ciLaneCount(),
                        source.boundaryAuditCount(),
                        source.handoffReceiptCount(),
                        source.status()
                ));
    }
}
