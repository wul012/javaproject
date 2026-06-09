package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .SourceHandoffSnapshot> sources
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("source-handoff-count=" + sources.size());
        sources.forEach(source -> lines.add(source.version()
                + " | "
                + source.endpoint()
                + " | "
                + source.handoffState()
                + " | status="
                + source.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.section(
                "Source Handoff",
                lines
        );
    }
}
