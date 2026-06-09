package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                            .SourceArchiveSnapshot> sources
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("source-archive-count=" + sources.size());
        sources.forEach(source -> lines.add(source.version()
                + " | "
                + source.endpoint()
                + " | "
                + source.archiveState()
                + " | status="
                + source.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport.section(
                "Source Archive",
                lines
        );
    }
}
