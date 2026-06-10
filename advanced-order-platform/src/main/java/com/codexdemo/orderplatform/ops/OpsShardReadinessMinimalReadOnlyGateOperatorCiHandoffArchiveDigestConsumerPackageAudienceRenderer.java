package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            .ConsumerAudience> audiences
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("consumer-audience-count=" + audiences.size());
        audiences.forEach(audience -> lines.add(audience.audience()
                + " | "
                + audience.owner()
                + " | packet="
                + audience.packet()
                + " | status="
                + audience.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
                .section("Consumer Audiences", lines);
    }
}
