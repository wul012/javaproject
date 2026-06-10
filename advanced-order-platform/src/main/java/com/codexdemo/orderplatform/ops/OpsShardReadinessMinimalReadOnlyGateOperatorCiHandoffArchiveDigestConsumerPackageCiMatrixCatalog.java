package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            .CiMatrixEntry> matrix(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source
            ) {
        return source.replayInstructions().stream()
                .map(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixCatalog
                        ::entry)
                .toList();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            .CiMatrixEntry entry(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                            .ReplayInstruction source
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                .CiMatrixEntry(
                        source.order(),
                        source.batch(),
                        source.commandFamily(),
                        source.readOnly(),
                        source.sourcePassed(),
                        source.readOnly() && source.sourcePassed() ? "passed" : "blocked"
                );
    }
}
