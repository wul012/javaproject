package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffBoundaryCatalog {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffBoundaryCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard> guards(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    source
    ) {
        return source.boundarySeals().stream()
                .map(seal -> new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard(
                        seal.code(),
                        seal.lockedBehavior(),
                        seal.auditEvidence(),
                        seal.locked(),
                        seal.status()
                ))
                .toList();
    }
}
