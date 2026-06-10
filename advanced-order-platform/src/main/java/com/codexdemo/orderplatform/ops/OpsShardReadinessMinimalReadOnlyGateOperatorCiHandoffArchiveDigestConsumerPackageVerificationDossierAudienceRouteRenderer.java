package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .AudienceRoute> entries
            ) {
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport
                .section("Audience Routes", entries.stream()
                        .map(entry -> entry.audience()
                                + " -> " + entry.reviewerLane()
                                + " | owner=" + entry.owner()
                                + " | packet=" + entry.packet()
                                + " | status=" + entry.status())
                        .toList());
    }
}
