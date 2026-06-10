package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceDigestCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceDigestCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            .SourceDigestSnapshot> snapshots(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source
            ) {
        return List.of(new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                .SourceDigestSnapshot(
                        source.version(),
                        source.endpoint(),
                        source.profile(),
                        source.sourceArchiveVersion(),
                        source.digestState(),
                        source.digestSectionCount(),
                        source.consumerPacketCount(),
                        source.replayInstructionCount(),
                        source.boundaryLockCount(),
                        source.status()
                ));
    }
}
