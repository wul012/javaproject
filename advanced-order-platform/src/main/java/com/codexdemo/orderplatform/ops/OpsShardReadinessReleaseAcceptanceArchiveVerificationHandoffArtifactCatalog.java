package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactCatalog {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck>
            crossChecks(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                            source
            ) {
        return source.artifactManifest().stream()
                .map(entry -> new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .ArtifactCrossCheck(
                                entry.name(),
                                entry.value(),
                                entry.required() ? "required-present" : "optional",
                                "passed".equals(entry.status()),
                                entry.status()
                        ))
                .toList();
    }
}
