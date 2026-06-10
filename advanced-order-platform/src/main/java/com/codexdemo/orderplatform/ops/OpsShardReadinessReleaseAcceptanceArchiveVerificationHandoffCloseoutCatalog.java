package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCloseoutCatalog {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCloseoutCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff> handoffs(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                    source
    ) {
        return source.closeoutLedger().stream()
                .map(entry -> new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff(
                        entry.order(),
                        entry.item(),
                        entry.owner(),
                        entry.evidence(),
                        entry.ready(),
                        entry.status()
                ))
                .toList();
    }
}
