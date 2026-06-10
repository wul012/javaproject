package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierBoundaryAuditCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierBoundaryAuditCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .BoundaryAudit> audits(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            source
            ) {
        return source.boundaryLocks().stream()
                .map(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierBoundaryAuditCatalog
                        ::audit)
                .toList();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .BoundaryAudit audit(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            .BoundaryLock source
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                .BoundaryAudit(
                        source.code(),
                        source.lockedBehavior(),
                        source.locked(),
                        "consumer-package-boundary-lock:" + source.reason(),
                        source.locked() ? "passed" : "blocked"
                );
    }
}
