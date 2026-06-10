package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceCatalog {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.SourceArchiveSnapshot>
            snapshots(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                            source
            ) {
        return List.of(new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.SourceArchiveSnapshot(
                source.version(),
                source.endpoint(),
                source.profile(),
                source.archiveRegistryState(),
                source.artifactManifestCount(),
                source.routePackageCount(),
                source.operatorPackCount(),
                source.ciAttestationCount(),
                source.boundarySealCount(),
                source.status()
        ));
    }
}
