package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveOperatorPackCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveOperatorPackCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
            .OperatorPackEntry> packs(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                            source
            ) {
        AtomicInteger order = new AtomicInteger(1);
        return source.signoffLanes().stream()
                .map(lane -> new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .OperatorPackEntry(
                                order.getAndIncrement(),
                                lane.owner(),
                                lane.evidence(),
                                lane.ready(),
                                lane.status()
                        ))
                .toList();
    }
}
