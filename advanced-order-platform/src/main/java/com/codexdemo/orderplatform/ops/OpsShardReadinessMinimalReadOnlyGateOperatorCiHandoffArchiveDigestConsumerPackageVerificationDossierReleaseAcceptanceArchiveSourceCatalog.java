package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveSourceCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveSourceCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
            .SourceArchiveSnapshot> snapshots(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                            source
            ) {
        return List.of(new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                .SourceArchiveSnapshot(
                        source.version(),
                        source.endpoint(),
                        source.profile(),
                        source.version(),
                        source.releaseAcceptanceState(),
                        source.readinessGateCount(),
                        source.signoffLaneCount(),
                        source.ciReplayLaneCount(),
                        source.boundaryControlCount(),
                        source.status()
                ));
    }
}
