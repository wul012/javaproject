package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceArchiveCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceArchiveCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .SourceArchiveSnapshot> snapshots(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            source
            ) {
        return List.of(new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                .SourceArchiveSnapshot(
                        source.version(),
                        source.endpoint(),
                        source.profile(),
                        source.sourceHandoffVersion(),
                        source.archiveState(),
                        source.artifactVerificationCount(),
                        source.operatorLaneVerificationCount(),
                        source.ciBatchVerificationCount(),
                        source.boundaryVerificationCount(),
                        source.status()
                ));
    }
}
