package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .CiLane> entries
            ) {
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport
                .section("CI Lanes", entries.stream()
                        .map(entry -> entry.order()
                                + ". " + entry.batch()
                                + " | command=" + entry.commandFamily()
                                + " | replayGroup=" + entry.replayGroup()
                                + " | readOnly=" + entry.readOnly()
                                + " | status=" + entry.status())
                        .toList());
    }
}
