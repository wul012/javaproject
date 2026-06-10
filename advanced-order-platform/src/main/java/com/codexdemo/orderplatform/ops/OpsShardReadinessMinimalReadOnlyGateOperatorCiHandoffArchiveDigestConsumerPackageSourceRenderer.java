package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            .SourceDigestSnapshot> sources
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("source-digest-count=" + sources.size());
        sources.forEach(source -> lines.add(source.version()
                + " | "
                + source.endpoint()
                + " | "
                + source.digestState()
                + " | status="
                + source.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
                .section("Source Digest", lines);
    }
}
