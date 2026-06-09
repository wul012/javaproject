package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .BoundaryLock> locks(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            source
            ) {
        return source.boundaryVerifications().stream()
                .map(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockCatalog
                        ::lock)
                .toList();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .BoundaryLock lock(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .BoundaryVerification source
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                .BoundaryLock(
                        source.code(),
                        source.lockedBehavior(),
                        source.locked(),
                        source.archived()
                                ? "archived boundary remains locked"
                                : "missing archived boundary lock"
                );
    }
}
