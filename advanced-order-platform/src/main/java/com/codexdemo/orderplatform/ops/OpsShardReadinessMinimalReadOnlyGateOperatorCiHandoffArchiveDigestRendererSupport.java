package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .MarkdownSection section(
                    String heading,
                    List<String> lines
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                .MarkdownSection(
                        heading,
                        List.copyOf(lines)
                );
    }
}
