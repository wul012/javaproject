package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionRenderer {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard> retentions
    ) {
        List<String> lines = new ArrayList<>();
        lines.add("retention-guard-count=" + retentions.size());
        retentions.forEach(retention -> lines.add(String.join(
                " | ",
                retention.name(),
                retention.sourceEvidence(),
                retention.retentionWindow(),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .flag("ready", retention.ready()),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(retention.status())
        )));
        return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .section("Retention Guards", lines);
    }
}
