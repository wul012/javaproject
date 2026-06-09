package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneVerificationCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneVerificationCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            .OperatorLaneVerification> laneVerifications(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source
            ) {
        return source.operatorLanes().stream()
                .map(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneVerificationCatalog
                        ::lane)
                .toList();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            .OperatorLaneVerification lane(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .OperatorLane source
            ) {
        boolean archived = source.ready();
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                .OperatorLaneVerification(
                        source.lane(),
                        source.order(),
                        source.owner(),
                        source.ready(),
                        archived,
                        archived ? "passed" : "blocked"
                );
    }
}
