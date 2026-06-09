package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                            .DigestSection> sections
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("digest-section-count=" + sections.size());
        sections.forEach(section -> lines.add(section.name()
                + "="
                + section.sourcePassed()
                + "/"
                + section.sourceTotal()
                + " | "
                + section.evidence()
                + " | status="
                + section.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport.section(
                "Digest Sections",
                lines
        );
    }
}
